package ui.generator;

import generator.Generatable;
import org.apache.commons.io.FileUtils;
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
import java.util.Map;

public class SceneGenerator implements Generatable {
    String table;
    List<String> listTable;
    Map<String, String> columnMap;
    List<String> field;
    List<String> fieldClass;

    public SceneGenerator(String table, List<String> listTable, Map<String, String> column) {
        this.table = table;
        this.listTable = listTable;
        this.columnMap = column;
        this.field = new ArrayList<String>();
        this.field.addAll(columnMap.keySet());
        this.fieldClass =  new ArrayList<String>();
        this.fieldClass.addAll(columnMap.values());

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

        final int[] iCount = {0};
        String switchFunction = listTable
                .stream()
                .map(table ->{
                            iCount[0]++;
                            return "@FXML\n" +
                                    "private void switch_"+table+"_Scene(MouseEvent event) {\n" +
                                    "Main.setCurrentTab("+ iCount[0] +");\n" +
                                "   SceneUtils.getInstance().switchScreen(this.rootPane, \"/fxml/"+table.toLowerCase()+"Scene.fxml\", 100);\n" +
                                "   }\n\n\t";
                        }

                )
                .reduce("", (a, b) -> a + b);

        String column = field
                .stream()
                .map(field ->
                        " TreeTableColumn<"+table+"ViewModel, String> col_"+field+" = new JFXTreeTableColumn<>(\""+ ToolUtils.convertProp(field)+"\");\n" +
                                "        col_"+field+".setSortable(false);" +
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

        String annotationFXML = field
                .stream()
                .map(field->
                        "@FXML\n" +
                                "    JFXTextField edt_"+ field +";"
                )
                .reduce("", (a, b) -> a + b);

        String getFieldOfData = field
                .stream()
                .map(field-> "\t\t\tthis.edt_"+field+".setText(data.get(index).get"+ToolUtils.convertProp(field)+"());"
                )
                .reduce("", (a, b) -> a + b);

        String getFieldToCreateVM = field
                .stream()
                .map(field-> "\n\t\t\tthis.edt_"+field+".getText(),"
                )
                .reduce("", (a, b) -> a + b);
        getFieldToCreateVM = getFieldToCreateVM.substring(0,getFieldToCreateVM.length() - 1);

        String getFieldToConvertEntity = field
                .stream()
                .map(field-> "\t\tres.set"+ToolUtils.convertProp(field)+"(("+columnMap.get(field)+") new ConvertUtil().ConvertToObject(\""+columnMap.get(field)+"\", model.get"+ToolUtils.convertProp(field)+"()));\n"
                )
                .reduce("", (a, b) -> a + b);


        String finalPersistenceContent = builder.toString();

        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%getFieldToConvertEntity%", getFieldToConvertEntity);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%getFieldToCreateVM%", getFieldToCreateVM);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%getFieldOfData%", getFieldOfData);
        finalPersistenceContent = StringUtils.replace(finalPersistenceContent, "%annotationFXML%", annotationFXML);
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

    }
}
