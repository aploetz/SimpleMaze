package tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.GraphicsTools;
import objects.LevelMap;

public class TileManager {

	private Tile[] viewTiles;

	private int tileSize;
	private int maxMapRow;
	private int maxMapCol;
	
	private int mapTileCodes[][];
	
	private LevelMap map;
	
	public TileManager(GamePanel gp, LevelMap map) {

		this.map = map;
		tileSize = gp.getTileSize();
		maxMapRow = gp.getMaxScreenRow();
		maxMapCol = gp.getMaxScreenColumn();
		viewTiles = new Tile[2];
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

		setupViewTile(0, "black.png", true);
		setupViewTile(1, "path.png", false);
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
	
	public int[][] getMapTileCodes() {
		return this.mapTileCodes; 
	}
	
	public Tile[] getTiles() {
		return this.viewTiles;
	}
}
