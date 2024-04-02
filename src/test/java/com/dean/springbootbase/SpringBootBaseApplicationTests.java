package com.dean.springbootbase;

import com.dean.springbootbase.domain.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/* 외부장치 연결을 위해 Testcontainers를 사용한다. */
@Testcontainers
@SpringBootTest
class FastcampusSpringBootPracticeApplicationTests {

    private static Logger logger  = LoggerFactory.getLogger(FastcampusSpringBootPracticeApplicationTests.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    private static final GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"));

    @BeforeAll
    static void setUp() {
      redisContainer.followOutput(new Slf4jLogConsumer(logger));
    }

    // 외부 프로퍼티 설정을 나중에 읽어서 주입하도록 하는 설정
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379));    // 레디스 컨테이너와 매핑된 포트를 알려주세요 -> getMappedPort
    }

    /**
     * 테스트가 시작하면 자동으로 도커 컨테이너를 올리고 테스트가 종료되면 컨테이너를 내린다.
     * */
    @Test
    void contextLoads() throws Exception {
        // Given

        // When
        GenericContainer.ExecResult execResult1 = redisContainer.execInContainer("redis-cli", "get", "student:cassie");
        GenericContainer.ExecResult execResult2 = redisContainer.execInContainer("redis-cli", "get", "student:jack");
        GenericContainer.ExecResult execResult3 = redisContainer.execInContainer("redis-cli", "get", "student:fred");

        Student actual1 = objectMapper.readValue(execResult1.getStdout(), Student.class);
        Student actual2 = objectMapper.readValue(execResult2.getStdout(), Student.class);
        Student actual3 = objectMapper.readValue(execResult3.getStdout(), Student.class);
        // Then
        // 컨테이너 안에서 명령을 수행해라
        assertThat(redisContainer.isRunning()).isTrue();
        assertThat(actual1).isEqualTo(Student.of("cassie", 18, Student.Grade.A));
        assertThat(actual2).isEqualTo(Student.of("jack", 15, Student.Grade.B));
        assertThat(actual3).isEqualTo(Student.of("fred", 14, Student.Grade.C));
    }

}
