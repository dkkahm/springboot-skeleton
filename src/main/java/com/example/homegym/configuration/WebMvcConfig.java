package com.example.homegym.configuration;

import com.example.homegym.interceptor.CheckTokenInterceptor;
import com.example.homegym.interceptor.LanguageInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private CheckTokenInterceptor checkTokenInterceptor;
    private LanguageInterceptor languageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor)
                .addPathPatterns("/test");

        registry.addInterceptor(languageInterceptor)
                .addPathPatterns("/**");
    }
}
