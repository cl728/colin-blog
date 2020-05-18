package fun.donglin.service;

import fun.donglin.entity.Comment;
import fun.donglin.entity.MyComment;
import fun.donglin.search.PageResult;

import java.util.List;

public interface CommentService {

    List<Comment> findByBlogId(Long blogId);

    boolean save(MyComment comment);

    Integer findCommentCount(Long blogId);

    PageResult<Comment> findByPage(Integer page, Integer rows, Boolean filterAdminComment, Long blogId);

    void delById(Long id);
}
