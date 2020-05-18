package fun.donglin.service;

import fun.donglin.entity.Comment;
import fun.donglin.entity.Message;
import fun.donglin.entity.MyMessage;
import fun.donglin.search.PageResult;

import java.util.List;

public interface MessageService {

    PageResult<Message> findByPage(Integer page, Integer rows, Boolean filterAdminMessage);

    List<Message> findAll();

    boolean save(MyMessage message);

    Integer findMessageCount();

    void delById(Long id);
}
