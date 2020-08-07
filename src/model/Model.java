/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classes.CopyFile;
import classes.Fighter;
import classes.FighterImg;
import classes.Game;
import classes.ImageCreator;
import classes.Match;
import classes.Session;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import stat_anim.StatFrame;
import view.AllStatsFrame;
import view.ViewFrame;

/**
 *
 * @author Polack
 */
public class Model {
    
    private ViewFrame v;
    //private AllStatsFrame asv;
    private StatFrame sv;
    
    private ArrayList<Game> gameList;
    private ArrayList<String> characterList;
    private ArrayList<Integer> pickChancesList;
    
    //private int[][] chosenCharacters, systems;
    private FighterImg[][] chosenFighters;
    private boolean[][] rounds;
    private boolean isRanked;
    
    private ArrayList<Integer> randList = null;
    private int currentGame = 0;
    private Session currentSession;
    private ArrayList<Session> previousSessionList;
    private ImageCreator imgCreator;
    private String str[] = {"Casual", "Ranked"};
    
    public Model() throws ParserConfigurationException, IOException, SAXException{
        
        isRanked = true;
        //this.v = v;
        //this.v.setModel(this);
        //this.sv = new StatFrame();
        //this.asv = new AllStatsFrame();
        this.gameList = new ArrayList<Game>();
        openXmlGameFile("games/games.xml");
        
        setCurrentGame(0);
    }
    
    /////////////
    // Boolean //
    /////////////
    
    public boolean isPlayerReady(int player){
        
        boolean b = true;
        
        for(int i = 0; i < this.chosenFighters.length; i++){
            if(this.chosenFighters[i][player].getFighter() == -1)
                b = false;
        }
        
        return b;
        
    }
    
    private int isThisFighterChosen(int fighter){
        int nb = -1;
        
        for(int slot = 0; slot < this.chosenFighters.length; slot++){
            if(fighter == this.chosenFighters[slot][0].getFighter()){
                nb = slot;
            }
        }
        
        return nb;
        
    }
    
    /////////////
    // Getters //
    /////////////

    public ArrayList<Integer> getRandList() {
        return randList;
    }

    public ImageCreator getImgCreator() {
        return imgCreator;
    }
    
    public ViewFrame getV() {
        return v;
    }
    
