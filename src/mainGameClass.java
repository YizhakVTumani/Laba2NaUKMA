import acm.graphics.*;
import acm.program.*;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

//Author: Hella Nikita, Hrokh Arsenii
//File: mainGameClass.java


public class mainGameClass extends GraphicsProgram {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;

    private static final int PADDLE_SPEED = 8;
    private static final int BALL_DIAMETER = 20;

    private static final int BRICKS_PER_ROW = 10;
    private static final int BRICK_ROWS = 10;
    private static final int BRICK_GAP = 4;
    private static final int BRICK_WIDTH = (WINDOW_WIDTH - (BRICKS_PER_ROW - 1) * BRICK_GAP) / BRICKS_PER_ROW;
    private static final int BRICK_HEIGHT = 8;
    private static final int DISTANCE_TO_FIRST_BRICK = 100;
    private Object rect;


    public void run(){
        this.setSize(WINDOW_WIDTH + 17, WINDOW_HEIGHT + 60); // на вінді бордери такі, можеш міняти під мак, але запиши це в коментах: Windows(+17; +60)
        addKeyListeners();
        addMouseListeners();


        startScreen();

        while (!gameStarted) {
            pause(50);
        }

        while (!gameEnded) {
            ballMovement();
            bonusMovement();
            pause(20);
        }

        if (gameEnded && ball == null) {
            replayScreen();
        }
    }

