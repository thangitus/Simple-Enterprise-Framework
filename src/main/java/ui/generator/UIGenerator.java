package ui.generator;

import generator.Generatable;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class UIGenerator implements Generatable {
    @Override
    public void generate(File directory) {
        File source = new File("src\\main\\java\\ui\\MainTemplate.java");
        File resourcesFolder = new File(directory.getAbsolutePath() + "\\src\\main\\java\\ui");

        try {
            FileUtils.copyFileToDirectory(source, resourcesFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
