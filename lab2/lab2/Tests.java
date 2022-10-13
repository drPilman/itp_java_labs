package lab2;

public class Tests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testEquals();
		testDist();

	}
	public static void testDist() {
		System.out.println((new Point3d(0, 0, 0)).distanceTo(new Point3d(500, 0,0)));

		System.out.println((new Point3d(0, 0, 0)).distanceTo(new Point3d(50, 0,0)));

		System.out.println((new Point3d(0, 0, 0)).distanceTo(new Point3d(0.1234, 0,0)));

		System.out.println((new Point3d(0, 0, 0)).distanceTo(new Point3d(1, 1,0)));
	}
	public static void testEquals() {
		Point2d s1 = new Point2d(0, 0);
		Point2d s2 = new Point2d(0, 1);
		Point2d s3 = new Point2d(0, 0);
		Point2d s4 = new Point2d();
		double s5 = 0.0;
		System.out.println(s1.equals(s2));
		System.out.println(s1.equals(s3));
		System.out.println(s1.equals(s4));
		System.out.println(s1.equals(null));
		System.out.println(s1.equals(s5) + "\n");

		Point3d ss1 = new Point3d(0, 0, 0);
		Point3d ss2 = new Point3d(0, 0, 1);
		Point3d ss3 = new Point3d(0, 0, 0);
		Point3d ss4 = new Point3d();
		double ss5 = 0.0;
		System.out.println(ss1.equals(ss2));
		System.out.println(ss1.equals(ss3));
		System.out.println(ss1.equals(ss4));
		System.out.println(ss1.equals(null));
		System.out.println(ss1.equals(ss5) + "\n");

		System.out.println(ss1.equals(s1));
		System.out.println(s1.equals(ss1));// PAM PAM PAM!!!
		

	}

}
