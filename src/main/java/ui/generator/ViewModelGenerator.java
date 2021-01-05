package ui.generator;

import generator.Generatable;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ViewModelGenerator implements Generatable {
    String table;
    List<String> listField;

    public ViewModelGenerator(String table, List<String> field) {
        this.table = table;
        this.listField = field;
    }

    @Override
    public void generate(File directory) {
        Path path = Paths.get("src/main/template/ui/ViewModelTemplate.txt");
        StringBuilder builder = new StringBuilder("");

        // Read persistence template file
        try {
            Files.lines(path).forEach((line) -> {
                builder.append(line + "\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        String strField = listField
                .stream()
                .map(field ->
                        "private SimpleStringProperty "+field+" = new SimpleStringProperty();\n\t"
                )
                .reduce("", (a, b) -> a + b);

        String support = listField
                .stream()
                .map(field ->
                        "public String get"+field+"() {\n" +
                                "        return "+field+".get();\n" +
                                "    }\n"+
                                "    public SimpleStringProperty "+field+"Property() {\n" +
                                "        return "+field+";\n" +
                                "    }\n" +
                                "    public void set"+field+"(String field1) {\n" +
                                "        this."+field+".set(field1);\n" +
                                "    }\n\n\t"
                )
                .reduce("", (a, b) -> a + b);

        String strConstructorBegin = "public "+table+"ViewModel";
        String strConstructorParams = listField
                .stream()
                .map(field ->
                        "String " + field +", "
                )
                .reduce("", (a, b) -> a + b);
        strConstructorParams = strConstructorParams.substring(0, strConstructorParams.length() - 2);
        System.out.println(strConstructorParams);
        String strConstructorSet = listField
                .stream()
                .map(field ->
                        "       this."+field+".set("+field+");\n"
                )
                .reduce("", (a, b) -> a + b);

        String strConstructor =  strConstructorBegin + "(" + strConstructorParams + "){\n"
                + strConstructorSet
                + "\t}";

        System.out.println(strConstructor);

        String finalPersistenceContent = builder.toString();

        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%table%", table);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%support%", support);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%constructor%", strConstructor);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%field%", strField);

        try {
            FileWriter myWriter = new FileWriter(directory);
            myWriter.write(finalPersistenceContent);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
