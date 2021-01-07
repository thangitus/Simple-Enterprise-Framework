package orm.config;

import generator.Generatable;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class PersistenceConfig implements Generatable {
    private List<String> entityClasses;
    private String user;
    private String passWord;
    private String jdbcDriver;
    private String url;

    public PersistenceConfig() {

    }

    public PersistenceConfig(List<String> entityClasses, String user, String passWord, String jdbcDriver, String url) {
        this.entityClasses = entityClasses;
        this.user = user;
        this.passWord = passWord;
        this.jdbcDriver = jdbcDriver;
        this.url = url;
    }

    @Override
    public void generate(File directory) {
        Path path = Paths.get("src\\main\\resources\\template\\PersistenceTemplate.txt");
        StringBuilder builder = new StringBuilder("");

        // Read persistence template file
        try {
            Files.lines(path).forEach((line) -> {
                builder.append(line + "\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert entity classes name to right format
        String entities = entityClasses.stream().map(classEntity -> "<class>entity." + classEntity + "</class>\n")
                                       .reduce("", (a, b) -> a + b);

        String finalPersistenceContent = builder.toString();

        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%entity%", entities);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%jdbcDriver%", jdbcDriver);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%user%", user);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%passWord%", passWord);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%url%", url);

        try {
            FileWriter myWriter = new FileWriter(directory + "\\persistence.xml");
            myWriter.write(finalPersistenceContent);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getEntityClasses() {
        return entityClasses;
    }

    public void setEntityClasses(List<String> entityClasses) {
        this.entityClasses = entityClasses;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }
}
