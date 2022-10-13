package lab2;

import java.util.Scanner;

/**
 * @author drpilman
 *
 */
public class Point3d extends Point2d {
	private double zCoord;

	/**
	 * @param x
	 * @param y
	 * @param z 
	 * Construct Point3d with 3 coord (x,y,z);
	 */
	public Point3d(double x, double y, double z) {
		super(x, y);
		zCoord = z;
	}

	/**
	 * Construct default zero Point3d with 3 coord (0,0,0);
	 */
	public Point3d() {
		super();
		zCoord = 0;
	}

	/**
	 * @return the zCoord
	 */
	public double getZ() {
		return zCoord;
	}

	/**
	 * @param z the z to set zCoord
	 */
	public void setZ(double z) {
		this.zCoord = z;
	}
	
	
	/**
	 * @param other
	 * @return true if all other.coords = this.coords
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Point3d other_point3d))
			return false;
		return other_point3d.zCoord == zCoord && super.equals((Point2d) other);
	}

	/**
	 * @param other
	 * @return Euclidean distance between this and other as Point3d
	 */
	public double distanceTo(Point3d other) {
		return round2(Math.sqrt(Math.pow(zCoord - other.zCoord, 2) + Math.pow(getX() - other.getX(), 2)
				+ Math.pow(getY() - other.getY(), 2)));
	}

	/**
	 * @param in Scanner 
	 * read 3 douuble with Scanner "in"
	 * Construct Point3d with scanned vars
	 */
	public Point3d(Scanner in) {
		this(in.nextDouble(), in.nextDouble(), in.nextDouble());
	}

}
