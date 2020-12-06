package io.spring.start.site.extension.dependency.web;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class WebBuildCustomizer implements BuildCustomizer<Build> {

    @Override
    public void customize(Build build) {

        if (!build.dependencies().has("validation")) {

            build.dependencies().add("validation",
                    Dependency.withCoordinates("org.springframework.boot", "spring-boot-starter-validation"));
        }
    }

}
