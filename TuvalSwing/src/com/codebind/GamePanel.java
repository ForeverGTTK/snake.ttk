package com.codebind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH= 600;
    static final int SCREEN_HIGHET= 600;

    static final int UNIT_SIZE= 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HIGHET)/(UNIT_SIZE*UNIT_SIZE);

    static final int BaseDelay= 175;

    static final int[] x = new int[SCREEN_WIDTH*SCREEN_HIGHET];
    static final int[] y = new int[SCREEN_WIDTH*SCREEN_HIGHET];

    int bodySize = 3;

    int appleEaten;
    int appleX;
    int appleY;

    int highestScore;

    char direction= 'R';
    boolean running = false;

    Timer timer;
    Random random;

    ImageIcon snakeHead;
    ImageIcon snakeBody;
    ImageIcon snakeBut;
    ImageIcon beer;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HIGHET));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        startGame();
    }

    public void startGame(){
        newApple();
        snakeHead= new ImageIcon("leonaidusFace.png");
        snakeBody= new ImageIcon("chain_PNG0.png");
        snakeBut = new ImageIcon("bottom-512.png");
        beer = new ImageIcon("beer.png");
        running = true;
        timer = new Timer(BaseDelay,this);
        timer.start();

    }
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw (Graphics g ){
        if (running){
            for (int i=0;i<SCREEN_HIGHET/UNIT_SIZE;i++){
                  g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HIGHET);
            }
            for (int i=0; i<SCREEN_WIDTH/UNIT_SIZE;i++){
                 g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
            }
                g.setColor(Color.red);
                g.drawOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

                for (int i=0; i<bodySize;i++){

                        g.setColor(Color.green);
                        g.drawRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
//            g.drawImage(beer.getImage(),appleX,appleY,UNIT_SIZE,UNIT_SIZE,this);

//            for (int i=0;i<bodySize;i++){
//                if(i==0){
//                    g.drawImage(snakeHead.getImage(),x[i],y[i],this);
//                }else if(i==bodySize-1){
//                    g.drawImage(snakeBut.getImage(),x[i],y[i],this);
//                }
//                else{ g.drawImage(snakeBody.getImage(),x[i],y[i],this);}
//            }
        }

    }

    public void newApple (){
        appleX= (random.nextInt(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY= (random.nextInt(SCREEN_HIGHET/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move() {
        for (int i=bodySize; i>0;i--){
        x[i]=x[i-1];
        y[i]=y[i-1];
        }
        switch (direction){
            case 'U':
                y[0]= y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]= y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]= x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]= x[0]+UNIT_SIZE;
                break;
        }
    }

    public void appleCheck (){
        if ( x[0] == appleX && y[0] == appleY){
            bodySize++;
            appleEaten++;
            newApple();
        }
    }

    public void checkCollition() {
        for (int i=bodySize;i>0;i--){
            if (x[0]==x[i]&&y[0]==y[i]){
                running=false;
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (running){
            move();
            appleCheck();
            checkCollition();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                     direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    running= !running;
                    break;
            }
        }
    }

}
