package fun.donglin.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片实体类
 */
@Entity
@Table(name = "t_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;                // 图片id

    @Column(name = "link")
    @NotNull
    private String link;            // 图片链接

    @Column(name = "description")
    private String description;     // 图片描述

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
