package com.hyphen.annotation;

public @interface CustomTag {
    String name() default "";
    String description() default "";
}
