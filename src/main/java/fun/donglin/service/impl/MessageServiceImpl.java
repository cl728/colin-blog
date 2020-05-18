package fun.donglin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.donglin.entity.Message;
import fun.donglin.entity.MyMessage;
import fun.donglin.mapper.MessageMapper;
import fun.donglin.search.PageResult;
import fun.donglin.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    //存放迭代找出的所有子代的集合
    private List<Message> tempReplyMessages = new ArrayList<>();

    @Override
    public PageResult<Message> findByPage(Integer page, Integer rows, Boolean filterAdminMessage) {
        PageHelper.startPage( page, rows );
        Boolean adminMessage = filterAdminMessage != null && filterAdminMessage ? false : null;
        List<Message> messages = this.messageMapper.findAll( adminMessage );
        PageInfo<Message> pageInfo = new PageInfo<>( messages );
        return new PageResult<>( pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList() );
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = this.messageMapper.findAllTop();
        return eachMessage( messages );
    }

    @Override
    @Transactional
    public boolean save(MyMessage message) {
        try {
            // 查询 parentMessage，赋值到 message 中
            if (message.getParentMessageId() != -1) {
                message.setParentMessage( this.messageMapper.findById( message.getParentMessageId() ) );
            } else {
                message.setParentMessage( null );
            }
            // 向 message 表添加数据
            this.messageMapper.insertSelective( message );
            // 关联 parentMessage
            if (message.getParentMessageId() != -1) {
                this.messageMapper.updateParentMessageId( message.getId(), message.getParentMessageId() );
            }
        } catch (Exception e) {
            LOGGER.error( "保存留言信息错误: {}", e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public Integer findMessageCount() {
        return this.messageMapper.findCount();
    }

    @Override
    @Transactional
    public void delById(Long id) {
        Message message = this.messageMapper.findById( id );
        // 如果当前留言有回复留言，需先将回复留言删除
        List<Message> replyMessages = message.getReplyMessages();
        if (!CollectionUtils.isEmpty( replyMessages )) {
            replyMessages.forEach( replyMessage -> this.delById( replyMessage.getId() ) );
        }
        this.messageMapper.deleteByPrimaryKey( id );
    }

    /**
     * 循环每个顶级的留言节点
     *
     * @param messages
     * @return
     */
    private List<Message> eachMessage(List<Message> messages) {
        List<Message> messagesView = new ArrayList<>();
        for (Message message : messages) {
            Message c = new Message();
            BeanUtils.copyProperties( message, c );
            messagesView.add( c );
        }
        // 合并留言的各层子代到第一级子代集合中
        combineChildren( messagesView );
        return messagesView;
    }

    /**
     * @param messages root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Message> messages) {

        for (Message message : messages) {
            List<Message> replyMessages = message.getReplyMessages();
            for (Message reply1 : replyMessages) {
                //循环迭代，找出子代，存放在 tempReplyMessages 中
                recursively( reply1 );
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            message.setReplyMessages( tempReplyMessages );
            //清除临时存放区
            tempReplyMessages = new ArrayList<>();
        }
    }

    /**
     * 递归迭代，剥洋葱
     *
     * @param message 被迭代的对象
     * @return
     */
    private void recursively(Message message) {
        if (!tempReplyMessages.contains( message )) {
            tempReplyMessages.add( message );// 顶节点添加到临时存放集合
        }
        if (!CollectionUtils.isEmpty( message.getReplyMessages() )) {
            List<Message> replyList = message.getReplyMessages();
            for (Message reply : replyList) {
                tempReplyMessages.add( reply );
                if (reply.getReplyMessages().size() > 0) {
                    recursively( reply );
                }
            }
        }
    }
}
