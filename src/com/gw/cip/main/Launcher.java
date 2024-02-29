package com.gw.cip.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.gw.cip.main.ui.field.ToolbarButton;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Launcher {

    LinkedHashMap<String, String> inputParameters = new LinkedHashMap<String,String>();
    String functionFilePath, inputParametersAsString = "", functionBody = "", unitName = "", functionCallName = "";
    Properties configProperties = new Properties();

    public Launcher(String functionFilePath) throws FileNotFoundException, IOException {
        this.functionFilePath = functionFilePath;
        System.out.println(System.getProperty("user.dir"));
        configProperties.load(new FileInputStream(System.getProperty("user.dir") + "\\config\\field.properties"));
    }

    protected void generate() throws ParserConfigurationException, TransformerException { 
        this.generateUIXML();
        this.generateInputParameters();
        this.generateFunctionCallNameFromPCF();
        this.generateExecuteFunction();
    }

    private void parseFunctionFile() {
        try{
            File functionFile = new File(functionFilePath);
            Scanner reader = new Scanner(functionFile);

            while(reader.hasNextLine()) {
                String data = reader.nextLine() + "\n";
                functionBody = functionBody + data;
                if(data.startsWith("function")) {
                    unitName = data.substring(data.indexOf(' '),data.indexOf('(')).trim();
                    inputParametersAsString = data.substring(data.indexOf('(')+1,data.indexOf(')')).trim();
                }
            }
            System.out.println("FUNCTION BODY : ");
            System.out.println(functionBody);
        }catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void generateInputParameters() {
        String[] individualInputParameters = inputParametersAsString.split(",");
        for(int i = 0; i < individualInputParameters.length ; i++) {
            String parameter = individualInputParameters[i].trim();
            inputParameters.put(parameter.split(":")[0].trim()+"_PCF",parameter.split(":")[1].trim());
        }
        System.out.println("INPUt PARAMS ");
        for(Map.Entry<String,String> entry : inputParameters.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }

    private void generateFunctionCallNameFromPCF() {
        var inputParamList = new ArrayList<String>(inputParameters.keySet());
        var inputParamsCommaSeparated = String.join(",", inputParamList);
        functionCallName = unitName + "(" + inputParamsCommaSeparated + ")";
    }

    private void generateExecuteFunction() throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("PCF");
        rootElement.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttribute("xsi:noNamespaceSchemaLocation","../../../../../pcf.xsd");
        doc.appendChild(rootElement);
        
        Element screenPanelElement = doc.createElement("Screen");
        screenPanelElement.setAttribute("id" , "myScreen");
        rootElement.appendChild(screenPanelElement);

        for(Map.Entry<String,String> entry : inputParameters.entrySet()) {
            Element variableElement = doc.createElement("Variable");
            variableElement.setAttribute("name",entry.getKey());
            variableElement.setAttribute("type",entry.getValue());
            screenPanelElement.appendChild(variableElement);
        }

        Element toolbarElement = doc.createElement("Toolbar");
        screenPanelElement.appendChild(toolbarElement);

        Element toolbarButtonElement = doc.createElement("ToolbarButton");
        ToolbarButton toolbarButton = new ToolbarButton(configProperties.getProperty("ToolbarButton"),toolbarButtonElement);
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
            String uiInputFieldConfig = configProperties.getProperty(entry.getValue());
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

    private void setAttribute(String attributeName, Map.Entry<String,String> entry, Element inputElement) {
        switch(attributeName) {
            case "editable" : inputElement.setAttribute(attributeName, "true");
            break;
            case "id" : inputElement.setAttribute(attributeName, entry.getKey().trim());
            break;
            case "label" : inputElement.setAttribute(attributeName, "\""+entry.getKey().trim()+"\"");
            break;
            case "value" : inputElement.setAttribute(attributeName, entry.getKey());
            break;
            case "valueType" : inputElement.setAttribute(attributeName, entry.getValue());
            break;
            case "currency" : inputElement.setAttribute(attributeName, "Currency.TC_USD");
            break;
            default : inputElement.setAttribute(attributeName, "");
        }
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

    private void generateUIXML() {
        parseFunctionFile();
    }

    public static void main(String args []) throws ParserConfigurationException, TransformerException, FileNotFoundException, IOException {
        Launcher code2UITest = new Launcher("C:\\bc-ci-mvp\\Code2UI\\testFunction.txt");
        code2UITest.generate();
    }
}
