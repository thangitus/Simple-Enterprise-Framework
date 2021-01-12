package ui.tool;

import java.util.*;

public class ConvertedMap{
    private HashMap<String, IConverted> list;

    ConvertedMap(){
        list = new HashMap<>();
        list.put("Integer", new IntegerConverted());
        list.put("String", new StringConverted());
        list.put("Boolean", new BooleanConverted());
    }

    public HashMap<String, IConverted> getList() {
        return list;
    }

    public static void main(String[] args) {
        ConvertUtil convertUtil = new ConvertUtil();
        String test = "Boolean";
        convertUtil.setConvertor("Integer");
        System.out.println(convertUtil.ConvertToObject(test));
    }
}
