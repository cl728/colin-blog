package fun.donglin.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客实体类
 */
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;                // 博客id

    @Column(name = "title")
    @NotNull(message = "标题不能为空！")
    private String title;           // 博客标题

    @Column(name = "content")
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @NotNull(message = "博客内容不能为空！")
    private String content;         // 博客内容

    @Column(name = "description")
    @NotNull
    private String description;     // 博客摘要

    @Column(name = "first_picture")
    @NotNull(message = "首图引用地址不能为空！")
    private String firstPicture;    // 首图

    @Column(name = "flag")
    @NotNull(message = "标志不能为空！")
    private String flag;            // 标志

    @Column(name = "views")
    private Integer views;          // 浏览次数

    @Column(name = "appreciation")
    private Boolean appreciation;   // 是否开启赞赏

    @Column(name = "share_statement")
    private Boolean shareStatement; // 是否开启转载声明

    @Column(name = "comment_able")
    private Boolean commentAble;    // 是否允许评论

    @Column(name = "published")
    private Boolean published;      // 是否发布

    @Column(name = "commended")
    private Boolean commended;      // 是否推荐

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;        // 创建时间

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;        // 更新时间

    private Type type;              // blog - type 多对一

    private List<Tag> tags = new ArrayList<>();     // blog - tag 多对多

    private User user;              // blog - user 多对一

    private List<Comment> comments = new ArrayList<>(); // blog - comment 一对多

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Boolean appreciation) {
        this.appreciation = appreciation;
    }

    public Boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(Boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public Boolean isCommentAble() {
        return commentAble;
    }

    public void setCommentAble(Boolean commentAble) {
        this.commentAble = commentAble;
    }

    public Boolean isPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean isCommended() {
        return commended;
    }

    public void setCommended(Boolean commended) {
        this.commended = commended;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + (content == null ? "" : content.substring( 0, 10 )) + '\'' +
                ", description='" + (description == null ? "" : description.substring( 0, 10 )) + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentAble=" + commentAble +
                ", published=" + published +
                ", commended=" + commended +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
