package com.jet.compiler;

import com.google.auto.service.AutoService;
import com.google.gson.Gson;
import com.jet.annotation.JUri;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 * 直接Build，由于Gradle的配置，代码不变，APT可能不执行，要执行clean + build;
 *
 * @author zhengxiaobin
 * @since 17/7/13
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.jet.annotation.JUri"})
public class RouterProcessor extends AbstractProcessor {
    //测试保存在META-INF里面
    static final String METADATA_PATH = "META-INF/spring-configuration-metadata.json";
    public static final String ASSET_JSON = "assets/router/module.json";

    public static final String ASSET_PATH = "assets/router/";
    public static final String FILE_SUFFIX = ".json";

    public static final String PkgName = "com.jet.router.data";
    public static final String ClassName = "RouterTable";

    /**
     * APT 默认目录
     */
    Filer filer;
    private Types types;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        types = processingEnvironment.getTypeUtils();

    }

    /**
     * @param annotations      所有支持的Annotation
     * @param roundEnvironment 当前环境
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        try {
            //APT 会执行多轮，需要过滤掉
            if (annotations == null || annotations.isEmpty()) {
                System.out.println(">>> annotations is null... <<<");
                return true;
            }

            HashMap<String, String> map = new HashMap<>();
            for (TypeElement annotation : annotations) {

                Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(annotation);
                for (Element element : elements) {
                    JUri uri = element.getAnnotation(JUri.class);
                    TypeElement typeElement = (TypeElement) element;

                    String value = uri.value();
                    String[] array = uri.array();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(value);
                    list.addAll(Arrays.asList(array));
                    String clazzName = typeElement.getQualifiedName().toString();
                    for (String key : list) {
                        //避免Key是空的情况
                        if (key.length() == 0) {
                            break;
                        }
                        map.put(key, clazzName);
                    }

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
     * http://blog.csdn.net/crazy1235/article/details/51876192
     * http://blog.csdn.net/qq_26376637/article/details/52374063
     *
     * @param map
     * @throws Exception
     */
    private void createJava(HashMap<String, String> map) throws Exception {
        String content = new Gson().toJson(map);
        //打印出内容
        System.out.println(">>> content:... <<<   " + content);
        writeFile(content);
    }

    /**
     * 生成JSON文件保存到Assets里面
     *
     * @param content
     * @throws Exception
     */
    private void writeFile(String content) throws Exception {
        FileObject fileObject = createResource();
//        FileObject fileObject = createSourcePath();

        Writer writer = fileObject.openWriter();
        writer.write(content);
        writer.close();
//        System.out.println("Done");
    }

    /**
     * 获取Resource地址
     *
     * @return
     * @throws IOException
     */
    private FileObject createResource() throws IOException {
        String string = types.toString();
        //使用HashCode作为文件名字，避免冲突
        int hashCode = types.hashCode();
//        System.out.println("typename:  " + string + "   hashCode: " + hashCode);

        String path = ASSET_PATH + hashCode + FILE_SUFFIX;
//        String path = ASSET_JSON;
//        String path =METADATA_PATH;
        FileObject resource = filer
                .createResource(StandardLocation.CLASS_OUTPUT, "", path);
        return resource;
    }

    /**
     * 生成Java 源代码；
     *
     * @param map
     */
    @Deprecated
    private void createSource(HashMap<String, String> map) throws Exception {
        CodeBlock.Builder builder = CodeBlock.builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String clazz = entry.getValue();

//            builder.add("$T.createBean($S, $S);", RouterBean.class, key, clazz);
        }
        CodeBlock codeBlock = builder.build();


        FieldSpec field = FieldSpec.builder(HashMap.class, "map", Modifier.PUBLIC, Modifier.STATIC)
                                   .initializer(CodeBlock.of("new HashMap()"))
                                   .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(ClassName + "$$1")
                                    .addModifiers(Modifier.PUBLIC)
                                    .addStaticBlock(codeBlock)
                                    .addField(field)
                                    .build();
        JavaFile javaFile = JavaFile.builder(PkgName, typeSpec).build();
        //打印
//        javaFile.writeTo(System.out);
//        String content = javaFile.toString();
        javaFile.writeTo(filer);
    }

    /**
     * 获取源代码路径
     *
     * @return
     * @throws IOException
     */
    @Deprecated
    private FileObject createSourcePath() throws IOException {
//        String string = types.toString();
//        int hashCode = types.hashCode();
//        System.out.println("typename:  "+string+"   hashCode: " + hashCode);

        FileObject resource = filer
                .createSourceFile("com.test.go." + "RouterTable" + "$$1");
        return resource;
    }


}
