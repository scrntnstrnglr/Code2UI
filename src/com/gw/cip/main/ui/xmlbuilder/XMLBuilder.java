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

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gw.cip.main.FunctionParser;
import com.gw.cip.main.Launcher;
import com.gw.cip.main.ui.field.AttributeValueMapBuilder;
import com.gw.cip.main.ui.field.XMLElement;

public abstract class XMLBuilder {

    Properties fieldProperties, attributeProperties, typeProperties;
    FunctionParser parsedFunction;
    Document doc;
    LinkedList<XMLBuilderVariable> variables = new LinkedList<XMLBuilderVariable>();
    String functionBody;

    public XMLBuilder(Launcher uiBuilder) {
        this.fieldProperties = uiBuilder.getFieldProperties();
        this.attributeProperties = uiBuilder.getAttributeProperties();
        this.typeProperties = uiBuilder.getTypeProperties();
        this.parsedFunction = uiBuilder.getParsedFunction();
        init();
    }

    private void init() {
        this.variables = parsedFunction.getVariables();
        this.functionBody = parsedFunction.getFunctionBody();
    }

    protected abstract void buildDocumentElements();

    protected void createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        doc=docBuilder.newDocument();
    }

    protected void writeXML() throws TransformerException {
        try (FileOutputStream output = new FileOutputStream(XMLBuilderConstants.PCF_FILE_OUTPUT_PATH)) {
            writeXML(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
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
