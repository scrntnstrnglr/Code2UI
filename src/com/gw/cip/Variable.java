package com.gw.cip;

public class Variable {

    String VARIABLE_NAME, VARIABLE_TYPE;
    String name,type;
    public Variable(String name, String type) {
        this.name = name;
        this.type = type;
        VARIABLE_NAME = name+"_PCF";
        VARIABLE_TYPE = type;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    }
