package com.dean.springbootbase;

import com.dean.springbootbase.config.MyProperties;
import com.dean.springbootbase.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

@EnableConfigServer
@RequiredArgsConstructor
@ConfigurationPropertiesScan
@SpringBootApplication(
//        exclude = {WebMvcAutoConfiguration.class} // AutoConfiguration을 키고 끌수있다.
//        excludeName = {"org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration"}    // 이름으로도 제외할 수 있다.
//        scanBasePackages = "com.dean.springbootbase" // 어디서 부터 스캔할지 패키지를 정의한다.
)
public class SpringBootBaseApplication {

    private final MyProperties myProperties;
    private final StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBaseApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        System.out.println("내 키는: " + myProperties.getHeight());
        studentService.printStudent("jack");
        studentService.printStudent("jack");
        studentService.printStudent("jack");
        studentService.printStudent("fred");
        studentService.printStudent("cassie");
        studentService.printStudent("cassie");
    }

}
