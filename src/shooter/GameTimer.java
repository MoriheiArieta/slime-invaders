package shooter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/*
* Class for GameTimer Object which is a subclass of the Animation class. It must override the handle method.
*
* @author Arieta_Eres
* @created_date 2021-12-20 21:46
*
*/

public class GameTimer extends AnimationTimer{

	private GraphicsContext gc;
	private Scene theScene;
	private Blue myShip;
	private Group root;
	private Font font;


	//Arrays
	private ArrayList<SlimeBoss> bossFishes;
	private ArrayList<PowerUp> buffs;
	private ArrayList<Slime> slimes;


	//counter variables
	private int gameTimeSecond=0;
	private int gameTimeMinute=1;
	private int score, newFishCounter, bossFishCounter, currentBossHp, result;
	private Text scoreText, shipStrengthText,bossSlimeHpText, countdownTimer;

	//timer variables
	private String ddSecond, ddMinute;
	private DecimalFormat dFormat;
	private GameStage gameStage;
	private boolean stopped;

	//container variables
	private HBox hbox;
	private StackPane strengthPane, cdTimerPane, scorePane, bossHpPane;
	private ImageView strengthImgView, cdTimerImgView, scoreImgView, bossHpImgView;
	private static int MAX_HP;


	//status bar images
	public final static Image CD_TIMER_BG = new Image("images/timer_bg.png", GameStage.WINDOW_WIDTH*.15,38, false, false);
	public final static Image SCORE_BG = new Image("images/sp_bg.png", GameStage.WINDOW_WIDTH*.10,40, false, false);

	//strength bar
	public final static Image FULL_HP_BAR = new Image("images/hp_bar_100.png", GameStage.WINDOW_WIDTH*.25,50, false, false);
	public final static Image NINETY_HP_BAR = new Image("images/hp_bar_90.png", GameStage.WINDOW_WIDTH*.25,50, false, false);
	public final static Image QUARTER_HP_BAR = new Image("images/hp_bar_75.png", GameStage.WINDOW_WIDTH*.25,50, false, false);
	public final static Image HALF_HP_BAR = new Image("images/hp_bar_50.png", GameStage.WINDOW_WIDTH*.25,50, false, false);
	public final static Image FOURTH_HP_BAR = new Image("images/hp_bar_25.png", GameStage.WINDOW_WIDTH*.25,50, false, false);
	public final static Image TENTH_HP_BAR = new Image("images/hp_bar_10.png", GameStage.WINDOW_WIDTH*.25,50, false, false);

	//enemy hp bar
	public final static Image BOSS_FULL_HP_BAR = new Image("images/boss_hp_bar_100.png", GameStage.WINDOW_WIDTH*.25,50, false, false);
	public final static Image BOSS_NINETY_HP_BAR = new Image("images/boss_hp_bar_90.png", GameStage.WINDOW_WIDTH*.25,50, false, false);
	public final static Image BOSS_QUARTER_HP_BAR = new Image("images/boss_hp_bar_75.png", GameStage.WINDOW_WIDTH*.25,50, false, false);
	public final static Image BOSS_HALF_HP_BAR = new Image("images/boss_hp_bar_50.png", GameStage.WINDOW_WIDTH*.25,50, false, false);
	public final static Image BOSS_FOURTH_HP_BAR = new Image("images/boss_hp_bar_25.png", GameStage.WINDOW_WIDTH*.25,50, false, false);
	public final static Image BOSS_TENTH_HP_BAR = new Image("images/boss_hp_bar_10.png", GameStage.WINDOW_WIDTH*.25,50, false, false);


	public static final int INITIAL_NUM_FISHES = 7;
	public static final int NEW_MAX_NUM_FISHES = 3;
	public static final int MAX_NUM_BOSS_FISHES = 1;

