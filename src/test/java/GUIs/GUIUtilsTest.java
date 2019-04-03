package GUIs;

import org.junit.Test;

import javax.swing.ImageIcon;

import static GUIs.GUIUtils.scaleImage;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GUIUtilsTest {

	@Test
	public void testScaleImage() {
		final int width = 30;
		final int height = 30;
		final String path = "resources/objects/NetworkDevices/pc/pcYellow.png";
		ImageIcon image = new ImageIcon(path);
		assertThat(image.getIconWidth(),not(equalTo(width)));
		assertThat(image.getIconHeight(), not(equalTo(height)));

		image = scaleImage(path, width, height);

		assertEquals(image.getIconWidth(), width);
		assertEquals(image.getIconHeight(), height);
	}
}
