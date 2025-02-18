package shooter;

import javafx.scene.image.Image;
/***********************************************************
* Class for Shuriken Object which shows the bullet shot by blue.
*
* @author Arieta_Eres
* @created_date 2021-12-20 21:46
*
***********************************************************/
public class Shuriken extends Sprite {
	private final int BULLET_SPEED = 20;
	public final static int BULLET_WIDTH = 30;
	public final static Image BULLET_IMAGE = new Image("images/bullet.gif",Shuriken.BULLET_WIDTH,Shuriken.BULLET_WIDTH,false,false);

	//constructor
	public Shuriken(int x, int y){
		super(x,y);
		this.loadImage(Shuriken.BULLET_IMAGE);
	}

	//method that will move/change the x position of the bullet
	public void move(){
		/*
		 * TODO: Change the x position of the bullet depending on the bullet speed.
		 * 					If the x position has reached the right boundary of the screen,
		 * 						set the bullet's visibility to false.
		 */
		this.x+=BULLET_SPEED;
		if ((this.x+Shuriken.BULLET_WIDTH)>=GameStage.WINDOW_WIDTH) {
			this.setVisible(false);
		}
	}
}