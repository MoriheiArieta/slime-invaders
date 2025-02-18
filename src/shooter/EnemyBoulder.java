package shooter;

import javafx.scene.image.Image;
/***********************************************************
* Class for EnemyBoulder Object which shows the bullets shots by the Boss Slime.
*
* @author Arieta_Eres
* @created_date 2021-12-20 21:46
*
***********************************************************/
public class EnemyBoulder extends Sprite {

	private final int ENEMY_BULLET_SPEED = 5;
	public final static int ENEMY_BULLET_WIDTH = 30;
	public final static Image ENEMY_BULLET_IMAGE = new Image("images/enemy_bullet.png",Shuriken.BULLET_WIDTH,Shuriken.BULLET_WIDTH,false,false);

	//constructor
	public EnemyBoulder(int xPos, int yPos) {
		super(xPos, yPos);
		this.loadImage(EnemyBoulder.ENEMY_BULLET_IMAGE);
	}

	//method that handles the
	public void move() {
		this.x-=ENEMY_BULLET_SPEED;
		if (this.x<=0) {
			this.setVisible(false);
		}
	}
}
