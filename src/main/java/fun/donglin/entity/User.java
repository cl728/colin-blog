package fun.donglin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 管理员实体类
 */
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;            // 管理员id

    @Column(name = "nickname")
    private String nickname;    // 管理员昵称

    @Column(name = "username")
    @NotNull(message = "管理员名不能为空！")
    private String username;    // 管理员名

    @Column(name = "password")
    @NotNull(message = "密码不能为空！")
    @JsonIgnore
    private String password;    // 密码

    @Column(name = "email")
    private String email;       // 邮箱

    @Column(name = "avatar")
    private String avatar;      // 头像

    @Column(name= "type")
    private Integer type;       // 管理员类型，1为管理员，2为普通管理员

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;    // 创建时间

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTIme;    // 更新时间

    private List<Blog> blogList = new ArrayList<>();    // user - blog 一对多

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTIme() {
        return updateTIme;
    }

    public void setUpdateTIme(Date updateTIme) {
        this.updateTIme = updateTIme;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTIme=" + updateTIme +
                '}';
    }
}
