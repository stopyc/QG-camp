package cn.stopyc.service.impl;

import cn.stopyc.service.IResultSetHandler;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: Dream01
 * @description: IResultSetHandler的实现类之一, 返回一个javaBean集合
 * @author: stop.yc
 * @create: 2022-03-29 22:28
 **/
public class BeanListHandle<T> implements IResultSetHandler<List<T>> {
    private Class<T> clazz;

    /**
        获取当前对象的字节码信息
     *
     */
    public BeanListHandle(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> handle(ResultSet rs) throws Exception {

        //通过字节码信息获取类的信息
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
        //再通过类获取属性描述器,比如getter,setter这些
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        //类的列表
        List<T>list = new ArrayList<>();
        //进行遍历
        while(rs.next()) {
            //通过对象信息创建对象
            T obj = clazz.newInstance();

            for (PropertyDescriptor pd : pds) {
                //获取对应字段的信息
                Object object = rs.getObject(pd.getName());
                //获取setter
                pd.getWriteMethod().invoke(obj,object);
            }
            list.add(obj);
        }
        return list;
    }

}
