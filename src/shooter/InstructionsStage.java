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
* Class for InstructionStage Object which shows how to play the game and simple mechanics,controls and profiles.
*
* @author Arieta_Eres
* @created_date 2021-12-20 21:46
*
***********************************************************/
public class InstructionsStage {
	private Scene instScene;
	private Stage stage;
	private Group instRoot;
	private HBox arrowBox;
	private ImageView instImgView, nxtArwImgView, backArwImgView, backBtnImgView;

	public final static Image INST_PAGE_1 = new Image("images/instructions_page1.png", GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, false, false);
	public final static Image INST_PAGE_2 = new Image("images/instructions_page2.png", GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, false, false);
	public final static Image INST_PAGE_3 = new Image("images/instructions_page3.png", GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, false, false);
	public final static Image INST_PAGE_4 = new Image("images/instructions_page4.png", GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, false, false);
	public final static Image NEXT_ARROW = new Image("images/next_arrow.png", 60, 60, false, false);
	public final static Image BACK_ARROW = new Image("images/back_arrow.png", 60, 60, false, false);
	public final static Image BACK_BTN_IMAGE = new Image("images/x_button.png", 45, 45, false, false);

	//constructor
	InstructionsStage() {
		this.instRoot = new Group();
		this.instScene = new Scene(instRoot, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.instImgView = new ImageView();
		this.arrowBox = new HBox();
		this.nxtArwImgView = new ImageView();
		this.backArwImgView = new ImageView();
		this.backBtnImgView = new ImageView();
	}

	//sets instructions scene to stage
	public void setInstStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle(MenuStage.GAME_TITLE);
		this.instImgView.setImage(InstructionsStage.INST_PAGE_1);

		this.nxtArwImgView.setImage(InstructionsStage.NEXT_ARROW);
		this.backArwImgView.setImage(InstructionsStage.BACK_ARROW);
		this.backBtnImgView.setImage(InstructionsStage.BACK_BTN_IMAGE);


		this.backBtnImgView.setLayoutX(16);
		this.backBtnImgView.setLayoutY(16);
		this.backArwImgView.setVisible(false);

		this.arrowBox.setAlignment(Pos.CENTER);
		this.arrowBox.setLayoutY(GameStage.WINDOW_HEIGHT-65);
		this.arrowBox.setPadding(new Insets(0,0,5,0));
		this.arrowBox.setSpacing(650);
		this.arrowBox.setPrefWidth(GameStage.WINDOW_WIDTH);

		this.addEventHandler(this.nxtArwImgView);
		this.addEventHandler(this.backArwImgView);
		this.addEventHandler(this.backBtnImgView);

		this.arrowBox.getChildren().addAll(this.backArwImgView, this.nxtArwImgView);

		this.instRoot.getChildren().addAll(instImgView, this.arrowBox, this.backBtnImgView);
		this.stage.setScene(this.instScene);
		this.stage.show();
	}


	//adds event handler to button imageviews
	private void addEventHandler(ImageView arrow) {
		arrow.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

				if (arrow.equals(nxtArwImgView)) {
					if (instImgView.getImage().equals(InstructionsStage.INST_PAGE_1)) {
						backArwImgView.setVisible(true);
						nxtArwImgView.setVisible(true);
						instImgView.setImage(InstructionsStage.INST_PAGE_2);
					}
					else if(instImgView.getImage().equals(InstructionsStage.INST_PAGE_2)) {
						backArwImgView.setVisible(true);
						nxtArwImgView.setVisible(true);
						instImgView.setImage(InstructionsStage.INST_PAGE_3);
					}
					else if (instImgView.getImage().equals(InstructionsStage.INST_PAGE_3)) {
						backArwImgView.setVisible(true);
						nxtArwImgView.setVisible(false);
						instImgView.setImage(InstructionsStage.INST_PAGE_4);
					}
				}

				if (arrow.equals(backArwImgView)) {
					if (instImgView.getImage().equals(InstructionsStage.INST_PAGE_4)) {
						backArwImgView.setVisible(true);
						nxtArwImgView.setVisible(true);
						instImgView.setImage(InstructionsStage.INST_PAGE_3);
					}
					else if (instImgView.getImage().equals(InstructionsStage.INST_PAGE_3)) {
						backArwImgView.setVisible(true);
						nxtArwImgView.setVisible(true);
						instImgView.setImage(InstructionsStage.INST_PAGE_2);
					}
					else if (instImgView.getImage().equals(InstructionsStage.INST_PAGE_2)) {
						backArwImgView.setVisible(false);
						nxtArwImgView.setVisible(true);
						instImgView.setImage(InstructionsStage.INST_PAGE_1);
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
