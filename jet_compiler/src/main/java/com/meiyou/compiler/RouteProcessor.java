package com.meiyou.compiler;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({ "com.meiyou.annotation.JUri"})
public class RouteProcessor extends  AbstractProcessor{

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
