package fun.donglin.web.admin;

import fun.donglin.entity.Blog;
import fun.donglin.search.BlogParams;
import fun.donglin.search.PageResult;
import fun.donglin.search.TagParams;
import fun.donglin.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 根据Blog参数分页查询博客列表
     *
     * @param page        页码数
     * @param rows        每页的条数，默认为5条，不允许用户修改
     * @param title       标题
     * @param typeName    类型名称
     * @param recommended 是否推荐
     * @param published   是否发布
     * @return 分页博客列表
     */
    @GetMapping("blogs/page/blogParams")
    public ResponseEntity<PageResult<Blog>> findByPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(name = "rows", defaultValue = "5") Integer rows,
                                                       @RequestParam(name = "title", required = false) String title,
                                                       @RequestParam(name = "typeName", required = false) String typeName,
                                                       @RequestParam(name = "isCommended", required = false) Boolean recommended,
                                                       @RequestParam(name = "published", required = false) Boolean published) {
        PageResult<Blog> result = this.blogService.findByPage( page, rows, title, typeName, recommended, published );
        if (CollectionUtils.isEmpty( result.getItems() )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( result );
    }

    /**
     * 根据标签参数分页查询博客列表
     *
     * @param tagParams 标签参数，page 页码数；rows 每页的条数，默认为5，不允许用户进行修改；tagNames 标签名的集合
     * @return 分页博客列表
     */
    @PostMapping("blogs/page/tagParams")
    public ResponseEntity<PageResult<Blog>> findByPageAndTagNames(@RequestBody TagParams tagParams) {
        Integer page = tagParams.getPage();
        if (page == null) {
            page = 1;
        }
        Integer rows = tagParams.getRows();
        if (rows == null) {
            rows = 5;
        }
        List<String> tagNames = tagParams.getTagNames();
        PageResult<Blog> result = this.blogService.findByPage( page, rows, tagNames );
        if (CollectionUtils.isEmpty( result.getItems() )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( result );
    }

    /**
     * 查询所有博客
     *
     * @return 所有博客的列表
     */
    @GetMapping("blogs")
    public ResponseEntity<List<Blog>> findAll() {
        List<Blog> blogList = this.blogService.findAll();
        if (CollectionUtils.isEmpty( blogList )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( blogList );
    }

    /**
     * 根据博客id查询博客
     *
     * @param id   博客id
     * @param flag 是否需要将content由markdown格式转成html
     * @return 博客实体类
     */
    @GetMapping("blogs/id/{id}/{flag}")
    public ResponseEntity<Blog> findById(@PathVariable Long id, @PathVariable Boolean flag) {
        Blog blog = this.blogService.findById( id, flag );
        if (blog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( blog );
    }

    /**
     * 根据日期字符串查询博客列表
     *
     * @param date 日期字符串
     * @return 博客列表
     */
    @GetMapping("blogs/date/{date}")
    public ResponseEntity<List<Blog>> findByDate(@PathVariable String date) {
        List<Blog> blogList = this.blogService.findByDate( date );
        if (CollectionUtils.isEmpty( blogList )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( blogList );
    }

    /**
     * 查询未发布的博客
     *
     * @return 未发布的博客实体类
     */
    @GetMapping("blogs/unpublished")
    public ResponseEntity<Blog> findUnpublished() {
        Blog blog = this.blogService.findUnpublished();
        if (blog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( blog );
    }

    /**
     * 查询最新发布的三条博客
     *
     * @param recommended 是否推荐
     * @return 最新发布的三条博客
     */
    @GetMapping("blogs/latestThree")
    public ResponseEntity<List<Blog>> findLatestThree(@RequestParam(name = "recommended", required = false) Boolean recommended) {
        List<Blog> blogList = this.blogService.findLatestThree( recommended );
        if (CollectionUtils.isEmpty( blogList )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( blogList );
    }

    /**
     * 根据查询博客实体类，并使其浏览次数加一
     *
     * @param id 博客id
     * @return 博客实体类
     */
    @PutMapping("blogs/plusViews/{id}")
    public ResponseEntity<Blog> plusViews(@PathVariable Long id) {
        Blog blog = this.blogService.plusViews( id );
        if (blog == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok( blog );
    }

    /**
     * 根据 BlogParams 保存博客实体类
     *
     * @param blogParams 博客参数，blog 博客实体类，typeName 分类名称，tagNames 标签名称集合
     * @return 新保存的博客对象
     */
    @PostMapping("admin/blogs")
    public ResponseEntity<Blog> save(@RequestBody BlogParams blogParams) {
        Long id = blogParams.getBlog().getId();
        if (id == -1) {
            blogParams.getBlog().setId( null );
            this.blogService.save( blogParams );
            return ResponseEntity.status( HttpStatus.CREATED ).build();
        } else {
            boolean flag = this.blogService.updateById( blogParams );
            if (!flag) {
                return ResponseEntity.status( HttpStatus.BAD_REQUEST ).build();
            }
            return ResponseEntity.status( HttpStatus.CREATED ).build();
        }
    }

    /**
     * 根据 BlogParams 更新博客实体类
     *
     * @param blogParams 博客参数，blog 博客实体类，typeName 分类名称，tagNames 标签名称集合
     * @return 更新后的博客对象
     */
    @PutMapping("admin/blogs")
    public ResponseEntity<Blog> update(@RequestBody BlogParams blogParams) {
        boolean flag = this.blogService.updateById( blogParams );
        if (!flag) {
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).build();
        }
        return ResponseEntity.status( HttpStatus.NO_CONTENT ).build();
    }

    /**
     * 根据博客id切换置顶标志
     *
     * @param id 博客id
     * @return 更新后的博客对象
     */
    @PutMapping("admin/blogs/{id}")
    public ResponseEntity<Blog> toggleTop(@PathVariable Long id) {
        boolean flag = this.blogService.toggleTop( id );
        if (!flag) {
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).build();
        }
        return ResponseEntity.status( HttpStatus.NO_CONTENT ).build();
    }

    /**
     * 根据博客id删除博客对象
     *
     * @param id 博客id
     * @return 空文档
     */
    @DeleteMapping("admin/blogs/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.blogService.delById( id );
        return ResponseEntity.noContent().build();
    }
}
