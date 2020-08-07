/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.CharacterSystem;
import classes.CopyFile;
import classes.Logo;
import controler.Controler;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Model;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Polack
 */
public class ViewFrame extends JFrame implements MouseListener{
    
    private Model m;
    private Controler c;
    
    // NORTH (Games)
    private JPanel gamePanel;
    
    // CENTER (Fight)
    private PlayerPanel player1Panel;
    private ChooseRandPanel randPane;
    
    
    // SOUTH (Character)
    private CharacterPanel characterPanel;
    
    
    
    
    public ViewFrame(Model m, Controler c) throws IOException{
        
        CopyFile.copyFileUsingStream(new File("chrono.png"), new File("chrono_obs.png"));
        
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                try {
                    writeCharacterXmlFile("frame.xml");
                    CopyFile.copyFileUsingStream(new File("blank.png"), new File("cam_obs.png"));
                    CopyFile.copyFileUsingStream(new File("blank.png"), new File("game_obs.png"));
                    //CopyFile.copyFileUsingStream(new File("blank.png"), new File("stat_obs.gif"));
                    CopyFile.copyFileUsingStream(new File("blank.png"), new File("chrono_obs.png"));
                } catch (IOException ex) {
                    Logger.getLogger(ViewFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(ViewFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerException ex) {
                    Logger.getLogger(ViewFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        
        this.m = m;
        this.c = c;
        //this.setTitle("Bouton");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ////////////
        //        //
        ////////////
        try {
            openXmlFile("frame.xml");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ViewFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ViewFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.setLocationRelativeTo(null);
        
        
        //this.setContentPane(new JPanel());
        
        //setFrame();
        
        //player2Panel = new PlayerPanel("games/SFV", 2, 2);
        
        setFrame(false);
        
        
        //characterPanel.setPreferredSize(new Dimension (974,543));
        
        //this.getContentPane().add(new JButton("NORTH"), BorderLayout.NORTH);

//        this.getContentPane().add(new JButton("CENTER"), BorderLayout.CENTER);
//        //Au nord
//        this.getContentPane().add(new JButton("NORTH"), BorderLayout.NORTH);

        //À l'ouest
        
        //À l'est
//        this.getContentPane().add(player2Panel, BorderLayout.EAST);
        //Au sud
        
        
        //this.addMouseListener(this);
        
        this.setVisible(true);
        
    }
    
    /////////////
    // Getters //
    /////////////

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public PlayerPanel getPlayer1Panel() {
        return player1Panel;
    }

    public CharacterPanel getCharacterPanel() {
        return characterPanel;
    }
    
    /////////////
    // Setters //
    /////////////
    
    public void setModel(Model m){
        this.m = m;
    }
    
    public void setFrame(boolean isRand) throws IOException{
        
        int current_game = m.getCurrentGame();
        this.setLayout(new BorderLayout());
//        if(game == -1){
//            gamePanel = new GamePanel(c,m,5);
//            
//        
//        }
//        else{
//            this.getContentPane().removeAll();
//            current_game = game;
//        }
        this.getContentPane().removeAll();
        gamePanel = new GamePanel(c,m,5,current_game);
        //gamePanel = new GamePanel(c,m,5);
        
        
        characterPanel = null;
        characterPanel = new CharacterPanel(c, m);
        
        this.getContentPane().add(gamePanel, BorderLayout.NORTH);
        
        if(isRand){
            randPane = new ChooseRandPanel(m, c, m.getRandList());
            this.getContentPane().add(randPane, BorderLayout.CENTER);
        }
        else{
            player1Panel = new PlayerPanel(m, 
                                       c, 
                                       m.getGameList().get(current_game).getNbCharacters(),
                                       m.getGameList().get(current_game).getNbRound(),
                                       "games/" + m.getGameList().get(current_game).getName(), 
                                       m.getGameList().get(current_game).getNbSystem(), 
                                       m.getGameList().get(current_game).getNbMode(), 
                                       m.getCharacterList());
            this.getContentPane().add(player1Panel, BorderLayout.CENTER);
            randPane = null;
        }
        
        this.getContentPane().add(characterPanel, BorderLayout.SOUTH);
        
        System.out.println(this.characterPanel.getSize().getHeight());
        
        this.setSize(990, 623 + this.characterPanel.getHeight());
        
        this.repaint();
        this.revalidate();
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        //System.out.println(((Logo)e.getSource()).getNb());
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void openXmlFile(String filePath) throws ParserConfigurationException, IOException, SAXException{
        
        int x = 0, y = 0;
        File fileXML = new File(filePath);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xml = builder.parse(fileXML);
        
        Element e = (Element)xml.getFirstChild();
        
        x = Integer.parseInt(e.getAttribute("x_pos"));
        y = Integer.parseInt(e.getAttribute("y_pos"));
        
        this.setLocation(x, y);
        
    }
    
    private void writeCharacterXmlFile(String filePath) throws ParserConfigurationException, TransformerConfigurationException, TransformerException{
        
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("Position");
        
        Attr x = document.createAttribute("x_pos");
        x.setValue(Integer.toString(this.getX()));
        root.setAttributeNode(x);
        
        Attr y = document.createAttribute("y_pos");
        y.setValue(Integer.toString(this.getY()));
        root.setAttributeNode(y);
        
        document.appendChild(root);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(filePath));
        
        transformer.transform(domSource, streamResult);
        
        
    }
    
    
}
