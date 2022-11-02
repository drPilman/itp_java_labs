/**
 * 
 */
package fractal;

import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * @author drpilman
 *
 */
public class JImageDisplay extends JComponent {

	private BufferedImage image;

	JImageDisplay(int width, int height) {
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(width, height));
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(this.image, 0, 0, image.getWidth(), image.getHeight(), null);
	}

	public void clearImage() {
		this.image.flush();
	}

	public void drawPixel(int x, int y, int rgbColor) {
		this.image.setRGB(x, y, rgbColor);
	}
}
