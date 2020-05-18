package fun.donglin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.donglin.search.PageResult;
import fun.donglin.entity.Type;
import fun.donglin.mapper.TypeMapper;
import fun.donglin.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Override
    public PageResult<Type> findAll(Integer page, Integer rows, String typeName) {
        PageHelper.startPage( page, rows );
        List<Type> types = this.typeMapper.findAll( typeName );
        PageInfo<Type> pageInfo = new PageInfo<>( types );
        return new PageResult<>( pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList() );
    }

    @Override
    public List<Type> findAll() {
        return this.typeMapper.selectAll();
    }

    @Override
    public List<Type> findAllWithBlogList() {
        return this.typeMapper.findAll( null );
    }

    @Override
    public Type findByName(String name) {
        Type record = new Type();
        record.setName( name );
        return this.typeMapper.selectOne( record );
    }

    @Override
    public Type findById(Long id) {
        return this.typeMapper.selectByPrimaryKey( id );
    }

    @Override
    public List<Type> findFirstSix() {
        List<Type> tempTypes = this.typeMapper.findAll( null );
        if (CollectionUtils.isEmpty( tempTypes ) || tempTypes.size() <= 6) {
            return tempTypes;
        }
        List<Type> types = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            types.add( tempTypes.get( i ) );
        }
        return types;
    }

    @Override
    @Transactional
    public boolean saveOne(Type type) {
        Type record = new Type();
        record.setName( type.getName() );
        Type queryType = this.typeMapper.selectOne( type );
        if (queryType == null) {
            this.typeMapper.insert( type );
            return true;
        }
        LOGGER.info( "保存失败，该分类名已经存在：{}", type.getName() );
        return false;
    }

    @Override
    @Transactional
    public boolean update(Type type) {
        Type record = new Type();
        record.setName( type.getName() );
        Type selectedType = this.typeMapper.selectOne( record );
        if (selectedType == null) {
            this.typeMapper.updateByPrimaryKeySelective( type );
            return true;
        }
        LOGGER.info( "更新失败，该标签名已经存在：{}", type.getName() );
        return false;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.typeMapper.deleteByPrimaryKey( id );
    }
}
