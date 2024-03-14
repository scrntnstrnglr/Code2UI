package com.gw.cip;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class FunctionParser {

    String functionFilePath, inputParametersAsString = "", functionBody = "", unitName = "", functionCallName = "";
    LinkedList<XMLBuilderVariable> functionVariables = new LinkedList<XMLBuilderVariable>();

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
            XMLBuilderVariable variable = new XMLBuilderVariable(parameter.split(":")[0].trim(), parameter.split(":")[1].trim());
            functionVariables.add(variable);
        }
    }

    private void generateFunctionCallNameFromPCF() {
        functionCallName = unitName + "(";
        String variableNames = "";
        for(XMLBuilderVariable var : this.functionVariables) {
            if(variableNames.isEmpty()){
                variableNames=String.join(",",var.getNameForPCF());
            } else {
                variableNames=String.join(",",variableNames,var.getNameForPCF());
            }
        }
        functionCallName = unitName + "(" + variableNames +")";
    }

    public LinkedList<XMLBuilderVariable> getVariables() {
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

    public static void main (String args[]) {
        var fParser = new FunctionParser("C:\\bc-ci-mvp\\Code2UI\\testFunction.txt");
        System.out.println("Variables : ");
        for(XMLBuilderVariable var : fParser.getVariables()) {
            System.out.println(var.getName() + "," + var.getType());
        }
        System.out.println("Function body : \n" + fParser.getFunctionBody());
        System.out.println("Function call name : " + fParser.getFunctionCallName());
        System.out.println("Unit name : " + fParser.getUnitName());;
    }
}
