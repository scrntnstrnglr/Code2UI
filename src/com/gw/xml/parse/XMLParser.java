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
import org.xml.sax.SAXException;

import com.gw.cip.main.FunctionParser;
import com.gw.cip.main.Launcher;
import com.gw.cip.main.ui.xmlbuilder.XMLBuilder;

public class XMLParser extends XMLBuilder {

    private Document document;
    private boolean fileExists=false;
    public XMLParser(Launcher uiBuilder) {
        super(uiBuilder);
    }

    @Override
    protected void createDocument() throws ParserConfigurationException {
        try {
            File typeListFile = new File(super.getUIBuilder().getTypelistFile());
            if(typeListFile.exists() && !typeListFile.isDirectory()) {
                fileExists=true;
            } else {
                fileExists=false;
            }
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            document = docBuilder.parse(typeListFile);
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void buildDocumentElements() {
        // TODO Auto-generated method stub
        if(fileExists) {
            String unitName = super.getUIBuilder().getParsedFunction().getUnitName();
            Element typeCodeElement = document.createElement("typecode");
            typeCodeElement.setAttribute("code", unitName);
            typeCodeElement.setAttribute("desc", unitName);
            typeCodeElement.setAttribute("name", unitName);
            this.getRootElement().appendChild(typeCodeElement);
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

    private boolean utilityExists() {
        int childNodesSize = this.getRootElement().getChildNodes().getLength();
        System.out.println(childNodesSize);
        for(int i=0;i<childNodesSize;i++) {
            Element element = (Element) document.getChildNodes().item(i);
            System.out.println(element.getAttribute("code"));
        }
        return false;
    }

    public static void main(String args []) throws ParserConfigurationException, TransformerException, FileNotFoundException, IOException {
        try{
            Properties runTimeConfigurationProperties = new Properties();
            String runTimeConfigurationFile = System.getProperty("user.dir") + "\\config\\runtime\\runtime.properties";
            runTimeConfigurationProperties.load(new FileInputStream(runTimeConfigurationFile));
            FunctionParser fParser = new FunctionParser("C:\\bc-ci-mvp\\Code2UI\\in\\testFunction.txt");
            Launcher uiBuilder = new Launcher("C:\\bc-ci-mvp\\Code2UI\\in\\testFunction.txt", "Worksheet");
            XMLParser xmlParser = new XMLParser(uiBuilder);
            xmlParser.buildXML();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
