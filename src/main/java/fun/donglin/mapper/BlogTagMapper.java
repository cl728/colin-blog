package fun.donglin.mapper;

import fun.donglin.entity.Blog;
import fun.donglin.entity.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogTagMapper {

    @Insert("insert into t_blog_tags(blog_list_id, tags_id) values(#{blogId}, #{tagId})")
    void insert(@Param("blogId") Long blogId, @Param("tagId") Long tagId);

    @Delete("delete from t_blog_tags where blog_list_id = #{blogId}")
    void delete(Long blogId);

    @Select("select * from t_tag where id in (select tags_id from t_blog_tags where blog_list_id = #{blogId})")
    List<Tag> findByBid(Long blogId);

    @Select("select * from t_blog where id in (select blog_list_id from t_blog_tags where tags_id = #{tagId}) and published = true")
    List<Blog> findByTid(Long tagId);

    @Select("select blog_list_id from t_blog_tags where tags_id = #{tagId}")
    List<Long> findBidsByTagId(Long tagId);
}
