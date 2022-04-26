package com.codebind;

import javax.swing.*;
import java.awt.*;

import static com.codebind.GamePanel.*;

public class BaseFrame extends JFrame {

    JFrame frame;
    public   JPanel rightPanel;
    public   JPanel mainPanel;

    public static int score;
    public static int GameScore =0;
    public static JPanel scorePanel;

    public static JButton resetButton;
    public static JButton pauseButton;
    public static JSlider speedSlider;


    BaseFrame () {
        ImageIcon image = new ImageIcon("G_Logo.png");
        this.setIconImage(image.getImage());
        baseBuild();
        rightPanelBuild();
        mainPanel.add(new GamePanel());
        mainPanel.grabFocus();
        frame.setVisible(true);

    }
    public void pauseMessage (){
        JOptionPane.showMessageDialog(null,"You paused !\n\t press again to continue?","Snake.TTK",JOptionPane.INFORMATION_MESSAGE);
    }
    void baseBuild () {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10,0));
        frame.setSize(SCREEN_WIDTH+200, SCREEN_HIGHET+200);

        // ------------------- panels -----------------------
        JPanel leftPanel= new JPanel();
        rightPanel= new JPanel();
        JPanel topPanel= new JPanel();
        JPanel bottomPanel= new JPanel();
        mainPanel= new JPanel();
        leftPanel.setBackground(Color.black);
        rightPanel.setBackground(Color.black);
        topPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setBackground(Color.black);
        mainPanel.setBackground(Color.green);
        leftPanel.setPreferredSize(new Dimension(100,400));
        rightPanel.setPreferredSize(new Dimension(100,400));
        topPanel.setPreferredSize(new Dimension(400,50));
        bottomPanel.setPreferredSize(new Dimension(400,50));
        mainPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HIGHET));
        frame.add(leftPanel,BorderLayout.WEST);
        frame.add(rightPanel,BorderLayout.EAST);
        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(bottomPanel,BorderLayout.SOUTH);
        frame.add(mainPanel,BorderLayout.CENTER);
    }

    void rightPanelBuild (){
         scorePanel = new JPanel();
//        scoreLabel.addVetoableChangeListener();   )..addComponentListener(e -> score!=Integer.getInteger(scoreLabel.getText())? scoreLabel.setText(String.valueOf(score)) :);
        rightPanel.setFocusable(false);
        resetButton= new JButton("Reset");
        pauseButton= new JButton("Pause");
        speedSlider = new JSlider(1,25,5);

        speedSlider.setPreferredSize(new Dimension(100,50));
        speedSlider.setPaintTicks(true);
        speedSlider.setMinorTickSpacing(2);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintLabels(true);

        pauseButton.setFocusable(false);
        resetButton.setFocusable(false);
        speedSlider.setFocusable(false);

        scorePanel.setBackground(Color.darkGray);
        scorePanel.setSize(100,70);


        pauseButton.addActionListener
        (e -> {
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
        rightPanel.add(scorePanel,BorderLayout.AFTER_LAST_LINE);


    }





}

