package com.system.bhouse.bhouse.Annotation.ClassRun;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018-07-05.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Annotation.ClassRun
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface BindView {
    int value() default 1;
}
