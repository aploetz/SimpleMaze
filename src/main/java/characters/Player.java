package characters;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.GraphicsTools;
import game.KeyHandler;

public class Player {

	private BufferedImage up1, down1, left1, right1;
	
	private int tileSize;
	private int screenX;
	private int screenY;
	private int speed = 4;
	
	private String direction;
	private Rectangle solidArea;
	
	private GamePanel gamePanel;
	private KeyHandler keyHandler;
	
	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;
		tileSize = gamePanel.getTileSize();

		// hard-coding player image solid area
		solidArea = new Rectangle(6, 6, 20, 20);
		
		loadPlayerImages();
	}
	
	public void update() {
		
		if (keyHandler.isUpPressed() || keyHandler.isDownPressed()
				|| keyHandler.isRightPressed() || keyHandler.isLeftPressed()) {
			
			if (keyHandler.isUpPressed()) {
				direction = "up";
			} else if (keyHandler.isDownPressed()) {
				direction = "down";
			} else if (keyHandler.isLeftPressed()) {
				direction = "left";
			} else if (keyHandler.isRightPressed()) {
				direction = "right";
			}
			
			// check for collision
			boolean collision = gamePanel.getCollisionChecker().checkTile(this);
			
			if (!collision) {
				// we're sure there are no collisions, so move player in that direction.
				switch (direction) {
				case "up":
					screenY -= speed;
					break;
				case "down":
					screenY += speed;
					break;
				case "left":
					screenX -= speed;
					break;
				case "right":
					screenX += speed;
					break;						
				}
			}
		}
	}

	private void loadPlayerImages() {
		
		up1 = setupPlayerImage("/characters/player_map_up_1.png", tileSize, tileSize);
		down1 = setupPlayerImage("/characters/player_map_down_1.png", tileSize, tileSize);
		right1 = setupPlayerImage("/characters/player_map_right_1.png", tileSize, tileSize);
		left1 = setupPlayerImage("/characters/player_map_left_1.png", tileSize, tileSize);
	}
	
	private BufferedImage setupPlayerImage(String imagePath, int width, int height) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath));
			// scale player tile
			image = GraphicsTools.scaleTile(image, width, height);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return image;
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			image = up1;
			break;
			
		case "down":
			image = down1;
			break;
			
		case "left":
			image = left1;
			break;
			
		case "right":
			image = right1;
			break;
		}
		
		g2.drawImage(image, screenX, screenY, null);
	}

	public void setDirection(String direction) {
		
		this.direction = direction;
	}
	
	public String getDirection() {
		
		return this.direction;
	}
	
	public int getScreenX() {
		
		return this.screenX;
	}
	
	public void setScreenX(int screenX) {
		
		this.screenX = screenX;
	}

	public int getScreenY() {
		
		return this.screenY;
	}
	
	public void setScreenY(int screenY) {
		
		this.screenY = screenY;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public Rectangle getSolidArea() {
		return this.solidArea;
	}
	
	public int getTileX() {
		return this.screenX / tileSize;
	}
	
	public int getTileY() {
		return this.screenY / tileSize;
	}
}
