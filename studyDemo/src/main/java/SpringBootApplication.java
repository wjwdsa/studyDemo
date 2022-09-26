import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/******************************
 * @Description 项目启动类
 * @author Administrator
 * @date 2022-09-26 13:18
 ******************************/
@EnableAutoConfiguration
@RestController
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(InternetMain.class, args);
    }

    @RequestMapping("/")
    public String home() {
        return "Hello Spring Boot";
    }
}
