package fun.donglin.entity;

public class MyComment extends Comment {

    private Long blogId;
    private Long parentCommentId;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    @Override
    public String toString() {
        return super.toString() + "MyComment{" +
                "blogId=" + blogId +
                ", parentCommentId=" + parentCommentId +
                '}';
    }
}
