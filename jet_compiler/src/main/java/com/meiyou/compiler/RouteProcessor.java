package com.meiyou.compiler;

import com.google.auto.service.AutoService;
import com.meiyou.annotation.JUri;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.meiyou.annotation.JUri"})
public class RouteProcessor extends AbstractProcessor {

    /**
     * @param annotations      所有支持的Annotation
     * @param roundEnvironment 当前环境
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        try {
            HashMap<String, TypeElement> map = new HashMap<>();
            for (TypeElement annotation : annotations) {
                Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(annotation);
                for (Element element : elements) {
                    JUri uri = element.getAnnotation(JUri.class);
                    TypeElement typeElement = (TypeElement) element;

                    String value = uri.value();
//                String clazzName = typeElement.getQualifiedName().toString();
                    map.put(value, typeElement);
                }
            }
            //生成Java代码
            createJava(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * javapoet 🔚介绍
     * <p>
     * http://www.jianshu.com/p/95f12f72f69a
     * http://www.jianshu.com/p/76e9e3a8ec0f
     *http://blog.csdn.net/crazy1235/article/details/51876192
     * http://blog.csdn.net/qq_26376637/article/details/52374063
     * @param map
     * @throws Exception
     */
    private void createJava(HashMap<String, TypeElement> map) throws Exception {
        CodeBlock.Builder builder = CodeBlock.builder();
        for (Map.Entry<String, TypeElement> entry : map.entrySet()) {
            String key = entry.getKey();
            TypeElement element = entry.getValue();
            builder.add("map.put($s, $s);", key, element.getQualifiedName());
        }
        CodeBlock codeBlock = builder.build();

        FieldSpec field = FieldSpec.builder(HashMap.class, "map", Modifier.PUBLIC)
                                   .initializer(CodeBlock.of("new HashMap()")).build();

        TypeSpec typeSpec = TypeSpec.classBuilder("RouteTemp")
                                    .addField(field)
                                    .addStaticBlock(codeBlock)
                                    .build();
        JavaFile javaFile = JavaFile.builder("com.meiyou.temp2", typeSpec).build();
        javaFile.writeTo(System.out);
    }

}
