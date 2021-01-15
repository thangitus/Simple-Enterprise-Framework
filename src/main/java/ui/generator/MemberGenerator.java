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

public class MemberGenerator implements Generatable {
    String demo;

    public MemberGenerator(String demo) {
        this.demo = demo;
    }

    @Override
    public void generate(File directory) {
        Path loginPath = Paths.get("src\\main\\template\\ui\\LoginSceneTemplate.java");
        File register = new File("src\\main\\template\\ui\\RegisterSceneTemplate.java");

        File targetLoginFile = new File(directory.getAbsolutePath() + "\\src\\main\\java\\ui\\scene\\LoginSceneTemplate.java");
        File targetRegisterFile = new File(directory.getAbsolutePath() + "\\src\\main\\java\\ui\\scene\\RegisterSceneTemplate.java");


        StringBuilder builder = new StringBuilder("");

        // Read persistence template file
        try {
            Files.lines(loginPath).forEach((line) -> {
                builder.append(line + "\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        String finalPersistenceContent = builder.toString();

        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%demo%", demo);


        try {
            FileWriter myWriter = new FileWriter(targetLoginFile);
            myWriter.write(finalPersistenceContent);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            targetRegisterFile.createNewFile();
            FileUtils.copyFile(register, targetRegisterFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
