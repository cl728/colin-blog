package fun.donglin.web.admin;

import fun.donglin.entity.Type;
import fun.donglin.search.PageResult;
import fun.donglin.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 根据分类参数查询分类分页列表
     *
     * @param page     页码数
     * @param rows     每页的条数
     * @param typeName 分类名称
     * @return 分类分页列表
     */
    @GetMapping("types/page")
    public ResponseEntity<PageResult<Type>> findByPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(name = "rows", defaultValue = "5") Integer rows,
                                                       @RequestParam(name = "typeName", required = false) String typeName) {
        PageResult<Type> result = this.typeService.findAll( page, rows, typeName );
        if (CollectionUtils.isEmpty( result.getItems() )) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( result );
    }

    /**
     * 查询所有分类列表
     *
     * @return 所有分类的列表
     */
    @GetMapping("types")
    public ResponseEntity<List<Type>> findAll() {
        List<Type> types = this.typeService.findAll();
        if (CollectionUtils.isEmpty( types )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( types );
    }

    /**
     * 查询所有分类列表，以及每个分类下的博客列表
     *
     * @return 所有分类列表，以及每个分类下的博客列表
     */
    @GetMapping("types/withBlogs")
    public ResponseEntity<List<Type>> findAllWithBlogList() {
        List<Type> types = this.typeService.findAllWithBlogList();
        if (CollectionUtils.isEmpty( types )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( types );
    }

    /**
     * 根据分类id查询分类实体类
     *
     * @param id 分类id
     * @return 分类实体类
     */
    @GetMapping("types/id/{id}")
    public ResponseEntity<Type> findById(@PathVariable Long id) {
        Type type = this.typeService.findById( id );
        if (type == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( type );
    }

    /**
     * 根据分类名称查询分类实体类
     *
     * @param name 分类名称
     * @return 分类实体类
     */
    @GetMapping("types/name/{name}")
    public ResponseEntity<Type> findByName(@PathVariable String name) {
        Type type = this.typeService.findByName( name );
        if (type == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( type );
    }

    /**
     * 保存分类实体类
     *
     * @param type 要保存的分类实体类
     * @return 新保存的分类的实体类
     */
    @PostMapping("admin/types")
    public ResponseEntity<Type> save(@RequestBody @Valid Type type) {
        boolean flag = this.typeService.saveOne( type );
        if (!flag) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    /**
     * 更新分类实体类
     *
     * @param type 要更新的分类实体类
     * @return 新更新的分类实体类
     */
    @PutMapping("admin/types")
    public ResponseEntity<Type> update(@RequestBody Type type) {
        boolean flag = this.typeService.update( type );
        if (flag) {
            return ResponseEntity.status( HttpStatus.CREATED ).build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 根据分类id删除分类实体类
     *
     * @param id 分类id
     * @return 空文档
     */
    @DeleteMapping("admin/types/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.typeService.delete( id );
        return ResponseEntity.noContent().build();
    }
}
