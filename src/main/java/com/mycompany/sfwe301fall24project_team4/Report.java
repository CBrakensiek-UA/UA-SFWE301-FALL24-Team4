/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;
import java.util.ArrayList;

/**
 *
 */
public class Report {
    private ArrayList<String> contents;
    
    public Report(){
        contents = new ArrayList<>();
    }
    
    public void addLine(String line){
        contents.add(line);
    }
    
    public void printReport(){
        for(String s : contents){
            System.out.println(s);
        }
    }
}
