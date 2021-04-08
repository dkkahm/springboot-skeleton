package com.example.homegym.interceptor;

import com.example.homegym.exceptions.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LanguageInterceptor implements HandlerInterceptor {
    @Value("${homegym.defaults.language}")
    String defaultLanguage;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String language = request.getHeader("Accept-Language");
        if(language == null) {
            language = defaultLanguage;
        } else {
            int indexOfBreak = language.indexOf('-');
            if(indexOfBreak != -1) {
                language = language.substring(0, indexOfBreak);
            }
        }
        request.setAttribute("language", language);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
