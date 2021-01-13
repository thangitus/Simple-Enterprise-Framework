package ui.tool;

public class IntegerConverted implements IConverted{
    @Override
    public Object convertEntity(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public Boolean isConverted(String type) {
        return type.equals("Integer");
    }
}
