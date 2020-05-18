package fun.donglin.web.admin;

import fun.donglin.entity.Comment;
import fun.donglin.entity.MyComment;
import fun.donglin.entity.User;
import fun.donglin.search.PageResult;
import fun.donglin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 查询分页参数查询博客评论列表
     *
     * @param page   页码数，默认查询第一页
     * @param rows   每页的条数，默认为5条，不允许用户修改
     * @param blogId 博客id，非必需，若为null则查询所有
     * @return
     */
    @GetMapping("comments/page")
    public ResponseEntity<PageResult<Comment>> findByPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(name = "rows", defaultValue = "5") Integer rows,
                                                          @RequestParam(name = "filterAdminComment", required = false) Boolean filterAdminComment,
                                                          @RequestParam(name = "blogId", required = false) Long blogId) {
        PageResult<Comment> result = this.commentService.findByPage( page, rows, filterAdminComment, blogId );
        if (CollectionUtils.isEmpty( result.getItems() )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( result );
    }

    /**
     * 根据博客id查询评论列表
     *
     * @param blogId 博客id
     * @return 评论列表
     */
    @GetMapping("comments/{blogId}")
    public ResponseEntity<List<Comment>> findByBlogId(@PathVariable Long blogId) {
        List<Comment> comments = this.commentService.findByBlogId( blogId );
        if (CollectionUtils.isEmpty( comments )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( comments );
    }

    /**
     * 根据博客id查询评论数量
     *
     * @param blogId 博客id
     * @return 评论数量
     */
    @GetMapping("comments/count/{blogId}")
    public ResponseEntity<Integer> findCommentCount(@PathVariable Long blogId) {
        Integer count = this.commentService.findCommentCount( blogId );
        return ResponseEntity.ok( count );
    }

    /**
     * 发布评论
     *
     * @param comment 评论实体类
     * @param session 会话对象
     * @return 新发布的评论实体类
     */
    @PostMapping("comments")
    public ResponseEntity<Comment> publish(@RequestBody MyComment comment, HttpSession session) {
        User user = (User) session.getAttribute( "user" );
        if (user != null) {
            comment.setAvatar( user.getAvatar() );
            comment.setAdminComment( true );
        }
        boolean flag = this.commentService.save( comment );
        if (!flag) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    /**
     * 根据评论id删除评论实体类
     *
     * @param id 评论id
     * @return 空文档
     */
    @DeleteMapping("admin/comments/{id}")
    public ResponseEntity<Void> delById(@PathVariable Long id) {
        this.commentService.delById( id );
        return ResponseEntity.noContent().build();
    }
}
