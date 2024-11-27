/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;
import java.util.ArrayList;
import java.io.*;

/**
 * This class manages a an external file for use making log CSVs and report files.
 */
public class ExternalFile {
    private String filename;
    private ArrayList<String> contents;
    
    public ExternalFile(){
        filename = "TestLog.csv";
        
    }
    public ExternalFile(String filename){
        this.filename = filename;
        contents = new ArrayList<>();
    }
    
    public ArrayList<String> getContents(){
        return contents;
    }
    
    public boolean readFromFile(){
        // returns whether reading was successful
        
        try{
            // check if file exists and create one if it doesn't
            File file = new File(filename);
            if(!file.exists()){
                file.createNewFile();
            }
        }
        catch(IOException e){
            System.out.println("Error verifying existance of " + filename + ": " + e.getMessage());
            return false;
        }
        
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            contents.clear();
            
            // add each line of the file to contents
            String line;
            while((line = reader.readLine()) != null){
                contents.add(line);
            }
        }
        catch(IOException e){
            System.out.println("Error reading file " + filename + ": " + e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public boolean writeToFile(){
        try(FileWriter writer = new FileWriter(filename)){
            for(String content : contents){
                writer.write(content + "\n");
                //System.out.println("wrote: " + content);
                
            }
        }
        catch(IOException e){
            System.out.println("Error writing to file " + filename + ": " + e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public void printContents(){
        System.out.println("Contents BEGIN");
        for(String content : contents){
            System.out.println(content);
        }
        System.out.println("Contents END");
    }
    
    public void addContent(String content){
        contents.add(content);
    }
    
    public void clearContent(){
        contents.clear();
    }
}
