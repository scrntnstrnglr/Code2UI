package com.gw.cip.main.ui.xmlbuilder;

import java.util.HashMap;

import com.gw.cip.main.FunctionParser;

public class XMLBuilderWorksheetVariable extends XMLBuilderVariable {

    public XMLBuilderWorksheetVariable(String name, String type, FunctionParser fParser) {
        super(name, type, fParser);
        //TODO Auto-generated constructor stub
    }

    public String getName() {
        return XMLBuilderConstants.WORKSHEET_TYPELIST_NAME;
    }

    @Override
    public String getType() {
        return XMLBuilderConstants.WORKSHEET_TYPELIST_TYPE;
    }

        public HashMap<String,String> getElementMap () {
        HashMap<String,String> variableMap = new HashMap<String,String>(super.getElementMap());
        variableMap.put("VARIABLE_NAME", this.getName());
        variableMap.put("VARIABLE_TYPE", this.getType());
        return variableMap;
    }
    
}
