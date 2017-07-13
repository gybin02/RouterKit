package com.meiyou.compiler;

import com.google.auto.service.AutoService;
import com.meiyou.annotation.AutoParcel;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.example.AutoParcel")
public final class AutoParcelProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Collection<? extends Element> annotatedElements =
                roundEnvironment.getElementsAnnotatedWith(AutoParcel.class);
        List<TypeElement> typesIn = ElementFilter.typesIn(annotatedElements);

//        List<TypeElement> types =
//                new ArrayList<TypeElement>().Builder<TypeElement>()
//                        .addAll(ElementFilter.typesIn(annotatedElements))
//                        .build();

        for (TypeElement type : typesIn) {
            processType(type);
        }

        // 返回 true ，其他处理器不关心 AutoParcel  注解
        return true;
    }

    private void processType(TypeElement type) {
//        String className = generatedSubclassName(type);
//        String source = generateClass(type, className);
//        writeSourceFile(className, source, type);
    }

    private void writeSourceFile(
            String className,
            String text,
            TypeElement originatingType) {
        try {
            JavaFileObject sourceFile =
                    processingEnv.getFiler().
                            createSourceFile(className, originatingType);
//            Writerwriter = sourceFile.openWriter();
//            try {
//                writer.write(text);
//            } finally {
//                writer.close();
//            }
        } catch (IOException e) {// silent}
        }
    }
}