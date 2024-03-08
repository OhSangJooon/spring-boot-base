package com.dean.springbootbase.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConstructorBinding // 상수처럼 final을 써서 사용하도록 만들어준다. 이것을 사용하지 않으면, final을 없애고 set을 사용해야한다.
@ConfigurationProperties("iam")
public class CustomProperties {

    /**
     * 와아 이건 configuration processor 테스트
     */
    private final Duration duration;

    public CustomProperties(@DefaultValue("1") @DurationUnit(ChronoUnit.MILLIS) @Name("duration") Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }
}