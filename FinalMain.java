

import java.util.LinkedList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;
import java.io.File;

public class FinalMain extends Application {
	
	Random r;
	Scene scene;
	long lasttime;
	boolean firsttime = true;
	LinkedList<Platform> platforms; //container for holding all the platforms
	LinkedList<Circle> fireballList; //container for holding all the balls
	
	
	//Variables for keep track of which key is pressed
	 boolean leftPressed = false;
	 boolean rightPressed = false;
	 boolean upPressed = false;
	 boolean downPressed = false;
	 boolean spacebarPressed = false;
	 
	 int timer = 0; //keep track of time
	 int velocity = 200;
	
	 
	 Player character; //character
	 Pane gameWindow;
	 Pane gameScore;
	 
	 //Set the bounds of the screen
	 int platformWidth = 200;
	 int platformHeight = 15;
	 int gameWindowWidth = 500;
	 int gameWindowHeight = 700;
	 int scorePoints = 0;
	 Rectangle background; //for displaying background
	 
	 Text gameStatus = new Text();  //for showing game score
	 Driver d = new Driver(); //for animation
	 AudioClip soundClip; //for playing sound


	
	public static void main( String[] args )
	{ launch(args);}
	
    @Override
    public void start(Stage stage)
    { 
    	
    	//Create window
    	r = new Random();
    	Pane root = new Pane();
    	gameWindow = new Pane();
    	gameScore = new Pane();
        scene = new Scene(root, gameWindowWidth, gameWindowHeight);
        stage.setTitle("Floater");
	    stage.setScene(scene);
	    stage.setResizable(false);
	    stage.show();
	    stage.setResizable(false);
	    
	    platforms = new LinkedList<Platform>();
	    fireballList = new LinkedList<Circle>();
	    
	    //Display background and capture user keys
	    createBackground(gameWindow);
	   	captureUserKey(scene,root);
	    
	    root.getChildren().add(gameWindow);
	    root.getChildren().add(gameScore);
	    
	    //Display platforms,score and player
	    initializeScoreCounter();
	    initalizeGame(root);
	    
	    //Initialize sound
	    initializeSound();
	    
	   
	    
	    
 
    	



   }
    
    public void initializeSound() {
    	File soundFile = new File("game-over.mp3");
		String loc  = soundFile.toURI().toString();
		System.out.println(loc);
		soundClip = new AudioClip(soundFile.toURI().toString());
		soundClip.setVolume( .7 );
    }
   
    
    //Add score counter to root and display it
   public void initializeScoreCounter() {
	   	gameStatus.setText("Score: 0"); 
	   	Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 15); 
	   	gameStatus.setFont(font); 
	   	Pane container = new Pane();
	   	container.getChildren().add(gameStatus);
	   	gameScore.getChildren().add(container);
	   	container.setLayoutX(150);
	   	container.setLayoutY(15);
	   	
