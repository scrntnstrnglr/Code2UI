package com.gw.cip.main.ui.xmlbuilder;

import java.util.HashMap;

import com.gw.cip.main.FunctionParser;

public class XMLBuilderVariable extends XMLBuilderElement {

    
    String type;
    public XMLBuilderVariable(String name, String type, FunctionParser fParser) {
        super(name,fParser);
        this.type = type;
    }

    public String getNameForPCF() {
        return this.name+"_PCF";
    }

    public String getType() {
        return this.type;
    }

    public HashMap<String,String> getElementMap () {
        HashMap<String,String> variableMap = new HashMap<String,String>(super.getElementMap());
        variableMap.put("VARIABLE_NAME", this.getNameForPCF());
        variableMap.put("VARIABLE_TYPE", this.getType());
        return variableMap;
    }


}
