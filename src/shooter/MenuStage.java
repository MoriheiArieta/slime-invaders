package shooter;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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
import javafx.util.Duration;
/***********************************************************
* Class for MenuStage Object which displays the main menu of the game.
*
* @author Arieta
* @created_date 2021-12-20 21:46
*
***********************************************************/
public class MenuStage {
	private Scene menuScene, splashScene;
	private Stage stage;
	private VBox vBox;
	private StackPane menuRoot, splashRoot, newGameBtnPane, aboutBtnPane, instructionsBtnPane;
	private ImageView menuImgView, splashImgView, newGameBtn, aboutBtn, instructionsBtn;
	private Text titleText, newGameBtnText, instructionBtnText, aboutBtnText;
	private int splashCounter;

	public static final int MENU_WIDTH = 540;
	public static final int MENU_HEIGHT = 600;
	public static final int BUTTON_WIDTH = 210;
	public static final int BUTTON_HEIGHT = 50;

	public final static String GAME_TITLE= "SLIME INVADERS";
	public final static Image MENU_BG = new Image("images/menu_bg.gif", MenuStage.MENU_WIDTH,MenuStage.MENU_HEIGHT, false, false);
	public final static Image SPLASH_BG = new Image("images/loading.gif", MenuStage.MENU_WIDTH,MenuStage.MENU_HEIGHT, false, false);
	public final static Image SPLASH_DONE_BG = new Image("images/loading_full.png", MenuStage.MENU_WIDTH,MenuStage.MENU_HEIGHT, false, false);
	public final static Image BUTTON_IMAGE = new Image("images/button.png", MenuStage.BUTTON_WIDTH, MenuStage.BUTTON_HEIGHT, false, false);

	//constructor
	public MenuStage() {
		this.menuRoot = new StackPane();
		this.splashRoot = new StackPane();
		this.vBox = new VBox();
		this.menuScene = createMenuScene();
		this.splashScene = createSplashScene();
	}

	//sets splash scene to stage
	public void setSplashStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle(MenuStage.GAME_TITLE);
		this.splashTimeline();
	}

	//sets menu stage to scene
	public void setMenuStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle(MenuStage.GAME_TITLE);
		this.switchScenes(menuScene);
		this.stage.show();
	}

	//creates splash scene
	private Scene createSplashScene() {
		Scene scene = new Scene(splashRoot, MenuStage.MENU_WIDTH,MenuStage.MENU_HEIGHT);
		this.splashImgView = new ImageView(MenuStage.SPLASH_BG);
		this.splashRoot.getChildren().add(this.splashImgView);

		return scene;
	}

	//creates menu scene
	private Scene createMenuScene() {
		Scene scene = new Scene(menuRoot, MenuStage.MENU_WIDTH,MenuStage.MENU_HEIGHT, Color.TRANSPARENT);

		//set menu ImageView background
		this.menuImgView = new ImageView(MenuStage.MENU_BG);

		//instantiate and modify titleText
		Font theFont = Font.loadFont("file:src/font/Kemco Pixel Bold.ttf",  60);
		this.titleText = new Text(" "+MenuStage.GAME_TITLE);
		this.titleText.setTextAlignment(TextAlignment.CENTER);
		this.titleText.setFont(theFont);
		this.titleText.setLineSpacing(30);
		this.titleText.setWrappingWidth(MenuStage.MENU_WIDTH);
		this.titleText.setFill(Color.CYAN);
		this.titleText.setStroke(Color.DARKCYAN);

		Font buttonFont = Font.loadFont("file:src/font/Kemco Pixel Bold.ttf",  18);

		//button panes
		this.newGameBtnPane = new StackPane();
		this.instructionsBtnPane = new StackPane();
		this.aboutBtnPane = new StackPane();

		//button imageViews
		this.newGameBtn = new ImageView(MenuStage.BUTTON_IMAGE);
		this.instructionsBtn = new ImageView(MenuStage.BUTTON_IMAGE);
		this.aboutBtn = new ImageView(MenuStage.BUTTON_IMAGE);

		//button texts
		this.newGameBtnText = new Text("New Game");
		this.newGameBtnText.setFill(Color.DARKCYAN);
		this.newGameBtnText.setFont(buttonFont);

		this.instructionBtnText = new Text("Instructions");
		this.instructionBtnText.setFill(Color.DARKCYAN);
		this.instructionBtnText.setFont(buttonFont);

		this.aboutBtnText = new Text("About");
		this.aboutBtnText.setFill(Color.DARKCYAN);
		this.aboutBtnText.setFont(buttonFont);

		//button event handlers
		this.addEventHandler(this.newGameBtnPane);
		this.addEventHandler(this.instructionsBtnPane);
		this.addEventHandler(this.aboutBtnPane);

		//add button contents to button StackPanes
		this.newGameBtnPane.getChildren().addAll(this.newGameBtn, this.newGameBtnText);
		this.instructionsBtnPane.getChildren().addAll(this.instructionsBtn, this.instructionBtnText);
		this.aboutBtnPane.getChildren().addAll(this.aboutBtn, this.aboutBtnText);


		//modify Vbox
		this.vBox.setAlignment(Pos.CENTER);
		this.vBox.setSpacing(50);

		//add buttons to VBox
		this.vBox.getChildren().addAll(titleText,this.newGameBtnPane, this.instructionsBtnPane, this.aboutBtnPane);
		this.menuRoot.getChildren().addAll(this.menuImgView, vBox);

		return scene;
	}


	//adds event handler for button panes
	private void addEventHandler(StackPane btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (btn.equals(newGameBtnPane)) {
					stage.close();
					GameStage theGameStage = new GameStage();
					theGameStage.setStage(stage);
				}

				if (btn.equals(instructionsBtnPane)) {
					stage.close();
					InstructionsStage inst = new InstructionsStage();
					inst.setInstStage(stage);
				}

				if (btn.equals(aboutBtnPane)) {
					stage.close();
					AboutStage about = new AboutStage();
					about.setAboutStage(stage);
				}
			}
		});
	}

	//sets scene to stage
	private void switchScenes(Scene scene) {
		this.stage.setScene(scene);
	}

	//shows splash screen
	private void splashTimeline() {
		this.stage.setScene(this.splashScene);

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), (ActionEvent event) -> {
			splashCounter++;
			if (splashCounter==1) {
//				this.switchScenes(scene);

				this.splashImgView.setImage(MenuStage.SPLASH_DONE_BG);
			}
			if (splashCounter==2) {
				this.switchScenes(this.menuScene);
			}
        }));

        timeline.setCycleCount(2);
        timeline.play();
        this.stage.show();
	}


}
