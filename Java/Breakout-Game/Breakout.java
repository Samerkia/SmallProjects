/**
 * 
 */
package application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Nicholas Raffel
 * @author Luke Gauvin
 * @version 5/4/2020
 */
public class Breakout extends Application {

	private Stage window; //Main window
	

	private Scene startScene;//Start screen for the Start button
	private Scene inGameScene;// Scene for when you're playing the game
	private Scene endGameScene;//Final Scene 
	private int score;//Score
	private int ballCount;// Counter for your remaining balls
	private Group gamePane;//Pane for our fame

	
	private double horizontalSpeed;
	private double verticalSpeed;
	
	private Circle ball;
	private Image bar;
	private ImageView imview;
	private Timeline loop;
	private Pane game;
	private Blocks[][] blockArray;
	private Bounds[][] blockBounds;

	private Circle ball1;
	private Circle ball2;
	private Circle ball3;
	private Text scoreTxt;
	
	Button retryFalse;
	
	@Override
	public void start(Stage window) throws Exception {
		this.window = window;
		initializeStartScreen();
		window.show();
	}
	
	/**
	 * @author Nicholas Raffel
	 * Creates the start scene, where the player is welcomed and presses the start button  
	 */
	public void initializeStartScreen()
	{
		//SoundCreator to call to the sounds and play them
		SoundCreator gameSound = new SoundCreator();
		gameSound.playGamePlaySound();
		
		//Sets fonts and texts
		Font font = new Font("Comic Sans MS", 50);
		Font font2 = new Font("Comic Sans MS", 20);
		Text text = new Text("Breakout");
		Text text2 = new Text("Use Left/Right Arrow keys or A/D to move");
		text.setFont(font);
		text.setFill(Color.BLACK);
		text2.setFont(font2);
		text2.setFill(Color.BLACK);
		
		//Start Button creation
		Button start = new Button("Start");
		start.setFont(font);
		start.setPrefWidth(250);
		start.setPrefHeight(70);
		start.setTooltip(new Tooltip("Starts the game!"));
		start.setOnAction(this::processStartRetry);
		
		//VBox to actually have all things shown
		VBox startPane = new VBox(text, text2, start);
		startPane.setAlignment(Pos.CENTER);
		startPane.setSpacing(50);
		startPane.setStyle("-fx-background-color: Red");
		
		//Puts everything into the scene and sets the title 
		startScene = new Scene(startPane, 500,500, Color.BLUE);
		window.setScene(startScene);
		window.setTitle("Breakout!");
	}
	
	/**
	 * @author Nicholas Raffel and Luke Gauvin
	 * @param e, the name of the event that starts the start or the retry of playing the game
	 * This method is the start and retry of the game, it is called to reset the game screen 
	 */
	public void processStartRetry(Event e)
	{
		initializeInGameScene();
		inGameScene.setOnKeyPressed(this::processBarMovement);
		
		horizontalSpeed = 2.5;
        verticalSpeed = 5;
		
		loop.setCycleCount(Timeline.INDEFINITE);
		loop.play();
	}
	
