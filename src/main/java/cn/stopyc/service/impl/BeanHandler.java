package cn.stopyc.service.impl;

import cn.stopyc.service.IResultSetHandler;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;

/**
 * @program: Dream01
 * @description: IResultSetHandler的实现类之一, 返回一个javaBean
 * @author: stop.yc
 * @create: 2022-03-29 22:15
 **/
public class BeanHandler<T> implements IResultSetHandler<T> {
    private Class<T> clazz;

    /**获取传进来的类对象的字节码信息*/
    public BeanHandler(Class<T> clazz) {
        this.clazz = clazz;
    }


    @Override
    public T handle(ResultSet rs) throws Exception {
        //如果查到了东西
        if (rs.next()) {
            //首先先根据字节码信息创建对象,等会返回
            T obj = clazz.newInstance();
            //再拿到类的信息
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
            //拿到类中的属性描述器
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            //遍历描述器,进行获取查询结果的对象信息进行封装
            for (PropertyDescriptor pd : pds) {
                //获取查询结果集的对应字段的信息
                String name = pd.getName();

                Object object = rs.getObject(pd.getName());

                //通过调用setter写进信息
                pd.getWriteMethod().invoke(obj,object);
            }
            return obj;
        }
        return null;

    }
}
