package shooter;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/***********************************************************
* Class for GameStage Object which shows the game window.
*
* @author Arieta_Eres
* @created_date 2021-12-20 21:46
*
***********************************************************/
public class GameStage {
	public static final int GAME_WINDOW_HEIGHT = 550;
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;

	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private int finalScore;

	public final static Image BACKGROUND = new Image("images/bg.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	public final static Image DAMAGED_BACKGROUND = new Image("images/bg_damaged.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	public final static Image DAMAGED_FLASHED_BACKGROUND = new Image("images/bg_damaged_flash.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	public final static Image BUFFED_BACKGROUND = new Image("images/bg_buffed.png",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);



	//the class constructor
	GameStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.GAME_WINDOW_HEIGHT, Color.LIGHTSKYBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		//instantiate an animation timer
		this.gametimer = new GameTimer(this.gc,this.scene, this.root, this);


	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		this.canvas.setLayoutY(GameStage.GAME_WINDOW_HEIGHT-GameStage.WINDOW_HEIGHT);
		this.root.getChildren().add(canvas);

		this.stage.setTitle(MenuStage.GAME_TITLE);
		this.stage.setScene(this.scene);

		//invoke the start method of the animation timer
		this.gametimer.start();
		this.stage.show();
	}

	//method to instantiate game over stage
	void flashGameOver(int result) {
		GameOverStage go = new GameOverStage(result, this.finalScore);
		go.setMenuStage(this.stage);
	}

	//method that sets the final score of player
	void setFinalScore(int score) {
		this.finalScore = score;
	}





}

