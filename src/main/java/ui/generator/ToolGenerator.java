package ui.generator;

import generator.Generatable;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ToolGenerator implements Generatable {
    @Override
    public void generate(File directory) {
        File source = new File("src\\main\\template\\tool");

        File resourcesFolder = new File(directory.getAbsolutePath() + "\\src\\main\\java\\ui\\tool");

        try {
            FileUtils.copyDirectory(source, resourcesFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
