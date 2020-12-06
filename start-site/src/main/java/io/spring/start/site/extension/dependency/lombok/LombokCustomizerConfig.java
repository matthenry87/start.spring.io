package io.spring.start.site.extension.dependency.lombok;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.spring.build.gradle.ConditionalOnGradleVersion;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@ConditionalOnRequestedDependency("lombok")
@ProjectGenerationConfiguration
public class LombokCustomizerConfig {

    @Bean
    @ConditionalOnBuildSystem(MavenBuildSystem.ID)
    public BuildCustomizer<MavenBuild> mavenLombokPluginCustomizer() {
        return (build) -> {

            Consumer<MavenPlugin.Builder> builderConsumer = (plugin) ->

                    plugin.configuration((configurationBuilder -> {

                        boolean hasMavenPlugin = build.plugins().has("org.apache.maven.plugins", "maven-compiler-plugin");

                        if (!hasMavenPlugin) {

                            configurationBuilder.add("source", "${java.version}");
                            configurationBuilder.add("target", "${java.version}");
                        }

                        configurationBuilder.configure("annotationProcessorPaths", configurationBuilder1 ->

                                configurationBuilder1.add("path", """

                                        <groupId>org.projectlombok</groupId>
                                        <artifactId>lombok</artifactId>
                                        <version>${lombok.version}</version>
                                        """)
                        );
                    }));

            build.plugins().add("org.apache.maven.plugins", "maven-compiler-plugin", builderConsumer);
        };
    }

    @Bean
    @ConditionalOnGradleVersion({"4", "5", "6"})
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    public LombokGradleBuildCustomizer lombokGradleBuildCustomizer(InitializrMetadata metadata) {

        return new LombokGradleBuildCustomizer(metadata);
    }
}
