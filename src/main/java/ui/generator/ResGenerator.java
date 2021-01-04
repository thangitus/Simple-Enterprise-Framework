package ui.generator;

import generator.Generatable;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ResGenerator implements Generatable {
    @Override
    public void generate(File directory) {
        File source = new File("src\\main\\resources");

        File resourcesFolder = new File(directory.getAbsolutePath() + "\\src\\main\\resources");

        try {
            FileUtils.copyDirectory(source, resourcesFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ResGenerator().generate(new File("C:\\Users\\nghia\\Desktop\\KHTN-4-1\\OOP\\hibernate"));
    }
}
