package com.yuanxiaotu.config.datasource.annotation;

import java.lang.annotation.*;

/**
 * @author xiaotianyu
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Slave {
}
