package ui.generator;

import generator.Generatable;
import org.apache.commons.lang3.StringUtils;
import ui.ToolUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SceneGenerator implements Generatable {
    String table;
    List<String> listTable;
    List<String> field;

    public SceneGenerator(String table, List<String> listTable, List<String> field) {
        this.table = table;
        this.listTable = listTable;
        this.field = field;
    }

    @Override
    public void generate(File directory) {
        Path path = Paths.get("src/main/template/ui/SceneTemplate.txt");
        StringBuilder builder = new StringBuilder("");

        // Read persistence template file
        try {
            Files.lines(path).forEach((line) -> {
                builder.append(line + "\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        String switchFunction = listTable
                .stream()
                .map(table ->
                        "public void switch_"+table+"_Scene(MouseEvent event) {\n" +
                                "        Platform.runLater(() -> {\n" +
                                "            System.out.println(\"Switch to "+table+"\");\n" +
                                "            FXMLLoader loader = new FXMLLoader(getClass().getResource(\"/fxml/"+table.toLowerCase()+"Scene.fxml\"));\n" +
                                "            Parent root = null;\n" +
                                "            try {\n" +
                                "                root = loader.load();\n" +
                                "            } catch (IOException e) {\n" +
                                "                e.printStackTrace();\n" +
                                "            }\n" +
                                "            Scene scene = new Scene(Objects.requireNonNull(root));\n" +
                                "            scene.setFill(Color.TRANSPARENT);\n" +
                                "            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();\n" +
                                "            stage.setScene(scene);\n" +
                                "        });\n" +
                                "   }\n\n\t"
                )
                .reduce("", (a, b) -> a + b);

        String column = field
                .stream()
                .map(field ->
                        " TreeTableColumn<"+table+"ViewModel, String> col_"+field+" = new JFXTreeTableColumn<>(\""+ ToolUtils.convertProp(field)+"\");\n" +
                                "        col_"+field+".setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().get"+ToolUtils.convertProp(field)+"()));\n\n\t\t"
                )
                .reduce("", (a, b) -> a + b);

        String addColumn = field
                .stream()
                .map(field->
                        "col_" + field + ", "
                        )
                .reduce("", (a, b) -> a + b);
        if (field.size() > 0){
            addColumn = addColumn.substring(0, addColumn.length() - 2);
        }

        String getField = field
                .stream()
                .map(field->
                        "                model.get" + ToolUtils.convertProp(field) + "().toString(),\n"
                )
                .reduce("", (a, b) -> a + b);
        getField = getField.substring(0, getField.length()-2);



        String finalPersistenceContent = builder.toString();

        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%table%", table);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%switchFunction%", switchFunction);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%column%", column);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%addColumn%", addColumn);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%getField%", getField);


        try {
            FileWriter myWriter = new FileWriter(directory);
            myWriter.write(finalPersistenceContent);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        List<String> test = new ArrayList<String>();
        test.add("monhoc");
        test.add("hocsinh");

        List<String> test1 = new ArrayList<String>();
        test1.add("monhoc");
        test1.add("hocsinh");
        new SceneGenerator("monhoc", test, test1).generate(new File("./test.java"));
    }
}
