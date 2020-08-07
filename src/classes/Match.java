/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.time.LocalDateTime;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Polack
 */
public class Match {
    
    private Fighter[][] fightersTab;
    private boolean[][] roundsTab;
    private int [] date = new int[5];
    
    public Match(LocalDateTime dateTime,
                 FighterImg[][] fightersTab,
                 boolean[][] roundsTab){
        
        this.fightersTab = new Fighter[fightersTab.length][2];
        for(int fighter = 0; fighter < fightersTab.length; fighter++){
            
            this.fightersTab[fighter][0] = fightersTab[fighter][0].getFighterData();
            this.fightersTab[fighter][1] = fightersTab[fighter][1].getFighterData();
            
        }
        
//        this.fightersTab = fightersTab.clone();
//        System.out.println(this.fightersTab[0][0]);
//        if(systemsTab == null)
//            this.systemsTab = null;
//        else
//            this.systemsTab = systemsTab.clone();
        
        this.roundsTab = roundsTab.clone();
        
        this.date[0] = dateTime.getYear();
        this.date[1] = dateTime.getMonthValue();
        this.date[2] = dateTime.getDayOfMonth();
        this.date[3] = dateTime.getHour();
        this.date[4] = dateTime.getMinute();
        
    }
    
    public Match(int[] date,
                 Fighter[][] fightersTab,
                 boolean[][] roundsTab){
        
        this.fightersTab = fightersTab.clone();
        
//        if(systemsTab == null)
//            this.systemsTab = null;
//        else
//            this.systemsTab = systemsTab.clone();
        
        this.roundsTab = roundsTab.clone();
        
        this.date[0] = date[0];
        this.date[1] = date[1];
        this.date[2] = date[2];
        this.date[3] = date[3];
        this.date[4] = date[4];
        //getMessageOnScreen();
        
        
    }
    
//    public void getMessageOnScreen(){
//        
//        System.out.println("Date: " + this.date[2] + "/" + this.date[1] + "/" + this.date[0] + " " + this.date[3] + ":" + this.date[4] );
//        
//        for(int x = 0; x < 2; x++){
//            System.out.println("Player " + x + ": ");
//            for(int i = 0; i < this.fightersTab.length; i++){
//                System.out.println("    Fighter: " + this.fightersTab[i][x]);
//            }
//            if(this.systemsTab != null){
//                for(int i = 0; i < this.systemsTab.length; i++){
//                    System.out.println("    System " + i + ": " + this.systemsTab[i][x]);
//                }
//            }
//            for(int i = 0; i < this.roundsTab.length; i++){
//                System.out.println("    Round " + i + ": " + this.roundsTab[i][x]);
//            }
//            
//        }
//        
//    }
    
    public int getYear(){
        return this.date[0];
    }
    
    public int getMonth(){
        return this.date[1];
    }
    
    public int getDay(){
        return this.date[2];
    }
    
    public int getHour(){
        return this.date[3];
    }
    
    public int getMinute(){
        return this.date[4];
    }

    public Fighter[][] getFightersTab() {
        return fightersTab;
    }
    
//    public int getFightersTab(int fighter, int player) {
//        return fightersTab[fighter][player];
//    }

//    public int[][] getSystemsTab() {
//        return systemsTab;
//    }
//    
//    public int getSystemsTab(int system, int player) {
//        return systemsTab[system][player];
//    }

    public boolean[][] getRoundsTab() {
        return roundsTab;
    }
    
    public boolean getRoundsTab(int rounds, int player) {
        return roundsTab[rounds][player];
    }
    
    public boolean isWin(){
        
        boolean b = false;
        int p1 = 0, p2 = 0;
        
        for(int i = 0; i < this.getRoundsTab().length; i++){
            if(this.roundsTab[i][0])
                p1++;
            
            if(this.roundsTab[i][1])
                p2++;
            
        }
        
        if(p1 == ((this.getRoundsTab().length/2)+1) && p1 > p2)
            b = true;
        
        return b;
        
    }
    
    public Element getMatchElement(Document document){
        
        Element match = document.createElement("Match");
        
        Attr aYear = document.createAttribute("year");
        aYear.setValue(Integer.toString(this.getYear()));
        Attr aMonth = document.createAttribute("month");
        aMonth.setValue(Integer.toString(this.getMonth()));
        Attr aDay = document.createAttribute("day");
        aDay.setValue(Integer.toString(this.getDay()));
        Attr aHour = document.createAttribute("hour");
        aHour.setValue(Integer.toString(this.getHour()));
        Attr aMinute = document.createAttribute("minute");
        aMinute.setValue(Integer.toString(this.getMinute()));
        
        match.setAttributeNode(aYear);
        match.setAttributeNode(aMonth);
        match.setAttributeNode(aDay);
        match.setAttributeNode(aHour);
        match.setAttributeNode(aMinute);
        
        for(int player = 0; player < 2; player++){
            Element player_element = document.createElement("Player");
            
            for(int fighter = 0; fighter < this.fightersTab.length; fighter++){
                Element e = document.createElement("Fighter");
                
                if(this.fightersTab[fighter][player].getSystem() != null){
                    for(int system = 0; system < this.fightersTab[fighter][player].getSystem().length; system++){
                        Attr attr = document.createAttribute("system_" + (system+1));
                        attr.setValue(Integer.toString(this.fightersTab[fighter][player].getSystem()[system]));
                        e.setAttributeNode(attr);
                    }
                }
                
                e.setTextContent(Integer.toString(this.fightersTab[fighter][player].getFighter()));
                player_element.appendChild(e);
            }
            
//            if(this.fightersTab[][] != null){
//                for(int j = 0; j < this.systemsTab.length; j++){
//                    Element e = document.createElement("System");
//                    e.setTextContent(Integer.toString(this.systemsTab[j][i]));
//                    player.appendChild(e);
//                }
//            }
            
            for(int round = 0; round < this.roundsTab.length; round++){
                Element e = document.createElement("Round");
                e.setTextContent(Boolean.toString(this.roundsTab[round][player]));
                player_element.appendChild(e);
            }
            
            match.appendChild(player_element);
        }
        
        
        return match;
        
    }
    
    
    
}
