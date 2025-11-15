import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.*;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//Author: Hella Nikita, Hrokh Arsen
//File: mainGameClass.java


public class mainGameClass extends GraphicsProgram {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;

    private static final int PADDLE_SPEED = 2;
    private static final int BALL_DIAMETER = 20;


    public void run(){
        this.setSize(WINDOW_WIDTH + 17, WINDOW_HEIGHT + 60); // на вінді бордери такі, можеш міняти під мак, але запиши це в коментах: Windows(+17; +60)
        addKeyListeners();
        addMouseListeners();



        startScreen();


    }

    public void startScreen(){
        startScreenCanvas = new GRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        buttonLevel1 = new GRect(WINDOW_WIDTH/10,WINDOW_HEIGHT/3, 2*WINDOW_WIDTH/10,WINDOW_HEIGHT/4);
        buttonLevel2 = new GRect(4*WINDOW_WIDTH/10,WINDOW_HEIGHT/3, 2*WINDOW_WIDTH/10,WINDOW_HEIGHT/4);
        buttonLevel3 = new GRect(7*WINDOW_WIDTH/10,WINDOW_HEIGHT/3, 2*WINDOW_WIDTH/10,WINDOW_HEIGHT/4);

        buttonLevel1Text = new GLabel("Level 1");
        buttonLevel2Text = new GLabel("Level 2");
        buttonLevel3Text = new GLabel("Level 3");



        buttonRules = new GRect(2*WINDOW_WIDTH/10,3.5*WINDOW_HEIGHT/5, 6*WINDOW_WIDTH/10,WINDOW_HEIGHT/8);
        buttonRulesText = new GLabel("Rules");



        add(startScreenCanvas);
        add(buttonLevel1);
        add(buttonLevel2);
        add(buttonLevel3);
        add(buttonRules);

        /*
        В аддерах нижче - ідеальне відцентрування тексту по бАтону
         */
        add(buttonLevel1Text, buttonLevel1.getX() + buttonLevel1.getWidth()/2 - buttonLevel1Text.getWidth()/2,  buttonLevel1.getY() +  buttonLevel1.getHeight()/2);
        add(buttonLevel2Text, buttonLevel2.getX() + buttonLevel2.getWidth()/2 - buttonLevel2Text.getWidth()/2,  buttonLevel2.getY() +  buttonLevel2.getHeight()/2);
        add(buttonLevel3Text, buttonLevel3.getX() + buttonLevel3.getWidth()/2 - buttonLevel3Text.getWidth()/2,  buttonLevel3.getY() +  buttonLevel3.getHeight()/2);
        add(buttonRulesText, buttonRules.getX() + buttonRules.getWidth()/2 - buttonRulesText.getWidth()/2, buttonRules.getY() +  buttonRules.getHeight()/2);


    }

    private void rulesScreen(){
        rulesScreen = new GRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        rulesText = new GLabel("Should write some rules");

        rulesScreen.setFilled(true);
        /*
        Треба написати правила та відформатувати їх
         */

        add(rulesScreen);
        add(rulesText);
    }

    public void mouseClicked(MouseEvent e){

        /*
        перевірка, чи натискає користувач на кнопки на мейн меню скріні
         */

        if(buttonRules.contains(e.getX(), e.getY())){
            rulesScreen();
        }
        else if(buttonLevel1.contains(e.getX(), e.getY())){
            level1();
        }
//        if(buttonLevel2.contains(e.getX(), e.getY())){
//            level2();
//        }
//        if(buttonLevel3.contains(e.getX(), e.getY())){
//            level3();
//        }
    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            paddleMovementLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            paddleMovementRight();
        }
    }

    private void level1(){
        remove(buttonLevel1);
        remove(buttonLevel2);
        remove(buttonLevel3);
        remove(buttonRules);


        level1Screen = new GRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        level1Screen.setFillColor(Color.white);
        level1Screen.setFilled(true);


        GRect rect = new GRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT/10);
        rect.setFilled(true);


        add(level1Screen);
        add(rect);
        paddle();
        ball();

        if (gameEnded){
            startScreen();
        }

    }

    private void paddle(){
        paddleBox = new GRect((double) (WINDOW_WIDTH - WINDOW_WIDTH / 10) /2,WINDOW_HEIGHT - (double) WINDOW_HEIGHT /20, (double) WINDOW_WIDTH /10, (double) WINDOW_HEIGHT /20);
        paddleBox.setFilled(true);
        add(paddleBox);
    }

    private void ball(){
        ball = new GOval(WINDOW_WIDTH/2, WINDOW_HEIGHT/2, BALL_DIAMETER, BALL_DIAMETER );
        ball.setFilled(true);
        add(ball);
    }
    private void ballMovement(){

    }


    private void paddleMovementRight(){
        if(paddleBox != null){
            paddleBox.move(PADDLE_SPEED, 0);
        }
    }
    private void paddleMovementLeft(){
        if(paddleBox != null){
            paddleBox.move(-PADDLE_SPEED, 0);
        }
    }

    RandomGenerator rnd = new RandomGenerator();

    private GRect settingsScreen;

    private GOval ball;
    private GRect paddleBox;

    private GRect startScreenCanvas;

    private GRect buttonLevel1;
    private GLabel buttonLevel1Text;
    private GRect buttonLevel2;
    private GLabel buttonLevel2Text;
    private GRect buttonLevel3;
    private GLabel buttonLevel3Text;

    private GRect buttonRules;
    private GLabel buttonRulesText;
    private GRect rulesScreen;
    private GLabel rulesText;

    private GRect level1Screen;
    private GRect level2Screen;
    private GRect level3Screen;

    private boolean gameEnded;

}