	//Creates the game screen
	/**
	 * @author Nicholas Raffel
	 * Creates the in game scene, boundaries, blocks and calls to other methods to create the bar, and ball.
	 */
	public void initializeInGameScene()
	{
		score = 0;
		ballCount = 3;
		
		Font scoreFont = Font.font("Comic Sans MS",FontWeight.BOLD,35);
		
		game = new Pane();
		//Creates the score 
		scoreTxt = new Text(200,200,"Score: " + score);
		scoreTxt.setFont(scoreFont);
		scoreTxt.setFill(Color.WHITE);
		Text ballCnt = new Text(200,200,"Balls: ");
		ballCnt.setFont(scoreFont);
		ballCnt.setFill(Color.WHITE);
		
		//Top bar graphic
		StackPane topPane = new StackPane();
		topPane.setStyle("-fx-background-color: Grey");
		topPane.setPadding(new Insets(0,1650,100,0));
		topPane.getChildren().addAll(scoreTxt,ballCnt);
		scoreTxt.setTranslateX(150);
		scoreTxt.setTranslateY(90);
		ballCnt.setTranslateX(1200);
		ballCnt.setTranslateY(90);
		VBox top = new VBox(topPane);
		
		//Left bar graphic
		FlowPane leftPane = new FlowPane();
		leftPane.setStyle("-fx-background-color: Grey");
		leftPane.setPadding(new Insets(0,-250,950,0));
		VBox left = new VBox(leftPane);
		
		//Right bar graphic
		FlowPane rightPane = new FlowPane();
		rightPane.setStyle("-fx-background-color: Grey");
		rightPane.setPadding(new Insets(0,-250,950,0));
		rightPane.setTranslateX(1500);
		VBox right = new VBox(rightPane);
		
		initializeBar();
		gamePane = new Group(top,left,right,game,imview);
		initalizeBallCount();
		initializeBall();
		
		//Creates the blocks
		blockArray = new Blocks[5][8];
		blockBounds = new Bounds[5][8];
		double blockStartX = 60;
        double blockStartY = 120;

        //Outer For Loop for the rows and the start position of the vertical placement of the blocks
        //Inner For Loop to place the blocks in the row and the columns and adds it to the game scene to be played
        for(int row =0; row < 5; row++)
        {
            blockStartY += 60;
            //This is the inner for loop for the placement of the blocks
            for(int col =0; col < 8; col++)
            {
                blockStartX += 155;
                Blocks block1 = new Blocks(4-row);
                blockArray[row][col] = block1;
                game.getChildren().addAll(blockArray[row][col].getRectangle());
                blockArray[row][col].getRectangle().setX(blockStartX);
                blockArray[row][col].getRectangle().setY(blockStartY);
                blockBounds[row][col] = blockArray[row][col].getRectangle().getBoundsInLocal();
            }
            
            blockStartX = 60;
        }
		
		inGameScene = new Scene(gamePane,1650,950,Color.BLACK);
		inGameScene.getWindow();
		window.setScene(inGameScene);
		window.setX(125);
		window.setY(50);
	}
	
	/**
	 * @author Nicholas Raffel
	 * returns the count of the balls so the game scene can be updated and the player knows how many lives/balls remain
	 */
	public void initalizeBallCount()
	{

		ball1 = new Circle(15,Color.WHITE);
		ball1.setTranslateX(1325);
		ball1.setTranslateY(117);
		
		ball2 = new Circle(15,Color.WHITE);
		ball2.setTranslateX(1365);
		ball2.setTranslateY(117);
		
		ball3 = new Circle(15,Color.WHITE);
		ball3.setTranslateX(1405);
		ball3.setTranslateY(117);
		
		game.getChildren().addAll(ball1,ball2,ball3);
	}
	