	GameTimer(GraphicsContext gc, Scene theScene, Group root, GameStage gameStage){
		this.gc = gc;
		this.theScene = theScene;
		this.gameStage = gameStage;
		this.myShip = new Blue("Blue",150,250);
		this.slimes = new ArrayList<>();
		this.bossFishes = new ArrayList<>();
		this.buffs = new ArrayList<>();
		this.root =root;
		this.scoreText = new Text();
		this.shipStrengthText = new Text();
		this.bossSlimeHpText = new Text();
		this.countdownTimer = new Text();
		this.dFormat = new DecimalFormat("00");
		this.stopped = false;
		this.hbox = new HBox();
		GameTimer.MAX_HP = this.myShip.getStrength();

		//call method to handle mouse click event
		this.handleKeyPressEvent();

		//call the spawnFishes method
		this.spawnSlimes();

		//call spawnTimer method to start timer
		this.timerInit();

		//call spawnTimer method
		this.spawnNewSlime();

		//call spawnBossFish method
		this.spawnBossFish();

		//call spawnBuffs method
		this.spawnBuffs();

		//initiate status bar contents
		this.cdTimerImgView = new ImageView(GameTimer.CD_TIMER_BG);
		this.cdTimerPane = new StackPane();

		this.scoreImgView = new ImageView(GameTimer.SCORE_BG);
		this.scorePane = new StackPane();

		this.strengthImgView = new ImageView();
		this.strengthPane = new StackPane();

		this.bossHpImgView = new ImageView();
		this.bossHpPane = new StackPane();
		this.bossHpPane.setVisible(false);

	}

	@Override
	public void handle(long currentNanoTime) {
		this.hbox.setPrefWidth(GameStage.WINDOW_WIDTH);
		this.hbox.setAlignment(Pos.CENTER);
		this.hbox.setSpacing(20);
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.GAME_WINDOW_HEIGHT);
		this.showBackground();

		//movement/popup calls
		this.myShip.move();
		/*
		 * TODO: Call the moveBullets and moveFishes methods
		 */
		this.moveFishes();
		this.moveBullets();
		this.moveBossFish();
		this.showPUps();

		//render the ship
		this.myShip.render(this.gc);

		/*
		 * TODO: Call the renderFishes and renderBullets methods
		 */
		this.renderFishes();
		this.renderBullets();
		this.renderBossFish();
		this.renderPowerUps();

		//call checkHit
		this.checkHit();

		//status bar
		this.showStatus(this.countdownTimer);
		this.showStatus(this.scoreText);
		this.showStatus(this.shipStrengthText);
		this.showStatus(this.bossSlimeHpText);

