package shooter;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/***********************************************************
* Class for GameOverStage Object which shows the result window after losing/winning the game.
*
* @author Arieta_Eres
* @created_date 2021-12-20 21:46
*
***********************************************************/

public class GameOverStage {

	private Scene gameOverScene;
	private Stage stage;
	private VBox vBox;
	private StackPane menuRoot, menuBtnPane, exitBtnPane, scorePane;
	private ImageView gameOverImgView, newGameBtn, exitBtn, spImgView;
	private Text resultText, newGameBtnText, spText, exitBtnText;
	private int score;


	public static final Image WIN_BG = new Image("images/winbackground.gif", GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, false, false);
	public static final Image LOSE_BG = new Image("images/losebackground.gif", GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, false, false);
	public static final Image SP_BG = new Image("images/sp_bg.png", GameStage.WINDOW_WIDTH*.15,60, false, false);

	//constructor
	GameOverStage(int result, int score) {
		this.menuRoot = new StackPane();
		this.vBox = new VBox();
		this.gameOverScene = createGameOverScene(result, score);

	}


	//method that sets menu scene to stage
	public void setMenuStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle(MenuStage.GAME_TITLE);
		this.stage.setScene(gameOverScene);
		this.stage.show();
	}


	//creates the menu scene
	private Scene createGameOverScene(int result, int score) {

		Scene scene = new Scene(menuRoot,GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.score = score;
		//set menu ImageView background
		if (result==0) {
			this.resultText = new Text(" YOU LOSE!");
			this.gameOverImgView = new ImageView(GameOverStage.LOSE_BG);
		}

		if (result==1) {
			this.resultText = new Text(" YOU WIN!");
			this.gameOverImgView = new ImageView(GameOverStage.WIN_BG);
		}


		//instantiate and modify resultText
		Font theFont = Font.loadFont("file:src/font/Kemco Pixel Bold.ttf",  70);
		Font buttonFont = Font.loadFont("file:src/font/Kemco Pixel Bold.ttf",  20);

		this.resultText.setTextAlignment(TextAlignment.CENTER);
		this.resultText.setFont(theFont);
		this.resultText.setFill(Color.AQUA);
		this.resultText.setStroke(Color.BLACK);


		//button panes
		this.menuBtnPane = new StackPane();
		this.scorePane = new StackPane();
		this.exitBtnPane = new StackPane();

		//button imageViews
		this.newGameBtn = new ImageView(MenuStage.BUTTON_IMAGE);
		this.spImgView = new ImageView(GameOverStage.SP_BG);
		this.exitBtn = new ImageView(MenuStage.BUTTON_IMAGE);


		//button texts
		this.newGameBtnText = new Text("Menu");
		this.newGameBtnText.setFill(Color.DARKCYAN);
		this.newGameBtnText.setFont(buttonFont);

		this.spText = new Text("   "+this.score);
		this.spText.setFill(Color.CYAN);
		this.spText.setFont(buttonFont);

		this.exitBtnText = new Text("Exit");
		this.exitBtnText.setFill(Color.DARKCYAN);
		this.exitBtnText.setFont(buttonFont);


		//button event handlers
		this.addEventHandler(this.menuBtnPane);
		this.addEventHandler(this.scorePane);
		this.addEventHandler(this.exitBtnPane);

		//add button contents to button StackPanes
		this.menuBtnPane.getChildren().addAll(this.newGameBtn, this.newGameBtnText);
		this.scorePane.getChildren().addAll(this.spImgView, this.spText);
		this.exitBtnPane.getChildren().addAll(this.exitBtn, this.exitBtnText);


		//modify Vbox
		this.vBox.setAlignment(Pos.CENTER);
		this.vBox.setSpacing(50);

		//add buttons to VBox
		this.vBox.getChildren().addAll(resultText,this.scorePane,this.menuBtnPane,  this.exitBtnPane);
		this.menuRoot.getChildren().addAll(this.gameOverImgView, vBox);

		return scene;
	}

	//button event handlers
	private void addEventHandler(StackPane btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (btn.equals(menuBtnPane)) {
					stage.close();
					MenuStage menu = new MenuStage();
					menu.setMenuStage(stage);
				}

				if (btn.equals(exitBtnPane)) {
					System.exit(0);
				}
			}
		});
	}

}
