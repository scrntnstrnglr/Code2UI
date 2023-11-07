package com.gw.cip.main;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Launcher {

    String functionFilePath;
    HashMap<String, String> inputParameters = new HashMap<String,String>();
    String inputParametersAsString = "";
    String functionBody = "";

    public Launcher(String functionFilePath) {
        this.functionFilePath = functionFilePath;
    }

    private void parseFunctionFile() {
        try{
            File functionFile = new File(functionFilePath);
            Scanner reader = new Scanner(functionFile);

            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                if(data.startsWith("function")) {
                    System.out.println(data);
                }
            }
        }catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void generateInputParameters() {

    }

    private void generateExecuteFunction() {

    }

    private void generateUIXML() {
        parseFunctionFile();

    }

    public static void main(String args []) {
        Launcher code2UITest = new Launcher("C:\\Code2UI\\testFunction.txt");
        code2UITest.generateUIXML();
    }
}
