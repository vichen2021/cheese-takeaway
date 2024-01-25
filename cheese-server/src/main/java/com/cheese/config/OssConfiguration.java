package com.cheese.config;

import com.cheese.properties.AliOssProperties;
import com.cheese.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建AliOssUtil对象
 *
 */

@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean
    // @ConditionalOnMissingBean 是一个 Spring 引导注解，仅当应用程序上下文中没有相同类型的现有 Bean 时，它才用于有条件地创建 Bean。它通常用于配置类或组件类中，以根据特定 Bean 的存在与否来定制 Bean 创建过程。
    public AliOssUtil AliOssUtil(AliOssProperties aliOssProperties) {
        log.info("开始创建阿里云文件上传工具类对象:{}", aliOssProperties);
        return new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName()
        );
    }
}