		//check if ship collected any buff
		this.checkBuffCollect();



	}

	//strength bar image loader
	private void loadStrengthImg() {
		if (this.myShip.getStrength()<=GameTimer.MAX_HP && this.myShip.getStrength()>GameTimer.MAX_HP*.99) {
			this.strengthImgView.setImage(GameTimer.FULL_HP_BAR);
		}
		if (this.myShip.getStrength()<=GameTimer.MAX_HP*.99 && this.myShip.getStrength()>GameTimer.MAX_HP*.75) {
			this.strengthImgView.setImage(GameTimer.NINETY_HP_BAR);
		}
		if (this.myShip.getStrength()<=GameTimer.MAX_HP*.75 && this.myShip.getStrength()>GameTimer.MAX_HP*.5) {
			this.strengthImgView.setImage(GameTimer.QUARTER_HP_BAR);
		}
		if (this.myShip.getStrength()<=GameTimer.MAX_HP*.5 && this.myShip.getStrength()>GameTimer.MAX_HP*.25) {
			this.strengthImgView.setImage(GameTimer.HALF_HP_BAR);
		}
		if (this.myShip.getStrength()<=GameTimer.MAX_HP*.25 && this.myShip.getStrength()>GameTimer.MAX_HP*.10) {
			this.strengthImgView.setImage(GameTimer.FOURTH_HP_BAR);
		}
		if (this.myShip.getStrength()<=GameTimer.MAX_HP*.10) {
			this.strengthImgView.setImage(GameTimer.TENTH_HP_BAR);
		}
		if (this.myShip.getStrength()>GameTimer.MAX_HP&&!this.myShip.isBuffed()) {
			GameTimer.MAX_HP=this.myShip.getStrength();
			this.strengthImgView.setImage(GameTimer.FULL_HP_BAR);
		}

	}

	//bos hp image loader
	private void loadBossHpImg() {
		if (this.currentBossHp<=SlimeBoss.BOSS_FISH_HP && this.currentBossHp>SlimeBoss.BOSS_FISH_HP*.99) {
			this.bossHpImgView.setImage(GameTimer.BOSS_FULL_HP_BAR);
		}
		if (this.currentBossHp<=SlimeBoss.BOSS_FISH_HP*.99 && this.currentBossHp>SlimeBoss.BOSS_FISH_HP*.75) {
			this.bossHpImgView.setImage(GameTimer.BOSS_NINETY_HP_BAR);
		}
		if (this.currentBossHp<=SlimeBoss.BOSS_FISH_HP*.75 && this.currentBossHp>SlimeBoss.BOSS_FISH_HP*.5) {
			this.bossHpImgView.setImage(GameTimer.BOSS_QUARTER_HP_BAR);
		}
		if (this.currentBossHp<=SlimeBoss.BOSS_FISH_HP*.5 && this.currentBossHp>SlimeBoss.BOSS_FISH_HP*.25) {
			this.bossHpImgView.setImage(GameTimer.BOSS_HALF_HP_BAR);
		}
		if (this.currentBossHp<=SlimeBoss.BOSS_FISH_HP*.25 && this.currentBossHp>SlimeBoss.BOSS_FISH_HP*.10) {
			this.bossHpImgView.setImage(GameTimer.BOSS_FOURTH_HP_BAR);
		}
		if (this.currentBossHp<=SlimeBoss.BOSS_FISH_HP*.10) {
			this.bossHpImgView.setImage(GameTimer.BOSS_TENTH_HP_BAR);
		}
	}

	//method that will show status texts
	private void showStatus(Text text) {
		this.font = Font.loadFont("file:src/font/square_pixel-7.ttf", 26);
		text.setFont(this.font);

		if (text.equals(this.countdownTimer)) {
			if (this.gameTimeMinute==0 && this.gameTimeSecond<=10) {
				text.setFill(Color.RED);
			} else {
				text.setFill(Color.AQUA);
			}
			text.setText(this.showCountDownTimer());
			this.cdTimerPane.getChildren().removeAll(this.cdTimerImgView, text);
			this.cdTimerPane.getChildren().addAll(this.cdTimerImgView, text);
		}


		if (text.equals(this.scoreText)) {
			text.setText("  "+this.score);
			this.scorePane.getChildren().removeAll(this.scoreImgView, text);
			this.scorePane.getChildren().addAll(this.scoreImgView, text);
		}


		if (text.equals(this.shipStrengthText)) {
			text.setText("   "+this.myShip.getStrength()+"/"+GameTimer.MAX_HP);
			this.loadStrengthImg();
			this.strengthPane.getChildren().removeAll(this.strengthImgView, text);
			this.strengthPane.getChildren().addAll(this.strengthImgView, text);
		}


		if (text.equals(this.bossSlimeHpText)) {
			text.setText("   "+this.currentBossHp+"/"+SlimeBoss.BOSS_FISH_HP);
			this.loadBossHpImg();
			this.bossHpPane.getChildren().removeAll(this.bossHpImgView, text);
			this.bossHpPane.getChildren().addAll(this.bossHpImgView, text);
		}

		this.hbox.getChildren().removeAll(this.cdTimerPane, this.scorePane, this.strengthPane, this.bossHpPane);
		this.hbox.getChildren().addAll(this.cdTimerPane, this.scorePane, this.strengthPane, this.bossHpPane);
		this.root.getChildren().remove(this.hbox);
		this.root.getChildren().add(this.hbox);
	}

	//method that will show countdown timer
	private String showCountDownTimer() {
		this.ddSecond = this.dFormat.format(this.gameTimeSecond);
		this.ddMinute = this.dFormat.format(this.gameTimeMinute);
		String timetText = new String(" "+this.ddMinute+":"+this.ddSecond);

		if (this.gameTimeSecond==-1) {
			this.gameTimeSecond = 59;
			this.gameTimeMinute--;
			this.ddSecond = this.dFormat.format(this.gameTimeSecond);
			this.ddMinute = this.dFormat.format(this.gameTimeMinute);
		}
		if (this.gameTimeMinute==0 && this.gameTimeSecond==0) {
			if (this.myShip.getStrength()>0) {
				this.setResult(1);
				this.showResult();
			}

		}
		return timetText;
	}

	//method that will render/draw the slimes to the canvas
	private void renderFishes() {
		for (Slime f : this.slimes){
			f.render(this.gc);
		}
	}

	//method that will render/draw the bullets to the canvas
	private void renderBullets() {
		/*
		 *TODO: Loop through the bullets arraylist of myShip
		 *				and render each bullet to the canvas
		 */
		for (Shuriken bullet : this.myShip.getBullets()) {
			bullet.render(this.gc);
		}

	}

	//method that will render/draw the bossFish to the canvas
	private void renderBossFish() {
		for (SlimeBoss bossFish : this.bossFishes) {
			bossFish.render(this.gc);
		}
	}

	//method that will render/draw the powerups to the canvas
	private void renderPowerUps() {
		for (PowerUp buff : this.buffs) {
			buff.render(this.gc);
		}
	}

	//method that will render/draw the enemy bullets to the canvas
	private void renderEnemyBullets(SlimeBoss boss) {
		for (EnemyBoulder enemyBullets : boss.getEnemyBullets()) {
			enemyBullets.render(this.gc);
		}
	}

	//method that will spawn/instantiate seven slimes at a random x,y location on the right side of the screen
	private void spawnSlimes(){
		Random r = new Random();
		for(int i=0;i<(GameTimer.INITIAL_NUM_FISHES);i++){
			int x = r.nextInt(((GameStage.WINDOW_WIDTH-Slime.FISH_WIDTH)/2))+((GameStage.WINDOW_WIDTH-Slime.FISH_WIDTH)/2);
			int y = r.nextInt(GameStage.WINDOW_HEIGHT-Slime.FISH_WIDTH);
			/*
			 *TODO: Add a new object Slime to the slimes arraylist
			 */
			Slime fish = new Slime(x, y);
			this.slimes.add(fish);
		}
	}

	//method that will show powerups
	private void showPUps() {
		for (int i = 0; i < this.buffs.size(); i++) {
			if (!this.buffs.get(i).isVisible()) {
				this.buffs.remove(i);
			}
		}
	}

	//method that will move the bullets shot by blue
	private void moveBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Shuriken> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Shuriken b = bList.get(i);
			/*
			 * TODO:  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.
			 */
			if (b.isVisible()) {
				b.move();
			} else {
				bList.remove(b);
			}
		}
	}

	//method that will move the enemy bullets shot by the boss slime
	private void moveEnemyBullets(SlimeBoss boss) {
		ArrayList<EnemyBoulder> eBList = boss.getEnemyBullets();
		for (int i = 0; i < eBList.size(); i++) {
			EnemyBoulder eB = eBList.get(i);
			if (eB.isVisible()) {
				eB.move();
			} else {
				eBList.remove(eB);
			}
		}
	}

	//method that will move the slimes
	private void moveFishes(){
		//Loop through the slimes arraylist
		for(int i = 0; i < this.slimes.size(); i++){
			Slime f = this.slimes.get(i);
			/*
			 * TODO:  *If a fish is alive, move the fish. Else, remove the fish from the slimes arraylist.
			 */
			if (f.isAlive()) {
				f.move();
			} else {
				this.slimes.remove(f);
			}
		}
	}

	//method that will move the boss fish
	private void moveBossFish() {
		for (int i = 0; i < this.bossFishes.size(); i++) {
			SlimeBoss bossFish = this.bossFishes.get(i);
			if (bossFish.isAlive()) {
				if (bossFish.isVisible()) {
					this.currentBossHp=bossFish.getHP();
					if (this.currentBossHp<(SlimeBoss.BOSS_FISH_HP/2)) {
						bossFish.isAngry();
						bossFish.enragedMovement();

					} else {
						bossFish.move();
					}
					this.bossHpPane.setVisible(true);
					this.moveEnemyBullets(bossFish);
					this.renderEnemyBullets(bossFish);
					this.checkEnemyHit(bossFish);
				}
			} else {
				this.score+=5;
				this.currentBossHp=0;
				this.bossHpPane.setVisible(false);
				this.bossFishes.remove(bossFish);
			}
		}
	}

	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyShip(code);
			}
		});


		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            @Override
					public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMyShip(code);
		                myShip.isShooting();
		            }
		        });
    }

	//method that will move the ship depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.UP) {
			this.myShip.setDY(-Blue.SHIP_MOVEMENT);
		}

		if(ke==KeyCode.LEFT) {
			this.myShip.setDX(-Blue.SHIP_MOVEMENT);
		}

		if(ke==KeyCode.DOWN) {
			this.myShip.setDY(Blue.SHIP_MOVEMENT);
		}

		if(ke==KeyCode.RIGHT) {
			this.myShip.setDX(Blue.SHIP_MOVEMENT);
		}

		if(ke==KeyCode.SPACE) {
			this.myShip.shoot();
			this.myShip.isDoneShooting();
		}

		System.out.println(ke+" key pressed.");
   	}

	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMyShip(KeyCode ke){
		this.myShip.setDX(0);
		this.myShip.setDY(0);
	}

	//method that will check if a bullet hits an enemy
	private void checkHit() {
		ArrayList<Shuriken> bList = this.myShip.getBullets();
		for (Shuriken element : bList) {
			for (Slime element2 : this.slimes) {
				if (element.collidesWith(element2)) {
					if (element2.isAlive()) {
						element.setVisible(false);
						element2.die();
						this.score++;
					}
				}
			}
		}
		this.checkBossHit();
		this.checkShipHit(this.slimes);
		this.checkShipHit(this.bossFishes);
	}

	//method that will check for bullet and bossFish collission
	private void checkBossHit() {
		ArrayList<Shuriken> bList = this.myShip.getBullets();
		for (Shuriken element : bList) {
			for (SlimeBoss element2 : this.bossFishes) {
				if (element.collidesWith(element2)) {
					element.setVisible(false);
					element2.damage(this.myShip.getStrength());
				}
			}
		}
	}

	//check if enemy bullet collides with ship
	private void checkEnemyHit(SlimeBoss boss) {
		ArrayList<EnemyBoulder> eBList = boss.getEnemyBullets();
		for (EnemyBoulder element : eBList) {
			if (element.collidesWith(this.myShip)) {
				if (this.myShip.isAlive()) {
					element.setVisible(false);
					this.myShip.shipDamage(SlimeBoss.BOSS_FISH_DAMAGE, this);
				}

			}
		}
	}

	//method to check if Blue collects a buff
	private void checkBuffCollect() {
		for (PowerUp element : this.buffs) {
			if (this.myShip.collidesWith(element)) {
				if (this.myShip.isAlive()) {

					if (element.getType()==PowerUp.PEARL_TYPE) {
						element.setVisible(false);
						this.myShip.pearlBuff();
					}

					if (element.getType()==PowerUp.SHELL_TYPE) {
						element.setVisible(false);
						this.myShip.shellBuff();
					}

					if (element.getType()==PowerUp.TRIDENT_TYPE) {
						element.setVisible(false);
						this.myShip.tridentBuff();
					}
				}
			}
		}
	}

	//method that will check if an enemy hits blue
	private void checkShipHit(ArrayList array) {

			if (array.equals(this.slimes)) {
				for (int i = 0; i < array.size(); i++) {
					if (this.slimes.get(i).collidesWith(this.myShip)) {
						if (this.slimes.get(i).isAlive()) {
							if (this.myShip.isAlive()) {
								this.slimes.get(i).die();
								this.myShip.shipDamage(Slime.FISH_DAMAGE, this);
							}
						}
					}
				}
			}

			else if (array.equals(this.bossFishes)) {
				for (int j = 0; j < array.size(); j++) {
					if (this.bossFishes.get(j).collidesWith(this.myShip)) {
						if (this.myShip.isAlive()) {
							this.myShip.shipDamage(SlimeBoss.BOSS_FISH_DAMAGE, this);
						}
					}
				}

			}
	}

	//method for countdown timer
	private void timerInit() {
		Timer mt = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (stopped) {
					this.cancel();
				} else {
					gameTimeSecond--;
				}
			}
		};
		mt.scheduleAtFixedRate(task, 1000, 1000);
	}


	//method that will spawn/instantiate three slimes at a random x,y location of the screen at a 5-second interval;
	private void spawnNewSlime() {
		ArrayList<Slime> fList = this.slimes;
		Random r = new Random();
		Timer mt =new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (stopped) {
					this.cancel();
				} else {
					if (newFishCounter!=GameTimer.NEW_MAX_NUM_FISHES) {
						newFishCounter++;
						int x = r.nextInt(GameStage.WINDOW_WIDTH-Slime.FISH_WIDTH);
						int y = r.nextInt(GameStage.WINDOW_HEIGHT-Slime.FISH_WIDTH);
						Slime fish = new Slime(x, y);
						fList.add(fish);
					}
				}
			}
		};

		mt.scheduleAtFixedRate(task, 5000, 5000);
		this.slimes.addAll(fList);
	}

	//method that spawns boss after 30 seconds
	private void spawnBossFish(){
		this.bossSlimeHpText.setVisible(false);
		ArrayList<SlimeBoss> bfList = this.bossFishes;
		Random r = new Random();
		Timer mt = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (stopped) {
					this.cancel();
				} else {
					if (bossFishCounter!=GameTimer.MAX_NUM_BOSS_FISHES) {
						bossFishCounter++;
						int x = r.nextInt(((GameStage.WINDOW_WIDTH-SlimeBoss.BOSS_FISH_WIDTH)/2))+((GameStage.WINDOW_WIDTH-SlimeBoss.BOSS_FISH_WIDTH)/2);
						int y = r.nextInt(GameStage.WINDOW_HEIGHT-SlimeBoss.BOSS_FISH_WIDTH);

						SlimeBoss bossFish = new SlimeBoss(x, y);
						bossFish.setVisible(true);
						bfList.add(bossFish);

						bossSlimeHpText.setVisible(true);
					}
				}
			}
		};
		mt.schedule(task, 30000);
		this.bossFishes.addAll(bfList);
	}

	//method that spawns buff every 10 seconds
	private void spawnBuffs() {
		ArrayList<PowerUp> powerUpList = this.buffs;
		Random r = new Random();
		Timer mt = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (stopped) {
					this.cancel();
				} else {
					int x = r.nextInt((GameStage.WINDOW_WIDTH/2)-PowerUp.POWER_UP_SIZE);
					int y = r.nextInt(GameStage.WINDOW_HEIGHT-PowerUp.POWER_UP_SIZE);

					PowerUp buff = new PowerUp(x, y);
					powerUpList.add(buff);
					pUpTimer(buff);
				}
			}
		};
		mt.scheduleAtFixedRate(task, 10000, 10000);
		this.buffs.addAll(powerUpList);
	}

	//method that lets powerups stay for 5 seconds if uncollected
	private void pUpTimer(PowerUp buff) {
		Timer mt = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (stopped) {
					this.cancel();
				} else {
					buff.setVisible(false);
				}
			}
		};
		mt.schedule(task, 5000);
	}


	//method that stops all timers in this class
	protected boolean getStop() {
		return this.stopped;
	}


	//method that sets the result of the game
	protected void setResult(int result) {
		this.result = result;
	}

	//method that shows the gameover stage
	protected void showResult() {
		this.gameStage.setFinalScore(this.score);
		this.stopThis();
		this.gameStage.flashGameOver(this.result);
	}

	//changes background image
	private void showBackground() {
		if (this.myShip.isAlive()) {
			this.gc.drawImage(GameStage.BACKGROUND, 0, 0);
		}
		if (!this.myShip.isAlive() && !this.myShip.isBuffed() && this.myShip.isFlashing()) {
			this.gc.drawImage(GameStage.DAMAGED_BACKGROUND, 0, 0);
		}
		if (!this.myShip.isAlive() && !this.myShip.isBuffed() && !this.myShip.isFlashing()) {
			this.gc.drawImage(GameStage.DAMAGED_FLASHED_BACKGROUND, 0, 0);
		}
		if (this.myShip.isBuffed()) {
			this.gc.drawImage(GameStage.BUFFED_BACKGROUND, 0, 0);
		}
	}

	//stops this timer
	protected void stopThis() {
		this.stopped=true;
		this.stop();
	}

}
