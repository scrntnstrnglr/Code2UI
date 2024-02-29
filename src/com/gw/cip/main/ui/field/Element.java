package com.gw.cip.main.ui.field;

public abstract class Element {

    protected String name;
    protected String[] requiredAttributes;
    protected org.w3c.dom.Element xmlElement;

    Element(String attributeNames, org.w3c.dom.Element xmlElement) {
        this.name = attributeNames.substring(0,attributeNames.indexOf(','));
        this.requiredAttributes = attributeNames.substring(attributeNames.indexOf(','),attributeNames.length()).split(",");
        this.xmlElement = xmlElement;
    }

    public void loadRequiredAttributes() {
        for(int i=0 ; i<requiredAttributes.length;i++) {
            loadRequiredAttribute(requiredAttributes[i]);
        }
    }

    private void loadRequiredAttribute(String attributeName) {
        switch(attributeName) {
            case "id" : xmlElement.setAttribute("id", this.getID());
            break;
            case "label" : xmlElement.setAttribute("label", this.getLabel());
            break;
            case "value" : xmlElement.setAttribute("value", this.getValue());
            break;
            case "valueType" : xmlElement.setAttribute("valueType", this.getValueType());
            break;
            case "editable" : xmlElement.setAttribute("editable", this.getEditable());
            break;
            case "currency" : xmlElement.setAttribute("currency", this.getCurrency());
            break;
            case "action" : xmlElement.setAttribute("action", this.getAction());
            break;
            default : xmlElement.setAttribute("value", this.getValue());
        }
    }

    public abstract String getID();
    public abstract String getLabel();
    public abstract String getValue();
    public abstract String getValueType();
    public abstract String getEditable();
    public abstract String getCurrency();
    public abstract String getAction();
}