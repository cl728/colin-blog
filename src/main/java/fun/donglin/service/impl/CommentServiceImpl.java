package fun.donglin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.donglin.entity.Comment;
import fun.donglin.entity.MyComment;
import fun.donglin.mapper.BlogMapper;
import fun.donglin.mapper.CommentMapper;
import fun.donglin.search.PageResult;
import fun.donglin.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BlogMapper blogMapper;

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplyComments = new ArrayList<>();

    @Override
    public PageResult<Comment> findByPage(Integer page, Integer rows, Boolean filterAdminComment, Long blogId) {
        PageHelper.startPage( page, rows );
        Boolean adminComment = filterAdminComment != null && filterAdminComment ? false : null;
        List<Comment> comments;
        if (blogId != null && blogId != -1) {
            comments = this.commentMapper.findListByBlogId( blogId, adminComment );
        } else {
            comments = this.commentMapper.findAll( adminComment );
        }
        PageInfo<Comment> pageInfo = new PageInfo<>( comments );
        return new PageResult<>( pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList() );
    }

    @Override
    public List<Comment> findByBlogId(Long blogId) {
        List<Comment> comments = this.commentMapper.findByBlogId( blogId );
        return eachComment( comments );
    }

    @Override
    public Integer findCommentCount(Long blogId) {
        return this.commentMapper.findCountByBlogId( blogId );
    }

    @Override
    @Transactional
    public boolean save(MyComment comment) {
        try {
            // 查询 blog，赋值到 comment 中
            comment.setBlog( this.blogMapper.findById( comment.getBlogId() ) );

            // 查询 parentComment，赋值到 comment 中
            if (comment.getParentCommentId() != -1) {
                comment.setParentComment( this.commentMapper.findById( comment.getParentCommentId() ) );
            } else {
                comment.setParentComment( null );
            }


            // 向 comment 表添加数据
            this.commentMapper.insertSelective( comment );
            // 关联 blog
            this.commentMapper.updateBlogId( comment.getBlogId() );
            // 关联 parentComment
            if (comment.getParentCommentId() != -1) {
                this.commentMapper.updateParentCommentId( comment.getId(), comment.getParentCommentId() );
            }
        } catch (Exception e) {
            LOGGER.error( "保存评论信息错误: {}", e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void delById(Long id) {
        Comment comment = this.commentMapper.findById( id );
        // 如果当前评论有回复评论，需先将回复评论删除
        List<Comment> replyComments = comment.getReplyComments();
        if (!CollectionUtils.isEmpty( replyComments )) {
            replyComments.forEach( replyComment -> this.delById( replyComment.getId() ) );
        }
        this.commentMapper.deleteByPrimaryKey( id );
    }

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties( comment, c );
            commentsView.add( c );
        }
        // 合并评论的各层子代到第一级子代集合中
        combineChildren( commentsView );
        return commentsView;
    }

    /**
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replyComments = comment.getReplyComments();
            for (Comment reply1 : replyComments) {
                //循环迭代，找出子代，存放在 tempReplyComments 中
                recursively( reply1 );
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments( tempReplyComments );
            //清除临时存放区
            tempReplyComments = new ArrayList<>();
        }
    }

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        if (!tempReplyComments.contains( comment )) {
            tempReplyComments.add( comment );// 顶节点添加到临时存放集合
        }
        if (!CollectionUtils.isEmpty( comment.getReplyComments() )) {
            List<Comment> replyList = comment.getReplyComments();
            for (Comment reply : replyList) {
                tempReplyComments.add( reply );
                if (reply.getReplyComments().size() > 0) {
                    recursively( reply );
                }
            }
        }
    }
}
