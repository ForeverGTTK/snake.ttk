package com.codebind;

import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.lang.reflect.Array;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH= 600;
    static final int SCREEN_HIGHET= 600;

    static final int UNIT_SIZE= 20;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HIGHET)/(UNIT_SIZE*UNIT_SIZE);

    static final int BaseDelay= 1000;
    public static int GameDelay=BaseDelay;

    static final int[] x = new int[SCREEN_WIDTH*SCREEN_HIGHET];
    static final int[] y = new int[SCREEN_WIDTH*SCREEN_HIGHET];

    public static final int defaultBodySize= 50;
    int bodySize = defaultBodySize;

    public static int appleEaten;
    int appleX;
    int appleY;

    int highestScore=0;

    char direction= 'R';
   public static boolean running = false;

    Timer timer;
    Random random;

    Image wormHead;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HIGHET));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        BaseFrame.resetButton.addActionListener(e -> resetGame());
        BaseFrame.speedSlider.addChangeListener(e -> GamePanel.GameDelayupdate());     //todo
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(GameDelay,this);
        timer.start();

    }

    public void deathMessage (){
        JOptionPane.showMessageDialog(null,"You Lost !\n\t Would you like to Play Again?","Snake.TTK",JOptionPane.OK_OPTION);
       resetGame();
    }

    void resetGame (){
            timer.stop();
            for (int i=0;i<=bodySize;i++){
                x[i]=0;
                y[i]=0;
            }
            BaseFrame.Hscore.scoreCheck();

            appleEaten=0;
            direction='R';
            bodySize=defaultBodySize;
            startGame();
    }
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw (Graphics g ){
        if (running){
            g.setColor(Color.black);

            for (int i=0;i<SCREEN_HIGHET/UNIT_SIZE;i++){// draw horizontal lines
                  g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HIGHET);
            }
            for (int i=0; i<SCREEN_WIDTH/UNIT_SIZE;i++){ //draw vertical lines
                 g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
            }
                g.setColor(Color.red);                  // draw apple
                g.drawOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
                g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
            for (int i=0; i<bodySize;i++){  //draw snake body
                if (i==0){
                    g.drawImage(wormHead,x[0],y[0],UNIT_SIZE,UNIT_SIZE, this);
                }
                g.setColor(Color.blue);
                g.fillRoundRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE,i,i);
            }
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(String.valueOf(BaseFrame.GameScore),SCREEN_WIDTH/2,SCREEN_HIGHET);
        }else {
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:  "+ BaseFrame.GameScore,(SCREEN_WIDTH-metrics.stringWidth("Score:  "+ BaseFrame.GameScore))/2,g.getFont().getSize());
            g.drawString("Apples Eaten:  "+ appleEaten,(SCREEN_WIDTH-metrics.stringWidth("apples eaten:  " + appleEaten))/2,g.getFont().getSize()*2);

            g.setColor(Color.WHITE);
            g.drawString("Highe Score list",(SCREEN_WIDTH-metrics.stringWidth("Highe Score list: "))/2,g.getFont().getSize()*3);
            for (int i = 1; i<= BaseFrame.Hscore.getListLength(); i++){
                g.drawString(i+".\t  "+ BaseFrame.Hscore.getName(i-1) +BaseFrame.Hscore.getScore(i-1),(SCREEN_WIDTH-metrics.stringWidth("Score:  "+ BaseFrame.GameScore))/2,g.getFont().getSize()*(3+i));
            }
        }

    }

    public void newApple () {
        appleX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = (random.nextInt(SCREEN_HIGHET / UNIT_SIZE)) * UNIT_SIZE;
        for (int i=0;i<bodySize;i++){
        if (appleX == x[i])
            appleX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        if (appleY == y[i]) {
            appleY = (random.nextInt(SCREEN_HIGHET / UNIT_SIZE)) * UNIT_SIZE;
        }
        }
        return;
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
            bodySize+=2;
            appleEaten++;
            BaseFrame.GameScore = (bodySize+appleEaten)*BaseFrame.speedSlider.getValue();
            newApple();
        }
    }

    public void checkCollition() {
        for (int i=bodySize;i>0;i--){
            if (x[0]==x[i]&&y[0]==y[i]){
                running=false;
            }
        }
        if (!running){
            timer.stop();
            deathMessage();
        }
        if (x[0]<this.getMinimumSize().width-UNIT_SIZE&&direction=='L'){
            x[0]=SCREEN_WIDTH;}
        if (x[0]>(SCREEN_WIDTH)&&direction=='R'){
            x[0]=0;}
        if (y[0]<this.getMinimumSize().height-UNIT_SIZE&&direction=='U'){
            y[0]=SCREEN_HIGHET;}
        if (y[0]>(SCREEN_HIGHET)&&direction=='D'){
            y[0]=0;}
    }

    public static void GameDelayupdate(){
        GameDelay = BaseDelay/BaseFrame.speedSlider.getValue();
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (running){
            move();
            appleCheck();
            checkCollition();
            int tempDelay=BaseDelay;
            GameDelayupdate();
            if (GameDelay!=tempDelay){
            timer.setDelay(GameDelay);
            timer.start();
            }

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
