package fun.donglin.mapper;

import fun.donglin.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommentMapper extends Mapper<Comment> {

    @Results(id = "commentMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "admin_comment", property = "adminComment"),
            @Result(column = "blog_id", property = "blog",
                    one = @One(select = "fun.donglin.mapper.BlogMapper.findById", fetchType = FetchType.EAGER)),
            @Result(column = "parent_comment_id", property = "parentComment",
                    one = @One(select = "fun.donglin.mapper.CommentMapper.findParentByParentId", fetchType = FetchType.EAGER)),
            @Result(column = "id", property = "replyComments",
                    many = @Many(select = "fun.donglin.mapper.CommentMapper.findReplyListByCid", fetchType = FetchType.LAZY))
    })
    @Select("select * from t_comment where blog_id = #{blogId} and parent_comment_id is null")
    List<Comment> findByBlogId(Long blogId);

    @ResultMap("commentMap")
    @Select("<script>" +
            "select * from t_comment <where>" +
            "blog_id = #{blogId}" +
            "<if test='adminComment!=null'> and admin_comment = #{adminComment}</if>" +
            "</where>" +
            "</script>")
    List<Comment> findListByBlogId(Long blogId, Boolean adminComment);

    @ResultMap("commentMap")
    @Select("select * from t_comment where id = #{id}")
    Comment findById(Long id);

    @Results(@Result(column = "create_time", property = "createTime"))
    @Select("select * from t_comment where id = #{parentCommentId}")
    Comment findParentByParentId(Long parentCommentId);

    @ResultMap("commentMap")
    @Select("select t1.* from t_comment t1, t_comment t2 where t1.parent_comment_id = t2.id and t2.id = #{id}")
    List<Comment> findReplyListByCid(Long id);

    @Update("update t_comment set blog_id = #{blogId}")
    void updateBlogId(Long blogId);

    @Update("update t_comment set parent_comment_id = #{parentCommentId} where id = #{id}")
    void updateParentCommentId(@Param("id") Long id, @Param("parentCommentId") Long parentCommentId);

    @Select("select count(id) from t_comment where blog_id = #{blogId}")
    Integer findCountByBlogId(Long blogId);

    @ResultMap("commentMap")
    @Select("<script>" +
            "select * from t_comment <where>" +
            "<if test='adminComment!=null'> and admin_comment = #{adminComment}</if>" +
            "</where>" +
            "</script>")
    List<Comment> findAll(Boolean adminComment);
}
