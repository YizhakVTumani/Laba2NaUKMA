import acm.graphics.*;
import acm.program.*;
import acm.util.RandomGenerator;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

//Author: Hella Nikita, Hrokh Arsenii
//File: mainGameClass.java


public class mainGameClass extends GraphicsProgram {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;

    private static final int PADDLE_SPEED = 20;
    private static final int BALL_DIAMETER = 20;

    private static final int BRICKS_PER_ROW = 10;
    private static final int BRICK_ROWS = 10;
    private static final int BRICK_GAP = 4;
    private static final int BRICK_WIDTH = (WINDOW_WIDTH - (BRICKS_PER_ROW - 1) * BRICK_GAP) / BRICKS_PER_ROW;
    private static final int BRICK_HEIGHT = 20;
    private static final int DISTANCE_TO_FIRST_BRICK = 50;
    private static final int MAX_SPEED = 10;


    public void run(){
        this.setSize(WINDOW_WIDTH + 17, WINDOW_HEIGHT + 60); // на вінді бордери такі, можеш міняти під мак, але запиши це в коментах: Windows(+17; +60)
        addKeyListeners();
        addMouseListeners();
        playBgMusic();

        startScreen();

        while (true){
            while (!gameStarted) {
                pause(50);
            }

            while (!gameEnded) {
                ballMovement();
                bonusMovement();
                pause(20);
            }

            while (gameEnded) {
                replayScreen();
                pause(50);
            }
        }
    }

    private void playBgMusic() {
        try {
            File musicPath = new File("Where I am From - Topher Mohr and Alex Elena.wav");
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(audioInput);
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundMusic.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            println(e.getMessage());
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
        rulesScreen.setFilled(true);
        rulesScreen.setFillColor(new Color(20, 33, 50));
        add(rulesScreen);

        rulesText = new GLabel("Breakout Game Rules");

        GLabel label1 = new GLabel("I. Objective");
        GLabel label2 = new GLabel("The primary goal of the game is to destroy all the colored bricks that form a wall at the top of the screen by hitting them with a ball.");
        GLabel label3 = new GLabel("II. Gameplay");
        GLabel label4 = new GLabel("The Paddle: Players control a horizontal paddle located at the bottom of the screen. The paddle can only move horizontally (left and right).");
        GLabel label5 = new GLabel("The Ball: A single ball is in play. The player launches the ball from the paddle toward the wall of bricks.");
        GLabel label6 = new GLabel("Destroying Bricks: Each time the ball hits a brick, that brick is destroyed, and the player scores points.");
        GLabel label7 = new GLabel("Rebound: The ball automatically reflects off the walls, the paddle, and the bricks. The angle of the ball's rebound from the paddle can ");
        GLabel label71 = new GLabel("be controlled by hitting the ball with different sections of the paddle.");
        GLabel label8 = new GLabel("Losing a Ball (Turn): If the player fails to hit the ball with the paddle and it travels past the bottom of the screen, one ball (life) is lost. ");
        GLabel label9 = new GLabel("End of Game: The game ends when the player has destroyed all the bricks on the screen (or cleared a set number of screens/walls) or when ");
        GLabel label91 = new GLabel("all of the player's balls have been used. ");
        GLabel label10 = new GLabel("III. Winning ");
        GLabel label11 = new GLabel("A player or team wins the game by being the first to completely destroy all the bricks on the two designated walls (screens). ");
        GLabel label12 = new GLabel("If all balls are lost before the walls are cleared, the winner is the player who has achieved the highest score.");

        rulesText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        label1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        label2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label3.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        label4.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label5.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label6.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label7.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label71.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label8.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label9.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label91.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label10.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        label11.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        label12.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));

