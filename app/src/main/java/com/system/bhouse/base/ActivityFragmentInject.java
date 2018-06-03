package com.system.bhouse.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.base
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ActivityFragmentInject {
    /**
     * 布局的ID
     */
    int contentViewId() default -1;
}
