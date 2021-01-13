package ui.tool;

import java.util.*;

public class ConvertedMap{
    private HashMap<String, IConverted> list;

    ConvertedMap(){
        list = new HashMap<>();
        list.put("java.lang.Integer", new IntegerConverted());
        list.put("java.lang.String", new StringConverted());
        list.put("java.lang.Boolean", new BooleanConverted());
        list.put("java.lang.Float", new FloatConverted());
    }

    public HashMap<String, IConverted> getList() {
        return list;
    }
}
