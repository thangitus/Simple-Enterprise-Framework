package ui.tool;

public class ConvertUtil {
    final ConvertedMap convertedMap;
    private IConverted convertor;

    ConvertUtil(){
        convertedMap = new ConvertedMap();
        convertor = new StringConverted();
    }

    public void setConvertor(String type){
        for (int i = 0; i < convertedMap.getList().size(); i++) {
            if(convertedMap.getList().get(type) != null){
                convertor = convertedMap.getList().get(type);
            }
        }
    }

    public Object ConvertToObject(String value){
       return convertor.convertEntity(value);
    }
}
