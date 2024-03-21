package com.gw.cip.main.ui.xmlbuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;

import com.gw.cip.main.Launcher;

public class XMLWorksheetBuilder extends XMLBuilder {


    Element screenPanelElement;
    public XMLWorksheetBuilder(Launcher uiBuilder) {
        super(uiBuilder);
    }

    @Override
    public void buildXML() throws ParserConfigurationException, TransformerException {
        super.buildXML();
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
        Element workSheetVariable = buildElement("Variable", "WorksheetVariable",worksheetElement);

        Element screenPanelElement = buildElement("Screen", worksheetElement);
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
    
}
