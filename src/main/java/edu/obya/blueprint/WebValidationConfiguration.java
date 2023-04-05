package edu.obya.blueprint;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter;
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor;
import com.atlassian.oai.validator.springmvc.SpringMVCLevelResolverFactory;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.io.IOException;

@Configuration
public class WebValidationConfiguration {

    @Bean
    public Filter validationFilter() {
        return new OpenApiValidationFilter(
                false, // enable request validation
                false  // enable response validation
        );
    }

    @Bean
    public WebMvcConfigurer addOpenApiValidationInterceptor(@Value("${openapi.spec.url}") final String specificationUrl) throws IOException {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(final @NonNull InterceptorRegistry registry) {
                registry.addInterceptor(
                        new OpenApiValidationInterceptor(
                                OpenApiInteractionValidator.createForSpecificationUrl(specificationUrl)
                                    .withLevelResolver(SpringMVCLevelResolverFactory.create())
                                    .build()));
            }
        };
    }
}
