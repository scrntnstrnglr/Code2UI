package com.gw.cip.main.ui.field;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.gw.cip.main.FunctionParser;
import com.gw.cip.main.ui.xmlbuilder.XMLBuilderButton;
import com.gw.cip.main.ui.xmlbuilder.XMLBuilderElement;
import com.gw.cip.main.ui.xmlbuilder.XMLBuilderVariable;
import com.gw.cip.main.ui.xmlbuilder.XMLBuilderWorksheetVariable;

public class AttributeValueMapBuilder {


    XMLBuilderElement xmlBuilderElement;
    Properties fieldProperties = new Properties();
    Properties attributeProperties = new Properties();
    public AttributeValueMapBuilder(XMLBuilderElement element, Properties fieldProperties, Properties attributeProperties) {
        this.xmlBuilderElement = element;
        this.fieldProperties = fieldProperties;
        this.attributeProperties = attributeProperties;
    }

    public LinkedHashMap<String,String> load()  {
        LinkedHashMap<String,String> attributeValueMap = new LinkedHashMap<String,String>();
        String properties = fieldProperties.getProperty(xmlBuilderElement.getName());
        if(properties!=null) {
            String[] propertyArray = properties.split(",");
            for(int i = 0 ; i<propertyArray.length;i++) {
                String property = propertyArray[i];
                String value = attributeProperties.getProperty(property);
                if(value!=null) {
                    if (value.startsWith("{") && value.endsWith("}")) {
                        String valueName = value.substring(1, value.length()-1).trim();
                        attributeValueMap.put(property,xmlBuilderElement.getElementMap().get(valueName));
                    } else {
                        attributeValueMap.put(property, value);
                    }
                } 
            }
        } else {
            System.out.println(" No field properties found!");
        }
        return attributeValueMap;
    }

    public LinkedHashMap<String,String> load(XMLBuilderVariable variable) {
        LinkedHashMap<String,String> attributeValueMap = new LinkedHashMap<String,String>();
        String properties = fieldProperties.getProperty(xmlBuilderElement.getName());
        if(properties!=null) {
            String[] propertyArray = properties.split(",");
            for(int i = 0 ; i<propertyArray.length;i++) {
                String property = propertyArray[i];
                String value = attributeProperties.getProperty(property);
                if(value!=null) {
                     if (value.startsWith("{") && value.endsWith("}")) {
                        String valueName = value.substring(1, value.length()-1).trim();
                        attributeValueMap.put(property,variable.getElementMap().get(valueName));
                    } else {
                        attributeValueMap.put(property, value);
                    }
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


        Properties tProperties = new Properties();
        tProperties.load(new FileInputStream(System.getProperty("user.dir") + "\\config\\type.properties"));
        /**
         * testing for PCF
         
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement("PCF");
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fProperties, aProperties).load();
        for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        } */

        /**
         * testing for Screen
         
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement("Screen");
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fProperties, aProperties).load();
        for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        } */

                /**
         * testing for Worksheet
        FunctionParser fParser = new FunctionParser("C:\\bc-ci-mvp\\Code2UI\\in\\testFunction.txt");
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement("Worksheet",fParser);
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fProperties, aProperties).load();
        for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        } */
        
                        /**
         * testing for scope
        FunctionParser fParser = new FunctionParser("C:\\bc-ci-mvp\\Code2UI\\in\\testFunction.txt");
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement("Scope",fParser);
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fProperties, aProperties).load();
        for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        } */
        
        
       /**  testing for Variables 
          FunctionParser fParser = new FunctionParser("C:\\bc-ci-mvp\\Code2UI\\testFunction.txt");
        for(XMLBuilderVariable variable : fParser.getVariables()) {
            XMLBuilderElement xmlBuilderElement = new XMLBuilderElement("Variable");
            HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fProperties, aProperties).load(variable);
              for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
                System.out.println(entry.getKey() + "->" + entry.getValue());
            } 
        } */  

                /**
         * testing for toolbar
         
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement("Toolbar");
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fProperties, aProperties).load();
        for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        } */
        

                /**
         * testing for toolbarbutton
         
        FunctionParser fParser = new FunctionParser("C:\\bc-ci-mvp\\Code2UI\\in\\testFunction.txt");
        XMLBuilderElement xmlBuilderElement = new XMLBuilderButton("ToolbarButton",fParser);
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fProperties, aProperties).load();
        for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        } 
*/

        /**
         * testing for DVPanel
         
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement("DetailViewPanel");
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fProperties, aProperties).load();
        for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        } */ 

                /**
         * testing for InputColumn
         
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement("InputColumn");
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fProperties, aProperties).load();
        for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        } */ 


               /**  testing for Variables
          FunctionParser fParser = new FunctionParser("C:\\bc-ci-mvp\\Code2UI\\testFunction.txt");
        for(XMLBuilderVariable variable : fParser.getVariables()) {
            XMLBuilderElement xmlBuilderElement = new XMLBuilderElement(tProperties.getProperty(variable.getType()));
            HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fProperties, aProperties).load(variable);
              for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
                System.out.println(entry.getKey() + "->" + entry.getValue());
            } 
        }    */

                       /**  testing for XMLWOrksheet Variable*/
        FunctionParser fParser = new FunctionParser("C:\\bc-ci-mvp\\Code2UI\\in\\testFunction.txt");
          XMLBuilderWorksheetVariable xmlBuilderWorksheetVariable = new XMLBuilderWorksheetVariable("Variable",fParser);
            HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderWorksheetVariable, fProperties, aProperties).load();
            System.out.println(rootElementAttributeMap.size()); 
            for (Map.Entry<String,String> entry : rootElementAttributeMap.entrySet()) {
                System.out.println(entry.getKey() + "->" + entry.getValue());
            } 
    }
    
}
