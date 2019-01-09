package com.gezhiwei.projectstart.config;

import com.gezhiwei.projectstart.util.TemplateRenderer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @ClassName: InitializrAutoConfiguration
 * @Author: 葛志伟(赛事)
 * @Description:
 * @Date: 2019/1/8 13:38
 * @modified By:
 */
@Configuration
public class InitializrAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TemplateRenderer templateRenderer(Environment environment) {
        Binder binder = Binder.get(environment);
        boolean cache = binder.bind("spring.mustache.cache", Boolean.class).orElse(true);
        TemplateRenderer templateRenderer = new TemplateRenderer();
        templateRenderer.setCache(cache);
        return templateRenderer;
    }
}
