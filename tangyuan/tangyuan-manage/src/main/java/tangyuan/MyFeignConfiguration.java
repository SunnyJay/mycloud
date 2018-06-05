package tangyuan;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFeignConfiguration {
    //表示使用Feign自己的注解url方式
//    @Bean
//    public Contract feignContract() {
//        return new feign.Contract.Default();
////    }

@Bean
public ErrorDecoder errorDecoder(){
    return new FeignErrorDecoder ();
}
}
