package shooter;

import java.util.Random;

import javafx.scene.image.Image;
/***********************************************************
* Class for PowerUp Object which contains power up images and type.
*
* @author Arieta_Eres
* @created_date 2021-12-20 21:46
*
***********************************************************/
public class PowerUp  extends Sprite{

	private String type;

	public final static int POWER_UP_SIZE = 50;
	public final static Image PEARL_IMAGE = new Image("images/bubble.png", PowerUp.POWER_UP_SIZE*.8, PowerUp.POWER_UP_SIZE*.8, false, false);
	public final static Image SHELL_IMAGE = new Image("images/shell.png", PowerUp.POWER_UP_SIZE, PowerUp.POWER_UP_SIZE, false, false);
	public final static Image TRIDENT_IMAGE = new Image("images/trident.png", PowerUp.POWER_UP_SIZE, PowerUp.POWER_UP_SIZE, false, false);

	public final static String PEARL_TYPE = "pearl";
	public final static String SHELL_TYPE = "shell";
	public final static String TRIDENT_TYPE = "trident";

	//constructor
	public PowerUp(int xPos, int yPos) {
		super(xPos, yPos);

		//randomize type of powerup
		Random r = new Random();
		int typeNum = r.nextInt(3);
		if (typeNum==0) {
			this.type=PowerUp.PEARL_TYPE;
		}
		if (typeNum==1) {
			this.type=PowerUp.SHELL_TYPE;
		}
		if (typeNum==2) {
			this.type=PowerUp.TRIDENT_TYPE;
		}


		//load image depending on type
		switch (this.type) {
		case PowerUp.PEARL_TYPE:
			this.img = PowerUp.PEARL_IMAGE;
			break;
		case PowerUp.SHELL_TYPE:
			this.img = PowerUp.SHELL_IMAGE;
			break;
		case PowerUp.TRIDENT_TYPE:
			this.img = PowerUp.TRIDENT_IMAGE;
			break;

		}

	}

	//returns power up type
	protected String getType() {
		return this.type;
	}
}
