/**
 * 
 */
package task2;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author drpilman
 *
 */
public class Task2 {
	public static String repeat(String from, int count) {
		assert (count > 0);
		char[] charArray = new char[from.length() * count];
		for (int i = 0; i < from.length(); ++i) {
			for (int j = 0; j < count; ++j) {
				charArray[i * count + j] = from.charAt(i);
			}
		}
		return new String(charArray);
	}

	@Test
	public void test_repeat() {
		assertEquals(repeat("12 3", 2), "1122  33");
		assertEquals(repeat("mice", 5), "mmmmmiiiiiccccceeeee");
		assertEquals(repeat("hello", 3), "hhheeellllllooo");
	}

	public static int differenceMaxMin(int[] array) {
		assert (array.length != 0);
		int min = array[0], max = array[0];
		for (int var : array) {
			if (min > var)
				min = var;
			if (max < var)
				max = var;
		}
		return max - min;

	}

	@Test
	public void test_differenceMaxMin() {
		assertEquals(differenceMaxMin(new int[] { 10, 4, 1, 4, -10, -50, 32, 21 }), 82);
	}

	public static boolean isAvgWhole(int[] array) {
		int c = 0;
		for (int var : array) {
			c = (c + var) % array.length;
		}
		return c == 0;
	}

	@Test
	public void test_isAvgWhole() {
		assertEquals(isAvgWhole(new int[] { 1, 3 }), true);
		assertEquals(isAvgWhole(new int[] { 1, 2, 3, 4 }), false);
		assertEquals(isAvgWhole(new int[] { 1, 5, 6 }), true);
		assertEquals(isAvgWhole(new int[] { 1, 1, 1 }), true);
		assertEquals(isAvgWhole(new int[] { 9, 2, 2, 5 }), false);
	}

	public static int[] cumulativeSum(int[] array) {
		int[] res = new int[array.length];
		int c = 0;
		for (int i = 0; i < array.length; i++)
			res[i] = c += array[i];
		return res;
	}

	@Test
	public void test_cumulativeSum() {
		assertArrayEquals(cumulativeSum(new int[] { 1, 2, 3 }), new int[] { 1, 3, 6 });
		assertArrayEquals(cumulativeSum(new int[] { 1, -2, 3 }), new int[] { 1, -1, 2 });
		assertArrayEquals(cumulativeSum(new int[] { 3, 3, -2, 408, 3, 3 }), new int[] { 3, 6, 4, 412, 415, 418 });
	}

	public static int getDecimalPlaces(String s) {
		String[] w = s.split("\\.");
		assert (w.length < 3);
		if (w.length == 1)
			return 0;
		return w[1].length();
	}

	@Test
	public void test_getDecimalPlaces() {
		assertEquals(getDecimalPlaces("123.30"), 2);
		assertEquals(getDecimalPlaces("123"), 0);
		assertEquals(getDecimalPlaces("123.3"), 1);
	}

	public static int Fibonacci(int n) {
		assert (n >= 0);
		int a = 1, b = 1, c = 0;
		for (int i = 1; i < n; i++) {
			c = b;
			b = a + b;
			a = c;
		}
		return b;
	}

	@Test
	public void test_Fibonacci() {
		assertEquals(Fibonacci(3), 3);
		assertEquals(Fibonacci(7), 21);
		assertEquals(Fibonacci(12), 233);
	}

	public static boolean isValid(String s) {
		return s.matches("[0-9]{5}");
	}

	@Test
	public void test_isValid() {
		assertEquals(isValid("12345"), true);
		assertEquals(isValid("123 5"), false);
		assertEquals(isValid("123"), false);
		assertEquals(isValid("123e5"), false);
	}

	public static boolean isStrangePair(String a, String b) {
		if (a.length()==0 && b.length()==0) return true;
		if (a.length()==0 || b.length()==0) return false;
		return a.charAt(0) == b.charAt(b.length() - 1) && a.charAt(a.length() - 1) == b.charAt(0);
	}
	@Test
	public void test_isStrangePair() {
		assertEquals(isStrangePair("ratio", "orator"), true);
		assertEquals(isStrangePair("sparkling", "groups"), true);
		assertEquals(isStrangePair("bush", "hubris"), false);
		assertEquals(isStrangePair("",""), true);
	}
	
	public static boolean isPrefix(String main, String pre) {
		if (pre.length()==0)return true;
		assert(pre.charAt(pre.length()-1)=='-');
		return main.startsWith(pre.substring(0, pre.length()-1));
	}
	
	public static boolean isSuffix(String main, String suf) {
		if (suf.length()==0)return true;
		assert(suf.charAt(0)=='-');
		return main.endsWith(suf.substring(1));
	}
	
	@Test
	public void test_isPre_Suf_fix() {
		assertEquals(isPrefix("automation", "auto-"),true);
		assertEquals(isSuffix("arachnophobia", "-phobia"), true);
		assertEquals(isPrefix("retrospect", "sub-"),false);
		assertEquals(isSuffix("vocation", "-logy"),false);
	}
	public static int boxSeq(int n) {
		return n/2*2+(n%2)*3; //n-n%2+(n%2)*3;
	}
	@Test
	public void test_boxSeq() {
		assertEquals(boxSeq(0),0);
		assertEquals(boxSeq(1),3);
		assertEquals(boxSeq(2),2);
		assertEquals(boxSeq(3),5);
		assertEquals(boxSeq(4),4);
	}
	
	
}
