package io.github.ztmark.learningspringboot2.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Author: Mark
 * Date  : 2017/12/10
 */
@Component
public class LearningSpringBoot2HealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().withDetail("msg", "good").build();
    }

}
