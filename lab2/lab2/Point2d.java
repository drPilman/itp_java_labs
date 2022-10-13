package lab2;

/**
 * 
 * @author drpilman 2-dimensional point class
 *
 */
public class Point2d {
	private double xCoord;
	private double yCoord;

	/**
	 * @param x
	 * @param y
	 * Construct Point2d with 2 coord (x,y);
	 */
	public Point2d(double x, double y) {
		xCoord = x;
		yCoord = y;
	}
	
	/**
	 * Construct default zero Point2d with 2 coord (0,0);
	 */
	public Point2d() {
		this(0, 0);
	}

	/**
	 * @return the xCoord
	 */
	public double getX() {
		return xCoord;
	}

	/**
	 * @param xCoord the xCoord to set
	 */
	public void setX(double val) {
		xCoord = val;
	}

	/**
	 * @return the yCoord
	 */
	public double getY() {
		return yCoord;
	}

	/**
	 * @param yCoord the yCoord to set
	 */
	public void setY(double val) {
		yCoord = val;
	}

	/**
	 * @param other
	 * @return true if all other.coords = this.coords
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Point2d other_point2d))
			return false;
		return other_point2d.xCoord == xCoord && other_point2d.yCoord == yCoord;
	}
	
	
	/**
	 * @param x - double
	 * @return x rounded at 2 digits ofter dot
	 */
	static double round2(double x) {
		return Math.round(x * 100) / 100.0;
	}

}
