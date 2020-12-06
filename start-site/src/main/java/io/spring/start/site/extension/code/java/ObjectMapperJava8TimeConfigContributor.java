package io.spring.start.site.extension.code.java;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.language.SourceStructure;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class ObjectMapperJava8TimeConfigContributor implements ProjectContributor {

    private final IndentingWriterFactory indentingWriterFactory;
    private final ProjectDescription projectDescription;

    @Override
    public void contribute(Path path) throws IOException {

        SourceStructure sourceStructure = this.projectDescription.getBuildSystem()
                .getMainSource(path, this.projectDescription.getLanguage());

        String packageName = projectDescription.getPackageName();

        createObjectMapperConfigClass(sourceStructure, packageName);

    }

    private void createObjectMapperConfigClass(SourceStructure sourceStructure, String packageName) throws IOException {

        packageName = packageName + ".config";

        Path output = sourceStructure.createSourceFile(packageName, "ObjectMapperJava8TimeConfig");
        Files.createDirectories(output.getParent());

        try (IndentingWriter writer = this.indentingWriterFactory.createIndentingWriter("java",
                Files.newBufferedWriter(output))) {

            writer.write("""
                    package $package;
                                    
                    import com.fasterxml.jackson.databind.ObjectMapper;
                    import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
                    import org.springframework.context.annotation.Bean;
                    import org.springframework.context.annotation.Configuration;
                    import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
                    
                    @Configuration
                    public class ObjectMapperJava8TimeConfig {
                    
                        @Bean
                        ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
                    
                            builder.modules(new JavaTimeModule());
                    
                            return builder.createXmlMapper(false).build();
                        }
                    
                    }
                    """.replace("$package", packageName));
        }
    }
}
