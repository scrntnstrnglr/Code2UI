package com.gw;

import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.gw.cip.main.ui.xmlbuilder.XMLBuilderConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.FileHandler;

public class FileLogger {

    Properties runTimeProperties;
    String loggerFileName;
    FileHandler fileHandler;
    Logger logger;
    public FileLogger(Properties runtimeProperties) throws SecurityException, IOException {
        load();
    }

    public void load() throws SecurityException, IOException {
        logger = Logger.getLogger("CODE2UILOGGER");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        loggerFileName = timeStamp+".log";
        fileHandler = new FileHandler(createLoggerDirectory() + "//" + loggerFileName);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
    }

    private String createLoggerDirectory() {
        File loggerDirectory = new File("C://Code2UI//log");
        if(!loggerDirectory.exists()){
            loggerDirectory.mkdirs();
        }
        return loggerDirectory.getAbsolutePath();
    }

    public Logger getLogger() {
        return this.logger;
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        String currentWorkingDirectory = System.getProperty("user.dir");
        String RUNTIME_PROPERTIES_PATH = currentWorkingDirectory + XMLBuilderConstants.RUNTIME_PROPERTIES_PATH;
        Properties runtimeProperties = new Properties();
        runtimeProperties.load(new FileInputStream(RUNTIME_PROPERTIES_PATH));
        FileLogger fLogger = new FileLogger(runtimeProperties);
    }
    
}
