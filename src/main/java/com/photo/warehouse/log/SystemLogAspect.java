package com.photo.warehouse.log;

import com.photo.warehouse.biz.backstage.BaseLogBiz;
import com.photo.warehouse.model.backstage.BaseLog;
import com.photo.warehouse.util.ObjectRestResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * @Description: 定义日志切入类
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:05
 * @Version: 1.0
 */
@Aspect
@Component
@Order(-5)
public class SystemLogAspect {
    @Autowired
    private BaseLogBiz baseLogBiz;

    /***
     * 定义controller切入点拦截规则，拦截SystemControllerLog注解的方法
     */
    @Pointcut("@annotation(com.photo.warehouse.log.SystemControllerLog)")
    public void controllerAspect(){}

    /***
     * 拦截控制层的操作日志
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public ObjectRestResponse recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        BaseLog baseLog = new BaseLog();
        Object proceed = null ;
        //获取session中的用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().getAttribute("user");
//        systemLog.setUserid("vesus");
        //获取请求的ip
        String ip = request.getRemoteAddr();
        //获取执行的方法名
//        System.out.println(joinPoint.getSignature().getName());

        //获取方法执行前时间
        Date date=new Date();
        proceed = joinPoint.proceed();
        ObjectRestResponse restResponse = (ObjectRestResponse) proceed;
        if (restResponse.getResultCode().equals(ReturnCode.RES_SUCCESS)){
            baseLog.setLogcat("info");
            baseLog.setMessage(restResponse.getMessage());
            baseLog.setUserid("userId");
            baseLog.setUsername("userName");
            baseLog.setClientip(ip);
            baseLog.setOperatemodel(getControllerMethodDescription(joinPoint));
            baseLog.setOperateact(restResponse.getActionType());
//            //获取执行方法的注解内容
//            System.out.println("获取执行方法的注解内容");
//            System.out.println(getControllerMethodDescription(joinPoint)+":"+restResponse.getMessage());
//            System.out.println("logtype:不写");
//            System.out.println("logcat:info");
//            System.out.println("message:" + restResponse.getMessage());
//            System.out.println("exception:无数据");
//            System.out.println("userId:1");
//            System.out.println("userName:userName");
//            System.out.println("allPathName:不写");
//            System.out.println("clientip"+ip);
//            System.out.println("operateurl:不写");
//            System.out.println("operatemodel："+getControllerMethodDescription(joinPoint));
//            System.out.println("operateact："+restResponse.getActionType());
        }else{
            baseLog.setLogcat("error");
            baseLog.setMessage(restResponse.getMessage());
            baseLog.setException(restResponse.getException());
            baseLog.setUserid("userId");
            baseLog.setUsername("userName");
            baseLog.setClientip(ip);
            baseLog.setOperatemodel(getControllerMethodDescription(joinPoint));
            baseLog.setOperateact(restResponse.getActionType());
//            System.out.println("logtype:不写");
//            System.out.println("logcat:error");
//            System.out.println("message:" + restResponse.getMessage());
//            System.out.println("exception:"+restResponse.getException());
//            System.out.println("userId:1");
//            System.out.println("userName:userName");
//            System.out.println("allPathName:不写");
//            System.out.println("clientip"+ip);
//            System.out.println("operateurl:不写");
//            System.out.println("operatemodel："+getControllerMethodDescription(joinPoint));
//            System.out.println("operateact："+restResponse.getActionType());
        }
       List<String> stringList =  restResponse.getStringList();
        String returnStr = "" ;
        for (String id : stringList) {
           returnStr +=id +",";
        }
        baseLog.setOperatecomtent(returnStr);
        baseLog.setOperatedate(date);
//        System.out.println("operatecomtent:"+returnStr);
//        System.out.println("operatedate:"+date);
//        System.out.println("operatemark:无备注");
        baseLogBiz.insertSelective(baseLog);
        return restResponse ;
    }

    /***
     * 获取controller的操作信息
     * @param point
     * @return
     */
    public String getControllerMethodDescription(ProceedingJoinPoint point) throws  Exception{
        //获取连接点目标类名
        String targetName = point.getTarget().getClass().getName() ;
        //获取连接点签名的方法名
        String methodName = point.getSignature().getName() ;
        //获取连接点参数
        Object[] args = point.getArgs() ;
        //根据连接点类的名字获取指定类
        Class targetClass = Class.forName(targetName);
        //获取类里面的方法
        Method[] methods = targetClass.getMethods() ;
        String description="" ;
        for (Method method : methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length){
                    description = method.getAnnotation(SystemControllerLog.class).descrption();
                    break;
                }
            }
        }
        return description ;
    }
}
