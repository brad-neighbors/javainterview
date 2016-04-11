package com.jglitter.web;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.jglitter.web")
@Import(com.jglitter.persistence.Config.class)
public class Config {

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("com.jglitter.web");
        resourceConfig.register(JacksonFeature.class);
        resourceConfig.property(ServerProperties.TRACING, TracingConfig.ALL.name());
        resourceConfig.register(LoggingFilter.class);
        ServletContainer servletContainer = new org.glassfish.jersey.servlet.ServletContainer(resourceConfig);
        return new ServletRegistrationBean(servletContainer, "/api/*");
    }
}
