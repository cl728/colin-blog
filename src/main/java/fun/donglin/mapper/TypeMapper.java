package fun.donglin.mapper;

import fun.donglin.entity.Type;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TypeMapper extends Mapper<Type> {

    @Results(id = "typeMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(property = "blogList", column = "id",
                    many = @Many(select = "fun.donglin.mapper.BlogMapper.findByTid",
                            fetchType = FetchType.LAZY))
    })
    @Select("<script>" +
            "select * from t_type <where>" +
            "<if test='typeName!=null and typeName.length()>0'> and name = #{typeName}</if>" +
            "</where>" +
            "</script>")
    List<Type> findAll(String typeName);

    @Select("select * from t_type where id = #{id}")
    Type findById(Long id);
}
