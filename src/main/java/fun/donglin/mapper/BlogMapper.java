package fun.donglin.mapper;

import fun.donglin.entity.Blog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BlogMapper extends Mapper<Blog> {

    @Results(id = "blogMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "first_picture", property = "firstPicture"),
            @Result(column = "share_statement", property = "shareStatement"),
            @Result(column = "comment_able", property = "commentAble"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "type_id", property = "type",
                    one = @One(select = "fun.donglin.mapper.TypeMapper.findById", fetchType = FetchType.EAGER)),
            @Result(column = "user_id", property = "user",
                    one = @One(select = "fun.donglin.mapper.UserMapper.findById", fetchType = FetchType.EAGER)),
            @Result(column = "id", property = "tags",
                    many = @Many(select = "fun.donglin.mapper.BlogTagMapper.findByBid", fetchType = FetchType.LAZY))
    })
    @Select("<script>" +
            "select * from t_blog <where>" +
            "<if test='title!=null and title.length()>0'> and title like #{title}</if>" +
            "<if test='typeId!=null'> and type_id = #{typeId}</if>" +
            "<if test='commended!=null'> and commended = #{commended}</if>" +
            "<if test='published!=null'> and published = #{published}</if>" +
            "</where>" +
            " order by create_time desc" +
            "</script>")
    List<Blog> findAll(String title, Long typeId, Boolean commended, Boolean published);

    @ResultMap("blogMap")
    @Select("select * from t_blog where id in (select blog_list_id from t_blog_tags where tags_id = #{tagId}) and published = true order by create_time desc")
    List<Blog> findAllAndTagId(Long tagId);

    @Results(@Result(column = "create_time", property = "createTime"))
    @Select("select id, title, create_time, flag from t_blog where DATE_FORMAT(create_time,'%Y') = #{date} and published = true order by create_time desc")
    List<Blog> findByDate(String date);

    @Select("select * from t_blog where type_id = #{tid} and published = true order by create_time desc")
    List<Blog> findByTid(Long tid);

    @Select("select * from t_blog where user_id = #{uid} order by create_time desc")
    List<Blog> findByUid(Long uid);

    @ResultMap("blogMap")
    @Select("select * from t_blog where id = #{id}")
    Blog findById(Long id);

    @ResultMap("blogMap")
    @Select("select * from t_blog where id = #{id} and published = true")
    Blog findByIdAndPublished(Long id);

    @ResultMap("blogMap")
    @Select("select * from t_blog where published = 0")
    Blog findUnpublished();

    @Update("update t_blog set type_id = #{typeId} where id = #{blogId}")
    void updateTypeId(@Param("blogId") Long blogId, @Param("typeId") Long typeId);

    @Update("update t_blog set user_id = #{userId} where id = #{blogId}")
    void updateUserId(Long blogId, Long userId);

    @Update( "update t_blog set views = views + 1 where id = #{id}" )
    void updateViewsById(Long id);
}
