package ui;

public class ToolUtils {
    static public String convertProp(String prop){
        if(prop.length() == 0){
            return prop;
        } else {
            String first = prop.substring(0,1);
            return first.toUpperCase() + prop.substring(1);
        }
    }
}