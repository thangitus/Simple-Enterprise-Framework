package ui.tool;

public class FloatConverted implements IConverted{
    @Override
    public Object convertEntity(String value) {
        return Float.parseFloat(value);
    }

    @Override
    public Boolean isConverted(String type) {
        return type.equals("Float");
    }
}
