package it.yobibit.jaxb;

import com.sun.codemodel.JDefinedClass;

import java.lang.annotation.Annotation;

public class LombokCommand extends Command {

    private final Class<? extends Annotation>[] lombokAnnotations;

    public LombokCommand(String name, Class<? extends Annotation> ... lombokAnnotations) {
        super(name, "add Lombok @" + name + " annotation");
        this.lombokAnnotations = lombokAnnotations;
    }

    @Override
    public void editGeneratedClass(JDefinedClass generatedClass) {
        for (Class<? extends Annotation> lombokAnnotation : lombokAnnotations) {
            generatedClass.annotate(lombokAnnotation);
        }
    }
}