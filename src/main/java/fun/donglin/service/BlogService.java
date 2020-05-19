package fun.donglin.service;

import fun.donglin.entity.Blog;
import fun.donglin.search.BlogParams;
import fun.donglin.search.PageResult;

import java.util.List;

public interface BlogService {
    PageResult<Blog> findByPage(Integer page, Integer rows, String title, String typeName, Boolean commended, Boolean published);

    PageResult<Blog> findByPage(Integer page, Integer rows, List<String> tagNames);

    Blog findById(Long id, Boolean flag);

    Blog findUnpublished();

    List<Blog> findAll();

    List<Blog> findLatestThree(Boolean recommended);

    void delById(Long id);

    void save(BlogParams blogParams);

    boolean updateById(BlogParams blogParams);

    List<Blog> findByDate(String date);

    Blog plusViews(Long id);

    boolean toggleTop(Long id);
}
