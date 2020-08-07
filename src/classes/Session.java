/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Polack
 */

public class Session {
    
    private String folderPath;
    private ArrayList<Match> matchList;
    private int nbFiles;
    
    public Session(String game, ArrayList<String> nameList, String isRanked){
       
        String folder = "games/" + game + setFolderName(nameList);
        createFolder(folder);
        createFolder(folder + "/Sessions/");
        this.folderPath = folder + "/Sessions/" + isRanked + "/";
        createFolder(this.folderPath);
        this.matchList = new ArrayList<Match>();
        this.nbFiles = getNbFiles();
        
        
    }
    
    public Session(String game, ArrayList<String> nameList, int nbSession, int nbFighters, int nbSystems, int nbRounds, String isRanked){
        
        this.folderPath = "games/" + game + setFolderName(nameList) + "/Sessions/" + isRanked + "/";
        this.matchList = new ArrayList<Match>();
        try {
            openSessionFile(new File (this.folderPath + "/Session_" + nbSession + ".xml"), nbFighters, nbSystems, nbRounds);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Session(String path){
        
        this.folderPath = path;
        this.matchList = new ArrayList<Match>();
        
        
        try {
            openSessionFile(new File(path), 1, 2, 3);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /////////////
    // GETTERS //
    /////////////
    
    public ArrayList<Match> getMatchList() {
        return matchList;
    }
    
    public int getNbFiles(){
        return new File(folderPath).listFiles().length;
    }
    
    public int getWinPercent(){
        
        int nb = (getNbWin()*100)/getNbFight();
        
        if((getNbWin()*100)%getNbFight() != 0){
            nb++;
        }
        
        return nb;
        
    }
    
    public int getNbFight(){
        return this.matchList.size();
    }
    
    public int getNbWin(){
        
        int win = 0;
        
        if(this.matchList.size() > 0){
            for(int i = 0; i < this.matchList.size(); i++){
                if(this.matchList.get(i).isWin()){
                    win++;
                }
            }
        }
        
        return win;
        
    }
    
    /////////////
    // SETTERS //
    /////////////
    
    private String setFolderName(ArrayList<String> nameList){
        
        String folderName;
        
        if(nameList.size() == 3)
            folderName = "/team/";
        else
            folderName = "/characters/";
        
        System.out.println(folderName);
        
        for(int i = 0; i < nameList.size(); i++){
            if(i != 0)
                folderName = folderName + " ";
            
            folderName = folderName + nameList.get(i);
            
            System.out.println(folderName);
        }
        
        return folderName;
        
    }
    
    ///////////
    // OTHER //
    ///////////
    
    private void createFolder(String folder){
        File file = new File(folder);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
                //return true;
            } 
        }
        //return false;
    }
    
    
    ///////////////
    // XML FILES //
    ///////////////
    
    public void writeSessionFile() throws ParserConfigurationException, TransformerConfigurationException, TransformerException{
        
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        documentBuilder = documentFactory.newDocumentBuilder();
        
        Document document = documentBuilder.newDocument();
        
        Element root = document.createElement("Session");
        document.appendChild(root);
        
        for(int i = 0; i < this.matchList.size(); i++){
            Element match = this.matchList.get(i).getMatchElement(document);
            root.appendChild(match);
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(folderPath + "/Session_" + nbFiles + ".xml"));
        
        transformer.transform(domSource, streamResult);
        
        System.out.println("File Session_" + nbFiles + " saved!");
    }
    
    public void openSessionFile(File sessionFile, int nbFighters, int nbSystems, int nbRounds) throws ParserConfigurationException, SAXException, IOException{
        
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xml = builder.parse(sessionFile);
        //Element root = xml.getDocumentElement();
        NodeList matchNodeList = xml.getElementsByTagName("Match");
        //System.out.println(root.getElementsByTagName("Round").getLength());
        
        
        
        for (int match = 0; match < matchNodeList.getLength(); match++) { // All Matchs
            
            Fighter[][] fighters = new Fighter[nbFighters][2];
            for(int fighter = 0; fighter < nbFighters; fighter++){
                for(int player = 0; player < 2; player++){
                    fighters[fighter][player] = new Fighter(nbSystems);
                }
            }
            boolean[][] rounds = new boolean[nbRounds][2];
            int[] date = new int[5];
            
            date[0] = Integer.parseInt(((Element)matchNodeList.item(match)).getAttribute("year"));
            date[1] = Integer.parseInt(((Element)matchNodeList.item(match)).getAttribute("month"));
            date[2] = Integer.parseInt(((Element)matchNodeList.item(match)).getAttribute("day"));
            date[3] = Integer.parseInt(((Element)matchNodeList.item(match)).getAttribute("hour"));
            date[4] = Integer.parseInt(((Element)matchNodeList.item(match)).getAttribute("minute"));
            
            NodeList playerNodeList = matchNodeList.item(match).getChildNodes();
            
            for (int player = 0; player < playerNodeList.getLength(); player++) { // 2 Players
                
                NodeList fighterNodeList = ((Element)playerNodeList.item(player)).getElementsByTagName("Fighter");
                
                for (int fighter = 0; fighter < fighterNodeList.getLength(); fighter++) {
                    fighters[fighter][player].setFighter(Integer.parseInt(fighterNodeList.item(fighter).getTextContent()));
                    if(nbSystems > 0){
                        System.out.println(nbSystems);
                        for (int system = 0; system < nbSystems; system++) {
//                            System.out.println("System " + k + " " + i + ": " + Integer.parseInt(((Element)fighterNodeList.item(i)).getAttribute("system_" + (k+1))));
//                            System.out.println();
                            fighters[fighter][player].setSystem(system, Integer.parseInt(((Element)fighterNodeList.item(fighter)).getAttribute("system_" + (system+1))));
                            //systems[k][i] = Integer.parseInt(((Element)fighterNodeList.item(fighter)).getAttribute("system_" + (system+1)));
                        }
                    }
                }
                
//                if(nbSystems > 0){
//                    NodeList systemNodeList = ((Element)playerNodeList.item(j)).getElementsByTagName("System");
//                    
//                    for (int i = 0; i < systemNodeList.getLength(); i++) {
//                        systems[i][j] = Integer.parseInt(systemNodeList.item(i).getTextContent());
//                    }
//                }
                
                NodeList roundsNodeList = ((Element)playerNodeList.item(player)).getElementsByTagName("Round");
              
                for (int round = 0; round < roundsNodeList.getLength(); round++) {
                    rounds[round][player] = Boolean.parseBoolean(roundsNodeList.item(round).getTextContent());
                }
                
            }
            this.matchList.add(new Match(date, fighters, rounds));
            
        }
        
    }
    
}
