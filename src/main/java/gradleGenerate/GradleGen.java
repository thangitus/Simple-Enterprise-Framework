package gradleGenerate;

import generator.Generatable;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class GradleGen implements Generatable {

    @Override
    public void generate(File directory) {
        File source = new File("src\\main\\java\\gradleGenerate\\template");
        try {
            FileUtils.copyDirectory(source, directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
