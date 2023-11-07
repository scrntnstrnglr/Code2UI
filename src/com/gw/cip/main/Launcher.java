package com.gw.cip.main;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Launcher {

    String functionFilePath;
    LinkedHashMap<String, String> inputParameters = new LinkedHashMap<String,String>();
    String inputParametersAsString = "";
    String functionBody = "";
    String unitName;

    public Launcher(String functionFilePath) {
        this.functionFilePath = functionFilePath;
    }

    private void parseFunctionFile() {
        try{
            File functionFile = new File(functionFilePath);
            Scanner reader = new Scanner(functionFile);

            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                if(data.startsWith("function")) {
                    unitName = data.substring(data.indexOf(' '),data.indexOf('(')).trim();
                    inputParametersAsString = data.substring(data.indexOf('(')+1,data.indexOf(')')).trim();
                    System.out.println(unitName);
                    System.out.println(inputParametersAsString);
                }
            }
        }catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void generateInputParameters() {
        String[] individualInputParameters = inputParametersAsString.split(",");
        for(int i = 0; i < individualInputParameters.length ; i++) {
            String parameter = individualInputParameters[i].trim();
            inputParameters.put(parameter.split(":")[0].trim(),parameter.split(":")[1].trim());
        }
        System.out.println("INPUt PARAMS ");
        for(Map.Entry<String,String> entry : inputParameters.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }

    private void generateExecuteFunction() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("PCF");
        doc.appendChild(rootElement);
        Element dvPanelElement = doc.createElement("DetailViewPanel");
        rootElement.appendChild(dvPanelElement);
        Element inputColumnElement = doc.createElement("InputColumn");
        dvPanelElement.appendChild(inputColumnElement);

        Element textInputElement = doc.createElement("TextInput");
        textInputElement.setAttribute("editable","true");
        textInputElement.setAttribute("id","account");
        textInputElement.setAttribute("label","Account");
        textInputElement.setAttribute("value","1234");

        inputColumnElement.appendChild(textInputElement);

        writeXml(doc,System.out);

    }

    private void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

    private void generateUIXML() {
        parseFunctionFile();

    }

    public static void main(String args []) throws ParserConfigurationException, TransformerException {
        Launcher code2UITest = new Launcher("C:\\Code2UI\\testFunction.txt");
        //code2UITest.generateUIXML();
        //code2UITest.generateInputParameters();
        code2UITest.generateExecuteFunction();
    }
}
