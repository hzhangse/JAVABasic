package com.train.jdk16.annotation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("PluggableAPT.ToBeTested")//������"*"��ʾ֧������Annotations
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyAnnotationProcessor extends AbstractProcessor {
    private void note(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
	 //   System.out.println(msg);
    }
    /*
     * (non-Javadoc)
     * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //annotations��ֵ��ͨ��@SupportedAnnotationTypes��������Ŀ��Դ����ӵ�е�����Annotations
        for(TypeElement te:annotations){
            note("annotation:"+te.toString());
        }
        Set<? extends Element> elements = roundEnv.getRootElements();//��ȡԴ�����ӳ�����
        for(Element e:elements){
            //��ȡԴ�������ĳ�Ա
            List<? extends Element> enclosedElems = e.getEnclosedElements();
            //���·�����Ա,���˵�������Ա
            List<? extends ExecutableElement> ees = ElementFilter.methodsIn(enclosedElems);
            for(ExecutableElement ee:ees){
                note("--ExecutableElement name is "+ee.getSimpleName());
                List<? extends AnnotationMirror> as = ee.getAnnotationMirrors();//��ȡ������Annotations
                note("--as="+as); 
                for(AnnotationMirror am:as){
                    //��ȡAnnotation��ֵ
                    Map<? extends ExecutableElement, ? extends AnnotationValue> map= am.getElementValues();
                    Set<? extends ExecutableElement> ks = map.keySet();
                    for(ExecutableElement k:ks){//��ӡAnnotation��ÿ��ֵ
                        AnnotationValue av = map.get(k);
                        note("----"+ee.getSimpleName()+"."+k.getSimpleName()+"="+av.getValue());
                    }
                }
            }
        }
        return false;
    }
}
