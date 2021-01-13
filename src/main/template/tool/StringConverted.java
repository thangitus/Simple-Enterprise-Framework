package ui.tool;

public class StringConverted implements IConverted {
    @Override
    public Object convertEntity(String value) {
        return value;
    }

    @Override
    public Boolean isConverted(String type) {
        return type.equals("String");
    }
}
