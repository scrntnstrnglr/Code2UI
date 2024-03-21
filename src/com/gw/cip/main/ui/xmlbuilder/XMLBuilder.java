package com.gw.cip.main.ui.xmlbuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gw.cip.main.FunctionParser;
import com.gw.cip.main.Launcher;
import com.gw.cip.main.ui.field.AttributeValueMapBuilder;
import com.gw.cip.main.ui.field.XMLElement;

public abstract class XMLBuilder {

    Launcher uiBuilder;
    Properties fieldProperties, attributeProperties, typeProperties;
    FunctionParser parsedFunction;
    Document doc;
    LinkedList<XMLBuilderVariable> variables = new LinkedList<XMLBuilderVariable>();
    String functionBody,outputDirectoryPath;

    public XMLBuilder(Launcher uiBuilder) {
        this.uiBuilder = uiBuilder;
        this.fieldProperties = uiBuilder.getFieldProperties();
        this.attributeProperties = uiBuilder.getAttributeProperties();
        this.typeProperties = uiBuilder.getTypeProperties();
        this.parsedFunction = uiBuilder.getParsedFunction();
        this.outputDirectoryPath = uiBuilder.getOutputPath();
        init();
    }

    private void init() {
        this.variables = parsedFunction.getVariables();
        this.functionBody = parsedFunction.getFunctionBody();
    }

    public void buildXML () throws ParserConfigurationException, TransformerException {
        this.createDocument();
        this.buildDocumentElements();
        this.writeXML();
    }

    protected abstract void buildDocumentElements();

    protected void createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        doc=docBuilder.newDocument();
    }

    protected void writeXML() throws TransformerException {
        try (FileOutputStream output = new FileOutputStream(this.outputDirectoryPath)) {
            writeXML(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Launcher getUIBuilder() {
        return this.uiBuilder;
    }

    protected void writeXML(String outputFile, Document doc) throws TransformerException {
        try (FileOutputStream output = new FileOutputStream(outputFile)) {
            writeXML(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Element buildElement(String elementName) {
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement(elementName,parsedFunction);
        HashMap<String, String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement,
                fieldProperties, attributeProperties).load();
        XMLElement rootElementXMLElement = new XMLElement(rootElementAttributeMap, elementName, doc);
        return rootElementXMLElement.getElement();
    }

    protected Element buildElement(String elementName, Element parentElement) {
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement(elementName,parsedFunction);
        HashMap<String, String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement,
                fieldProperties, attributeProperties).load();
        XMLElement rootElementXMLElement = new XMLElement(rootElementAttributeMap, elementName, doc, parentElement);
        return rootElementXMLElement.getElement();
    }

    //this is for the Variable element, not for variables inside the inputcolumn
    protected Element buildElement(String elementName, XMLBuilderVariable variable, Element parentElement) {
        XMLBuilderElement xmlBuilderElement = new XMLBuilderElement(elementName,parsedFunction);
        HashMap<String, String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderElement,
                fieldProperties, attributeProperties).load(variable);
        XMLElement rootElementXMLElement = new XMLElement(rootElementAttributeMap, elementName, doc, parentElement);
        return rootElementXMLElement.getElement();
    }


    protected Element buildElement(String elementName, FunctionParser parsedFunction, Element parentElement) {
        XMLBuilderButton xmlBuilderButton = new XMLBuilderButton(elementName, parsedFunction);
        HashMap<String, String> rootElementAttributeMap = new AttributeValueMapBuilder(xmlBuilderButton,
                fieldProperties, attributeProperties).load();
        XMLElement rootElementXMLElement = new XMLElement(rootElementAttributeMap, elementName, doc, parentElement);
        return rootElementXMLElement.getElement();
    }

    private Element buildWorkSheetVariableElement(String elementName, Element parentElement) {
        XMLBuilderWorksheetVariable xmlBuilderWorksheetVariable = new XMLBuilderWorksheetVariable(elementName, this.parsedFunction);
        HashMap<String,String> elementAttributeMap = new AttributeValueMapBuilder(xmlBuilderWorksheetVariable, fieldProperties, attributeProperties).load();
        XMLElement variableElement = new XMLElement(elementAttributeMap, elementName, doc,parentElement);
        return variableElement.getElement();
    }
    
    protected Element buildElement(String elementName, String elementType, Element parentElement) {
        switch(elementType) {
            case "WorksheetVariable" : return buildWorkSheetVariableElement(elementName,parentElement);
            default : return buildElement(elementName,parentElement);
        }
    }

    private void writeXML(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.US_ASCII.name());

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
    
    public Document getDocument () {
        return this.doc;
    }

}
