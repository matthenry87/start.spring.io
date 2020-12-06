package io.spring.start.site.extension.code.java;

import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

@RequiredArgsConstructor
@ConditionalOnWebApplication(type = SERVLET)
@ProjectGenerationConfiguration
public class ObjectMapperJava8TimeConfig {

    private final IndentingWriterFactory indentingWriterFactory;
    private final ProjectDescription projectDescription;

    @Bean
    ObjectMapperJava8TimeConfigContributor objectMapperJava8TimeConfig() {

        return new ObjectMapperJava8TimeConfigContributor(indentingWriterFactory, projectDescription);
    }

}
