package gradleGenerate;

import generator.Generatable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GradleGen implements Generatable {

    private String projectName;

    public GradleGen (String projectName){
        this.projectName = projectName;
    }

    @Override
    public void generate(File directory) {
        File source = new File("src\\main\\resources\\template\\templateGradle");
        try {
            FileUtils.copyDirectory(source, directory);

            Path path = Paths.get(directory.getPath(), "settings.gradle");
            StringBuilder builder = new StringBuilder("");

            try {
                Files.lines(path).forEach((line) -> {
                    builder.append(line + "\n");
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            String settingFinal = builder.toString();

            settingFinal = StringUtils.replace(settingFinal, "%ProjectName%", projectName);

            try {
                FileWriter myWriter = new FileWriter(directory + "\\settings.gradle");
                myWriter.write(settingFinal);
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
