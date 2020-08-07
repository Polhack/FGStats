/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Polack
 */
public class Character {
    
    private String name;
    private int pickChances;
    
    public Character(String name, String pickChances) throws ParserConfigurationException, IOException, SAXException{
        
        this.name = name;
        this.pickChances = Integer.parseInt(pickChances);
    
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getPickChances(){
        return this.pickChances;
    }
    
    public void setPickChancesDown(){
        
        if(this.pickChances > 1)
            this.pickChances--;
        
        System.out.println(this.name + " has now " + this.pickChances);
        
    }
    
    public boolean setPickChancesUp(){
        
        if(this.pickChances < 9){
            this.pickChances++;
            System.out.println(this.name + " has now " + this.pickChances);
            return true;
        }
        return false;
    }
}
