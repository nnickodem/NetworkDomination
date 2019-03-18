package GUIs;

import javax.swing.ImageIcon;
import java.awt.Image;

public class GUIUtils {

	/**
	 * Scales the image files that are loaded into the proper game image size wanted
	 * @param imageFile
	 * @return imageIcon
	 */
	public static ImageIcon scaleImage(final String imageFile, final Integer width, final Integer height){
		ImageIcon imageIcon = new ImageIcon(imageFile);
		Image image = imageIcon.getImage();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newImage);
		return imageIcon;
	}

}
