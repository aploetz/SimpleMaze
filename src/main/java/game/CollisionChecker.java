package game;

import characters.Player;

public class CollisionChecker {

	private GamePanel gamePanel;
	private int tileSize;
	
	public CollisionChecker(GamePanel gp) {
		this.gamePanel = gp;
		this.tileSize = gp.getTileSize();
	}
	
	public boolean checkTile(Player player) {

		boolean returnVal = false;
		
		int playerLeftX = player.getScreenX() + player.getSolidArea().x;
		int playerRightX = player.getScreenX() + player.getSolidArea().x
			+ player.getSolidArea().width;
		int playerTopY = player.getScreenY() + player.getSolidArea().y;
		int playerBottomY = player.getScreenY() + player.getSolidArea().y
			+ player.getSolidArea().height;
		
		int playerLeftCol = playerLeftX / tileSize;
		int playerRightCol = playerRightX / tileSize;
		int playerTopRow = playerTopY / tileSize;
		int playerBottomRow = playerBottomY / tileSize;
		
		int nextTile1;
		int nextTile2;
		int nextX;
		int nextY;
		
		switch (player.getDirection()) {
			case "up":
				nextY = playerTopY - player.getSpeed();
				playerTopRow = nextY / tileSize;
				
				if (nextY < 0) {
					// don't let them go off of the map
					returnVal = true;
				} else {
					nextTile1 = gamePanel.getTileManager().getMapTileCodes()[playerLeftCol][playerTopRow];
					nextTile2 = gamePanel.getTileManager().getMapTileCodes()[playerRightCol][playerTopRow];
					
					if (gamePanel.getTileManager().getTiles()[nextTile1].isCollision()
							|| gamePanel.getTileManager().getTiles()[nextTile2].isCollision()) {
						returnVal = true;
					}
				}
				break;
			case "down":
				nextY = playerBottomY + player.getSpeed();
				playerBottomRow = nextY / tileSize;

				if (playerBottomRow >= gamePanel.getMaxScreenRow()) {
					// don't let them go off of the map
					returnVal = true;
				} else {
					nextTile1 = gamePanel.getTileManager().getMapTileCodes()[playerLeftCol][playerBottomRow];
					nextTile2 = gamePanel.getTileManager().getMapTileCodes()[playerRightCol][playerBottomRow];
					
					if (gamePanel.getTileManager().getTiles()[nextTile1].isCollision()
						|| gamePanel.getTileManager().getTiles()[nextTile2].isCollision()) {
						returnVal = true;
					}
				}
				break;
			case "left":
				nextX = playerLeftX - player.getSpeed();
				playerLeftCol = nextX / tileSize;
				
				if (nextX < 0) {
					// don't let them go off of the map
					returnVal = true;
				} else {
					nextTile1 = gamePanel.getTileManager().getMapTileCodes()[playerLeftCol][playerTopRow];
					nextTile2 = gamePanel.getTileManager().getMapTileCodes()[playerLeftCol][playerBottomRow];
					
					if (gamePanel.getTileManager().getTiles()[nextTile1].isCollision()
						|| gamePanel.getTileManager().getTiles()[nextTile2].isCollision()) {
						returnVal = true;
					}
				}
				break;
			case "right":
				nextX = playerRightX + player.getSpeed();
				playerRightCol = nextX / tileSize;
				
				if (playerRightCol >= gamePanel.getMaxScreenColumn()) {
					// don't let them go off of the map
					returnVal = true;
				} else {
					nextTile1 = gamePanel.getTileManager().getMapTileCodes()[playerRightCol][playerTopRow];
					nextTile2 = gamePanel.getTileManager().getMapTileCodes()[playerRightCol][playerBottomRow];
					
					if (gamePanel.getTileManager().getTiles()[nextTile1].isCollision()
						|| gamePanel.getTileManager().getTiles()[nextTile2].isCollision()) {
						returnVal = true;
					}
				}
				break;
		}
		
		return returnVal;
	}
}
