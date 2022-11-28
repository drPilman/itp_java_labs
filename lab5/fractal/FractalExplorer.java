package fractal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

public class FractalExplorer {
	private int size;
	private JImageDisplay jImageDisplay;
	private FractalGenerator fractalGenerator;
	private Rectangle2D.Double range;

	FractalExplorer(int size, FractalGenerator fractalGenerator) {
		this.size = size;
		this.fractalGenerator = fractalGenerator;
		range = new Rectangle2D.Double();
		fractalGenerator.getInitialRange(range);
	}

	private void createAndShowGUI() {
		JFrame frame = new JFrame("FractalExplorer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();

		contentPane.setLayout(new BorderLayout());

		var jComboBox = new JComboBox<FractalGenerator>();
		jComboBox.addItem(new Mandelbrot());
		jComboBox.addItem(new Tricorn());
		jComboBox.addItem(new Ship());

		var jFileChooser = new JFileChooser();
		var filter = new FileNameExtensionFilter("PNG Images", "png");
		jFileChooser.setFileFilter(filter);
		jFileChooser.setAcceptAllFileFilterUsed(false);

		class ResetListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (Button.valueOf(e.getActionCommand())) {
				case CHOOSE:
					fractalGenerator = (FractalGenerator) jComboBox.getSelectedItem();
				case RESET:
					jImageDisplay.clearImage();
					jImageDisplay.repaint();
					fractalGenerator.getInitialRange(range);
					drawFractal();
					break;
				case SAVE:
					String errorMsg=null;
					try {
						if (jFileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
							var file = jFileChooser.getSelectedFile();
							if (!file.exists()) file.createNewFile();
							if (file.canWrite())
								ImageIO.write(jImageDisplay.getImage(), "png", file);
							else {
								errorMsg="Cannot write to this path";
							}
						}
					} catch (Exception e1) {
						errorMsg=e1.getMessage();
					} finally {
						if (errorMsg!=null)
							JOptionPane.showMessageDialog(frame, errorMsg, "Cannot Save Image",
									JOptionPane.ERROR_MESSAGE);
					}

				}

			}

		}
		var resetListener = new ResetListener();

		jComboBox.addActionListener(resetListener);
		jComboBox.setActionCommand(Button.CHOOSE.toString());
		JPanel jPanel = new JPanel();

		jPanel.add(new JLabel("Fractal:"));
		jPanel.add(jComboBox);

		contentPane.add(jPanel, BorderLayout.NORTH);

		class ImageMouseListener implements MouseListener {
			@Override
			public void mouseClicked(MouseEvent e) {
				jImageDisplay.clearImage();
				jImageDisplay.repaint();
				double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, size, e.getX());
				double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, size, e.getY());
				fractalGenerator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
				drawFractal();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		}

		jImageDisplay = new JImageDisplay(size, size);
		jImageDisplay.addMouseListener(new ImageMouseListener());
		contentPane.add(jImageDisplay, BorderLayout.CENTER);

		JButton resetButton = new JButton("Reset");

		resetButton.addActionListener(resetListener);
		resetButton.setActionCommand(Button.RESET.toString());

		JButton saveButton = new JButton("Save");

		saveButton.addActionListener(resetListener);
		saveButton.setActionCommand(Button.SAVE.toString());

		var jPanelS = new JPanel();
		jPanelS.add(saveButton);
		jPanelS.add(resetButton);

		contentPane.add(jPanelS, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public static void main(String[] args) {
		FractalExplorer fractalExplorer = new FractalExplorer(800, new Mandelbrot());
		fractalExplorer.createAndShowGUI();
		fractalExplorer.drawFractal();
	}

	/**
	 * iterate over all image's pixel resize image's coord to fractal's coord
	 * calculate num iteration of this coord translate num of iteration to RGBcolor
	 * draw this pixel
	 */
	private void drawFractal() {
		double xCoord, yCoord;
		int n, rgbColor;
		float hue;
		for (int x = 0; x < size; ++x) {
			xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, size, x);
			for (int y = 0; y < size; ++y) {
				yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, size, y);
				n = fractalGenerator.numIterations(xCoord, yCoord);
				if (n == -1) {
					rgbColor = 0;
				} else {
					hue = 0.7f + (float) n / 200f;
					rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
				}
				jImageDisplay.drawPixel(x, y, rgbColor);
			}
		}
		jImageDisplay.repaint();
	}
}

enum Button {
	RESET, CHOOSE, SAVE
}
