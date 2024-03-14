package com.gw.cip.main.ui.field;

import com.gw.cip.XMLBuilderElement;

public class ToolbarButton extends XMLBuilderElement{


    public ToolbarButton(String attributeNames, org.w3c.dom.Element xmlElement) {
        super(attributeNames,xmlElement);
        this.loadRequiredAttributes();
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        return "executeButton";
    }

    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
        return "\"Execute\"";
    }

    @Override
    public String getValue() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValue'");
    }

    @Override
    public String getValueType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValueType'");
    }

    @Override
    public String getEditable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEditable'");
    }

    @Override
    public String getCurrency() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrency'");
    }

    @Override
    public String getAction() {
        // TODO Auto-generated method stub
        return "tesetFunctionCall()";
    }
    
}
