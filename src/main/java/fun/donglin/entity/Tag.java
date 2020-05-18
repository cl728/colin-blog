package fun.donglin.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签实体类
 */
@Entity
@Table(name = "t_tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;        // 标签id

    @Column(name = "name")
    private String name;    // 标签名称

    private List<Blog> blogList = new ArrayList<>();    // tag - blog 多对多

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
