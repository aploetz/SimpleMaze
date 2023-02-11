package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import characters.Player;
import objects.LevelMap;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {

	final int originalTileSize = 16;
	final int scale = 2;

	// screen settings
	private final int tileSize = originalTileSize * scale;  // 32x32 by default
	private final int maxScreenCol = 16;
	private final int maxScreenRow = 16;
	private final int screenWidth = tileSize * maxScreenCol; // 512x
	private final int screenHeight = tileSize * maxScreenRow; // 512
	
	// Game engine
	private Thread gameThread;
	private TileManager tileMgr;
	private KeyHandler keyHandler = new KeyHandler();
	private LevelMap map = new LevelMap();
	private CollisionChecker collisionChecker;
	private final int fPS = 60; // frames per second
	
	// characters and objects
	private Player player = new Player(this, keyHandler);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
		
		// prepare level map
		map.setFileName("level1.txt");
		map.setStartX(0);
		map.setStartY(14);
		map.setFinishX(15);
		map.setFinishY(15);
		map.setStartDirection("right");
		
		// Player
		player.setDirection(map.getStartDirection());
		player.setScreenX(map.getStartX() * tileSize);
		player.setScreenY(map.getStartY() * tileSize);
		
		// invoke TileManager and CollisionChecker
		this.tileMgr = new TileManager(this, map);
		this.collisionChecker = new CollisionChecker(this);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void stopGame() {
		gameThread = null;
	}
	
	public void run() {
		
		double drawInterval = 1000000000/fPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while (gameThread != null) {
			// testing
			// System.out.println("Game loop is running");
			
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update() {
		
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
    	// draw map
    	tileMgr.drawMap(g2);
    	
    	// draw player on map
    	player.draw(g2);
		
	    g2.dispose();
	}
	
	public TileManager getTileManager() {
		return this.tileMgr;
	}
	
	public CollisionChecker getCollisionChecker() {
		return this.collisionChecker;
	}
	public int getTileSize() {
		return this.tileSize;
	}

	public int getMaxScreenRow() {
		return this.maxScreenRow;
	}

	public int getMaxScreenColumn() {
		return this.maxScreenCol;
	}	
}