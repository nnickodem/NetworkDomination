package GUIs;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUIUtils {

	/**
	 * Scales the given image file to the given width and height
	 * @param imageFile image file path
	 * @param width desired width
	 * @param height desired height
	 * @return resized Image Icon
	 */
	public static ImageIcon scaleImage(final String imageFile, final Integer width, final Integer height) {
		ImageIcon imageIcon = new ImageIcon(imageFile);
		Image image = imageIcon.getImage();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newImage);
		return imageIcon;
	}

	/**
	 * Organizes side component, setting borders, width/height, backgrounds, etc
	 * @param mainPanel main panel the side panel will be located in
	 * @param topPanel top button panel
	 * @param bottomPanel bottom button panel
	 */
	public static void organizeSideComponent(final JPanel mainPanel, final JPanel topPanel, final JPanel bottomPanel) {
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final double screenWidth = screenSize.getWidth();
		final double screenHeight = screenSize.getHeight();
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));

		topPanel.setBorder(BorderFactory.createEtchedBorder());

		bottomPanel.setBorder(BorderFactory.createEtchedBorder());

		containerPanel.add(topPanel);
		containerPanel.add(bottomPanel);
		containerPanel.setBackground(Color.lightGray);
		containerPanel.setBounds((int)screenWidth - 140, 0, 140, (int)screenHeight);
		mainPanel.add(containerPanel);

		//reset container to read the new components added
		mainPanel.revalidate();
	}

	/**
	 * Sets button label formatting and adds it to the button
	 * @param button button
	 * @param label button label
	 */
	public static void createButtonLabel(final JButton button, final JLabel label) {
		label.setFont(new Font("Arial", Font.PLAIN, 18));
		label.setForeground(Color.WHITE);
		label.setAlignmentY(.3f);
		label.setAlignmentX(Label.CENTER_ALIGNMENT);
		button.add(label);
	}

	public static void createCampaignLabel(final JButton button, final JLabel label){
		label.setFont(new Font("Arial", Font.PLAIN, 18));
		label.setForeground(Color.BLACK);
		label.setAlignmentY(.6f);
		label.setAlignmentX(.1f);
		button.add(label);
	}

	/**
	 * Creates a Map of coordinates of each network device.
	 * @param connections List of connections between buttons
	 */
	public static List<Map.Entry<Point, Point>> mapConnections(final List<Map.Entry<JButton, JButton>> connections){
		List<Map.Entry<Point, Point>>lineMap = new ArrayList<>();
		for(Map.Entry<JButton, JButton> connection : connections){
			Point tempA = connection.getKey().getLocation();
			Point tempB = connection.getValue().getLocation();
			tempA = new Point((int)tempA.getX() + 30, (int)tempA.getY() + 30);
			tempB = new Point((int)tempB.getX() + 30, (int)tempB.getY() + 30);
			lineMap.add(new AbstractMap.SimpleEntry<>(tempA, tempB));
		}
		return lineMap;
	}

}
