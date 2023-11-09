package com.gw.cip.main;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.*;

public class DatabaseConnector {  //http://10.128.10.60:9090/

    DatabaseConnector(String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection(url,username,password);
        con.close();
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        DatabaseConnector dbConnector = new DatabaseConnector("jdbc:h2:file:/tmp/guidewire/bc","", "");
    }

}
