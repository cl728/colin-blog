package fun.donglin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("fun.donglin.mapper")
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run( BlogApplication.class, args );
    }

}
