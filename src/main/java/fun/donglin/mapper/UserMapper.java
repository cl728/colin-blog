package fun.donglin.mapper;

import fun.donglin.entity.User;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    @Results(id = "userMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(property = "blogList", column = "id",
                    many = @Many(select = "fun.donglin.mapper.BlogMapper.findByUid",
                            fetchType = FetchType.LAZY))
    })
    @Select("select * from t_user")
    List<User> findAll();

    @Select("select * from t_user where id = #{id}")
    User findById(Long id);
}
