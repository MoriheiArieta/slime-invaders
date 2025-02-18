package shooter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;
/***********************************************************
* Class for SlimeBoss Object which contains Slime Boss properties.
*
* @author Arieta_Eres
* @created_date 2021-12-20 21:46
*
***********************************************************/
public class SlimeBoss extends Sprite{

	private boolean alive, angry;
	private int speed, hp, flashCounter;
	private ArrayList<EnemyBoulder> enemyBullets;
	//attribute that will determine if a fish will initially move up/right
	private boolean moveUp, moveRight;

	public final static int BOSS_FISH_WIDTH=120;
	public final static int BOSS_FISH_DAMAGE=50;
	public final static int BOSS_FISH_HP=3000;

	public final static Image BOSS_FISH_IMAGE = new Image("images/boss.png",SlimeBoss.BOSS_FISH_WIDTH,SlimeBoss.BOSS_FISH_WIDTH,false,false);
	public final static Image ANGRY_BOSS_FISH_IMAGE = new Image("images/angry_boss.png",SlimeBoss.BOSS_FISH_WIDTH,SlimeBoss.BOSS_FISH_WIDTH,false,false);
	public final static Image ANGRY_BOSS_FLASH_FISH_IMAGE = new Image("images/angry_flash_boss.png",SlimeBoss.BOSS_FISH_WIDTH,SlimeBoss.BOSS_FISH_WIDTH,false,false);

	//constructor
	SlimeBoss(int x, int y) {
		super(x, y);
		this.alive = true;
		this.angry = false;

		this.showBossImage();
		Random r = new Random();
		this.moveRight = r.nextBoolean();
		this.moveUp = r.nextBoolean();
		this.speed = r.nextInt(Slime.MAX_FISH_SPEED-1)+1;
		this.visible = false;
		this.enemyBullets = new ArrayList<>();
		this.shootEnemyBullets();
		this.hp = SlimeBoss.BOSS_FISH_HP;
	}

	//method that will move this sprite
	void move(){
		//vertical movement
		if (this.moveUp) {
			this.y+=this.speed;
		}
		if ((this.y+SlimeBoss.BOSS_FISH_WIDTH)>GameStage.WINDOW_HEIGHT) {
			this.moveUp=false;
		}
		if (!this.moveUp) {
			this.y-=this.speed;
		}
		if (this.y<0) {
			this.moveUp=true;
		}
	}

	void enragedMovement() {
		//horizontal movement
		if (this.moveRight) {
			this.x+=this.speed;
		}
		if ((this.x+SlimeBoss.BOSS_FISH_WIDTH)>GameStage.WINDOW_WIDTH) {
			this.moveRight=false;
		}
		if (!this.moveRight) {
			this.x-=this.speed;
		}
		if (this.x<0) {
			this.moveRight=true;
		}

		//vertical movement
		if (this.moveUp) {
			this.y+=this.speed;
		}
		if ((this.y+SlimeBoss.BOSS_FISH_WIDTH)>GameStage.WINDOW_HEIGHT) {
			this.moveUp=false;
		}
		if (!this.moveUp) {
			this.y-=this.speed;
		}
		if (this.y<0) {
			this.moveUp=true;
		}
	}

	//method that will get the bullets shot by the enemy
	public ArrayList<EnemyBoulder> getEnemyBullets() {
		return this.enemyBullets;
	}

	//method that will handle enemy bullet movement
	protected void shootEnemyBullets() {
		if (this.isAlive()) {
			this.initEnemyBullets();
		}
	}

	//method that will instantiate enemy bullets
	private void initEnemyBullets() {
		Timer mt = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (isAlive()) {
					int xPos = (int) (x - width+SlimeBoss.BOSS_FISH_WIDTH);
					int yPos = (int) (y + height/3);
					EnemyBoulder bullet = new EnemyBoulder(xPos, yPos);
					enemyBullets.add(bullet);
				} else {
					this.cancel();
				}

			}
		};
		mt.scheduleAtFixedRate(task, 0, 1000);
	}

	//method that will handle damage whenever this is hit by a bullet
	protected void damage(int playerStrength) {
		this.hp-=playerStrength;
		if (this.hp<=0) {
			this.die();
		}
	}

	//method that will render the flashing effect of the slimeboss
	protected void showBossImage() {
		Timer mt = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (!angry) {
					loadImage(SlimeBoss.BOSS_FISH_IMAGE);
				} else {
					flashCounter++;
					if (isAlive()) {
						if (flashCounter%2==0) {
							loadImage(SlimeBoss.ANGRY_BOSS_FISH_IMAGE);
						} else {
							loadImage(SlimeBoss.ANGRY_BOSS_FLASH_FISH_IMAGE);
						}
					} else {
						mt.cancel();
						this.cancel();
					}
				}
			}
		}; mt.scheduleAtFixedRate(task, 0, 500);
	}



	//method that will return boolean value of alive attribute
	public boolean isAlive() {
		return this.alive;
	}

	//method to get the hp of this object
	public int getHP() {
		return this.hp;
	}

	//overwrites angry attribute
	protected void isAngry() {
		this.angry=true;
	}

	//method that will kill this boss
	private void die(){
    	this.alive = false;
    }
}
