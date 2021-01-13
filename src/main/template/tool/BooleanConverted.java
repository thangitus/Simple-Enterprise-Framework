package ui.tool;

public class BooleanConverted implements IConverted {
    @Override
    public Object convertEntity(String value) {
        return Boolean.parseBoolean(value);
    }

    @Override
    public Boolean isConverted(String type) {
        return type.equals("Boolean");
    }
}
