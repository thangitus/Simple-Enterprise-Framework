package ui.generator;

import generator.Generatable;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FXMLGenerator implements Generatable {
    private String table;
    //private List<String> listEntity; //TODO Gen sau
    private List<String> listField;

    public FXMLGenerator(String table, List<String> listEntity, List<String> listField) {
        this.table = table;
        //this.listEntity = listEntity;
        this.listField = listField;
    }

    @Override
    public void generate(File directory) {
        Path path = Paths.get("src/main/template/Main.txt");
        StringBuilder builder = new StringBuilder("");

        // Read persistence template file
        try {
            Files.lines(path).forEach((line) -> {
                builder.append(line + "\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

//        listEntity.stream().map().reduce("")
        // Convert entity classes name to right format
        String strField = listField
                .stream()
                .map(field ->
                                "<JFXTextField fx:id=\"EDT" + field + "\" focusColor=\"#006a8b\" labelFloat=\"true\" promptText=\"" + field + "\">" + "\n"
                                                    +"<VBox.margin>" +"\n"
                                                    +    "<Insets bottom=\"24.0\" />" +"\n"
                                                    +"</VBox.margin>" + "\n"
                                                +"</JFXTextField>"
                )
                .reduce("", (a, b) -> a + b);

        String finalPersistenceContent = builder.toString();

        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%field%", strField);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%table%", table);

        try {
            FileWriter myWriter = new FileWriter(directory);
            myWriter.write(finalPersistenceContent);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
