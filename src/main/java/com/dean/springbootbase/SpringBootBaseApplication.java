package com.dean.springbootbase;

import com.dean.springbootbase.config.MyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@ConfigurationPropertiesScan
@SpringBootApplication(
//        exclude = {WebMvcAutoConfiguration.class} // AutoConfiguration을 키고 끌수있다.
//        excludeName = {"org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration"}    // 이름으로도 제외할 수 있다.
//        scanBasePackages = "com.dean.springbootbase" // 어디서 부터 스캔할지 패키지를 정의한다.
)
public class SpringBootBaseApplication {

    private final MyProperties myProperties;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBaseApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println("myProperties : " + myProperties.getHeight());
    }

}
