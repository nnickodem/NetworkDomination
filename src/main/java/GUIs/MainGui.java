package GUIs;

import Objects.GameLevel;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;

public class MainGui extends JFrame {

	private MainMenu mainMenu;
	private LevelGUI levelGUI;
	private GameLevel level;

	public MainGui(){
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Network Domination");
		//setBorder(BorderFactory.createEmptyBorder());
		setBackground(Color.DARK_GRAY);
	}

	public void createMainMenu(){
		mainMenu = new MainMenu();
		add(new MainMenu());
	}

	public void createLevel(){
		//add(new LevelGui());
	}

	public void createCampaignScreen(){
		add(new CampaignGUI());
	}
}
