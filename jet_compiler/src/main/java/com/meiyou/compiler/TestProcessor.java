package com.meiyou.compiler;

import com.meiyou.annotation.Test;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

//@AutoService(Processor.class)
//对应getSupportedSourceVersion方法
//@SupportedSourceVersion(SourceVersion.get())
//@SupportedAnnotationTypes({ "com.example.Test" })
public class TestProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> annotations = new LinkedHashSet<>();
        annotations.add(Test.class.getCanonicalName());
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("------ process -----");
        //MethodSpec这个类是要引入'com.squareup:javapoet:1.8.0'包，方便通过代码创建java文件
        MethodSpec main = MethodSpec.methodBuilder("main")
                                    //.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                    .returns(void.class)
                                    .addParameter(String[].class, "args")
                                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                                    .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                                      //netstat -an | grep .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                      .addMethod(main)
                                      .build();

        JavaFile javaFile = JavaFile.builder("com.francis.helloworld", helloWorld)
                                    .build();

        try {
            //这里的输出要在Gradle Console中看
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}