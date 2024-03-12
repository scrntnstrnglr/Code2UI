package com.gw.cip.main.ui.field;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

public class XMLElement {

    HashMap<String,String> attributeValueMap = new HashMap<String,String>();
    org.w3c.dom.Element xmlElement;
    
    public XMLElement(HashMap<String,String> attributeValueMap, String tagName, Document doc, org.w3c.dom.Element parentElement) {
        this.attributeValueMap = attributeValueMap;
        initializeElement(tagName,parentElement, doc);
    }

    public XMLElement(HashMap<String,String> attributeValueMap, String tagName, Document doc) {
        this.attributeValueMap = attributeValueMap;
        initializeElement(tagName, doc);
    }

    private void initializeElement(String tagName, org.w3c.dom.Element parentElement, Document doc) {
        this.xmlElement = doc.createElement(tagName);
        loadAttributes();
        parentElement.appendChild(xmlElement);
    }

    private void initializeElement(String tagName, Document doc) {
        this.xmlElement = doc.createElement(tagName);
        loadAttributes();
        doc.appendChild(xmlElement);
    }

    public void loadAttributes() {
        for(Map.Entry<String,String> entry : this.attributeValueMap.entrySet()) {
            String attribute = entry.getKey();
            String value = entry.getValue();
            this.xmlElement.setAttribute(attribute, value);
        }
    }    

    public org.w3c.dom.Element getElement() {
        return this.xmlElement;
    }
}
