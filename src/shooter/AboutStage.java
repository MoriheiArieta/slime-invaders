package shooter;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
/***********************************************************
* Class for AboutStage window which shows the information about summary/lore of the game and the profiles of the developers.
*
* @author Arieta_Eres
* @created_date 2021-12-20 21:46
*
***********************************************************/
public class AboutStage {

	private Scene aboutScene;
	private Stage stage;
	private HBox arrowBox;
	private Group aboutRoot;
	private ImageView aboutImgView, backBtnImgView, nxtArwImgView, backArwImgView;

	public final static Image ABOUT_PAGE_1 = new Image("images/about_page1.png", GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, false, false);
	public final static Image ABOUT_PAGE_2 = new Image("images/about_page2.png", GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, false, false);


	//constructor
	AboutStage() {
		this.aboutRoot = new Group();
		this.aboutScene = new Scene(aboutRoot, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.arrowBox = new HBox();
		this.backBtnImgView = new ImageView();
		this.aboutImgView = new ImageView();
		this.nxtArwImgView = new ImageView();
		this.backArwImgView = new ImageView();
	}


	//method to set about scene to stage
	public void setAboutStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle(MenuStage.GAME_TITLE);

		this.aboutImgView.setImage(AboutStage.ABOUT_PAGE_1);
		this.nxtArwImgView.setImage(InstructionsStage.NEXT_ARROW);
		this.backArwImgView.setImage(InstructionsStage.BACK_ARROW);
		this.backBtnImgView.setImage(InstructionsStage.BACK_BTN_IMAGE);

		this.backBtnImgView.setLayoutX(16);
		this.backBtnImgView.setLayoutY(16);

		this.arrowBox.setAlignment(Pos.CENTER);
		this.arrowBox.setLayoutY(GameStage.WINDOW_HEIGHT-65);
		this.arrowBox.setPadding(new Insets(0,0,5,0));
		this.arrowBox.setSpacing(680);
		this.arrowBox.setPrefWidth(GameStage.WINDOW_WIDTH);

		this.addEventHandler(this.nxtArwImgView);
		this.addEventHandler(this.backArwImgView);
		this.addEventHandler(this.backBtnImgView);
		this.backArwImgView.setVisible(false);
		this.arrowBox.getChildren().addAll(this.backArwImgView, this.nxtArwImgView);
		this.aboutRoot.getChildren().addAll(aboutImgView, this.arrowBox,this.backBtnImgView);
		this.stage.setScene(this.aboutScene);
		this.stage.show();
	}

	//method that adds event handlers to about buttons
	private void addEventHandler(ImageView arrow) {
		arrow.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

				if (arrow.equals(nxtArwImgView)) {
					if (aboutImgView.getImage().equals(AboutStage.ABOUT_PAGE_1)) {
						backArwImgView.setVisible(true);
						nxtArwImgView.setVisible(false);
						aboutImgView.setImage(AboutStage.ABOUT_PAGE_2);
					}

				}

				if (arrow.equals(backArwImgView)) {
					if (aboutImgView.getImage().equals(AboutStage.ABOUT_PAGE_2)) {
						backArwImgView.setVisible(false);
						nxtArwImgView.setVisible(true);
						aboutImgView.setImage(AboutStage.ABOUT_PAGE_1);
					}
				}

				if (arrow.equals(backBtnImgView)) {
					stage.close();
					MenuStage menu = new MenuStage();
					menu.setMenuStage(stage);
				}

			}
		});
	}
}
