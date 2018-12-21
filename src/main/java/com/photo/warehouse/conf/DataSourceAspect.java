//package com.photo.warehouse.conf;
//
//import com.photo.warehouse.biz.backstage.PhotoInfoBiz;
//import com.photo.warehouse.biz.backstage.PhotoSortBiz;
//import com.photo.warehouse.database.DatabaseContextHolder;
//import com.photo.warehouse.database.DatabaseType;
//import com.photo.warehouse.mapper.backstage.PhotoInfoMapper;
//import com.photo.warehouse.mapper.backstage.PhotoSortMapper;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
///**
// * Created by CDZ on 2018/12/7.
// */
//@Aspect
//@Component
//public class DataSourceAspect {
//    /**
//     * 使用空方法定义切点表达式
//     */
//    @Pointcut("execution(* com.photo.warehouse.**.**.*(..))")
//    public void declareJointPointExpression() {
//    }
//
//    @Before("declareJointPointExpression()")
//    public void setDataSourceKey(JoinPoint point){
//        //根据连接点所属的类实例，动态切换数据源
//        if (point.getTarget() instanceof PhotoSortBiz
//                || point.getTarget() instanceof PhotoInfoBiz) {
//            DatabaseContextHolder.setDatabaseType(DatabaseType.datasource_1);
//        } else {//连接点所属的类实例是（当然，这一步也可以不写，因为defaultTargertDataSource就是该类所用的mytestdb）
//            DatabaseContextHolder.setDatabaseType(DatabaseType.datasource_2);
//        }
//    }
//}
