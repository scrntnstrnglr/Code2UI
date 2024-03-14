package com.gw.cip.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import com.gw.cip.main.ui.xmlbuilder.XMLBuilder;
import com.gw.cip.main.ui.xmlbuilder.XMLBuilderConstants;
import com.gw.cip.main.ui.xmlbuilder.XMLBuilderVariable;
import com.gw.cip.main.ui.xmlbuilder.XMLScreenBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;

public class Launcher {

    FunctionParser functionParser;
    Properties attributeProperties = new Properties();
    Properties fieldProperties = new Properties();
    Properties typeProperties = new Properties();

    private static final String currentWorkingDirectory = System.getProperty("user.dir");

    private static final String ATTRIBUTES_PROPERTIES_PATH = currentWorkingDirectory + XMLBuilderConstants.ATTRIBUTE_PROPERTIES_PATH;
    private static final String FIELD_PROPERTIES_PATH = currentWorkingDirectory + XMLBuilderConstants.FIELD_PROPERTIES_PATH;
    private static final String TYPE_PROPERTIES_PATH = currentWorkingDirectory + XMLBuilderConstants.TYPE_PROPERTIES_PATH;
    private static final String INPUT_DIRECTORY_PATH = currentWorkingDirectory + XMLBuilderConstants.INPUT_DIRETORY_PATH;
    private String functionFilePath,pcfType;

    public Launcher(String functionFilePath,String pcfType) throws FileNotFoundException, IOException, ParserConfigurationException, TransformerException {
        this.functionFilePath = functionFilePath;
        this.pcfType = pcfType;
        init();
    }

    private void init() throws ParserConfigurationException, TransformerException { 
        try{
            attributeProperties.load(new FileInputStream(ATTRIBUTES_PROPERTIES_PATH));
            fieldProperties.load(new FileInputStream(FIELD_PROPERTIES_PATH));
            typeProperties.load(new FileInputStream(TYPE_PROPERTIES_PATH));
            functionParser = new FunctionParser(this.functionFilePath);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void generate() throws TransformerException, ParserConfigurationException {
        switch (this.pcfType) {
            case "Screen" : 
            XMLScreenBuilder xmlScreenBuilder = new XMLScreenBuilder(this);
            xmlScreenBuilder.buildXML();
            break;
            case "Worksheet":
            XMLScreenBuilder xmlWorksheetBuilder = new XMLScreenBuilder(this);
            xmlWorksheetBuilder.buildXML();
            break;
            default : System.out.println("Unknown pcf type");
        }
    }

    public Properties getAttributeProperties () {
        return this.attributeProperties;
    }

    public Properties getFieldProperties () {
        return this.fieldProperties;
    }

    public Properties getTypeProperties () {
        return this.typeProperties;
    }

    public FunctionParser getParsedFunction() {
        return this.functionParser;
    }

    public void logDetails() {
        System.out.println(this.functionParser.getFunctionBody());
        System.out.println(this.functionParser.getFunctionCallName());
        System.out.println(this.functionParser.getUnitName());
        System.out.println(this.functionParser.getUnitName());
        for(XMLBuilderVariable var : this.functionParser.getVariables()) {
            System.out.println(var.getNameForPCF() + "," +  var.getType());
        }
    }

    public static void main(String args []) throws ParserConfigurationException, TransformerException, FileNotFoundException, IOException {
        try{
            File inputDirectoryPath = new File(INPUT_DIRECTORY_PATH);
            File filesList[] = inputDirectoryPath.listFiles();
            if(filesList.length == 0) {
                System.out.println("No function files to parse");
            } else {
                for(File file : filesList) {
                    Launcher code2UITest = new Launcher(file.getAbsolutePath(),"Screen");
                    code2UITest.generate();
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
