package com.gw.cip.main.ui.field;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AttributeValueMapBuilder {

    String elementName;
    Properties fieldProperties = new Properties();
    Properties attributeProperties = new Properties();
    HashMap<String,String> attributeValueMap = new HashMap<String,String>();
    public AttributeValueMapBuilder(String elementName, Properties fieldProperties, Properties attributeProperties) {
        this.elementName = elementName;
        this.fieldProperties = fieldProperties;
        this.attributeProperties = attributeProperties;
    }

    public HashMap<String,String> load()  {
        String[] propertyArray = fieldProperties.getProperty(elementName).split(",");
        for(int i = 0 ; i<propertyArray.length;i++) {
            String property = propertyArray[i];
            if(property!=null || !property.isEmpty() || property!="" || property!=" " || !property.isBlank()) {
                String value = attributeProperties.getProperty(property);
                if(value!=null) {
                    attributeValueMap.put(property, value);
                }
            }
        }
        return attributeValueMap;
    }

    public static void main (String args[]) throws FileNotFoundException, IOException {
        Properties fProperties = new Properties();
        fProperties.load(new FileInputStream(System.getProperty("user.dir") + "\\config\\field.properties"));

        Properties aProperties = new Properties();
        aProperties.load(new FileInputStream(System.getProperty("user.dir") + "\\config\\attribute.properties"));
        
        AttributeValueMapBuilder attributeValueMapBuilder = new AttributeValueMapBuilder("Variable", fProperties, aProperties);
        HashMap<String,String> map = new HashMap<String,String>(attributeValueMapBuilder.load());
    
        for (Map.Entry<String,String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }
    
}
