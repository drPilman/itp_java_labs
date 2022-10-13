package lab2;

import java.util.Scanner;

/**
 * @author drpilman
 *
 */
public class Lab1 {

	/**
	 * Input 3 Point3d as trinagle
	 * calculate and print triangle area
	 */
	public static void main(String[] args) {
		System.out.println("Input 3 Point3d in format:x y z");
		Scanner in = new Scanner(System.in);
		Point3d a = new Point3d(in), b = new Point3d(in), c = new Point3d(in);
		if (a.equals(b) || a.equals(c) || b.equals(c)){
			System.out.println("You input few equal points.\nArea equals to zero");
		}else {
			System.out.printf("Area equals to %f\n",computeArea(a, b, c));
		}

	}
	/**
	 * @param Point3d a,b,c (trinagle as 3 Point3d)
	 * @return triangle area
	 */
	public static double computeArea(Point3d a, Point3d b, Point3d c) {
		double ab = a.distanceTo(b), ac = a.distanceTo(c), bc = b.distanceTo(c);
		double p = (ab+ac+bc)/2;
		return Math.sqrt(p*(p-ac)*(p-ab)*(p-bc));
	}

}
