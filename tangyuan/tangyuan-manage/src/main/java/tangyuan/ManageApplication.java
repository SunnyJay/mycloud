package tangyuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 作者：sunna
 * 时间: 2018/4/8 14:20
 */
@SpringBootApplication
@EnableFeignClients
public class ManageApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ManageApplication.class, args);
    }
}
