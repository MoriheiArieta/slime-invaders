package shooter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;
/***********************************************************
* Class for Blue(playerObject) which shows the interactions of blue with game objects.
*
* @author Arieta
* @created_date 2021-12-20 21:46
*
***********************************************************/

public class Blue extends Sprite{

	private String name;
	private int strength, counter;
	private boolean alive,shellBuffed, tridentBuffed, buffed, flash, shooting;
	private static int currentStrength;
	private ArrayList<Shuriken> bullets;

	public final static int SHIP_WIDTH = 90;
	public final static int SHIP_HEIGHT = 65;
	public static final int SHIP_MOVEMENT = 10;

	public final static Image BLUE_IMAGE = new Image("images/aqua.png",Blue.SHIP_WIDTH,Blue.SHIP_HEIGHT,false,false);
	public final static Image BLUE_FLASH_IMAGE = new Image("images/aqua_flash.png",Blue.SHIP_WIDTH,Blue.SHIP_HEIGHT,false,false);
	public final static Image BLUE_SHELL_BUFFED_IMAGE = new Image("images/aqua_buffed.png",Blue.SHIP_WIDTH*1.12,Blue.SHIP_HEIGHT*1.12,false,false);
	public final static Image BLUE_SHELL_BUFFED_FLASH_IMAGE = new Image("images/aqua_flash_buffed.png",Blue.SHIP_WIDTH*1.12,Blue.SHIP_HEIGHT*1.12,false,false);
	public final static Image BLUE_TRIDENT_BUFFED_IMAGE = new Image("images/aqua_buffed_dd.png",Blue.SHIP_WIDTH*1.12,Blue.SHIP_HEIGHT*1.12,false,false);
	public final static Image BLUE_TRIDENT_FLASH_BUFFED_IMAGE = new Image("images/aqua_buffed_dd_flash.png",Blue.SHIP_WIDTH*1.12,Blue.SHIP_HEIGHT*1.12,false,false);

	//constructor
	public Blue(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.strength = r.nextInt(150-100)+100;
		this.alive = true;
		this.bullets = new ArrayList<>();
		this.loadImage(Blue.BLUE_IMAGE);
		this.visible=true ;
	}

	//method that will get the bullets 'shot' by blue
	public ArrayList<Shuriken> getBullets(){
		return this.bullets;
	}

	//method called if spacebar is pressed
	protected void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x + this.width+20);
		int y = (int) (this.y + this.height/3);
		/*
		 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
		 */

		if (this.visible) {
			if (this.isAlive()) {
				if (this.shooting) {
					Shuriken bullet = new Shuriken(x,y);
					this.bullets.add(bullet);
				}
			}
		} else {
			if (this.shooting) {
				Shuriken bullet = new Shuriken(x,y);
				this.bullets.add(bullet);
			}
		}
    }


	//method called if up/down/left/right arrow key is pressed.
	protected void move() {
		/*
		 *TODO: 		Only change the x and y position of the ship if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */
		this.x += this.dx;
		this.y += this.dy;

		if ((this.x+Blue.SHIP_WIDTH)>GameStage.WINDOW_WIDTH) {
			this.x=(GameStage.WINDOW_WIDTH-Blue.SHIP_WIDTH);
		}

		if (this.x<0) {
			this.x=0;
		}

		if ((this.y+Blue.SHIP_HEIGHT)>GameStage.WINDOW_HEIGHT) {
			this.y=(GameStage.WINDOW_HEIGHT-Blue.SHIP_HEIGHT);
		}

		if (this.y<0) {
			this.y=0;
		}
	}

	//method to check whenever the Blue is damaged by enemies
	protected void shipDamage(int typeOfDamage, GameTimer timer) {
			this.die();
			if (this.strength>typeOfDamage) {
				this.strength-=typeOfDamage;
				//flash effect
				flash(2);
				Timer mt = new Timer();
				TimerTask task = new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (timer.getStop()) {
							this.cancel();
						} else {
							revive();
						}
					}
				};
				mt.schedule(task, 2000);
			} else {
				timer.setResult(0);
				timer.showResult();
			}
	}

	//method for trident buff
	protected void tridentBuff() {
		this.tridentBuffed=true;
		this.strength=this.strength*2;
		this.flash(2);
		Timer mt = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				tridentBuffed=false;
				strength=strength/2;
			}
		};
		mt.schedule(task, 2000);
	}

	//method for pearl buff
	protected void pearlBuff() {
		this.strength+=50;
	}

	//method for shell buff
	protected void shellBuff() {
		this.shellBuffed=true;
		this.die();
		this.flash(3);
		this.visible=false;
		Timer mt = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				visible = true;
				shellBuffed = false;
				revive();
			}
		};
		mt.schedule(task, 3000);
	}

	//method that will handle flashing effect
	protected void flash(int i) {

		Timer mt = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				counter++;
				if (counter!=(i*4)) {

						if (counter%2==0) {
							flash=true;
							if (shellBuffed) {
								loadImage(Blue.BLUE_SHELL_BUFFED_IMAGE);
							}
							else if (tridentBuffed) {
								loadImage(Blue.BLUE_TRIDENT_BUFFED_IMAGE);
							}
							else {
								loadImage(Blue.BLUE_IMAGE);
							}
						} else {
							flash=false;
							if (shellBuffed) {
								loadImage(Blue.BLUE_SHELL_BUFFED_FLASH_IMAGE);
							}
							else if (tridentBuffed) {
								loadImage(Blue.BLUE_TRIDENT_FLASH_BUFFED_IMAGE);
							}
							else {
								loadImage(Blue.BLUE_FLASH_IMAGE);
							}
						}
				} else {
					counter=0;
					this.cancel();
					loadImage(Blue.BLUE_IMAGE);
				}
			}
		};
		mt.scheduleAtFixedRate(task, 0, 250);
	}

	// method that returns buffed value
	protected boolean isBuffed() {
		if (this.shellBuffed || this.tridentBuffed) {
			this.buffed = true;
		} else {
			this.buffed = false;
		}
		return this.buffed;

	}

	//method that returns flash value
	protected boolean isFlashing() {
		return this.flash;
	}

	//method that will return the value of alive variable
	public boolean isAlive(){
		return this.alive;
	}

	//method that will return blue's name
	public String getName(){
		return this.name;
	}

	//method that will revive blue
	public void revive() {
		this.alive=true;
	}

	//method that will kill blue
	private void die(){
    	this.alive = false;
    }

	//method to get the strength of the ship
	protected int getStrength() {
		return this.strength;
	}

	//method that will overwrite shooting variable
	protected void isShooting() {
		this.shooting=true;
	}

	//method that will ovewrite shooting variable
	protected void isDoneShooting() {
		this.shooting=false;
	}
}














