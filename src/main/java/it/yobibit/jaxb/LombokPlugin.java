package it.yobibit.jaxb;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import lombok.*;
import org.xml.sax.ErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LombokPlugin extends Plugin  {

    public static final String OPTION_NAME = "Xlombok";
    private final Command defaultCommand;
    private final Map<String,Command> commands = new HashMap<>();

    public LombokPlugin() {
        defaultCommand = new LombokCommand("Data", Data.class);

        addCommand(defaultCommand);
        addLombokCommand("Getter", Getter.class);
        addLombokCommand("Setter", Setter.class);
        addLombokCommand("GetterSetter", Getter.class, Setter.class);
        addLombokCommand("ToString", ToString.class);
        addLombokCommand("EqualsAndHashCode", EqualsAndHashCode.class);
        addLombokCommand("AllArgsConstructor", AllArgsConstructor.class);
        addLombokCommand("Builder", Builder.class);

        addCommand(new Command("removeGeneratedSourceSetters", "remove Setters from JAXB generated sources") {
            @Override
            public void editGeneratedClass(JDefinedClass generatedClass) {
                removeGeneratedSourceSetters(generatedClass);
            }
        });
    }

    private void addLombokCommand(String name, Class ... lombokAnnotation) {
        addCommand(new LombokCommand(name, lombokAnnotation));
    }

    private void addCommand(Command command) {
        commands.put(command.getParameter(), command);
    }

    @Override
    public String getOptionName() {
        return OPTION_NAME;
    }

    @Override
    public String getUsage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  -").append(OPTION_NAME).append(":  add Lombok annotations \n");
        for (Command command : commands.values()) {
            stringBuilder.append(command.getUsage()).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public int parseArgument(Options opt, String[] args, int i) {
        String arg = args[i].trim();
        Command command = commands.get(arg);
        if (command != null) {
            command.setEnabled(true);
            return 1;
        }
        return 0;
    }

    @Override
    public boolean run(Outline outline, Options options, ErrorHandler errorHandler) {
        // for each generated classes
        for (ClassOutline classOutline : outline.getClasses()) {

            boolean commandExecuted = false;
            for (Command command : commands.values()) {
                if (command.isEnabled()) {
                    command.editGeneratedClass(classOutline.implClass);
                    commandExecuted = true;
                }
            }

            if (!commandExecuted) {
                defaultCommand.editGeneratedClass(classOutline.implClass);
            }
        }
        return true;
    }

    private void removeGeneratedSourceSetters(JDefinedClass generatedClass) {
        List<JMethod> setters = new ArrayList<>();
        // find methods to remove
        for (JMethod method : generatedClass.methods()) {
            if (method.name().startsWith("set")) {
                // TODO check return type void?
                setters.add(method);
            }
        }

        // remove methods
        for (JMethod method : setters) {
            generatedClass.methods().remove(method);
        }
    }
}