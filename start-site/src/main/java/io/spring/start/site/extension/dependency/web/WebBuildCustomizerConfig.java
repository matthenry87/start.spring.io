package io.spring.start.site.extension.dependency.web;

import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnRequestedDependency("web")
public class WebBuildCustomizerConfig {

    @Bean
    public WebBuildCustomizer webBuildCustomizer() {

        return new WebBuildCustomizer();
    }

}
