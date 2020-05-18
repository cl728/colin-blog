package fun.donglin.web.admin;

import fun.donglin.entity.Message;
import fun.donglin.entity.MyMessage;
import fun.donglin.entity.User;
import fun.donglin.search.PageResult;
import fun.donglin.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 查询分页参数查询博客留言列表
     *
     * @param page   页码数，默认查询第一页
     * @param rows   每页的条数，默认为5条，不允许用户修改
     * @return
     */
    @GetMapping("messages/page")
    public ResponseEntity<PageResult<Message>> findByPage(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(name = "rows", defaultValue = "5") Integer rows,
                                                          @RequestParam(name = "filterAdminMessage", required = false) Boolean filterAdminMessage) {
        PageResult<Message> result = this.messageService.findByPage( page, rows, filterAdminMessage );
        if (CollectionUtils.isEmpty( result.getItems() )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( result );
    }

    /**
     * 查询留言列表
     *
     * @return 留言列表
     */
    @GetMapping("messages")
    public ResponseEntity<List<Message>> findAll() {
        List<Message> messages = this.messageService.findAll();
        if (CollectionUtils.isEmpty( messages )) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( messages );
    }

    /**
     * 查询留言数量
     *
     * @return 留言数量
     */
    @GetMapping("messages/count")
    public ResponseEntity<Integer> findMessageCount() {
        Integer count = this.messageService.findMessageCount();
        return ResponseEntity.ok( count );
    }

    /**
     * 发布留言
     *
     * @param message 留言实体类
     * @param session 会话对象
     * @return 新发布的留言实体类
     */
    @PostMapping("messages")
    public ResponseEntity<Message> publish(@RequestBody MyMessage message, HttpSession session) {
        User user = (User) session.getAttribute( "user" );
        if (user != null) {
            message.setAvatar( user.getAvatar() );
            message.setAdminMessage( true );
        }
        boolean flag = this.messageService.save( message );
        if (!flag) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    /**
     * 根据留言id删除留言实体类
     *
     * @param id 留言id
     * @return 空文档
     */
    @DeleteMapping("admin/messages/{id}")
    public ResponseEntity<Void> delById(@PathVariable Long id) {
        this.messageService.delById( id );
        return ResponseEntity.noContent().build();
    }
}
