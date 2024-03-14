package com.gw.cip.main.ui.xmlbuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;

import com.gw.cip.main.Launcher;

public class XMLWorksheetBuilder extends XMLScreenBuilder {


    Element screenPanelElement;
    public XMLWorksheetBuilder(Launcher uiBuilder) {
        super(uiBuilder);
    }

    @Override
    public void buildXML() throws ParserConfigurationException, TransformerException {
        super.buildXML();
        screenPanelElement = super.getScreenPanelElement();
    }

    @Override
    protected void buildDocumentElements() {
        // root PCF Element---------------------------------------------------------
        Element rootElement = buildElement("PCF");
        // screen panelElement------------------------------------------------------------
        Element worksheetElement = buildElement("Worksheet", rootElement);
        // scope element
        Element scopeElement = buildElement("Scope",worksheetElement);
        // work sheet variables
        Element workSheetVariable = buildElement("Variable",worksheetElement);

        worksheetElement.appendChild(screenPanelElement);
    }
    
}
