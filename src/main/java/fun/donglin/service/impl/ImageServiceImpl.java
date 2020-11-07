package fun.donglin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import fun.donglin.entity.Image;
import fun.donglin.search.PageResult;
import fun.donglin.mapper.ImageMapper;
import fun.donglin.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ImageMapper imageMapper;

    private static final List<String> CONTENT_TYPES = Arrays.asList( "image/jpeg", "image/gif", "image/png" );

    private static final Logger LOGGER = LoggerFactory.getLogger( ImageService.class );

    @Override
    @Transactional
    public String upload(MultipartFile file, String description) {

        String originalFilename = file.getOriginalFilename();
        // 校验文件的类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains( contentType )) {
            // 文件类型不合法，直接返回null
            LOGGER.info( "文件类型不合法：{}", originalFilename );
            return null;
        }

        try {
            // 校验文件的内容
            BufferedImage bufferedImage = ImageIO.read( file.getInputStream() );
            if (bufferedImage == null) {
                LOGGER.info( "文件内容不合法：{}", originalFilename );
                return null;
            }

            // 保存到服务器
            String ext = StringUtils.substringAfterLast( originalFilename, "." );
            StorePath storePath = this.storageClient.uploadFile( file.getInputStream(), file.getSize(), ext, null );

            //生成url地址
            String link = "http://www.pava.run/" + storePath.getFullPath();

            // 存储到数据库中
            Image record = new Image();
            record.setLink( link );
            if (StringUtils.isBlank( description )) {
                description = null;
            }
            record.setDescription( description );
            this.imageMapper.insertSelective( record );

            return link;
        } catch (IOException e) {
            LOGGER.error( "服务器内部错误：{}，所上传文件名为：{}", e.getMessage(), originalFilename );
        }
        return null;
    }

    @Override
    public PageResult<Image> findByPage(Integer page, Integer rows) {
        PageHelper.startPage( page, rows );
        List<Image> images = this.imageMapper.selectAll();
        PageInfo<Image> pageInfo = new PageInfo<>( images );
        return new PageResult<>( pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList() );
    }

    @Override
    public Image findById(Long id) {
        return this.imageMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional
    public boolean update(Image image) {
        try {
            this.imageMapper.updateByPrimaryKey( image );
        } catch (Exception e) {
            LOGGER.error( "更新图片信息错误: {}", e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.imageMapper.deleteByPrimaryKey( id );
    }
}