	/**
	 * @author Luke Gauvin (Did most of the harder work) and Nicholas Raffel
	 * A TimeLine loop for the animation for the ball to be able to move
	 * The for loop is the creation of the block bounds and and determines if the ball
	 * has hit the bar in order for the blocks to be "destroyed"
	 * all of the if statements inside of the for loops create the score and add onto the score
	 * The booleans are statements for the wall, roof, and bottom bounds for our game border 
	 */
	public void initializeBall()
	{
		//Puts the ball pane onto the game pane for its movement
		Pane ballPane = new Pane();
		ball = new Circle(15, Color.WHITE); // Creation of he ball
		ball.relocate(785, 790);// Relocates the ball to be in its center starting position 
		ballPane.getChildren().addAll(ball);//adds the ball to the ballPane
		
		game.getChildren().addAll(ballPane);// Adds the ballPane into the game
		
		//This is the Timeline loop for he ball movement animation
		loop = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

            @Override
            /**
             * @author Luke Gauvin & Nick Raffel
             * An event handler for the actual Timeline loop that stores the necessary input to use the timeline class
             */
            public void handle(final ActionEvent t) {
            	//Creates the balls speed and movement
                ball.setLayoutX(ball.getLayoutX() + horizontalSpeed);
                ball.setLayoutY(ball.getLayoutY() + verticalSpeed);
                
                //Creates the bounds for the bar so the ball can be bounced off of it
                final Bounds barBounds = imview.getBoundsInLocal();
                boolean atBlock = false; // True or false statement for if the ball is at the bar
                int tempScore = 1; //a temporary variable to hold the score               
                
                //For loop that determines if the ball as hit the block or not and
                //whether or not it should be destroyed or not
                for(int row =0; row < 5; row++)
                {
                	//Nested for loop to do what was stated above for both rows and columns
                    for(int col =0; col < 8; col++)
                    {
                        blockBounds[row][col] = blockArray[row][col].getRectangle().getBoundsInLocal();
                        atBlock = new Rectangle(ball.getLayoutX() - 20, ball.getLayoutY() - 20, 20, 20).intersects(blockBounds[row][col]);
                        //If statements to determine if the ball ever hits the block and 'destroys' the blocks and adds to the score
                        if(atBlock)
                        {                        	
                        	blockArray[row][col].destroyBlock();
                        	verticalSpeed *= -1.02;
                        	if(blockArray[row][col].getBlockLevel() < 0)
                        	{
                        		//Moves the blocks off of the scene to never be seen again during that current game
                        		blockArray[row][col].getRectangle().setX(1000000);
                        		blockArray[row][col].getRectangle().setY(1000000);
                        		//Series of if statements to add to the score according to the blocks that have been destoryed
                        		if(row == 4)// adds ten points as it is the closest row of blocks
                        		{
                        			tempScore *= ((row / 4) *10);
                        			scoreTxt.setFill(Color.GREY);
                        			score += tempScore;
                        		}
                        		if(row == 3)//adds twenty points as it is the second closest row of blocks
                        		{
                        			tempScore *= ((row / 3) *20);//adds thirty points as it is the middle row of blocks
                        			scoreTxt.setFill(Color.GREY);
                        			score += tempScore;
                        		}
                        		if(row == 2)//adds twenty points as it is the third furthest row of blocks
                        		{
                        			tempScore *= ((row / 2) *30);
                        			scoreTxt.setFill(Color.GREY);
                        			score += tempScore;
                        		}
                        		if(row == 1) //adds forty points as it is the second furthest row of blocks
                        		{
                        			tempScore *= ((row) *40);
                        			scoreTxt.setFill(Color.GREY);
                        			score += tempScore;
                        		}
                        		if(row == 0)//adds fifty points as it is the last row of blocks
                        		{
                        			tempScore *= ((row) +50); 
                            		scoreTxt.setFill(Color.GREY);
                        			score += tempScore;
                        		}
                        		
                        		//creates the font and look of the score again as the old score is
                        		//deleted and then the new score os overlayed on top of the old score
                        		//as a means of a live score counter
                        		Font scoreFont = Font.font("Comic Sans MS",FontWeight.BOLD,35);
                        		scoreTxt = new Text(0,0,"Score: " + score);
                        		scoreTxt.setFont(scoreFont);
                        		scoreTxt.setFill(Color.WHITE);
                        		scoreTxt.relocate(150,90);
                        		game.getChildren().add(scoreTxt);
                        		//An if statement to end the game and the loop if the maximum amount of score has been reached
                        		if(score == 1200)
                        		{
                        			loop.stop();
                        			processEndOfGame();
                        		}
                        		
                        	}
                        	//A local variable for sound to call to the Sound Creator class to play the correct sound
                        	SoundCreator gameSound = new SoundCreator();
                        	gameSound.playBlockBreakSound();
                            break;
                        }
                    }
                    //Breaks us out of the second part of the for loop to end it just to pre-reserve processing power
                    if(atBlock)
                        break;
                }
                
                //Boolean statements to determine if the ball is at a border or the bar and if it is to bounce off or not
                boolean atRightBorder = ball.getLayoutX() >= 1485;
                boolean atLeftBorder = ball.getLayoutX() <= 160;
                boolean atTopBorder = ball.getLayoutY() <= 164;
                boolean atBottomBorder = ball.getLayoutY() >= 1000;
                boolean atBar = new Rectangle(ball.getLayoutX(), ball.getLayoutY(), 20, 20).intersects(barBounds);

                //If statement for the bottom border and resets the ball position and reduces ball count if it goes off the bottom border
                if(atBottomBorder)
                {
                	ball.relocate(785, 790);
                	horizontalSpeed = 1.5;
                	verticalSpeed = 1.5;
                	imview.setX(690);
                	ballCount -= 1;
                	
                	if(ballCount == 2)
                		ball3.setFill(Color.GREY);
                	if(ballCount == 1)
                		ball2.setFill(Color.GREY);                
                	if(ballCount == 0)
                	{
                		loop.stop();
                		processEndOfGame();
                	}
                }
                //If statement to re-adjust the balls trajectory if it hit the side bounds
                //And plays the corresponding sound
                if (atRightBorder || atLeftBorder) {
                    horizontalSpeed *= -1.02;
                    verticalSpeed *= 1.02;
                    SoundCreator gameSound = new SoundCreator();
                    gameSound.playBallBounceSound();
                }
                //If statement to re-adjust the balls trajectory if it hit the top boundary or the bar
                //And plays the corresponding sound
                if (atTopBorder || atBar) {
                    verticalSpeed *= -1.02;
                    horizontalSpeed *= 1.02;
                    SoundCreator gameSound = new SoundCreator();
                    gameSound.playBallBounceSound();
                }
               
      
            }
        }));

	}
	
	/**
	 * @author Nicholas Raffel
	 * Creates the bar the player controls to hit the ball using red.png which is the picture
	 * used for the creation of our bar because it was easier that way to be able to move the bar
	 * using key strokes
	 */
	public void initializeBar()
	{
		bar = new Image("red.png");
		imview = new ImageView(bar);
		imview.setViewport(new Rectangle2D(0,0,275,25));
		imview.setX(690);
		imview.setY(825);
	}
	/**
	 * @author Nicholas Raffel
	 * The method that controls the movement of the bar using A/D or left and right arrow keys ]
	 * @param e name of the Key Event that allows the bar to be moved
	 */
	public void processBarMovement(KeyEvent e)
	{
		//Controls to move the bar left
		if((e.getCode() == KeyCode.A) || (e.getCode() == KeyCode.LEFT))
		{
			try
			{
				//Statement to move the bar left 15 pixels
				imview.setX(imview.getX() - 15);
			}
			catch(Exception ex)
			{
				System.out.println("Couldnt process scene change");
			}
		}
		//Controls to move the bar right
		if((e.getCode() == KeyCode.D) || (e.getCode() == KeyCode.RIGHT))
		{
			try
			{
				//Statement to move the ball right 15 pixels
				imview.setX(imview.getX() + 15);
			}
			catch(Exception ex)
			{
				System.out.println("Couldnt process scene change");
			}
		}
		
		//boundaries for the bars movement 
		if(imview.getX() < 150)
		{
			//if bar is to far the the right it wont move right any further
			imview.setX(150);
		}
		else if(imview.getX() > 1275)
		{
			//if bar is to far to the left it wont move left any further
			imview.setX(1275);
		}
		
	}
	
	/**
	 * @author Luke Gauvin
	 * Processes the last scene of the game for whether or not you have lost all of your balls or
	 * reached the maximum amount of points
	 */
	public void processEndOfGame()
	{
		
		//Button Creation for the End of Game scene
		Button retryTrue = new Button("Yes");
		Font comicSans = new Font("Comic Sans MS", 50);
		retryTrue.setTextFill(Color.BLACK);
		retryTrue.setFont(comicSans);
		retryTrue.setOnAction(this::processStartRetry);
		retryTrue.setTooltip(new Tooltip("Starts the game again!"));
		
		retryFalse = new Button("No");
		retryFalse.setTextFill(Color.BLACK);
		retryFalse.setFont(comicSans);
		retryFalse.setOnAction(this::processClose);
		retryFalse.setTooltip(new Tooltip("Closes Application!"));
		
		//Score Text creation for the final score for the end of game scene
		Text endScore1 = new Text("Total Score:");
		endScore1.setFont(comicSans);
		endScore1.setFill(Color.WHITE);
		
		//Actual points for what the score is
		Text endScore2 = new Text(Integer.toString(score));
		endScore2.setFont(comicSans);
		endScore2.setFill(Color.WHITE);
		
		//Texts for the Play again
		Text endScore3 = new Text("Play Again");
		endScore3.setFont(comicSans);
		endScore3.setFill(Color.WHITE);
		
		//Text for the Thank you
		Text thankYou = new Text("Thanks for playing!");
		thankYou.setFont(comicSans);
		thankYou.setFill(Color.WHITE);
		
		//HBox for the button placement
		HBox retry = new HBox(retryTrue, retryFalse);
		retry.setAlignment(Pos.CENTER);
		retry.setSpacing(40);
		
		//VBox for the placement of everything
		VBox root = new VBox(endScore1, endScore2, endScore3, retry, thankYou);
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: Blue");
		
		endGameScene = new Scene(root, 500, 500, Color.BLUE);
		
		window.setScene(endGameScene);

	}
	/**
	 * @author Nicholas Raffel
	 * @param e name of the Event for when the button 'No' button clicked to close the program
	 * Closes/Ends the program if the 'No' Button is clicked at the End of Game scene
	 */
	public void processClose(Event e) 
	{
		//If Statement to check if the 'No' button to playing again is clicked or not
		//and if it is clicked then the program closes
		if(retryFalse.isArmed())
		{
			try 
			{
				//Arguments for closing program
				((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
			} catch (Exception e1) {
				System.out.println("Exception caught");
			}
		}
	}


	/**
	 * @param args the name of the variable that assists in launching the program
	 * Method launches the programs
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
