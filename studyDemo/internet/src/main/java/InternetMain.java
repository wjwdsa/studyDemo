import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/******************************
 * @Description Internet启动类
 * @author Administrator
 * @date 2022-09-26 10:57
 ******************************/
@EnableAutoConfiguration
@RestController
public class InternetMain {
    public static void main(String[] args) {
        SpringApplication.run(InternetMain.class, args);
    }

    @RequestMapping("/internet")
    public String home() {
        return "Hello Spring Boot";
    }
}
