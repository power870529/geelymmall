package com.mmall.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 打印异常堆栈到日志文件
        log.error("{} Exception", request.getRequestURI(), ex);

        // 项目为前后端分离，不需要返回ModelAndView. 将ModelAndView转换成JsonView
        // 这里不使用 MappingJackson2JsonView，是因为pom文件中引用的jackson包为1.x。
        // 如果是2.x版本则需要使用 MappingJackson2JsonView
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());

        // modelAndView 中的key 和我们封装的ServerResponse中的字段保持一致
        modelAndView.addObject("status", ResponseCode.ERROR.getCode());
        modelAndView.addObject("msg", "接口异常，详情请查看服务端日志信息");
        modelAndView.addObject("data", ex.toString());
        return modelAndView;
    }
}
