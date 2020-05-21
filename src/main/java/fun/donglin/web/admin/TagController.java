package fun.donglin.web.admin;

import fun.donglin.search.PageResult;
import fun.donglin.entity.Tag;
import fun.donglin.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 根据标签参数查询标签分页列表
     *
     * @param page     页码数
     * @param rows     每页的条数
     * @return 标签分页列表
     */
    @GetMapping("tags/page")
    public ResponseEntity<PageResult<Tag>> findByPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                      @RequestParam(name = "rows", defaultValue = "5") Integer rows) {
        PageResult<Tag> result = this.tagService.findAll( page, rows );
        if (CollectionUtils.isEmpty( result.getItems() )) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( result );
    }

    /**
     * 查询所有标签列表
     *
     * @return 所有标签的列表
     */
    @GetMapping("tags")
    public ResponseEntity<List<Tag>> findAll() {
        List<Tag> tags = this.tagService.findAll();
        if (CollectionUtils.isEmpty( tags )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( tags );
    }

    /**
     * 查询所有标签列表，以及每个标签下的博客列表
     *
     * @return 所有标签列表，以及每个标签下的博客列表
     */
    @GetMapping("tags/withBlogs")
    public ResponseEntity<List<Tag>> findAllWithBlogList() {
        List<Tag> tags = this.tagService.findAllWithBlogList();
        if (CollectionUtils.isEmpty( tags )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( tags );
    }

    /**
     * 根据标签id查询标签实体类
     *
     * @param id 标签id
     * @return 标签实体类
     */
    @GetMapping("tags/id/{id}")
    public ResponseEntity<Tag> findById(@PathVariable Long id) {
        Tag tag = this.tagService.findById( id );
        if (tag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( tag );
    }

    /**
     * 根据标签名称查询标签实体类
     *
     * @param name 标签名称
     * @return 标签实体类
     */
    @GetMapping("tags/name/{name}")
    public ResponseEntity<Tag> findByName(@PathVariable String name) {
        Tag tag = this.tagService.findByName(name);
        if (tag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( tag );
    }

    /**
     * 保存标签实体类
     *
     * @param tag 要保存的标签实体类
     * @return 新保存的标签的实体类
     */
    @PostMapping("admin/tags")
    public ResponseEntity<Tag> save(@RequestBody @Valid Tag tag) {
        boolean flag = this.tagService.saveOne( tag );
        if (!flag) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    /**
     * 更新标签实体类
     *
     * @param tag 要更新的标签实体类
     * @return 新更新的标签实体类
     */
    @PutMapping("admin/tags")
    public ResponseEntity<Tag> update(@RequestBody Tag tag) {
        boolean flag = this.tagService.update( tag );
        if (flag) {
            return ResponseEntity.status( HttpStatus.CREATED ).build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 根据标签id删除标签实体类
     *
     * @param id 标签id
     * @return 空文档
     */
    @DeleteMapping("admin/tags/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.tagService.delete( id );
        return ResponseEntity.noContent().build();
    }
}
