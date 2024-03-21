package com.gw.cip.main.ui.xmlbuilder;

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

public class XMLScreenBuilder extends XMLBuilder {

    Element screenPanelElement;
    public XMLScreenBuilder(Launcher uiBuilder) {
        super(uiBuilder);
    }
    
    @Override
    public void buildXML() throws TransformerException, ParserConfigurationException {
        super.buildXML();
    }

    @Override
     protected void buildDocumentElements() {
      System.out.println("Screen panel element build called!!");
        // root PCF Element---------------------------------------------------------
        Element rootElement = buildElement("PCF");
        // screen panelElement------------------------------------------------------------
        Element screenPanelElement = buildElement("Screen", rootElement);
        // Variables------------------------------------------------------------------
        for (XMLBuilderVariable variable : this.variables) {
            buildElement("Variable", variable, screenPanelElement);
        }
        // toolbar-----------------------------------------------------------------------
        Element toolbarElement = buildElement("Toolbar", screenPanelElement);
        // toolbarbutton------------------------------------------------------------------
        Element toolbarButtoElement = buildElement("ToolbarButton",this.parsedFunction, toolbarElement);
        // detail view panel----------------------------------------------------------------
        Element dvPanelElement = buildElement("DetailViewPanel", screenPanelElement);
        // inputcolumn---------------------------------------------------------------------
        Element inputColoumnelement = buildElement("InputColumn", dvPanelElement);
        // input element-----------------------------------------------------------------
        for (XMLBuilderVariable variable : this.variables) {
            buildElement(typeProperties.getProperty(variable.getType()),variable, inputColoumnelement);
        }

        Element codeElement = buildElement("Code", screenPanelElement);

        CDATASection codeSection = doc.createCDATASection(functionBody);
        codeElement.appendChild(codeSection);
    }

    public Element getScreenPanelElement () {
        return this.screenPanelElement;
    }


}
