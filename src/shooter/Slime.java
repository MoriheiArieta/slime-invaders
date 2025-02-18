package shooter;

import java.util.Random;

import javafx.scene.image.Image;
/***********************************************************
* Class for Slime Object which contains slime underling properties.
*
* @author Arieta_Eres
* @created_date 2021-12-20 21:46
*
***********************************************************/
public class Slime extends Sprite {

	private boolean alive;
	//attribute that will determine if a fish will initially move to the right
	private boolean moveRight;
	private int speed;

	public static final int MAX_FISH_SPEED = 5;
	public final static int FISH_WIDTH=50;
	public final static int FISH_DAMAGE=30;

	public final static Image FISH_IMAGE = new Image("images/enemy.png",Slime.FISH_WIDTH,Slime.FISH_WIDTH,false,false);

	//constructor
	Slime(int x, int y){
		super(x,y);
		this.alive = true;
		this.loadImage(Slime.FISH_IMAGE);

		/*
		 *TODO: Randomize speed of fish and moveRight's initial value
		 */
		Random r = new Random();
		this.moveRight = r.nextBoolean();
		this.speed = r.nextInt(Slime.MAX_FISH_SPEED-1)+1;

	}

	//method that changes the x position of the fish
	void move(){
		/*
		 * TODO: 				If moveRight is true and if the fish hasn't reached the right boundary yet,
		 *    						move the fish to the right by changing the x position of the fish depending on its speed
		 *    					else if it has reached the boundary, change the moveRight value / move to the left
		 * 					 Else, if moveRight is false and if the fish hasn't reached the left boundary yet,
		 * 	 						move the fish to the left by changing the x position of the fish depending on its speed.
		 * 						else if it has reached the boundary, change the moveRight value / move to the right
		 */

		if (this.moveRight) {
			this.x+=this.speed;
		}
		if ((this.x+Slime.FISH_WIDTH)>GameStage.WINDOW_WIDTH) {
			this.moveRight=false;
		}
		if (!this.moveRight) {
			this.x-=this.speed;
		}
		if (this.x<0) {
			this.moveRight=true;
		}
	}

	//method that returns alive value
	public boolean isAlive() {
		return this.alive;
	}

	//method that kills slime
	protected void die(){
    	this.alive = false;
    }
}
