package fun.donglin.service;

import fun.donglin.entity.Image;
import fun.donglin.search.PageResult;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String upload(MultipartFile file, String description);

    PageResult<Image> findByPage(Integer page, Integer rows);

    void delete(Long id);

    Image findById(Long id);

    boolean update(Image image);
}
