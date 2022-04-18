package com.codebind;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BaseFrame extends JFrame implements KeyListener {

    JFrame frame;
    int score;
    JPanel rightPanel;
    public   JPanel mainPanel;
    JLabel snake;

    BaseFrame () {
        ImageIcon image = new ImageIcon("G_Logo.png");
        this.setIconImage(image.getImage());

        baseBuild();
        rightPanelBuild();
        frame.setVisible(true);
        int LastScore = Game();



    }

    int Game (){
        score=0;
        snakeMaker();
        return score;
    }

    void deathMessage (){
        JOptionPane.showMessageDialog(null,"You Lost !\n\t Would you like to Play Again?","Snake.TTK",JOptionPane.OK_OPTION);

    }

    void baseBuild () {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10,0));
        frame.setSize(500,500);

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
        leftPanel.setPreferredSize(new Dimension(100,100));
        rightPanel.setPreferredSize(new Dimension(100,100));
        topPanel.setPreferredSize(new Dimension(100,50));
        bottomPanel.setPreferredSize(new Dimension(100,50));
        mainPanel.setPreferredSize(new Dimension(300,300));
        frame.add(leftPanel,BorderLayout.WEST);
        frame.add(rightPanel,BorderLayout.EAST);
        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(bottomPanel,BorderLayout.SOUTH);
        frame.add(mainPanel,BorderLayout.CENTER);
    }

    void rightPanelBuild (){
        rightPanel.setFocusable(false);
        JButton resetButton= new JButton("Reset");
        JButton pauseButton= new JButton("Pause");
        JSlider speedSlider = new JSlider(0,10,5);
        speedSlider.setPreferredSize(new Dimension(100,50));
        speedSlider.setPaintTicks(true);
        speedSlider.setMinorTickSpacing(2);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintLabels(true);
        pauseButton.setFocusable(false);
        resetButton.setFocusable(false);
        speedSlider.setFocusable(false);
        resetButton.addActionListener(e -> Game());
        pauseButton.addActionListener (e -> {
            if (pauseButton.getText() == "Continue") {
                pauseButton.setText("Pause");
                mainPanel.addKeyListener(this);
            } else {
                pauseButton.setText("Continue");
                mainPanel.removeKeyListener(this);
            }
                                        });
        speedSlider.addChangeListener(e -> System.out.println(speedSlider.getValue()));
        rightPanel.add(new JLabel("Your Score"));
        rightPanel.add(resetButton);
        rightPanel.add(pauseButton);
        rightPanel.add(new JLabel("Game speed"));
        rightPanel.add(speedSlider);

    }

   JLabel snakeMaker(){
        try{
            System.out.println("Restarted");}
        catch (NullPointerException ex){
            System.out.println("first attempt");
        }
        mainPanel.setLayout(null);
        mainPanel.grabFocus();
        snake = new JLabel();
        snake.setBounds(150,50,30,30);
        snake.setBackground(Color.BLACK);
        snake.setOpaque(true);
       mainPanel.addKeyListener(this );
       mainPanel.add(snake);
       return snake;
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case 37: snake.setLocation(snake.getX()-5,snake.getY());
                break;
            case 38: snake.setLocation(snake.getX(),snake.getY()-5);
                break;
            case 39: snake.setLocation(snake.getX()+5,snake.getY());
                break;
            case 40: snake.setLocation(snake.getX(),snake.getY()+5);
                break;
        }

    }


    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}

