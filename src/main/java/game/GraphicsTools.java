package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GraphicsTools {

	public static BufferedImage scaleTile(BufferedImage smallImage, int width, int height) {

		BufferedImage scaledImage = new BufferedImage(width, height, smallImage.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(smallImage, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}
	
	public static void drawSubWindow(Graphics2D g2, int swX, int swY, int swWidth, int swHeight) {

		// black with an opaqueness alpha of 200
		Color color = new Color(0, 0, 0, 200);
		g2.setColor(color);
		g2.fillRoundRect(swX, swY, swWidth, swHeight, 35, 35);
		
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(5)); // pixel width
		g2.drawRoundRect(swX + 5, swY + 5, swWidth - 10, swHeight - 10, 25, 25);
	}
	
	public static void displayText(Graphics2D g2, int textX, int textY, String text, int size) {

		// white, Arial, 40pt
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.PLAIN, size));
		g2.drawString(text, textX, textY);
	}
}
