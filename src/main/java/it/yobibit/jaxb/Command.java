package it.yobibit.jaxb;

import com.sun.codemodel.JDefinedClass;
import lombok.Data;

@Data
public abstract class Command
{
    final String name;
    final String parameter;
    final String usage;
    boolean enabled;

    public Command(String name, String description) {
        this.name = name;
        this.parameter = "-" + LombokPlugin.OPTION_NAME + ":" + name;
        this.usage = "    " + parameter + ":  " + description;
    }

    public abstract void editGeneratedClass(JDefinedClass generatedClass);
}
