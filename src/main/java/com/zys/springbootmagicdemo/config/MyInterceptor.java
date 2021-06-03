package com.zys.springbootmagicdemo.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.ssssssss.magicapi.interceptor.SQLInterceptor;
import org.ssssssss.magicapi.model.RequestEntity;
import org.ssssssss.magicapi.modules.BoundSql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截器配置
 */
@Component
public class MyInterceptor implements SQLInterceptor, HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${isAllowed}")
    private Boolean isAllowed;

    /***
     * 自定义SQL拦截器,打印sql及参数
     * @param boundSql
     * @param requestEntity
     */
    @Override
    public void preHandle(BoundSql boundSql, RequestEntity requestEntity) {
        logger.warn("执行的SQL===> " + boundSql.getSql());
        logger.warn("执行的SQL参数===> " + Arrays.toString(boundSql.getParameters()));
    }

    /**
     * 配置资源拦截器，开发页面只能在开发中使用，在正式环境拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!isAllowed) {
            Map<String, Object> result = new HashMap<>();
            result.put("msg", "抱歉，未找到可访问资源！");
            result.put("status", false);
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(result));
            return false;
        }
        return true;
    }
}