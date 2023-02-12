package tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.GraphicsTools;

public class TileManager {

	private Tile[] viewTiles;

	private int tileSize;
	private int maxMapRow;
	private int maxMapCol;
	private int screenWidth;
	
	private int mapTileCodes[][];
	
	private LevelMap map;
	
	public TileManager(GamePanel gp, LevelMap map) {

		this.map = map;
		tileSize = gp.getTileSize();
		maxMapRow = gp.getMaxScreenRow();
		maxMapCol = gp.getMaxScreenColumn();
		screenWidth = gp.getScreenWidth();
		viewTiles = new Tile[3];
		mapTileCodes = new int[maxMapCol][maxMapRow];
		
		// initialize map to blank tiles
		for (int counterX = 0; counterX < maxMapCol; counterX++) {
			for (int counterY = 0; counterY < maxMapRow; counterY++) {
				mapTileCodes[counterX][counterY] = 0;
			}
		}
		
		loadTileImages();
		loadDungeonMap(this.map.getFileName());
	}
	
	private void loadDungeonMap(String mapFile) {

		try {
			
			InputStream inputStream = getClass().getResourceAsStream("/maps/" + mapFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			
			int intX = 0;
			int intY = 0;
			
			while (intX < maxMapCol && intY < maxMapRow) {
				
				String inputLine = br.readLine();
				
				while (intX < maxMapCol) {
					String tileCodes[] = inputLine.split(" ");
					
					int tileCode = Integer.parseInt(tileCodes[intX]);
					mapTileCodes[intX][intY] = tileCode;
					intX++;
				}
				
				if (intX >= maxMapCol) {
					intX = 0;
					intY++;
				}
			}
			
			br.close();
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadTileImages() {

		setupViewTile(0, "wall.png", true);
		setupViewTile(1, "path.png", false);
		setupViewTile(2, "finish.png", false);
	}
	
	private void setupViewTile(int tileIndex, String tileFileName, boolean collision) {
		
		try {
			BufferedImage scaledImage = ImageIO.read(getClass().getResourceAsStream("/tiles/" + tileFileName));
			scaledImage = GraphicsTools.scaleTile(scaledImage, tileSize, tileSize);
			viewTiles[tileIndex] = new Tile();
			viewTiles[tileIndex].setImage(scaledImage);
			viewTiles[tileIndex].setCollision(collision);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void drawMap(Graphics2D g2) {
		
		for (int counterX = 0; counterX < maxMapCol; counterX++) {

			int screenX = counterX * tileSize;
			
			for (int counterY = 0; counterY < maxMapRow; counterY++) {
				
				int screenY = counterY * tileSize;
				int tileNum = mapTileCodes[counterX][counterY];
				g2.drawImage(viewTiles[tileNum].getImage(), screenX, screenY, null);
			}
		}		
	}
	
	public void drawFinish(Graphics2D g2, double time) {
		
		int windowX = tileSize * 2;
		int windowY = tileSize / 2;
		int windowWidth = screenWidth - (tileSize * 4);
		int windowHeight = tileSize * 5;	
		DecimalFormat dFormat = new DecimalFormat("0.00");
		
		// window
		GraphicsTools.drawSubWindow(g2, windowX, windowY, windowWidth, windowHeight);
		// text
		int textX = windowX + (tileSize * 3);
		GraphicsTools.displayText(g2, textX, windowY + (tileSize * 2), "Finished", 50);
		GraphicsTools.displayText(g2, textX, windowY + (tileSize * 4),
				"in " + dFormat.format(time) + " seconds!", 30);
	}
	
	public int[][] getMapTileCodes() {
		return this.mapTileCodes; 
	}
	
	public Tile[] getTiles() {
		return this.viewTiles;
	}
}
