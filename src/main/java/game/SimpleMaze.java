package game;

import javax.swing.JFrame;

public class SimpleMaze {

	public static void main(String[] args) {

		// build JFrame for game area
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Simple Maze");
		
		// prepare game panel
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		// run game
		gamePanel.startGameThread();
	}
}
