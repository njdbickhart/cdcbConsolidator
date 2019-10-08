/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdcbconsolidator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author dbickhart
 */
public class databaseWrapper {
    private Connection connection;
    private final Map<String, List<String>> tables;
    private Path dbLocation;
    private final String defaultName = "CDCB.sqldb";
    private static final Logger log = Logger.getLogger(databaseWrapper.class.getName());
    
    public databaseWrapper(){
        String home = System.getProperty("user.home");
        Path homeP = Paths.get(home);
        homeP = homeP.resolve(defaultName);        
        
        this.tables = new HashMap<>();
        
        if(homeP.toFile().exists()){
            log.log(Level.FINE, "Writing to existing database");
            this.connectToDB(homeP.toString());
        }else if(homeP.toFile().canWrite()){
            log.log(Level.FINE, "Writing to new database");
            this.connectToDB(homeP.toString());
        }else{
            log.log(Level.FINE, "Could not write to home directory, writing to temp directory instead...");
            this.connectToDB(Paths.get(System.getProperty("java.io.tmpdir"))
                    .resolve(this.defaultName)
                    .toString());
        }
    }
    
    private void connectToDB(String locURL){
        try{
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + locURL);            
        }catch(SQLException ex){
            log.log(Level.SEVERE, "Error connecting to DB at URL" + locURL, ex);
        }
    }
    
    public void createTable(String tableName, String primaryKey, List<String> headers){
        // Take care of primary key removal
        headers = headers.stream()
                .filter(s -> !s.equals(primaryKey))
                .collect(Collectors.toList());
        

        if(!this.tables.containsKey(tableName)){
            this.tables.put(tableName, new ArrayList<>());
            this.tables.get(tableName).add(primaryKey);
            this.tables.get(tableName).addAll(headers);
            
            // Now to create it in the database
            StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (\n");
            sql.append(primaryKey).append(" text PRIMARY KEY,\n");
            for(String h : headers)
                sql.append(h).append(" text,\n");
            
            sql.append(")\n");
            try{
                Statement stmt = this.connection.createStatement();
                stmt.execute(sql.toString());
            }catch(SQLException ex){
                log.log(Level.SEVERE, "Error creating table: " + tableName, ex);
            }
        }else{
            log.log(Level.FINE, "Already knew table existed");
        }        
    }
    
    public void bufferedInsert(String tableName, List<? extends AnimalEntry> animals ){        
        // Obligatory check to make sure that the table exists
        // In practice, I should avoid this! Define the attributes up front
        if(!this.tables.containsKey(tableName))
            this.createTable(tableName, animals.get(0).getPrimaryKey(), animals.get(0).getAttributes());
        
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + "(");
        ArrayList<String> attributes = new ArrayList<>(this.tables.keySet());
        List<String> questions = attributes.stream()
                .map(s -> "?")
                .collect(Collectors.toList());
        
        sql.append(StrUtils.StrArray.Join(attributes, ",")).append(") VALUES(");
        sql.append(StrUtils.StrArray.Join((ArrayList<String>)questions, ",")).append(")");
        
        try{
            PreparedStatement pstmt = this.connection.prepareStatement(sql.toString());
            for(int j = 0; j < animals.size(); j++){
                for(int i = 0; i < attributes.size(); i++)
                    pstmt.setString(i, animals.get(j).getValue(attributes.get(i)));
                pstmt.executeUpdate();
            }
        }catch(SQLException ex){
            log.log(Level.SEVERE, "Error writing to SQL db for table: " + tableName, ex);
        }
    }
}
