package game;

import characters.Player;

public class CollisionChecker {

	private GamePanel gamePanel;
	private int tileSize;
	
	public CollisionChecker(GamePanel gp) {
		this.gamePanel = gp;
		this.tileSize = gp.getTileSize();
	}
	
	public boolean checkTile(Player entity) {

		boolean returnVal = false;
		
		int entityLeftX = entity.getScreenX() + entity.getSolidArea().x;
		int entityRightX = entity.getScreenX() + entity.getSolidArea().x
			+ entity.getSolidArea().width;
		int entityTopY = entity.getScreenY() + entity.getSolidArea().y;
		int entityBottomY = entity.getScreenY() + entity.getSolidArea().y
			+ entity.getSolidArea().height;
		
		int entityLeftCol = entityLeftX / tileSize;
		int entityRightCol = entityRightX / tileSize;
		int entityTopRow = entityTopY / tileSize;
		int entityBottomRow = entityBottomY / tileSize;
		
		int nextTile1;
		int nextTile2;
		int nextX;
		int nextY;
		
		switch (entity.getDirection()) {
			case "up":
				nextY = entityTopY - entity.getSpeed();
				entityTopRow = nextY / tileSize;
				
				if (nextY < 0) {
					// don't let them go off of the map
					returnVal = true;
				} else {
					nextTile1 = gamePanel.getTileManager().getMapTileCodes()[entityLeftCol][entityTopRow];
					nextTile2 = gamePanel.getTileManager().getMapTileCodes()[entityRightCol][entityTopRow];
					
					if (gamePanel.getTileManager().getTiles()[nextTile1].isCollision()
							|| gamePanel.getTileManager().getTiles()[nextTile2].isCollision()) {
						returnVal = true;
					}
					break;
				}
			case "down":
				nextY = entityBottomY + entity.getSpeed();
				entityBottomRow = nextY / tileSize;

				if (entityBottomRow >= gamePanel.getMaxScreenRow()) {
					// don't let them go off of the map
					returnVal = true;
				} else {
					nextTile1 = gamePanel.getTileManager().getMapTileCodes()[entityLeftCol][entityBottomRow];
					nextTile2 = gamePanel.getTileManager().getMapTileCodes()[entityRightCol][entityBottomRow];
					
					if (gamePanel.getTileManager().getTiles()[nextTile1].isCollision()
						|| gamePanel.getTileManager().getTiles()[nextTile2].isCollision()) {
						returnVal = true;
					}
				}
				break;
			case "left":
				nextX = entityLeftX - entity.getSpeed();
				entityLeftCol = nextX / tileSize;
				
				if (nextX < 0) {
					// don't let them go off of the map
					returnVal = true;
				} else {
					nextTile1 = gamePanel.getTileManager().getMapTileCodes()[entityLeftCol][entityTopRow];
					nextTile2 = gamePanel.getTileManager().getMapTileCodes()[entityLeftCol][entityBottomRow];
					
					if (gamePanel.getTileManager().getTiles()[nextTile1].isCollision()
						|| gamePanel.getTileManager().getTiles()[nextTile2].isCollision()) {
						returnVal = true;
					}
				}
				break;
			case "right":
				nextX = entityRightX + entity.getSpeed();
				entityRightCol = nextX / tileSize;
				
				if (entityRightCol >= gamePanel.getMaxScreenColumn()) {
					// don't let them go off of the map
					returnVal = true;
				} else {
					nextTile1 = gamePanel.getTileManager().getMapTileCodes()[entityRightCol][entityTopRow];
					nextTile2 = gamePanel.getTileManager().getMapTileCodes()[entityRightCol][entityBottomRow];
					
					if (gamePanel.getTileManager().getTiles()[nextTile1].isCollision()
						|| gamePanel.getTileManager().getTiles()[nextTile2].isCollision()) {
						returnVal = true;
					}
					break;
				}
		}
		
		return returnVal;
	}
}
