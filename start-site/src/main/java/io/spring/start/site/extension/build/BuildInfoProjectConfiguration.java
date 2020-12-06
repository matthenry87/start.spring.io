package io.spring.start.site.extension.build;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnRequestedDependency("logging")
public class BuildInfoProjectConfiguration {

    @Bean
    @ConditionalOnBuildSystem(MavenBuildSystem.ID)
    public BuildCustomizer<MavenBuild> springBootMavenPluginBuildInfoCustomizer() {
        return build ->

                build.plugins().add("org.springframework.boot", "spring-boot-maven-plugin",
                        plugin ->
                                plugin.execution("build-info", executionBuilder -> executionBuilder.goal("build-info"))
                );
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    public BuildCustomizer<GradleBuild> springBootGradlePluginBuildInfoCustomizer() {
        return build ->

                build.tasks().customize("spring", task -> task.invoke("buildInfo"));
    }

}