        rulesText.setColor(new  Color(255, 255, 255));
        label1.setColor(new  Color(255, 255, 255));
        label2.setColor(new  Color(255, 255, 255));
        label3.setColor(new  Color(255, 255, 255));
        label4.setColor(new  Color(255, 255, 255));
        label5.setColor(new  Color(255, 255, 255));
        label6.setColor(new  Color(255, 255, 255));
        label7.setColor(new  Color(255, 255, 255));
        label71.setColor(new  Color(255, 255, 255));
        label8.setColor(new  Color(255, 255, 255));
        label9.setColor(new  Color(255, 255, 255));
        label91.setColor(new  Color(255, 255, 255));
        label10.setColor(new  Color(255, 255, 255));
        label11.setColor(new  Color(255, 255, 255));
        label12.setColor(new  Color(255, 255, 255));
        add(rulesText, 80, rulesText.getHeight());
        add(label1, 20, label1.getHeight()*3);
        add(label2, 20, label1.getHeight()*5);
        add(label3, 20, label1.getHeight()*7);
        add(label4, 20, label1.getHeight()*9);
        add(label5, 20, label1.getHeight()*11);
        add(label6, 20, label1.getHeight()*13);
        add(label7, 20, label1.getHeight()*15);
        add(label71, 20, label1.getHeight()*17);
        add(label8, 20, label1.getHeight()*19);
        add(label9, 20, label1.getHeight()*21);
        add(label91, 20, label1.getHeight()*23);
        add(label10, 20, label1.getHeight()*25);
        add(label11, 20, label1.getHeight()*27);
        add(label12, 20, label1.getHeight()*29);

    }

    private void replayScreen(){
        if (replayScreen == null){
            replayScreen = new GRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
            replayScreen.setFilled(true);
            replayScreen.setFillColor(new Color(20, 33, 50));
            add(replayScreen);

            mainMenuButton = new GRect(WINDOW_WIDTH/10,WINDOW_HEIGHT/3, 2*WINDOW_WIDTH/10,WINDOW_HEIGHT/4);
            replayButton = new GRect(7*WINDOW_WIDTH/10,WINDOW_HEIGHT/3, 2*WINDOW_WIDTH/10,WINDOW_HEIGHT/4);

            mainMenuButton.setFilled(true);
            replayButton.setFilled(true);
            replayButton.setFillColor(new Color(57, 73, 171));
            mainMenuButton.setFillColor(new Color(57, 73, 171));

            if (livesAmount > 0 && gameEnded && bricksAmount == 0 && ball != null) {
                statusLabel = new GLabel("VICTORY!");
                statusLabel.setColor(Color.GREEN);
            } else {
                statusLabel = new GLabel("GAME OVER");
                statusLabel.setColor(Color.RED);
            }

            statusLabel.setFont("SansSerif-Bold-40");
            double x = (WINDOW_WIDTH - statusLabel.getWidth()) / 2;
            double y = (double) WINDOW_HEIGHT / 4;
            add(statusLabel, x, y);

            replayLabel = new GLabel("Replay");
            mainMenuLabel = new GLabel("Main menu");
            replayLabel.setColor(new  Color(255, 255, 255));
            mainMenuLabel.setColor(new  Color(255, 255, 255));


            add(mainMenuButton);
            add(replayButton);

            add(replayLabel, replayButton.getX() + replayButton.getWidth()/2 - replayLabel.getWidth()/2,  replayButton.getY() +  replayButton.getHeight()/2);
            add(mainMenuLabel, mainMenuButton.getX() + mainMenuButton.getWidth()/2 - mainMenuLabel.getWidth()/2,  mainMenuButton.getY() +  mainMenuButton.getHeight()/2);
        }
    }

    private void removeReplayScreen(){
        if (replayScreen != null){
            remove(replayButton);
            remove(replayLabel);
            remove(mainMenuButton);
            remove(mainMenuLabel);
            remove(replayScreen);
            replayButton = null;
            replayLabel = null;
            mainMenuButton = null;
            replayScreen = null;
            mainMenuLabel = null;
        }

    }

    public void mouseClicked(MouseEvent e){

        /*
        перевірка, чи натискає користувач на кнопки на мейн меню скріні
         */

        if(buttonLevel1 != null && buttonLevel1.isVisible() && buttonLevel1.contains(e.getX(), e.getY())) {
            level1();
        } else if(buttonRules != null && buttonRules.isVisible() && buttonRules.contains(e.getX(), e.getY())) {
            if (rulesScreen != null) startScreen();
            else rulesScreen();
        }
        if (replayButton != null && replayButton.contains(e.getX(), e.getY())){
            removeReplayScreen();
            level1();
        } else if(mainMenuButton != null && mainMenuButton.contains(e.getX(), e.getY())){
            removeReplayScreen();
            startScreen();
            gameStarted = false;
            gameEnded = false;
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
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            startScreen();
            removeReplayScreen();
            gameStarted = false;
        }
    }

    private void level1() {

        remove(buttonLevel1);
        remove(buttonLevel2);
        remove(buttonLevel3);
        remove(buttonRules);

        level1Screen = new GRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        level1Screen.setFillColor(new Color(20, 33, 50));
        level1Screen.setFilled(true);

        livesAmount = 3;
        bricksAmount = BRICK_ROWS * BRICKS_PER_ROW;
        verticalBallSpeed = 5;
        horizontalBallSpeed = 0;
        score = 0;
        scoreLabel = new GLabel("Score: 0");
        scoreLabel.setFont("SansSerif-Bold-20");
        scoreLabel.setColor(Color.WHITE);
        speedUpBonus = null;
        slowDownBonus = null;
        paddleBigBonus = null;
        paddleSmallBonus = null;
        secretBonus = null;
        add(level1Screen);
        drawHearts();
        drawBricks();
        paddle();
        ball();

        add(scoreLabel, 20, 40);
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
        if(ball != null) {
            ball.setLocation(WINDOW_WIDTH / 2.0 - BALL_DIAMETER/2.0, WINDOW_HEIGHT / 2.0);
            paddleBox.setLocation(WINDOW_WIDTH/2.0 - paddleBox.getWidth()/2.0, WINDOW_HEIGHT - paddleBox.getHeight());
            verticalBallSpeed = Math.abs(verticalBallSpeed);
            horizontalBallSpeed = 0;
            pause(1000);
        }
    }

    private GObject getCollidingObject() {
        double x = ball.getX();
        double y = ball.getY();

        GObject obj = getElementAt(x, y);
        if (obj == null) obj = getElementAt(x + BALL_DIAMETER, y);
        if (obj == null) obj = getElementAt(x, y + BALL_DIAMETER);
        if (obj == null) obj = getElementAt(x + BALL_DIAMETER, y + BALL_DIAMETER);

        if (obj != null) {
            if (obj == level1Screen || obj == level2Screen || obj == level3Screen) return null;
            if (obj == speedUpBonus ||
                    obj == slowDownBonus ||
                    obj == paddleBigBonus ||
                    obj == paddleSmallBonus ||
                    obj == secretBonus)
            {
                return null;
            }
            if (obj instanceof GLabel) return null;
        }
        return obj;
    }

    private boolean isBrick(GRect rect) {
        if (rect == null) return false;
        if (rect == paddleBox || rect == level1Screen) return false;

        double y = rect.getY();
        double top = (heart1.getY() + heart1.getHeight()) + DISTANCE_TO_FIRST_BRICK;
        double bottom = top + BRICK_ROWS * (BRICK_GAP + BRICK_HEIGHT);

        return  y >= top && y <= bottom;
    }

    private void paddle(){
        paddleBox = new GRect((double) (WINDOW_WIDTH - WINDOW_WIDTH / 10) /2,WINDOW_HEIGHT - (double) WINDOW_HEIGHT /20, (double) WINDOW_WIDTH /10, (double) WINDOW_HEIGHT /20);
        paddleBox.setFilled(true);
        paddleBox.setFillColor(Color.black);
        paddleBox.setColor(Color.white);
        add(paddleBox);
    }

    private void ball(){
        ball = new GOval(WINDOW_WIDTH/2, WINDOW_HEIGHT/2, BALL_DIAMETER, BALL_DIAMETER );
        ball.setFilled(true);
        ball.setFillColor(Color.black);
        ball.setColor(Color.white);
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
            bricksAmount--;
            score++;
            scoreLabel.setLabel("Score: " + score);
            bonusDistribute();
            verticalBallSpeed = -verticalBallSpeed;
            if (bricksAmount == 0) {
                gameEnded = true;
                return;
            }
        } else if (collidedObj != null && collidedObj == paddleBox) {
            ball.setLocation(ball.getX(), paddleBox.getY() - BALL_DIAMETER - 2);
            verticalBallSpeed = -Math.abs(verticalBallSpeed);
            horizontalBallSpeed = rnd.nextInt(-4, 4);
            if (horizontalBallSpeed == 0) {
                if (rnd.nextBoolean()) horizontalBallSpeed = 3;
                else horizontalBallSpeed = -3;
            }
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
        bonusRandomizer = rnd.nextInt(0, 6); // increased range so bonuses aren't too frequent

        if (bonusRandomizer == 1 && speedUpBonus == null) speedUpBonus();
        else if (bonusRandomizer == 2 && slowDownBonus == null) slowDownBonus();
        else if (bonusRandomizer == 3 && paddleBigBonus == null) bigPaddleBonus();
        else if (bonusRandomizer == 4 && paddleSmallBonus == null) littlePaddleBonus();
        else if (bonusRandomizer == 5 && secretBonus == null) secretBonus();
    }


    private void speedUpBonus(){
        speedUpBonus = new GImage("speedUpBubble.png");
        speedUpBonus.setSize(BALL_DIAMETER * 1.5, BALL_DIAMETER * 1.5);
        add(speedUpBonus, ball.getX(), ball.getY());
    }
    private void slowDownBonus(){
        slowDownBonus = new GImage("slowDownBubble.png");
        slowDownBonus.setSize(BALL_DIAMETER * 1.5, BALL_DIAMETER * 1.5);
        add(slowDownBonus, ball.getX(), ball.getY());
    }
    private void bigPaddleBonus(){
        paddleBigBonus = new GImage("bigBubble.png");
        paddleBigBonus.setSize(BALL_DIAMETER * 1.5, BALL_DIAMETER * 1.5);
        add(paddleBigBonus, ball.getX(), ball.getY());
    }
    private void littlePaddleBonus(){
        paddleSmallBonus = new GImage("littleBubble.png");
        paddleSmallBonus.setSize(BALL_DIAMETER * 1.5, BALL_DIAMETER * 1.5);
        add(paddleSmallBonus, ball.getX(), ball.getY());
    }
    private void secretBonus(){
        secretBonus = new GImage("heart.png");
        secretBonus.setSize(BALL_DIAMETER * 1.5, BALL_DIAMETER * 1.5);
        add(secretBonus, ball.getX(), ball.getY());
    }



    private void bonusMovement() {
        if (speedUpBonus != null) speedUpBonus.move(0, bonusVSpeed);
        if (slowDownBonus != null) slowDownBonus.move(0, bonusVSpeed);
        if (paddleBigBonus != null) paddleBigBonus.move(0, bonusVSpeed);
        if (paddleSmallBonus != null) paddleSmallBonus.move(0, bonusVSpeed);
        if (secretBonus != null) secretBonus.move(0, bonusVSpeed);

        if (speedUpBonus != null && speedUpBonus.getBounds().intersects(paddleBox.getBounds())) {
            remove(speedUpBonus);
            speedUpBonus = null;
            verticalBallSpeed *= 1.3;
            if (verticalBallSpeed > MAX_SPEED) verticalBallSpeed = MAX_SPEED;
        }
        else if (slowDownBonus != null && slowDownBonus.getBounds().intersects(paddleBox.getBounds())) {
            remove(slowDownBonus);
            slowDownBonus = null;
            verticalBallSpeed /= 1.3;
        }
        else if (paddleBigBonus != null && paddleBigBonus.getBounds().intersects(paddleBox.getBounds())) {
            remove(paddleBigBonus);
            paddleBigBonus = null;
            if (paddleBox.getWidth() < WINDOW_WIDTH / 2) {
                paddleBox.setSize(paddleBox.getWidth() * 2, paddleBox.getHeight());
            }
        }
        else if (paddleSmallBonus != null && paddleSmallBonus.getBounds().intersects(paddleBox.getBounds())) {
            remove(paddleSmallBonus);
            paddleSmallBonus = null;
            if (paddleBox.getWidth() > 20) {
                paddleBox.setSize(paddleBox.getWidth() / 2, paddleBox.getHeight());
            }
        }
        else if (secretBonus != null && secretBonus.getBounds().intersects(paddleBox.getBounds())) {
            remove(secretBonus);
            secretBonus = null;
            if (livesAmount < 3) {
                livesAmount++;
                if (livesAmount == 2) add(heart2);
                if (livesAmount == 3) add(heart3);
            }
        }

        if (speedUpBonus != null && speedUpBonus.getY() > WINDOW_HEIGHT) {
            remove(speedUpBonus);
            speedUpBonus = null;
        }
        if (slowDownBonus != null && slowDownBonus.getY() > WINDOW_HEIGHT) {
            remove(slowDownBonus);
            slowDownBonus = null;
        }
        if (paddleBigBonus != null && paddleBigBonus.getY() > WINDOW_HEIGHT) {
            remove(paddleBigBonus);
            paddleBigBonus = null;
        }
        if (paddleSmallBonus != null && paddleSmallBonus.getY() > WINDOW_HEIGHT) {
            remove(paddleSmallBonus);
            paddleSmallBonus = null;
        }
        if (secretBonus != null && secretBonus.getY() > WINDOW_HEIGHT) {
            remove(secretBonus);
            secretBonus = null;
        }
    }

    private int score;
    private GLabel scoreLabel;
    RandomGenerator rnd = new RandomGenerator();
    Clip backgroundMusic;
    private GLabel statusLabel;
    private int bricksAmount = BRICKS_PER_ROW * BRICK_ROWS;
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
    private GLabel mainMenuLabel;
    private GLabel replayLabel;


    private GRect level1Screen;
    private GRect level2Screen;
    private GRect level3Screen;

    private boolean gameEnded;
    private boolean gameStarted;
    private double verticalBallSpeed = 3;
    private int horizontalBallSpeed = rnd.nextInt(-3,3);

    GOval heart1;
    GOval heart2;
    GOval heart3;
    int livesAmount = 3;


    public static void main(String[] args) {
        new mainGameClass().start(args);
    }

}