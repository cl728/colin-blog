package fun.donglin.mapper;

import fun.donglin.entity.Tag;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TagMapper extends Mapper<Tag> {

    @Results(id = "tagMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(property = "blogList", column = "id",
                    many = @Many(select = "fun.donglin.mapper.BlogTagMapper.findByTid",
                            fetchType = FetchType.LAZY))
    })
    @Select("select * from t_tag")
    List<Tag> findAll();
}
