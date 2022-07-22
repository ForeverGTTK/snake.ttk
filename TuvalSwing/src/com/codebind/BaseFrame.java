package com.codebind;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;

public class BaseFrame extends JFrame {

    JFrame frame;
    public JPanel rightPanel;
    public JPanel mainPanel;
    public JPanel leftPanel;

    public static int score;
    public static int GameScore = 0;
    public static JPanel scorePanel;
    public static HscoreLS Hscore;
    private static String playerName = "ForeverGTTK";


    public static JButton resetButton;
    public static JButton pauseButton;
    public static JSlider speedSlider;


    BaseFrame() {
        ImageIcon image = new ImageIcon("G_Logo.png");
        this.setIconImage(image.getImage());
        this.Hscore= new HscoreLS();
        baseBuild();
        rightPanelBuild();
        mainPanel.add(new GamePanel());
        mainPanel.grabFocus();
        frame.setVisible(true);

    }

    public void pauseMessage() {
        JOptionPane.showMessageDialog(null, "You paused !\n\t press again to continue?", "Snake.TTK", JOptionPane.INFORMATION_MESSAGE);
    }

    void baseBuild() {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 0));
        frame.setSize(GamePanel.SCREEN_WIDTH + 200, GamePanel.SCREEN_HIGHET + 200);

        // ------------------- panels -----------------------
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        mainPanel = new JPanel();
        leftPanel.setBackground(Color.black);
        rightPanel.setBackground(Color.black);
        topPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setBackground(Color.black);
        mainPanel.setBackground(Color.green);
        leftPanel.setPreferredSize(new Dimension(100, 400));
        rightPanel.setPreferredSize(new Dimension(100, 400));
        topPanel.setPreferredSize(new Dimension(400, 50));
        bottomPanel.setPreferredSize(new Dimension(400, 50));
        mainPanel.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HIGHET));
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(mainPanel, BorderLayout.CENTER);
    }

    void rightPanelBuild() {
        scorePanel = new JPanel();
        rightPanel.setFocusable(false);
        resetButton = new JButton("Reset");
        pauseButton = new JButton("Pause");
        speedSlider = new JSlider(1, 25, 5);

        speedSlider.setPreferredSize(new Dimension(100, 50));
        speedSlider.setPaintTicks(true);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setPaintLabels(true);

        pauseButton.setFocusable(false);
        resetButton.setFocusable(false);
        speedSlider.setFocusable(false);

//        scorePanel.setBackground(Color.darkGray);
//        scorePanel.setOpaque(true);
//        scorePanel.setSize(100, 70);
//        leftPanel.add(scorePanel);

        pauseButton.addActionListener(e -> {
                    if (pauseButton.getText() == "Continue") {
                        pauseButton.setText("Pause");
                        GamePanel.running = true;
                    } else {
                        pauseButton.setText("Continue");
                        GamePanel.running = false;
                        pauseMessage();
                    }
                });

        rightPanel.add(resetButton);
        rightPanel.add(pauseButton);
        rightPanel.add(new JLabel("Game speed"));
        rightPanel.add(speedSlider);
        rightPanel.add(new JLabel("Your Score\n"));
        rightPanel.add(scorePanel, BorderLayout.AFTER_LAST_LINE);


    }

    public static String getPlayerName(){
        return playerName;
    }

    public void setPlayerName(String newName){
        this.playerName = newName;
    }

    public static class HscoreLS {
            private int[] scoretable;
            private static String[] nametable;
            private static final int Hscorelistlength = 9 ;

            HscoreLS(){
                this.scoretable= new int[Hscorelistlength] ;
                this.nametable= new String[Hscorelistlength];
                getlistfromFile();
            }

            public int getScore(int index){
            return this.scoretable[index];
            }

            public String getName(int index){
                return this.nametable[index];
            }

            public int getListLength(){
                return Hscorelistlength;
            }

            public void scoreCheck (){
                for (int i=0; i<getListLength();i++){
                    if(GameScore>this.scoretable[i]){
                            for (int k=getListLength(); k>i;k--){
                            try {
                                scoretable[k] = scoretable[k - 1];
                                nametable[k] = nametable[k-1];
                            }
                            catch (IndexOutOfBoundsException ex){continue;}
                            }
                        scoretable[i] = BaseFrame.GameScore;
                            nametable[i] = BaseFrame.getPlayerName();
                        writeListToFile();
                        break;
                    }
                }

            }

            public void writeListToFile(){
                OutputStream ops = null;
                ObjectOutputStream objOps = null;
                try {
                    ops = new FileOutputStream("Hscore.txt");
                    objOps = new ObjectOutputStream(ops);
                    objOps.writeObject(BaseFrame.Hscore);
                    objOps.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    try{
                        if(objOps != null) objOps.close();
                    } catch (Exception ex){

                    }
                }
            }

            public void getlistfromFile() {
                InputStream fileIs = null;
                ObjectInputStream objIs = null;
                try {
                    fileIs = new FileInputStream("Hscore.txt");
                    objIs = new ObjectInputStream(fileIs);
                    HscoreLS hscoreLS = (HscoreLS) objIs.readObject();
                    for (int i=0;i< getListLength();i++){
                       this.scoretable[i]= hscoreLS.scoretable[i];
                       this.nametable[i]= hscoreLS.nametable[i];
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (objIs != null) objIs.close();
                    } catch (Exception ex) {

                    }
                }

            }

        }

}

