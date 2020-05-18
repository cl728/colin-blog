package fun.donglin.web.admin;

import fun.donglin.entity.Image;
import fun.donglin.search.PageResult;
import fun.donglin.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * 根据图片参数查询图片分页列表
     *
     * @param page 页码数
     * @param rows 每页的条数
     * @return 图片分页列表
     */
    @GetMapping("images/page")
    public ResponseEntity<PageResult<Image>> findByPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(name = "rows", defaultValue = "5") Integer rows) {
        PageResult<Image> result = this.imageService.findByPage( page, rows );
        if (CollectionUtils.isEmpty( result.getItems() )) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( result );
    }

    /**
     * 根据图片id查询图片实体类
     *
     * @param id 图片id
     * @return 图片实体类
     */
    @GetMapping("images/{id}")
    public ResponseEntity<Image> findById(@PathVariable Long id) {
        Image image = this.imageService.findById( id );
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( image );
    }

    /**
     * 上传图片
     *
     * @param file        要上传的图片
     * @param description 图片的描述
     * @return 上传成功后图片的url
     */
    @PostMapping("admin/images/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam(name = "description") String description) {
        String url = this.imageService.upload( file, description );
        if (StringUtils.isBlank( url )) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status( HttpStatus.CREATED ).body( url );
    }

    /**
     * 更新图片实体类
     *
     * @param image 要更新的图片实体类
     * @return 新更新的图片实体类
     */
    @PutMapping("admin/images")
    public ResponseEntity<Image> update(@RequestBody Image image) {
        boolean flag = this.imageService.update( image );
        if (!flag) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    /**
     * 根据图片id删除图片实体类
     *
     * @param id 图片id
     * @return 空文档
     */
    @DeleteMapping("admin/images/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.imageService.delete( id );
        return ResponseEntity.noContent().build();
    }
}
