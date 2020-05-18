package fun.donglin.service;

import fun.donglin.search.PageResult;
import fun.donglin.entity.Tag;

import java.util.List;

public interface TagService {

    boolean saveOne(Tag type);

    Tag findById(Long id);

    boolean update(Tag type);

    void delete(Long id);

    PageResult<Tag> findAll(Integer page, Integer rows);

    List<Tag> findAll();

    Tag findByName(String name);

    List<Tag> findFirstTen();

    List<Tag> findAllWithBlogList();
}
