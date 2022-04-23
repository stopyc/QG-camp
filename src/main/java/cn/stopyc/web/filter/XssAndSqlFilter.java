package cn.stopyc.web.filter;

import cn.stopyc.bean.XssHttpServletRequestWrapperFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter("/*")
//public class XssAndSqlFilter implements Filter {
//    FilterConfig filterConfig = null;
//
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        //不需要拦截的地址
//        String[] urls = {"/login.html","user","/js/","/element-ui/","/register.html","/img/"};
//        //请求的地址
//        String url = request.getRequestURL().toString();
//        for (String s : urls) {
//            if (url.contains(s)) {
//                chain.doFilter(req,resp);
//                return;
//            }
//        }
//
//        String queryString = request.getQueryString();
//
//        chain.doFilter(new XssHttpServletRequestWrapperFilter(
//                (HttpServletRequest) request), resp);
//
//
//    }
//
//    @Override
//    public void destroy() {
//        this.filterConfig = null;
//    }
//
//    @Override
//    public void init(FilterConfig config) throws ServletException {
//        this.filterConfig = config;
//    }
//
//}
