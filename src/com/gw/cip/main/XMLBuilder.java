package com.gw.cip.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gw.cip.FunctionParser;
import com.gw.cip.main.ui.field.AttributeValueMapBuilder;
import com.gw.cip.main.ui.field.ToolbarButton;
import com.gw.cip.main.ui.field.XMLElement;

public class XMLBuilder {

    Properties fieldProperties, attributeProperties;
    FunctionParser parsedFunction;
    Document doc;

    public XMLBuilder (Properties fieldProperties, Properties attributeProperties, FunctionParser parsedFunction) {
        this.fieldProperties = fieldProperties;
        this.attributeProperties = attributeProperties;
        this.parsedFunction = parsedFunction;
    }

    public void buildXML () {
        
        LinkedHashMap<String,String> inputParameters = parsedFunction.getInputParameters();
        String functionBody = parsedFunction.getFunctionBody();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.newDocument();

        // root PCF Element--------------------------------------------------------------------------------------------
        Element rootElement = buildElement("PCF");
        // screen panelElement--------------------------------------------------------------------------------------------
        Element screenPanelElement = buildElement("Screen", rootElement);
        // Variables--------------------------------------------------------------------------------------------
        Element variableElement = buildElement("Variable",screenPanelElement);
        // toolbar--------------------------------------------------------------------------------------------
        Element toolbarElement = buildElement("Toolbar",screenPanelElement);
        // toolbar button--------------------------------------------------------------------------------------------
        Element toolbarButtoElement = buildElement("ToolbarButton",toolbarElement);

        Element toolbarButtonElement = doc.createElement("ToolbarButton");
        ToolbarButton toolbarButton = new ToolbarButton(properties.getProperty("ToolbarButton"),toolbarButtonElement);
       /** toolbarButtonElement.setAttribute("id", "executeButton");  //add function name and function call
        toolbarButtonElement.setAttribute("label", "\"Execute\"");
        toolbarButtonElement.setAttribute("action",functionCallName ); */
        toolbarElement.appendChild(toolbarButtonElement);

        Element dvPanelElement = doc.createElement("DetailViewPanel");
        dvPanelElement.setAttribute("id","TestDV");
        screenPanelElement.appendChild(dvPanelElement);

        Element inputColumnElement = doc.createElement("InputColumn");
        dvPanelElement.appendChild(inputColumnElement);

        for(Map.Entry<String,String> entry : inputParameters.entrySet()) {
            String uiInputFieldConfig = properties.getProperty(entry.getValue());
            String[] uiInputFieldNameAndProperties = uiInputFieldConfig.split(",");
            String uiInputField = uiInputFieldNameAndProperties[0];
            Element inputElement = doc.createElement(uiInputField);
            for(int i=1;i<uiInputFieldNameAndProperties.length;i++) {
                String attributeName = uiInputFieldNameAndProperties[i];
                setAttribute(attributeName,entry,inputElement);
            }
            inputColumnElement.appendChild(inputElement);
        }

        Element codeElement = doc.createElement("Code");
        screenPanelElement.appendChild(codeElement);

        CDATASection codeSection = doc.createCDATASection(functionBody);
        codeElement.appendChild(codeSection);
    
        try (FileOutputStream output = new FileOutputStream("C:\\MWFBI\\billingcenter\\modules\\configuration\\config\\web\\pcf\\myScreen.pcf")) {
            writeXml(doc, output);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Element buildElement(String elementName) {
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(elementName, fieldProperties, attributeProperties).load();
        XMLElement rootElementXMLElement = new XMLElement(rootElementAttributeMap, elementName, doc);
        return rootElementXMLElement.getElement();
    }

    private Element buildElement(String elementName, Element parentElement) {
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(elementName, fieldProperties, attributeProperties).load();
        XMLElement rootElementXMLElement = new XMLElement(rootElementAttributeMap, elementName, doc,parentElement);
        return rootElementXMLElement.getElement();
    }

    private void writeXml(Document doc,OutputStream output) throws TransformerException {
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.US_ASCII.name());

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
    
}
