package ca.ryerson.ee.coe848.dal;

import java.sql.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Hossein Fani
 */
public class SQLiteDatabaseManager {
    private Connection connection;
    
    private SQLiteDatabaseManager(){}
    
    private static SQLiteDatabaseManager singletonInstance;
    public synchronized static Connection getConnection() throws IOException, ClassNotFoundException, Exception {
        if(singletonInstance == null || singletonInstance.connection == null || singletonInstance.connection.isClosed()){
            singletonInstance = new SQLiteDatabaseManager();
            Properties settings = new Properties();
            settings.load(SQLiteDatabaseManager.class.getResourceAsStream("config.properties"));
            Class.forName(settings.getProperty("JdbcDriver"));
            Properties info = new Properties();
//            info.setProperty("user", settings.getProperty("DatabaseUser"));
//            info.setProperty("password", settings.getProperty("DatabasePassword"));
//            info.setProperty("allowMultiQueries", settings.getProperty("AllowMultiQueries"));
            
            singletonInstance.connection = DriverManager.getConnection(settings.getProperty("DatabaseUrl") + settings.getProperty("DatabaseName"), info);
            if(singletonInstance.connection == null)
                throw new Exception("Conncetion to the database failed!", null);
            }
        
            //This is very critical for handling transaction commit by program!
            singletonInstance.connection.setAutoCommit(false);
        return singletonInstance.connection;
        
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        singletonInstance.connection.close();
    }
}
