package fun.donglin.mapper;

import fun.donglin.entity.Message;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MessageMapper extends Mapper<Message> {

    @Results(id = "messageMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "admin_message", property = "adminMessage"),
            @Result(column = "parent_message_id", property = "parentMessage",
                    one = @One(select = "fun.donglin.mapper.MessageMapper.findParentByParentId", fetchType = FetchType.EAGER)),
            @Result(column = "id", property = "replyMessages",
                    many = @Many(select = "fun.donglin.mapper.MessageMapper.findReplyListByCid", fetchType = FetchType.LAZY))
    })
    @Select("select * from t_message where parent_message_id is null")
    List<Message> findAllTop();

    @ResultMap("messageMap")
    @Select("select * from t_message where id = #{id}")
    Message findById(Long id);

    @Results(@Result(column = "create_time", property = "createTime"))
    @Select("select * from t_message where id = #{parentMessageId}")
    Message findParentByParentId(Long parentMessageId);

    @ResultMap("messageMap")
    @Select("select t1.* from t_message t1, t_message t2 where t1.parent_message_id = t2.id and t2.id = #{id}")
    List<Message> findReplyListByCid(Long id);

    @Update("update t_message set parent_message_id = #{parentMessageId} where id = #{id}")
    void updateParentMessageId(@Param("id") Long id, @Param("parentMessageId") Long parentMessageId);

    @Select("select count(id) from t_message")
    Integer findCount();

    @ResultMap("messageMap")
    @Select("<script>" +
            "select * from t_message <where>" +
            "<if test='adminMessage!=null'> and admin_message = #{adminMessage}</if>" +
            "</where>" +
            "</script>")
    List<Message> findAll(Boolean adminMessage);
}
