package com.gw.cip.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
import com.gw.cip.Variable;
import com.gw.cip.XMLBuilderElement;
import com.gw.cip.XMLBuilderVariable;
import com.gw.cip.main.ui.field.AttributeValueMapBuilder;
import com.gw.cip.main.ui.field.ToolbarButton;
import com.gw.cip.main.ui.field.XMLElement;

public class XMLBuilder {

    Properties fieldProperties, attributeProperties, typeProperties;
    FunctionParser parsedFunction;
    Document doc;

    public XMLBuilder (Launcher uiBuilder) {
        this.fieldProperties = uiBuilder.getFieldProperties();
        this.attributeProperties = uiBuilder.getAttributeProperties();
        this.typeProperties = uiBuilder.getTypeProperties();
        this.parsedFunction = uiBuilder.getParsedFunction();
    }

    public void buildXML () throws TransformerException, ParserConfigurationException {
        
        LinkedList<XMLBuilderVariable> variables = parsedFunction.getVariables();
        String functionBody = parsedFunction.getFunctionBody();

        doc = createDocument();

        // root PCF Element--------------------------------------------------------------------------------------------
        Element rootElement = buildElement("PCF");
        // screen panelElement--------------------------------------------------------------------------------------------
        Element screenPanelElement = buildElement("Screen", rootElement);
        // Variables--------------------------------------------------------------------------------------------
        for(XMLBuilderVariable variable : variables) {
            buildElement("Variable",variable,screenPanelElement);
        }
        // toolbar--------------------------------------------------------------------------------------------
        Element toolbarElement = buildElement("Toolbar",parsedFunction,screenPanelElement);
        // toolbar button--------------------------------------------------------------------------------------------
        Element toolbarButtoElement = buildElement("ToolbarButton",toolbarElement);
        // detail view panel--------------------------------------------------------------------------------------------
        Element dvPanelElement = buildElement("DetailViewPanel",screenPanelElement);
        // input column--------------------------------------------------------------------------------------------
        Element inputColoumnelement = buildElement("InputColumn",dvPanelElement);
        // input element--------------------------------------------------------------------------------------------
        for(XMLBuilderVariable variable : variables) {
            buildElement(typeProperties.getProperty(variable.getType()),inputColoumnelement);
        }

        Element codeElement = buildElement("Code",screenPanelElement);

        CDATASection codeSection = doc.createCDATASection(functionBody);
        codeElement.appendChild(codeSection);
    
        try (FileOutputStream output = new FileOutputStream(XMLBuilderConstants.PCF_FILE_OUTPUT_PATH)) {
            writeXml(doc, output);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Element buildElement(String elementName) {
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement(elementName);
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fieldProperties, attributeProperties).load();
        XMLElement rootElementXMLElement = new XMLElement(rootElementAttributeMap, elementName, doc);
        return rootElementXMLElement.getElement();
    }

    private Element buildElement(String elementName, Element parentElement) {
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement(elementName);
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fieldProperties, attributeProperties).load();
        XMLElement rootElementXMLElement = new XMLElement(rootElementAttributeMap, elementName, doc,parentElement);
        return rootElementXMLElement.getElement();
    }

    private Element buildElement(String elementName, XMLBuilderVariable variable, Element parentElement) {
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement(elementName);
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement, fieldProperties, attributeProperties).load(variable);
        XMLElement rootElementXMLElement = new XMLElement(rootElementAttributeMap, elementName, doc,parentElement);
        return rootElementXMLElement.getElement();
    }

    private Element buildElement(String elementName, FunctionParser parsedFunction, Element parentElement) {
        XMLBuilderButton xmlBuilderButton = new XMLBuilderButton("ToolbarButton",parsedFunction);
        HashMap<String,String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderButton, fieldProperties, attributeProperties).load();
        XMLElement rootElementXMLElement = new XMLElement(rootElementAttributeMap, elementName, doc,parentElement);
        return rootElementXMLElement.getElement();
    }

    private Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.newDocument();

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
