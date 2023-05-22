package edu.obya.blueprint.config;

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

@Configuration
public class WebValidationConfiguration {

    @Bean
    public Filter validationFilter() {
        return new OpenApiValidationFilter(true, false);
    }

    @Bean
    public WebMvcConfigurer addOpenApiValidationInterceptor(OpenApiValidationInterceptor interceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(final @NonNull InterceptorRegistry registry) {
                registry.addInterceptor(interceptor);
            }
        };
    }

    @Bean
    public OpenApiValidationInterceptor openApiValidationInterceptor(OpenApiInteractionValidator validator) {
        return new OpenApiValidationInterceptor(validator);
    }

    @Bean
    public OpenApiInteractionValidator openApiInteractionValidator(@Value("${openapi.spec.url}") final String specificationUrl) {
        return OpenApiInteractionValidator.createForSpecificationUrl(specificationUrl)
                .withLevelResolver(SpringMVCLevelResolverFactory.create())
                .build();
    }
}
