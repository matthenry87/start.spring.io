/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.start.site.extension.build.maven;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;

import io.spring.initializr.generator.spring.build.BuildCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(MavenBuildSystem.ID)
class MavenProjectGenerationConfiguration {

    @Bean
    MavenBuildSystemHelpDocumentCustomizer mavenBuildSystemHelpDocumentCustomizer(ProjectDescription description) {
        return new MavenBuildSystemHelpDocumentCustomizer(description);
    }

    @Bean
    @ConditionalOnBuildSystem(MavenBuildSystem.ID)
    public BuildCustomizer<MavenBuild> jacocoPluginCustomizer() {
        return build -> {

            Consumer<MavenPlugin.Builder> builderConsumer = plugin -> {

                plugin.execution("prepare-agent", execution -> execution.goal("prepare-agent"));
                plugin.execution("report", execution -> {
                    execution.goal("report");
                    execution.phase("test");
                });
            };

            build.plugins().add("org.jacoco", "jacoco-maven-plugin", builderConsumer);
        };
    }

}
