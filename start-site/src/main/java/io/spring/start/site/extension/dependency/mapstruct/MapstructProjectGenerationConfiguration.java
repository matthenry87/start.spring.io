package io.spring.start.site.extension.dependency.mapstruct;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@ProjectGenerationConfiguration
@ConditionalOnRequestedDependency("mapstruct")
public class MapstructProjectGenerationConfiguration {

    @Bean
    @ConditionalOnBuildSystem(MavenBuildSystem.ID)
    public BuildCustomizer<MavenBuild> mavenMapstructPluginCustomizer() {
        return (build) -> {

            Consumer<MavenPlugin.Builder> builderConsumer = (plugin) ->

                    plugin.configuration((configurationBuilder -> {

                        boolean hasMavenPlugin = build.plugins().has("org.apache.maven.plugins", "maven-compiler-plugin");

                        if (!hasMavenPlugin) {

                            configurationBuilder.add("source", "${java.version}");
                            configurationBuilder.add("target", "${java.version}");
                        }

                        configurationBuilder.configure("annotationProcessorPaths", configurationBuilder1 ->

                                configurationBuilder1.add("path", "\n<groupId>org.mapstruct</groupId>" +
                                        "\n<artifactId>mapstruct-processor</artifactId>" +
                                        "\n<version>1.4.1.Final</version>\n")
                        );

                        configurationBuilder.configure("compilerArgs", configurationBuilder1 -> {
                            configurationBuilder1.add("arg", "-Amapstruct.suppressGeneratorTimestamp=true");
                            configurationBuilder1.add("arg", "-Amapstruct.defaultComponentModel=spring");
                        });
                    }));

            build.plugins().add("org.apache.maven.plugins", "maven-compiler-plugin", builderConsumer);
        };
    }

}
