package com.gw.cip.main.ui.xmlbuilder;

import java.util.HashMap;

import com.gw.cip.main.FunctionParser;

public class XMLBuilderWorksheetVariable extends XMLBuilderElement {

    public XMLBuilderWorksheetVariable(String name, FunctionParser fParser) {
        super(name,fParser);
        //TODO Auto-generated constructor stub
    }

    private String getType() {
        return XMLBuilderConstants.WORKSHEET_TYPELIST_TYPE;
    }

    private String getValueName() {
        return XMLBuilderConstants.WORKSHEET_TYPELIST_NAME;
    }

        public HashMap<String,String> getElementMap () {
        HashMap<String,String> variableMap = new HashMap<String,String>(super.getElementMap());
        variableMap.put("VARIABLE_NAME", this.getValueName());
        variableMap.put("VARIABLE_TYPE", this.getType());
        return variableMap;
    }
    
}
