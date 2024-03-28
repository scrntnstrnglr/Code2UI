package com.gw.xml.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gw.cip.main.Launcher;
import com.gw.cip.main.ui.xmlbuilder.XMLBuilder;

public class XMLParser extends XMLBuilder {

    private Document document;
    private String unitName;
    private File typeListFile;
    public XMLParser(Launcher uiBuilder) {
        super(uiBuilder);
    }

    @Override
    protected void createDocument() throws ParserConfigurationException {
        typeListFile = new File(super.getUIBuilder().getTypelistFile());
        unitName = super.getUIBuilder().getParsedFunction().getUnitName();
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            docBuilder = docFactory.newDocumentBuilder();
            document = docBuilder.parse(typeListFile);
        }catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void buildDocumentElements() {
        // TODO Auto-generated method stub
        if(fileExists()) {
            if(!utilityExists(unitName)){
    
                    Element typeCodeElement = document.createElement("typecode");
                    typeCodeElement.setAttribute("code", unitName);
                    typeCodeElement.setAttribute("desc", unitName);
                    typeCodeElement.setAttribute("name", unitName);
                    this.getRootElement().appendChild(typeCodeElement);
            }
        } else {
            //new file
        }
    }

    @Override
    protected void writeXML() {
        try {
            super.writeXML(super.getUIBuilder().getTypelistFile(), document);
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Element getRootElement() {
        return document.getDocumentElement();
    }

    private boolean utilityExists(String utilitName) {
        NodeList existingUtilities = this.getRootElement().getElementsByTagName("typecode");
        if(existingUtilities.getLength() >0 ) {
            for(int i =0 ;i<existingUtilities.getLength();i++) {
                Element currentElement = (Element)existingUtilities.item(i);
                if(currentElement.getAttribute("code").equals(utilitName)) {
                    return true;
                } 
            }
        } 
        return false;
    }

    private boolean fileExists() {
        if(typeListFile.exists() && !typeListFile.isDirectory()) {
            return true;
        } 
        return false;
    }

    public static void main(String args []) throws ParserConfigurationException, TransformerException, FileNotFoundException, IOException {
        try{
            Properties runTimeConfigurationProperties = new Properties();
            String runTimeConfigurationFile = System.getProperty("user.dir") + "\\config\\runtime\\runtime.properties";
            runTimeConfigurationProperties.load(new FileInputStream(runTimeConfigurationFile));
            Launcher uiBuilder = new Launcher("C:\\Code2UI\\in\\testFunction.txt", "Worksheet");
            XMLParser xmlParser = new XMLParser(uiBuilder);
            xmlParser.buildXML();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
