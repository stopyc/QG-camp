package cn.stopyc.web.filter;

import cn.stopyc.constant.SessionConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class LoginFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        resp.setContentType("text/json;charset=utf-8");

        //不需要拦截的地址
        String[] urls = {"/login.html","user","/js/","/element-ui/","/register.html","/img/"};
        //请求的地址
        String url = request.getRequestURL().toString();
        for (String s : urls) {
            if (url.contains(s)) {
                chain.doFilter(req,resp);
                return;
            }
        }

        HttpSession session = request.getSession();
        System.out.println("username");
        Object user = session.getAttribute("username");

        if (user == null) {
            //没有就拦截,回退到登录页面
            request.getRequestDispatcher("/login.html").forward(request,resp);
        }else {
            //登录过了,就放行
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
