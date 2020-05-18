package fun.donglin.web.admin;

import fun.donglin.entity.User;
import fun.donglin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 查询当前会话作用域下的用户对象
     *
     * @param session 会话对象
     * @return 用户实体类
     */
    @GetMapping("users")
    public ResponseEntity<User> findUser(HttpSession session) {
        User user = (User) session.getAttribute( "user" );
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( user );
    }

    /**
     * 管理员登录
     *
     * @param username 管理员名称
     * @param password 管理员密码
     * @param session  会话作用域
     * @return 登录的User对象
     */
    @PostMapping("admin/login")
    public ResponseEntity<User> login(@RequestParam String username,
                                      @RequestParam String password,
                                      HttpSession session) {
        User user = this.userService.checkUser( username, password );
        if (user != null) {
            user.setPassword( null );
            session.setAttribute( "user", user );
            session.setMaxInactiveInterval( 2 * 60 * 60 );
            return ResponseEntity.ok( user );
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 管理员注销
     *
     * @param session 会话对象
     * @return 空文档
     */
    @GetMapping("admin/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute( "user" );
        return ResponseEntity.noContent().build();
    }
}
