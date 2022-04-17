package cn.stopyc.web.filter;

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


        //不需要拦截的地址
        String[] urls = {"/login.html","user","/js/","/element-ui/"};
        //请求的地址
        String url = request.getRequestURL().toString();
        System.out.println(url);
        for (String s : urls) {
            if (url.contains(s)) {
                chain.doFilter(req,resp);
                System.out.println("没有拦截");
                return;
            }
        }

        HttpSession session = request.getSession();

        Object user = session.getAttribute("user");
        System.out.println(user);
        if (user == null) {
            //没有就拦截,回退到登录页面
            System.out.println("执行回退");
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
