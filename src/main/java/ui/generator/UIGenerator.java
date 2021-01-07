package ui.generator;

import generator.Generatable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UIGenerator implements Generatable {
    String demoByFirstTable;

    public UIGenerator(String demoByFirstTable) {
        this.demoByFirstTable = demoByFirstTable;
    }

    @Override
    public void generate(File directory) {
        Path path = Paths.get("src/main/template/ui/MainTemplate.txt");
        StringBuilder builder = new StringBuilder("");

        // Read persistence template file
        try {
            Files.lines(path).forEach((line) -> {
                builder.append(line + "\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        String finalPersistenceContent = builder.toString();

        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%demo%", demoByFirstTable.toLowerCase());


        try {
            FileWriter myWriter = new FileWriter(directory);
            myWriter.write(finalPersistenceContent);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
