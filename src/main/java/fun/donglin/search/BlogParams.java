package fun.donglin.search;

import fun.donglin.entity.Blog;

import java.util.List;

public class BlogParams {

    private Blog blog;
    private String typeName;
    private List<String> tagNames;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "BlogParams{" +
                "blog=" + blog +
                ", typeName='" + typeName + '\'' +
                ", tagNames=" + tagNames +
                '}';
    }
}
