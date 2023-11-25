package com.yuanxiaotu.config.datasource.aop;

import com.yuanxiaotu.config.datasource.enums.DataSourceEnum;
import com.yuanxiaotu.config.datasource.utils.DynamicDataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author xiaotianyu
 */
@Component
@Aspect
@Slf4j(topic = "dynamic-datasource")
public class DynamicDataSourceAnnotationAspect {

    @Around("@annotation(com.yuanxiaotu.config.datasource.annotation.Master)")
    public Object doAroundMaster(ProceedingJoinPoint joinPoint) throws Exception {
        Object res = null;
        try {
            DynamicDataSourceContext.set(DataSourceEnum.MASTER);
            log.info("当前数据源切换为：master");
            res = joinPoint.proceed();
        } catch (Throwable e) {
            throw new Exception(e.getMessage());
        } finally {
            DynamicDataSourceContext.remove();
        }
        return res;
    }


    @Around("@annotation(com.yuanxiaotu.config.datasource.annotation.Slave)")
    public Object doAroundSlave(ProceedingJoinPoint joinPoint) throws Exception {
        Object res = null;
        try {
            DynamicDataSourceContext.set(DataSourceEnum.SLAVE);
            log.info("当前数据源切换为：slave");
            res = joinPoint.proceed();
        } catch (Throwable e) {
            throw new Exception(e.getMessage());
        } finally {
            DynamicDataSourceContext.remove();
        }
        return res;
    }
}
