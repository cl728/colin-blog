package fun.donglin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.donglin.search.PageResult;
import fun.donglin.entity.Tag;
import fun.donglin.mapper.TagMapper;
import fun.donglin.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Override
    public PageResult<Tag> findAll(Integer page, Integer rows) {
        PageHelper.startPage( page, rows );
        List<Tag> tags = this.tagMapper.selectAll();
        PageInfo<Tag> pageInfo = new PageInfo<>( tags );
        return new PageResult<>( pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList() );
    }

    @Override
    public List<Tag> findAll() {
        return this.tagMapper.selectAll();
    }

    @Override
    public List<Tag> findAllWithBlogList() {
        return this.tagMapper.findAll();
    }

    @Override
    public List<Tag> findFirstTen() {
        List<Tag> tempTags = this.tagMapper.findAll();
        if (CollectionUtils.isEmpty( tempTags ) || tempTags.size() <= 10) {
            return tempTags;
        }
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tags.add( tempTags.get( i ) );
        }
        return tags;
    }

    @Override
    @Transactional
    public boolean saveOne(Tag tag) {
        Tag record = new Tag();
        record.setName( tag.getName() );
        Tag queryTag = this.tagMapper.selectOne( tag );
        if (queryTag == null) {
            this.tagMapper.insertSelective( tag );
            return true;
        }
        LOGGER.info( "保存失败，该标签名已经存在：{}", tag.getName() );
        return false;
    }

    @Override
    public Tag findById(Long id) {
        return this.tagMapper.selectByPrimaryKey( id );
    }

    @Override
    public Tag findByName(String name) {
        Tag record = new Tag();
        record.setName( name );
        return this.tagMapper.selectOne( record );
    }

    @Override
    @Transactional
    public boolean update(Tag tag) {
        Tag record = new Tag();
        record.setName( tag.getName() );
        Tag selectedTag = this.tagMapper.selectOne( record );
        if (selectedTag == null) {
            this.tagMapper.updateByPrimaryKeySelective( tag );
            return true;
        }
        LOGGER.info( "更新失败，该标签名已经存在：{}", tag.getName() );
        return false;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.tagMapper.deleteByPrimaryKey( id );
    }
}
