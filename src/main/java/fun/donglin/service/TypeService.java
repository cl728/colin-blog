package fun.donglin.service;

import fun.donglin.search.PageResult;
import fun.donglin.entity.Type;

import java.util.List;

public interface TypeService {

    boolean saveOne(Type type);

    Type findById(Long id);

    boolean update(Type type);

    void delete(Long id);

    PageResult<Type> findAll(Integer page, Integer rows, String typeName);

    List<Type> findAll();

    List<Type> findAllWithBlogList();

    Type findByName(String name);

    List<Type> findFirstSix();
}