    private void drawBricks() {
        double startX = getX();
        double startY = (heart1.getY() + heart1.getHeight()) + DISTANCE_TO_FIRST_BRICK;
        double x;
        double y;
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICKS_PER_ROW; j++) {
                x = startX + j * (BRICK_GAP + BRICK_WIDTH);
                y = startY  + i * (BRICK_GAP + BRICK_HEIGHT);
                brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
                brick.setFilled(true);
                if ((i + 1) <= 2) {
                    brick.setColor(Color.red);
                    brick.setFillColor(Color.red);
                } else if ((i + 1) > 2 && (i + 1) <= 4) {
                    brick.setColor(Color.ORANGE);
                    brick.setFillColor(Color.ORANGE);
                } else if ((i + 1) > 4 && (i + 1) <= 6) {
                    brick.setColor(Color.YELLOW);
                    brick.setFillColor(Color.YELLOW);
                } else if (((i + 1) > 6 && (i + 1) <= 8)) {
                    brick.setColor(Color.GREEN);
                    brick.setFillColor(Color.GREEN);
                } else if ((i + 1) > 8 && (i + 1) <= 10) {
                    brick.setColor(Color.CYAN);
                    brick.setFillColor(Color.CYAN);
                }
                add(brick);
            }
        }
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

        startScreenCanvas.setFillColor(new Color(20, 33, 50));
        buttonLevel1.setFillColor(new Color(57, 73, 171));
        buttonLevel2.setFillColor(new Color(57, 73, 171));
        buttonLevel3.setFillColor(new Color(57, 73, 171));
        buttonRules.setFillColor(new Color(161, 122, 42));

        startScreenCanvas.setFilled(true);
        buttonLevel1.setFilled(true);
        buttonLevel2.setFilled(true);
        buttonLevel3.setFilled(true);
        buttonRules.setFilled(true);

        buttonLevel1.setColor(new Color(91, 104, 117));
        buttonLevel2.setColor(new Color(91, 104, 117));
        buttonLevel3.setColor(new Color(91, 104, 117));
        buttonRules.setColor(new Color(91, 104, 117));

        buttonRulesText.setColor(new  Color(255, 255, 255));
        buttonLevel1Text.setColor(new  Color(255, 255, 255));
        buttonLevel2Text.setColor(new  Color(255, 255, 255));
        buttonLevel3Text.setColor(new  Color(255, 255, 255));





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

    private void replayScreen(){
        replayScreen = new GRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        replayScreen.setFilled(true);
        replayScreen.setFillColor(Color.white);
        add(replayScreen);

        mainMenuButton = new GRect(0,0,WINDOW_WIDTH/2,WINDOW_HEIGHT/2);
        replayButton = new GRect(WINDOW_WIDTH/2,WINDOW_HEIGHT/2,WINDOW_WIDTH/2,WINDOW_HEIGHT/2);



        add(mainMenuButton);
        add(replayButton);
    }

    public void mouseClicked(MouseEvent e){

        /*
        перевірка, чи натискає користувач на кнопки на мейн меню скріні
         */

        if(buttonRules!= null && buttonRules.contains(e.getX(), e.getY())){
            rulesScreen();
        }
        else if(buttonLevel1 != null && buttonLevel1.contains(e.getX(), e.getY())){
            level1();
        }

        if (replayButton != null && replayButton.contains(e.getX(), e.getY())){
            level1();
            remove(replayButton);
            remove(buttonLevel1);
            replayButton =null;
            mainMenuButton = null;
        }
        else if(mainMenuButton != null && mainMenuButton.contains(e.getX(), e.getY())){
            startScreen();
            remove(replayButton);
            remove(buttonLevel1);
            replayButton = null;
            mainMenuButton = null;
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

    private void level1() {

        remove(buttonLevel1);
        remove(buttonLevel2);
        remove(buttonLevel3);
        remove(buttonRules);

        level1Screen = new GRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        level1Screen.setFillColor(Color.white);
        level1Screen.setFilled(true);

        add(level1Screen);
        drawHearts();
        drawBricks();
        paddle();
        ball();
        gameEnded = false;
        gameStarted = true;

    }

    private void drawHearts() {
        int heartSize = 20;
        int gap = 5;
        double startX = WINDOW_WIDTH - (heartSize + gap) * 3;
        double y = 20;

        heart1 = new GOval(startX, y, heartSize, heartSize);
        heart1.setFilled(true);
        heart1.setColor(Color.RED);
        add(heart1);

        heart2 = new GOval(startX + heartSize + gap, y, heartSize, heartSize);
        heart2.setFilled(true);
        heart2.setColor(Color.RED);
        add(heart2);

        heart3 = new GOval(startX + (heartSize + gap) * 2, y, heartSize, heartSize);
        heart3.setFilled(true);
        heart3.setColor(Color.RED);
        add(heart3);

        livesAmount = 3;
    }

    private void handleLifeLoss() {
        livesAmount--;

        if (livesAmount == 2) {
            remove(heart3);
        } else if (livesAmount == 1) {
            remove(heart2);
        } else if (livesAmount <= 0) {
            remove(heart1);
            remove(ball);
            ball = null;
            gameEnded = true;
            return;
        }
        ball.setLocation(WINDOW_WIDTH / 2.0, WINDOW_HEIGHT / 2.0);
    }

    private GObject getCollidingObject() {
        double x = ball.getX();
        double y = ball.getY();

        GObject obj = getElementAt(x, y);
        if (obj != null) return obj;

        obj = getElementAt(x + BALL_DIAMETER, y);
        if (obj != null) return obj;

        obj = getElementAt(x, y + BALL_DIAMETER);
        if (obj != null) return obj;

        obj = getElementAt(x + BALL_DIAMETER, y + BALL_DIAMETER);
        if (obj != null) return obj;

        return null;
    }

    private GObject getCollidingObjectForBonusesSpeed() {
        if (speedUpBonus != null) {
            double x = speedUpBonus.getX();
            double y = speedUpBonus.getY();

            GObject obj = getElementAt(x, y);
            if (obj != null) return obj;

            obj = getElementAt(x + BALL_DIAMETER, y);
            if (obj != null) return obj;

            obj = getElementAt(x, y + BALL_DIAMETER);
            if (obj != null) return obj;

            obj = getElementAt(x + BALL_DIAMETER, y + BALL_DIAMETER);
            if (obj != null) return obj;
        }
        return null;
    }
    private GObject getCollidingObjectForBonusesSlow(){
        if (slowDownBonus != null) {
            double x = slowDownBonus.getX();
            double y = slowDownBonus.getY();

            GObject obj = getElementAt(x, y);
            if (obj != null) return obj;

            obj = getElementAt(x + BALL_DIAMETER, y);
            if (obj != null) return obj;

            obj = getElementAt(x, y + BALL_DIAMETER);
            if (obj != null) return obj;

            obj = getElementAt(x + BALL_DIAMETER, y + BALL_DIAMETER);
            if (obj != null) return obj;
        }
        return null;
    }
    private GObject getCollidingObjectForBonusesBig(){
        if (paddleBigBonus != null) {
            double x = paddleBigBonus.getX();
            double y = paddleBigBonus.getY();

            GObject obj = getElementAt(x, y);
            if (obj != null) return obj;

            obj = getElementAt(x + BALL_DIAMETER, y);
            if (obj != null) return obj;

            obj = getElementAt(x, y + BALL_DIAMETER);
            if (obj != null) return obj;

            obj = getElementAt(x + BALL_DIAMETER, y + BALL_DIAMETER);
            if (obj != null) return obj;
        }
        return null;
    }
    private GObject getCollidingObjectForBonusesSmall(){
        if (paddleSmallBonus != null) {
            double x = paddleSmallBonus.getX();
            double y = paddleSmallBonus.getY();

            GObject obj = getElementAt(x, y);
            if (obj != null) return obj;

            obj = getElementAt(x + BALL_DIAMETER, y);
            if (obj != null) return obj;

            obj = getElementAt(x, y + BALL_DIAMETER);
            if (obj != null) return obj;

            obj = getElementAt(x + BALL_DIAMETER, y + BALL_DIAMETER);
            if (obj != null) return obj;
        }
        return null;
    }
    private GObject getCollidingObjectForBonusesSpecial(){
        if (secretBonus != null) {
            double x = secretBonus.getX();
            double y = secretBonus.getY();

            GObject obj = getElementAt(x, y);
            if (obj != null) return obj;

            obj = getElementAt(x + BALL_DIAMETER, y);
            if (obj != null) return obj;

            obj = getElementAt(x, y + BALL_DIAMETER);
            if (obj != null) return obj;

            obj = getElementAt(x + BALL_DIAMETER, y + BALL_DIAMETER);
            if (obj != null) return obj;
        }
        return null;
    }

    private boolean isBrick(GRect rect) {
        if (rect == null) return false;
        if (rect == paddleBox || rect == level1Screen) return false;

        double y = rect.getY();
        double top = (heart1.getY() + heart1.getHeight()) + DISTANCE_TO_FIRST_BRICK;
        double bottom = top + BRICK_ROWS * (BRICK_GAP + BRICK_HEIGHT);

        return  y >= top && y <= bottom;
    }

    private boolean isBonus(GOval oval) {
        if (oval == null) return false;
        if (oval == ball) return false;

        double y = oval.getY();
        double top = oval.getY();
        double bottom = oval.getY() + oval.getHeight();

        return  y >= top && y <= bottom;
    }

    private boolean isPaddlo(GRect rect) {
        if (rect == null) return false;
        if (rect == level1Screen || rect.getY() <= brick.getY() + brick.getHeight()) return false;

        double y = rect.getY();
        double top = rect.getY();
        double bottom = rect.getY() + rect.getHeight();

        return  y >= top && y <= bottom;
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
        ball.move(horizontalBallSpeed, verticalBallSpeed);

        if  (ball.getX() <= 0) {
            ball.setLocation(0, ball.getY());
            horizontalBallSpeed = Math.abs(horizontalBallSpeed);
        } else if (ball.getX() + BALL_DIAMETER >= WINDOW_WIDTH) {
            ball.setLocation(WINDOW_WIDTH - BALL_DIAMETER, ball.getY());
            horizontalBallSpeed = -Math.abs(horizontalBallSpeed);
        }

        if (ball.getY() <= 0) {
            ball.setLocation(ball.getX(), 0);
            verticalBallSpeed = Math.abs(verticalBallSpeed);
        } else if (ball.getY() + BALL_DIAMETER >= WINDOW_HEIGHT) {
            handleLifeLoss();
            return;
        }

        GObject collidedObj = getCollidingObject();

        if (collidedObj != null && collidedObj instanceof GRect && isBrick((GRect) collidedObj)) {
            remove(collidedObj);
            bonusDistribute();
            verticalBallSpeed = Math.abs(verticalBallSpeed);
        } else if (collidedObj != null && collidedObj == paddleBox) {
            verticalBallSpeed = -Math.abs(verticalBallSpeed);
        }
    }

    private void paddleMovementRight(){
        if(paddleBox != null && paddleBox.getX() + paddleBox.getWidth() < WINDOW_WIDTH){
            paddleBox.move(PADDLE_SPEED, 0);
        }
    }

    private void paddleMovementLeft(){
        if(paddleBox != null && paddleBox.getX() > 0){
            paddleBox.move(-PADDLE_SPEED, 0);
        }
    }

    private void bonusDistribute(){
        bonusRandomizer = rnd.nextInt(0, 6);
        if (bonusRandomizer == 1){
            speedUpBonus();
        }
        if (bonusRandomizer == 2){
            slowDownBonus();
        }
        if (bonusRandomizer == 3){
            bigPaddleBonus();
        }
        if (bonusRandomizer == 4){
            littlePaddleBonus();
        }
        if (bonusRandomizer == 5){
            secretBonus();
        }
    }


    private void speedUpBonus(){
        speedUpBonus = new GImage("speedUpBubble.png");
        speedUpBonus.setSize(BALL_DIAMETER, BALL_DIAMETER);
        add(speedUpBonus, ball.getX(), ball.getY());
    }
    private void slowDownBonus(){
        slowDownBonus = new GImage("slowDownBubble.png");
        slowDownBonus.setSize(BALL_DIAMETER, BALL_DIAMETER);
        add(slowDownBonus, ball.getX(), ball.getY());
    }
    private void bigPaddleBonus(){
        paddleBigBonus = new GImage("bigBubble.png");
        paddleBigBonus.setSize(BALL_DIAMETER, BALL_DIAMETER);
        add(paddleBigBonus, ball.getX(), ball.getY());
    }
    private void littlePaddleBonus(){
        paddleSmallBonus = new GImage("littleBubble.png");
        paddleSmallBonus.setSize(BALL_DIAMETER, BALL_DIAMETER);
        add(paddleSmallBonus, ball.getX(), ball.getY());
    }
    private void secretBonus(){
        secretBonus = new GImage("heart.png");
        secretBonus.setSize(BALL_DIAMETER, BALL_DIAMETER);
        add(secretBonus, ball.getX(), ball.getY());
    }



    private void bonusMovement(){
        GObject collidedObjForBonusesSpeed = getCollidingObjectForBonusesSpeed();
        GObject collidedObjForBonusesSlow = getCollidingObjectForBonusesSlow();
        GObject collidedObjForBonusesBig = getCollidingObjectForBonusesBig();
        GObject collidedObjForBonusesSmall = getCollidingObjectForBonusesSmall();
        GObject collidedObjForBonusesSpecial = getCollidingObjectForBonusesSpecial();

        if (speedUpBonus != null && getCollidingObjectForBonusesSpeed() != null && getCollidingObjectForBonusesSpeed() == level1Screen) {
            speedUpBonus.move(0, bonusVSpeed);
        }
        if (slowDownBonus != null && getCollidingObjectForBonusesSlow() != null && getCollidingObjectForBonusesSlow() == level1Screen) {
            slowDownBonus.move(0, bonusVSpeed);
        }
        if (paddleBigBonus != null && getCollidingObjectForBonusesBig() != null && getCollidingObjectForBonusesBig() == level1Screen) {
            paddleBigBonus.move(0, bonusVSpeed);
        }
        if (paddleSmallBonus != null && getCollidingObjectForBonusesSmall() != null && getCollidingObjectForBonusesSmall() == level1Screen) {
            paddleSmallBonus.move(0, bonusVSpeed);
        }
        if (secretBonus != null && getCollidingObjectForBonusesSpecial() != null && getCollidingObjectForBonusesSpecial() == level1Screen) {
            secretBonus.move(0, bonusVSpeed);
        }


        if (collidedObjForBonusesSpeed != null && collidedObjForBonusesSpeed == paddleBox && bonusRandomizer == 1) {
            remove(speedUpBonus);
            speedUpBonus = null;
            verticalBallSpeed *= 1.3;
        }
        else if (collidedObjForBonusesSlow != null && collidedObjForBonusesSlow == paddleBox && bonusRandomizer == 2) {
            remove(slowDownBonus);
            slowDownBonus = null;
            verticalBallSpeed /= 1.3;
        }
        else if (collidedObjForBonusesSmall != null && collidedObjForBonusesSmall == paddleBox && bonusRandomizer == 3) {
            paddleBox.setSize(paddleBox.getWidth()/2, paddleBox.getHeight());

        }
        else if (collidedObjForBonusesBig != null && collidedObjForBonusesBig == paddleBox && bonusRandomizer == 4) {
            paddleBox.setSize(paddleBox.getWidth()*2, paddleBox.getHeight());
        }
        else if (collidedObjForBonusesSpecial != null && collidedObjForBonusesSpecial == paddleBox && bonusRandomizer == 5) {
            if (livesAmount == 1){
                livesAmount++;
                add(heart2);
            }
            else if (livesAmount == 2){
                livesAmount++;
                add(heart3);
            }
            else if (livesAmount == 0) {
                livesAmount++;
                add(heart1);
            }
        }
    }


    RandomGenerator rnd = new RandomGenerator();

    private int bonusRandomizer = 0;

    private int bonusVSpeed = 3;

    private GImage speedUpBonus;
    private GImage slowDownBonus;
    private GImage paddleBigBonus;
    private GImage paddleSmallBonus;
    private GImage secretBonus;

    private GOval ball;
    private GRect paddleBox;
    private GRect brick;

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

    private GRect replayScreen;
    private GRect replayButton;
    private GRect mainMenuButton;


    private GRect level1Screen;
    private GRect level2Screen;
    private GRect level3Screen;

    private boolean gameEnded;
    private boolean gameStarted;
    private double verticalBallSpeed = 5;
    private int horizontalBallSpeed = 1;

    int intlevel1;

    GOval heart1;
    GOval heart2;
    GOval heart3;
    int livesAmount = 3;


    public static void main(String[] args) {
        new mainGameClass().start(args);
    }

}