	   	//Allow accessibility 
	   	gameStatus.setFocusTraversable(true);
   }
    
   
   //Display player, platforms on screen and initialize array
   public void initalizeGame(Pane root) {
	   
	   //Game is restarting
	   if(platforms.size()>0 || fireballList.size()>0 || timer>0) {
		   
		   //Empty objects from previous game iteration and remove them from root
		   	gameWindow.getChildren().removeAll(fireballList);
			gameWindow.getChildren().removeAll(platforms);
			fireballList = new LinkedList<Circle>();
			platforms = new LinkedList<Platform>();
			
			if(gameWindow.getChildren().contains(character)) {
				System.out.println("Remove character in initialize");
				gameWindow.getChildren().remove(character);
			}
	   }
	   System.out.println("In initialize");
	   	gameStatus.setText("Press S to start \nUse left and right arrow keys to move \nuse space to move up"); 
	   	initSetup();
	   	
	   	//Setup player
	    character = new Player(50,50,"Wraith_03_Idle_011.png");
	    character.setCharacterCoordinates(400,400);
	    gameWindow.getChildren().add(character);
	    
	    scorePoints = 0;
	    timer = 0;
	    
   	
	    
   	
	   
   }
    
   
   public void createBackground(Pane playerWindow) {
		background = new Rectangle();
		    
		background.setX(0);
	   	background.setY(0);
	   	background.setWidth(gameWindowWidth);
	   	background.setHeight(gameWindowHeight);
	   	
	   	playerWindow.getChildren().add(background);
	   	
	   	Image image = new Image("file:background.png");
		ImagePattern imagePattern = new ImagePattern(image);
	   	background.setFill(imagePattern);
   }
   
   //Increment score counter and display updated one
   public void updateScore() {
	   scorePoints++;
	   gameStatus.setText("Score: "+scorePoints);
	   
   }
   
   
   public void createFireball(double playerXLocation,Pane gameWindow) {
	   	Circle fireball = new Circle();
	   	fireball.setRadius(10);
   		gameWindow.getChildren().add(fireball);
   		fireball.setCenterX(playerXLocation);
   		fireball.setCenterY(0);
   		fireball.setFill(Color.DARKRED);
   		
   		//Add to list to be used outside this function
   		fireballList.add(fireball);
   		
   }
    
    //Capture user key and then update variables or start game
    public void captureUserKey(Scene scene, Pane root) {
    	KeyCombination leftKey = new KeyCodeCombination(KeyCode.LEFT);
    	KeyCombination rightKey = new KeyCodeCombination(KeyCode.RIGHT);
    	KeyCombination upKey = new KeyCodeCombination(KeyCode.UP);
    	KeyCombination spaceKey = new KeyCodeCombination(KeyCode.SPACE);
    	scene.setOnKeyPressed 
        (  (KeyEvent ke) -> 
           {
        	   
           	if (ke.getCode()==KeyCode.SPACE)
        	{
           		
           		spacebarPressed = true;
           		
        	}
           	else {
           		spacebarPressed = false;
           	}
           	if(ke.getCode()==KeyCode.LEFT) {
           		leftPressed = true;
           		
           	}
           	if(ke.getCode()==KeyCode.RIGHT) {
    
           		
           		rightPressed = true;
           	}
           	if(ke.getCode()==KeyCode.UP) {
           		upPressed = true;
           	}
           	if(ke.getCode()==KeyCode.S) {
           		
           	  //Start game from anew
           	  initalizeGame(root);
           	  d.start();
           	  gameStatus.setText("Score: 0"); 
           	  
           	  //Mute game over sound
           	  if(soundClip.isPlaying()) {
           		  soundClip.stop();
           	  }
           	}
       
           	
           }
        );
    	
    	
    	//Update state of variables
    	scene.setOnKeyReleased((KeyEvent ke) -> 
           {
        	   if(leftKey.match(ke)) {
        		   leftPressed = false;
        	   }
        	   if(rightKey.match(ke)){
        		   rightPressed = false;
        	   }
        	   if(upKey.match(ke)) {
        		   upPressed = false;
        	   }
        	   
           }
        );
    	
    }
    
    
    //Create platforms and add them to the game window
    public void initSetup() {
    	
    	Platform p1 = new Platform(platformWidth,platformHeight,"object3.png");
    	p1.setPlatformCoordinates(210, 170);
    	gameWindow.getChildren().add(p1);
    	
    	
    	
    	
    	Platform p2 = new Platform(platformWidth,platformHeight,"object3.png");
    	p2.setPlatformCoordinates(10, 30);
    	gameWindow.getChildren().add(p2);
    	
    	
    	platforms.add(p1);
    	platforms.add(p2);
    	
    	
    }
    
    
    public void movePlatforms(double deltat) {
    	for (int i=0; i<platforms.size(); i++) {
    		platforms.get(i).moveDown(deltat);
    		
    	}
    }
    
    //Loop through all fireballs and move them down
    public void moveFireball(double deltat) {
    	for (int i=0; i<fireballList.size(); i++) {
    		double y = fireballList.get(i).getCenterY();
    		y = y + 300*deltat;
    		fireballList.get(i).setCenterY(y);
    	}
    	
    }
    
    
    //Create platform,add to array and assign to game window
    public void createPlatform(Pane gameWindow) {
    	
    	//Initialize coordinates for platform and assign image
    	double platformXLocation = 225*Math.random()+20;
    	Platform created = new Platform(platformWidth,platformHeight,"object3.png");
    	created.setPlatformCoordinates(platformXLocation, 0);
    	platforms.add(created);
    	gameWindow.getChildren().add(created);
    	
    }
    
    
    //Show last score to user and end game
    public void endGame() {
    	gameStatus.setText("Game Over - Your Score: "+scorePoints +"\n Press S to start");
    	gameWindow.getChildren().remove(character);
    	
    	//Stop timer and play gameover sound
		d.stop();
		firsttime = true;
		soundClip.play();
	
    }
    
    public class Driver extends AnimationTimer
    {
    	@Override
    	public void handle( long now )
    	{
    		if ( firsttime ) { lasttime = now; firsttime = false;  }
    		else
    		{
    			double deltat = (now-lasttime ) * 1.0e-9;
    			lasttime = now;
    			timer++;
    			
    			//Create platform, fireball and update score at specific intervals
    			if(timer%70==0) {
    				createPlatform(gameWindow);
    			}
    			if(timer%70==0) {
    				createFireball(character.getX(),gameWindow);
    			}
    			
    			if(timer%100==0) {
    				updateScore();
    			}
    			
    			
    			//Get coordinates of the player
    			double playerTopY = character.getY();
    			double playerBottomY = character.getY()+character.getHeight()-10;
    			double playerLeftX = character.getX();
    			double playerRightX = character.getX()+character.getWidth();
    			
    			
    			//Get coordinates of each platform
    		for(int i=0; i<platforms.size(); i++) {	
    			double platformTopY = platforms.get(i).getY();
    			double platformBottomY = platformTopY + platforms.get(i).getHeight();
    			double platformLeftX = platforms.get(i).getX();
    			double platformRightX = platformLeftX + platforms.get(i).getWidth();
    			
    			Bounds playerBound = character.getBoundsInLocal();
    			Bounds platformBound = platforms.get(i).getBoundsInLocal();
    			
    			//Check if player collides with platform
    			if(playerBound.intersects(platformBound)) {
    			
    				double platformMinYDiff = Math.abs(platformTopY - playerBottomY);
    				double platformMaxYDiff = Math.abs(platformBottomY - playerTopY);
    				double platformMinXDiff = Math.abs(platformLeftX - platformRightX);
    				double platformMaxXDiff = Math.abs(platformRightX - platformLeftX);
    				
    				double min = Math.min(platformMinYDiff, platformMaxYDiff); 
    				double minX = Math.min(platformMinXDiff, platformMaxXDiff);
    				
    				if(min == platformMinYDiff) {
    					// then we know we have collided with top of platform
    					character.setY(platforms.get(i).getY() - character.getHeight());
    			
    					
    				}
    				else if(min == platformMaxYDiff) {
    					// then we know we have collided with the bottom of the platform
    					character.setY(platformBottomY);
    					
    				}
    				
    			}
    			
    		}
    			
    			//Move player's position depending on the key pressed
    			if(leftPressed) {
    				character.moveLeft(deltat);
    			}
    			else if(rightPressed) {
    				character.moveRight(deltat);
    			}
    			else {
    				character.remainIdle();
    			}
    			if(spacebarPressed) {
    				character.setY(character.getY()-200*deltat);
    			}
    			if(spacebarPressed) {
    				character.setY(character.getY()+50*deltat);
    			}
    			else {
    				character.setY(character.getY()+100*deltat);
    			}
    			
    			//Move platforms and fireballs downward
    			movePlatforms(deltat);
    			moveFireball(deltat);
    			
    			//End game if fireball intersects with a player
    			for (int i=0; i<fireballList.size(); i++) {
	    			Bounds playerBound = character.getBoundsInLocal();
	    			Bounds fireballBound = fireballList.get(i).getBoundsInLocal();
	    			
	    			if(playerBound.intersects(fireballBound)) {
	    				endGame();
	    			}
    			}
    			
    			//End game if player goes out of windows
    			if(character.getY()>gameWindowHeight || character.getX()+20>gameWindowWidth || character.getX()+20<0 || character.getY()<0) {
    				endGame();
    			}
    		}
    			
    	}
    }

}
