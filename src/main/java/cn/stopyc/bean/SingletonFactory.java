package cn.stopyc.bean;

import cn.stopyc.dao.TaskDao;
import cn.stopyc.dao.impl.TaskDaoImpl;
import cn.stopyc.dao.impl.UserDaoImpl;
import cn.stopyc.service.impl.TaskServiceImpl;
import cn.stopyc.service.impl.UserServiceImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @program: qg
 * @description: 单例管理类
 * @author: stop.yc
 * @create: 2022-04-17 11:22
 **/
public class SingletonFactory {

    private static UserServiceImpl userServiceSingleton;
    private static TaskServiceImpl taskServiceSingleton;

    private static UserDaoImpl userDaoSingleton;
    private static TaskDaoImpl taskDaoSingleton;

    static {
        try {

            userServiceSingleton = (UserServiceImpl)getSingleByName(UserServiceImpl.class.getName());
            taskServiceSingleton = (TaskServiceImpl)getSingleByName(TaskServiceImpl.class.getName());

            userDaoSingleton = (UserDaoImpl)getSingleByName(UserDaoImpl.class.getName());
            taskDaoSingleton = (TaskDaoImpl)getSingleByName(TaskDaoImpl.class.getName());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object getSingleByName(String name) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        //反射获取class字节码
        Class<?> aClass = Class.forName(name);
        //根据字节码中的构造方法获取构造器
        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
        //设置该单例是否允许被创建
        declaredConstructor.setAccessible(true);
        //新建实例

        return declaredConstructor.newInstance();
    }

    /**
     * 调用并返回单例
     * @return
     */
    public static UserServiceImpl getUserServiceSingleton() {
        return userServiceSingleton;
    }
    public static TaskServiceImpl getTaskServiceSingleton() {
        return taskServiceSingleton;
    }


    public static UserDaoImpl getUserDaoSingleton() {
        return userDaoSingleton;
    }
    public static TaskDaoImpl getTaskDaoSingleton() {
        return taskDaoSingleton;
    }

}
