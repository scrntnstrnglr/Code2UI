package com.gw.cip;

import java.util.HashMap;

public class XMLBuilderElement {

    String name;

    public XMLBuilderElement(String name) {
        this.name=name;
    }

    public String getName() {
        return this.name;
    }

    private String getLabel() {
        switch (this.getName()) {
            case "ToolbarButton" : return "\"Execute\"";
            default : return this.getName();
        }
    }

    public HashMap<String,String> getElementMap () {
        HashMap<String,String> elementMap = new HashMap<String,String>();
        elementMap.put("ELEMENT_NAME",this.getName());
        elementMap.put("ELEMENT_LABEL",this.getLabel());
        return elementMap;
    }



}