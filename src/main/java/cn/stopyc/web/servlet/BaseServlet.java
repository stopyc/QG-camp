package cn.stopyc.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: qg-engineering-management-system
 * @description: BaseServlet, 统一管理servlet
 * @author: stop.yc
 * @create: 2022-04-16 12:30
 **/
public class BaseServlet extends HttpServlet {
    //统一,看看要调哪一个servlet
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.先获取请求路径
        String requestURI = req.getRequestURI();
        //2.获取请求路径的最后的方法名称
        int index = requestURI.lastIndexOf('/');
        String methodName = requestURI.substring(index + 1);
        //3.获取调用对象的字节码文件
        Class<? extends BaseServlet> clazz = this.getClass();
        try {
            Method method = clazz.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this, req,resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
