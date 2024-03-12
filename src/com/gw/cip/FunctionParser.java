package com.gw.cip;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class FunctionParser {

    String functionFilePath, inputParametersAsString = "", functionBody = "", unitName = "", functionCallName = "";
    ArrayList<Variable> functionVariables = new ArrayList<Variable>();

    public FunctionParser(String functionFilePath) {
        this.functionFilePath = functionFilePath;
        init();
    }

    protected void init() {
        readFile();
        generateVariables();
        generateFunctionCallNameFromPCF();
    }

    private void readFile() {
        try{
            File functionFile = new File(functionFilePath);
            Scanner reader = new Scanner(functionFile);

            while(reader.hasNextLine()) {
                String data = reader.nextLine() + "\n";
                functionBody = functionBody + data;
                if(data.startsWith("function")) {
                    unitName = data.substring(data.indexOf(' '),data.indexOf('(')).trim();
                    inputParametersAsString = data.substring(data.indexOf('(')+1,data.indexOf(')')).trim();
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void generateVariables() {
        String[] individualInputParameters = inputParametersAsString.split(",");
        for(int i = 0; i < individualInputParameters.length ; i++) {
            String parameter = individualInputParameters[i].trim();
            Variable variable = new Variable(parameter.split(":")[0].trim(), parameter.split(":")[1].trim());
            functionVariables.add(variable);
        }
    }


    ////use string join to append!!!

    private void generateFunctionCallNameFromPCF() {
        functionCallName = unitName + "(";
        for(Variable var : this.functionVariables) {
            functionCallName += var.getName()
        }
        functionCallName = unitName + "(" + inputParamsCommaSeparated + ")";
    }

    public ArrayList<Variable> getVariables() {
        return this.functionVariables;
    }

    public String getFunctionBody () {
        return this.functionBody;
    }

    public String getFunctionCallName () {
        return this.functionCallName;
    }

    public String getUnitName () {
        return this.unitName;
    }
}
