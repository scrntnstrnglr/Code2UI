package com.gw.cip.main.ui.xmlbuilder;

import java.util.HashMap;

import com.gw.cip.main.FunctionParser;

public class XMLBuilderElement {

    String name;

    FunctionParser parsedFunction;
    public XMLBuilderElement(String name, FunctionParser parsedFunction) {
        this.name=name;
        this.parsedFunction = parsedFunction;
    }

    public String getName() {
        return this.name;
    }

    private String getLabel() {
        switch (this.getName()) {
            case "ToolbarButton" : return "Execute";
            case "DetailViewPanel" : return "DetailViewPanelDV";
            case "Worksheet" : return this.parsedFunction.getPCFFileName();
            default : return this.getName();
        }
    }

    public HashMap<String,String> getElementMap () {
        HashMap<String,String> elementMap = new HashMap<String,String>();
        elementMap.put("ELEMENT_NAME",this.getLabel());
        elementMap.put("ELEMENT_LABEL","\""+this.getLabel()+"\"");
        elementMap.put("UNIT_NAME","\""+this.parsedFunction.getUnitName()+"\"");
        return elementMap;
    }



}