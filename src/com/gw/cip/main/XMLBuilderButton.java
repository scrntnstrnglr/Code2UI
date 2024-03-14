package com.gw.cip.main;

import java.util.HashMap;

import com.gw.cip.FunctionParser;
import com.gw.cip.XMLBuilderElement;

public class XMLBuilderButton extends XMLBuilderElement {

    FunctionParser fParser;
    public XMLBuilderButton(String name, FunctionParser fParser) {
        super(name);
        this.fParser = fParser;
    }

    private String getAction() {
        return this.fParser.getFunctionCallName();
    }

    public HashMap<String,String> getElementMap () {
        HashMap<String,String> variableMap = new HashMap<String,String>(super.getElementMap());
        variableMap.put("ACTION", this.getAction());
        return variableMap;
    }
    
}