    public int getCurrentGame() {
        return currentGame;
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<String> getCharacterList() {
        return characterList;
    }

    public ArrayList<Integer> getPickChancesList() {
        return pickChancesList;
    }

    public FighterImg[][] getChosenFighters() {
        return chosenFighters;
    }
    
//    public Fighter getChosenFighter(int slot, int player) {
//        return chosenFighters[slot][player];
//    }

//    public int[][] getChosenCharacters() {
//        return chosenCharacters;
//    }
//    
//    public int getChosenCharacters(int slot, int player) {
//        return chosenCharacters[slot][player];
//    }
//
//    public int[][] getSystems() {
//        return systems;
//    }

    public boolean[][] getRounds() {
        return rounds;
    }

    public Session getCurrentSession() {
        return currentSession;
    }
    
    private ArrayList<String> getFightersName(){
        
        ArrayList<String> list =  new ArrayList<String>();
        
        for(int i = 0; i < this.chosenFighters.length; i++){
            list.add(this.characterList.get(this.chosenFighters[i][0].getFighter()));
            
        }
        //System.out.println(list.get(0));
        return list;
        
    }
    
    private int[] getLastBestTotal(){
        
        //Session best = this.previousSessionList.get(0);
        int nbWinLast = -1, nbWinBest = -1, nbWinTotal = 0, 
            nbFightLast = -1, nbFightBest = -1, nbFightTotal = 0,
            nbPerCentBest = -1;
        
        if(this.previousSessionList.size() > 0){
            System.out.println("Size of previous sessions: " + this.previousSessionList.size());
            for(int i = 0; i < this.previousSessionList.size(); i++){
                Session s = this.previousSessionList.get(i);
                
                if(i == this.previousSessionList.size()-1){
                    nbWinLast = s.getNbWin(); 
                    nbFightLast = s.getNbFight();
                }
                
                if((s.getWinPercent() > nbPerCentBest) && (s.getNbWin() > nbWinBest)){
                    nbWinBest = s.getNbWin(); 
                    nbFightBest = s.getNbFight();
                }
                
                nbWinTotal += s.getNbWin(); 
                nbFightTotal += s.getNbFight();
                
            }
        }     
        
        int[] nb = {nbWinLast, nbFightLast, nbWinBest, nbFightBest, nbWinTotal, nbFightTotal};
        return nb;
        
    }
    
    public String getPath(){
        
        return "games/" + this.gameList.get(currentGame).getName();
        
    }
    
    private ArrayList<String> getPathList(){
        
        ArrayList<String> list = new ArrayList<String>();
        String path = "games/" + this.gameList.get(currentGame).getName() + "/characters/";
        
        
        for(int i = 0; i < this.chosenFighters.length; i++){
            if(this.chosenFighters[i][0].getFighter() == -1){
                list.add("");
            }
            else{
                list.add(path + this.characterList.get(this.chosenFighters[i][0].getFighter()) + "/");
            }
        }
        
        return list;
    }
    
    public boolean isRanked(){
        return this.isRanked;
    }
    
    /////////////
    // Setters //
    /////////////

    public void setIsRanked(boolean isRanked){
        this.isRanked = isRanked;
    }
    
    public void setV(ViewFrame v, StatFrame sv) {
        this.v = v;
        this.sv = sv;
    }
    
    public void setCurrentGame(int game) throws ParserConfigurationException, IOException, SAXException{
        
        try {
            CopyFile.copyFileUsingStream(new File("cam.png"), new File("cam_obs.png"));
            CopyFile.copyFileUsingStream(new File("game.png"), new File("game_obs.png"));
        } catch (IOException ex) {
            Logger.getLogger(ViewFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.currentGame = game;
        //System.out.println("nb char: " + this.gameList.get(game).getNbCharacters());
        this.chosenFighters = setFighterTab(this.gameList.get(game).getNbCharacters());
        
//        if(this.gameList.get(game).getNbSystem() > 0)
//            this.systems = setIntegerTabs(this.gameList.get(game).getNbSystem(), 0);
//        else
//            this.systems = null; 
        
        this.rounds = setBooleanTabs(this.gameList.get(game).getNbRound());
        
        this.characterList = new ArrayList<String>();
        this.pickChancesList = new ArrayList<Integer>();
        openXmlCharactersFile( this.getPath() + "/characters.xml");
        setPickChances();
        
        this.imgCreator = new ImageCreator();
        //CopyFile.copyFileUsingStream(new File("stat2.png"), new File("stat_obs.gif"));
        //CopyFile.copyFileUsingStream(new File("stat2.png"), new File("stat_obs.gif"));
        //this.imgCreator.getTextImage();
        
    }
    
    private void setPickChances(){
        
        int nb = pickChancesList.size()/2;
        boolean b = false;
        
        for(int i = 0; i < pickChancesList.size(); i++){
            if(pickChancesList.get(i) == -1){
                pickChancesList.set(i, nb);
                b = true;
            }
        }
        
        if(b){
            try {
                writeCharacterXmlFile();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    public void setRound(int round, int player){
        
        this.rounds[round][player] = !this.rounds[round][player];
        
        this.v.getPlayer1Panel().setRoundImg(round, player, this.getPath() + "/" + this.rounds[round][player] + ".png");
        this.v.getPlayer1Panel().repaint();
    }
    
    public void setSystem(int system, int player, int fighter){
        try {
            this.chosenFighters[fighter][player].setSystem(system, this.getGameList().get(this.currentGame).getNbMode(), this.getPath());
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.v.getPlayer1Panel().repaint();
        
    }
    
//    public void setSystem(int system, int player){
//        
//        if(this.systems[system][player] == this.gameList.get(currentGame).getNbMode()-1)
//            this.systems[system][player] = 0;
//        else
//            this.systems[system][player]++;
//        
//        String path = "games/" + this.gameList.get(currentGame).getName() + "/system/" + system + "/" + this.systems[system][player] + ".png";
//        
//        this.v.getPlayer1Panel().setSystemImg(system, player, path);
//        this.v.getPlayer1Panel().repaint();
//        
//    }
    
    public void setFighter(int slot, int player, int fighter) throws IOException{
        
        String name = (fighter != -1) ? this.characterList.get(fighter) : "Random";
        
        this.chosenFighters[slot][player].setFighter(fighter, "games/" + this.gameList.get(currentGame).getName() + "/characters/" + name + "/" + player + ".png");
        
        
//        this.v.getPlayer1Panel().setFighterImg(slot, 
//                                               player, 
//                                               "games/" + this.gameList.get(currentGame).getName() + "/characters/" + name + "/" + player + ".png");
        
        this.v.getPlayer1Panel().repaint();
        
        if(player == 0){
//            if(isPlayerReady(0)){
//            
//                this.currentSession = new Session(this.gameList.get(this.currentGame).getName(), getFightersName());
//                this.previousSessionList = new ArrayList<Session>();
//                int nbFiles = this.currentSession.getNbFiles();
//                if(nbFiles > 0){
//                    for(int i = 0; i < nbFiles; i++){
//                        this.previousSessionList.add(new Session(this.gameList.get(this.currentGame).getName(), getFightersName(), 
//                                                                 i,
//                                                                 this.gameList.get(this.currentGame).getNbCharacters(),
//                                                                 this.gameList.get(this.currentGame).getNbMode(),
//                                                                 this.gameList.get(this.currentGame).getNbRound()));
//                    }
//                }
//
//                this.imgCreator.setImageCreator(this.getLastBestTotal());
//                this.imgCreator.getGif(this.currentSession.getNbWin(),this.currentSession.getNbFight());
//
//            }
//            else{
//                this.imgCreator.resetImageCreator();
//                CopyFile.copyFileUsingStream(new File("stat2.png"), new File("stat_obs.gif"));
//            }
            setSession();
        }
        
        this.imgCreator.createFightersImage(getPathList());
        
    }
    
    public void setSession() throws IOException{
        if(isPlayerReady(0)){
            
                this.currentSession = new Session(this.gameList.get(this.currentGame).getName(), getFightersName(), isRanked ? str[1] : str[0]);
                this.previousSessionList = new ArrayList<Session>();
                int nbFiles = this.currentSession.getNbFiles();
                if(nbFiles > 0){
                    for(int i = 0; i < nbFiles; i++){
                        this.previousSessionList.add(new Session(this.gameList.get(this.currentGame).getName(), 
                                                                 getFightersName(), 
                                                                 i,
                                                                 this.gameList.get(this.currentGame).getNbCharacters(),
                                                                 this.gameList.get(this.currentGame).getNbMode(),
                                                                 this.gameList.get(this.currentGame).getNbRound(),
                                                                 isRanked ? str[1] : str[0]));
                    }
                }

                this.imgCreator.setImageCreator(this.getLastBestTotal());
                this.imgCreator.getGif(this.currentSession.getNbWin(),this.currentSession.getNbFight());
                
                // Stat Frames updates
                //this.asv.setVisible((int)this.v.getLocation().getX(), (int)this.v.getLocation().getY());
                //this.asv.refresh();
                
                this.sv.getP().setIsCurrent(true);
                if(nbFiles > 0)
                    this.sv.getP().setIsTotal(true);
                else
                    this.sv.getP().setIsTotal(false);
                
                this.sv.getP().setIsModified(true);
                this.sv.getP().refresh();
            }
            else{
                this.imgCreator.resetImageCreator();
                //CopyFile.copyFileUsingStream(new File("stat2.png"), new File("stat_obs.gif"));
                this.sv.getP().setIsCurrent(false);
                this.sv.getP().setIsTotal(false);
                this.sv.getP().setIsModified(false);
                this.sv.getP().refresh();
            }
    }
    
    public void prepareMultipleRandomFighters(){
        
        ArrayList<Integer> pickList = new ArrayList<Integer>();
        randList = new ArrayList<Integer>();
        
        for(int nbFighter = 0; nbFighter < this.pickChancesList.size(); nbFighter++){
            for(int nb = 0; nb < this.pickChancesList.get(nbFighter); nb++){
                pickList.add(nbFighter);
            }
        }
        
        while(randList.size() != 5){
            int randomFighter = ThreadLocalRandom.current().nextInt(0, pickList.size());
            if(!isSameCharacter(pickList.get(randomFighter), randList))
                randList.add(pickList.get(randomFighter));
        }
        
        for(int i = 0; i < 5; i++){
            int randomFighter = ThreadLocalRandom.current().nextInt(0, pickList.size());
            
        }
    }
    
    public void setRandomFighter(int slot) throws IOException{
        
        ArrayList<Integer> pickList = new ArrayList<Integer>();
        
        for(int nbFighter = 0; nbFighter < this.pickChancesList.size(); nbFighter++){
            for(int nb = 0; nb < this.pickChancesList.get(nbFighter); nb++){
                pickList.add(nbFighter);
            }
        }
        
        int randomFighter = ThreadLocalRandom.current().nextInt(0, pickList.size());
        updatePickChances(slot, pickList.get(randomFighter));
        
    }
    
    public void updatePickChances(int slot, int pick) throws IOException{
        
        setFighter(slot, 0, pick);
        
        if(isPlayerReady(0)){
            
            int points = this.pickChancesList.get(pick) - 1;
            ArrayList<Integer> selected = new ArrayList<Integer>();
            selected.add(pick);
            int score = 1;

            setPickChancesDown(pick);

            while(points != 0){
                System.out.println("Points: " + points);
                ArrayList<Integer> l = new ArrayList<Integer>();

                System.out.println("Creating list to up");
                for(int character = 0; character < this.pickChancesList.size(); character++){
                    if(!isSameCharacter(character, selected)){ // check ALL characters that changed
                        if(this.pickChancesList.get(character) == score){
                            System.out.println("Adding character " + character);
                            l.add(character);
                        }
                    }
                }
                System.out.println("List: " + l.size());
                if(l.size() > points){
                    System.out.println("Search for random");
                    // search 'points' random characters to up
                    ArrayList<Integer> rand = new ArrayList<Integer>();
                    while(rand.size() != points){
                        int randomFighter = ThreadLocalRandom.current().nextInt(0, l.size());
                        rand.add(l.get(randomFighter));
                        l.remove(randomFighter);
                    }
                    l = (ArrayList<Integer>) rand.clone();
                    //rand = null;

                }
                //compute ups
                for(int i = 0; i < l.size(); i++){
                    System.out.println("Up");
                    setPickChancesUp(l.get(i));
                    selected.add(l.get(i));
                }
                points = points - l.size();
                score++;
            }

            // Ancient Method

    //            pickList = new ArrayList<Integer>();
    //            for(int i = 0; i < this.pickChancesList.size(); i++){
    //                boolean b = true;
    //                for(int j = 0; j < this.gameList.get(this.currentGame).getNbCharacters(); j++){
    //                    if(i == this.chosenFighters[j][0].getFighter()){
    //                        b = false;
    //                    }
    //                }
    //                if(b){
    //                    pickList.add(i);
    //                }
    //            }
    ////            for(int j = 0; j < this.gameList.get(this.currentGame).getNbCharacters(); j++){
    //////                setPickChancesDown(this.chosenFighters[j][0].getFighter());
    //////                System.out.println("Down: " + this.chosenFighters[j][0].getFighter());
    //////                randomFighter = ThreadLocalRandom.current().nextInt(0, pickList.size());
    //////                setPickChancesUp(pickList.get(randomFighter));
    //////                System.out.println("Up: " + pickList.get(randomFighter));
    //////                pickList.remove(randomFighter);
    ////            }
    //            for(int j = 0; j < this.characterList.size(); j++){
    //                int temp = isThisFighterChosen(j);
    //                if(temp == -1){
    //                    setPickChancesUp(j);
    //                }else{
    //                    setPickChancesDown(j);
    //                }
    //
    //            }
            try {
                writeCharacterXmlFile();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Done!");
        }
        
        
    }
    
    private boolean isSameCharacter(int nb, ArrayList<Integer> l){
        
        for(int i = 0; i < l.size(); i++){
            if(nb == l.get(i)){
                return true;
            }
        }
        
        return false;
    }
    
    private void setPickChancesUp(int fighter){
        
        if(this.pickChancesList.get(fighter) < this.pickChancesList.size())
            this.pickChancesList.set(fighter, this.pickChancesList.get(fighter)+1);
        
    }
    
    private void setPickChancesDown(int fighter){
        
        if(this.pickChancesList.get(fighter) > 1)
            this.pickChancesList.set(fighter, /*this.pickChancesList.get(fighter)-1*/ 1);
        
    }
    
    private int[][] setIntegerTabs(int nbSlot, int value){
        
        int tab[][] = new int[nbSlot][2];
        
        for(int i = 0; i < nbSlot; i++){
            for(int j = 0; j < 2; j++){
                tab[i][j] = value;
            }
        }
        
        return tab;
        
    }
    
    private FighterImg[][] setFighterTab(int nbSlot){
        
        FighterImg tab[][] = new FighterImg[nbSlot][2];
        
        for(int fighter = 0; fighter < nbSlot; fighter++){
            for(int player = 0; player < 2; player++){
                try {
                    tab[fighter][player] = new FighterImg(this.gameList.get(this.currentGame).getNbSystem(), this.getPath(), player);
                } catch (IOException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return tab;
        
    }
    
    private boolean[][] setBooleanTabs(int nbRounds){
        
        boolean tab[][] = new boolean[nbRounds][2];
        
        for(int i = 0; i < nbRounds; i++){
            for(int j = 0; j < 2; j++){
                tab[i][j] = false;
            }
        }
        
        return tab;
        
    }
    
    ///////////
    // OTHER //
    ///////////
    
    public void addMatchToSession() throws IOException{
        
        this.currentSession.getMatchList().add(new Match(LocalDateTime.now(), this.chosenFighters, this.rounds));
        this.rounds = setBooleanTabs(this.gameList.get(currentGame).getNbRound());
        for(int round = 0; round < this.rounds.length; round++){
            for(int player = 0; player < 2; player++){
                this.v.getPlayer1Panel().setRoundImg(round, player, this.getPath() + "/" + this.rounds[round][player] + ".png");
            }
        }
        this.v.getPlayer1Panel().repaint();
        
        try {
            this.currentSession.writeSessionFile();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.imgCreator.getGif(this.currentSession.getNbWin(),this.currentSession.getNbFight());
        //this.asv.refresh();
        
        this.sv.getP().setIsModified(true);
        this.sv.getP().refresh();
        
    }
    
    ////////////////
    // XML READER //
    ////////////////
    
    private void openXmlCharactersFile(String filePath) throws ParserConfigurationException, IOException, SAXException{
        
        File fileXML = new File(filePath);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xml = builder.parse(fileXML);
        
        //System.out.println("Root element :" + xml.getDocumentElement().getNodeName());
               
        NodeList gameNodeList = xml.getElementsByTagName("Character");
			
	//System.out.println("----------------------------");
        
        //System.out.println("length :" + gameNodeList.getLength());

	for (int temp = 0; temp < gameNodeList.getLength(); temp++) {

            Node gameNode = gameNodeList.item(temp);

            if (gameNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) gameNode;
                this.characterList.add(eElement.getAttribute("name"));
                this.pickChancesList.add(Integer.parseInt(eElement.getAttribute("pick_chances")));
                //, eElement.getAttribute("pick_chances"
            }
	}
    }
    
    private void openXmlGameFile(String filePath) throws ParserConfigurationException, IOException, SAXException{
        
        File fileXML = new File(filePath);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xml = builder.parse(fileXML);
        
        //System.out.println("Root element :" + xml.getDocumentElement().getNodeName());
               
        NodeList gameNodeList = xml.getElementsByTagName("Game");
			
	//System.out.println("----------------------------");
        
        //System.out.println("length :" + gameNodeList.getLength());

	for (int temp = 0; temp < gameNodeList.getLength(); temp++) {

            Node gameNode = gameNodeList.item(temp);

            if (gameNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) gameNode;
                //System.out.println("\nName: " + eElement.getAttribute("name"));
                //System.out.println("\nTeam Game: " + eElement.getAttribute("team_game"));
                this.gameList.add(new Game(eElement.getAttribute("name"), 
                                           eElement.getAttribute("nb_round"), 
                                           eElement.getAttribute("nb_system"), 
                                           eElement.getAttribute("nb_mode_per_system")));
            }
	}
    }
    
    ////////////////
    // XML WRITER //
    ////////////////
    
    private void writeCharacterXmlFile() throws ParserConfigurationException, TransformerConfigurationException, TransformerException{
        
        String xmlFilePath = "games/" + this.gameList.get(this.currentGame).getName() + "/characters.xml";
        System.out.println(xmlFilePath);
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
 
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("Characters");
        
        document.appendChild(root);
        
        for(int i = 0; i < this.pickChancesList.size(); i++){
            //document.appendChild(root);
            Element character = document.createElement("Character");
            
            Attr name = document.createAttribute("name");
            Attr pickChances = document.createAttribute("pick_chances");
            
            name.setValue(this.characterList.get(i));
            pickChances.setValue(Integer.toString(this.pickChancesList.get(i)));
            
            character.setAttributeNode(name);
            character.setAttributeNode(pickChances);
            
            root.appendChild(character);
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
        
        transformer.transform(domSource, streamResult);

	System.out.println("File saved!");
        
    }
    
    
}
