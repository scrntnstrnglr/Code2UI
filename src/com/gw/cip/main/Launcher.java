package com.gw.cip.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.gw.FileLogger;
import com.gw.cip.main.ui.xmlbuilder.XMLBuilderConstants;
import com.gw.cip.main.ui.xmlbuilder.XMLScreenBuilder;
import com.gw.cip.main.ui.xmlbuilder.XMLWorksheetBuilder;
import com.gw.xml.parse.XMLParser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Launcher {

    FunctionParser functionParser;
    Properties attributeProperties = new Properties();
    Properties fieldProperties = new Properties();
    Properties typeProperties = new Properties();
    Properties runtimeProperties = new Properties();

    private static final String currentWorkingDirectory = System.getProperty("user.dir");

    private static final String ATTRIBUTES_PROPERTIES_PATH = currentWorkingDirectory + XMLBuilderConstants.ATTRIBUTE_PROPERTIES_PATH;
    private static final String FIELD_PROPERTIES_PATH = currentWorkingDirectory + XMLBuilderConstants.FIELD_PROPERTIES_PATH;
    private static final String TYPE_PROPERTIES_PATH = currentWorkingDirectory + XMLBuilderConstants.TYPE_PROPERTIES_PATH;
    private static final String RUNTIME_PROPERTIES_PATH = currentWorkingDirectory + XMLBuilderConstants.RUNTIME_PROPERTIES_PATH;
    private String functionFilePath,pcfType;

    public static Logger logger;

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
            runtimeProperties.load(new FileInputStream(RUNTIME_PROPERTIES_PATH));
            functionParser = new FunctionParser(this.functionFilePath);
            updateTypeList();
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
            XMLWorksheetBuilder xmlWorksheetBuilder = new XMLWorksheetBuilder(this);
            xmlWorksheetBuilder.buildXML();
            break;
            default : logger.info("Unknown pcf type");
        }
    }

    //updates the typelist to include the new utility
    private void updateTypeList() {
        try {
            //TO-DO : if the type list is not present, make it from scratch
            XMLParser xmlParser = new XMLParser(this);
            xmlParser.buildXML();
        } catch (ParserConfigurationException | TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

    public String getOutputPath() {
        return runtimeProperties.getProperty("OutputDirectoryPath");
    }

    public String getBillingCenterPath(){
        return runtimeProperties.getProperty("BillingCenterPath");
    }

    public String getBillingCenterConfigurationPath(){
        return this.getBillingCenterPath()+"\\modules\\configuration\\config";
    }

    public String getTypelistFile() {
        return this.getBillingCenterConfigurationPath()+"\\extensions\\typelist\\" + this.getTypelistName();
    }

    public String getTypelistName() {
        return runtimeProperties.getProperty("TypeList");
    }


    public static void main(String args []) throws ParserConfigurationException, TransformerException, FileNotFoundException, IOException {
        try{
            Properties runTimeConfigurationProperties = new Properties();
            runTimeConfigurationProperties.load(new FileInputStream(RUNTIME_PROPERTIES_PATH));
            File inputDirectoryPath = new File(runTimeConfigurationProperties.getProperty("InputDirectoryPath"));
            System.out.println(inputDirectoryPath);
            FileLogger fileLogger = new FileLogger(runTimeConfigurationProperties);
            logger = fileLogger.getLogger();
            File filesList[] = inputDirectoryPath.listFiles();
            if(filesList.length==0) {
                logger.info("No files to parse");
            } else {
                for(File file : filesList) {
                    logger.info("Working on - " + file.getAbsolutePath());
                    Launcher code2UITest = new Launcher(file.getAbsolutePath(),"Worksheet");
                    code2UITest.generate();
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.log(Level.ALL, "Exception occured", e);
        }
    }
}
