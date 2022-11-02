package fractal;

import java.awt.geom.Rectangle2D;

public class Mandelbrot extends fractal.FractalGenerator {

	public static final int MAX_ITERATIONS = 2000;

	@Override
	public void getInitialRange(Rectangle2D.Double range) {
		range.x = -2.0;
		range.y = -1.5;
		range.width = 3.0;
		range.height = 3.0;

	}

	@Override
	public int numIterations(double x, double y) {
		Complex z = new Complex();
		Complex c = new Complex(x, y);
		for (int i = 0; i < MAX_ITERATIONS; i++) {
			z.mul_self(z);
			z.add_self(c);
			if (z.len_squared() >= 4) {
				return i;
			}
		}
		return -1;
	}

}
