package com.photo.warehouse.conf;

import com.photo.warehouse.biz.backstage.BaseLogBiz;
import com.photo.warehouse.model.backstage.BaseLog;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONString;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Method;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private BaseLogBiz baseLogBiz;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.photo.warehouse.conf.MyLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        System.out.println("切面。。。。。");
        //        //保存日志
        BaseLog baseLog = new BaseLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();
            baseLog.setOperatemodel(value);//保存获取的操作
            System.out.println("功能模块："+ value);
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        System.out.println(className + "." + methodName);
//        sysLog.setMethod();

        //请求的参数
        Object[] args = joinPoint.getArgs();
        System.out.println(args);
        //将参数所在的数组转换成json
//        String params = JSON.toJSONString(args);
//        sysLog.setParams(params);

//        sysLog.setCreateDate(new Date());
        //获取用户名
//        sysLog.setUsername(ShiroUtils.getUserEntity().getUsername());
        //获取用户ip地址
//        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//        sysLog.setIp(IPUtils.getIpAddr(request));
//
//        //调用service保存SysLog实体类到数据库
//        sysLogService.save(sysLog);
    }

}
