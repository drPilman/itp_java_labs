import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class Task3 {
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return number of solutions of equation ax^2 + bx + c = 0
	 */
	int solutions(int a, int b, int c) {
		int d = b * b - 4 * a * c;
		return d == 0 ? 1 : (d < 0 ? 0 : 2);
	}

	@Test
	public void test_solutions() {
		assertEquals(solutions(1, 0, -1), 2);
		assertEquals(solutions(1, 0, 0), 1);
		assertEquals(solutions(1, 0, 1), 0);
	}

	/**
	 * @param s
	 * @return index of second word "zip"
	 */
	int findZip(String s) {
		return s.indexOf("zip", s.indexOf("zip") + 1);
	}

	@Test
	public void test_findZip() {
		assertEquals(findZip("all zip files are ziped"), 18);
		assertEquals(findZip("all zip files are compressed"), -1);
		assertEquals(findZip("all compressed files are compressed"), -1);
	}

	boolean checkPerfect(int n) {
		int s = 0;
		for (int i = 1; i < n; ++i) {
			if (n % i == 0)
				s += i;
		}
		return s == n;
	}

	@Test
	public void test_checkPerfect() {
		assertEquals(checkPerfect(6), true);
		assertEquals(checkPerfect(28), true);
		assertEquals(checkPerfect(496), true);
		assertEquals(checkPerfect(12), false);
		assertEquals(checkPerfect(97), false);
	}

	String flipEndChars(String s) {
		if (s.length() < 2)
			return "Incompatible.";
		if (s.charAt(0) == s.charAt(s.length() - 1))
			return "Two's a pair.";
		return s.charAt(s.length() - 1) + s.substring(1, s.length() - 1) + s.charAt(0);
	}

	@Test
	public void test_flipEndChars() {
		assertEquals(flipEndChars("Cat, dog, and mouse."), ".at, dog, and mouseC");
		assertEquals(flipEndChars("Ada"), "adA");
		assertEquals(flipEndChars("z"), "Incompatible.");
	}

	boolean isValidHexCode(String s) {
		return s.matches("#[0-9A-Fa-f]{6}");
	}

	@Test
	public void test_isValidHexCode() {
		assertEquals(isValidHexCode("#CD5C5C"), true);
		assertEquals(isValidHexCode("#EAECEE"), true);
		assertEquals(isValidHexCode("#eaecee"), true);
		assertEquals(isValidHexCode("#CD5C58C"), false);
		assertEquals(isValidHexCode("#CD5C5Z"), false);
		assertEquals(isValidHexCode("#CD5C&C"), false);
		assertEquals(isValidHexCode("CD5C5C"), false);
	}

	boolean same(Integer[] a, Integer[] b) {
		var aa = new HashSet<Integer>(Arrays.asList(a));
		var bb = new HashSet<Integer>(Arrays.asList(b));
		return aa.size() == bb.size();
	}

	@Test
	public void test_same() {
		assertEquals(same(new Integer[] { 1, 3, 4, 4, 4 }, new Integer[] { 2, 5, 7 }), true);
		assertEquals(same(new Integer[] { 9, 8, 7, 6 }, new Integer[] { 4, 4, 3, 1 }), false);
		assertEquals(same(new Integer[] { 2 }, new Integer[] { 3, 3, 3, 3, 3 }), true);
	}

	boolean isKaprekar(int n) {
		String s = Integer.toString(n * n);
		if (s.length() == 1)
			return n * n == n;
		return Integer.parseInt(s.substring(0, s.length() / 2))
				+ Integer.parseInt(s.substring(s.length() / 2, s.length())) == n;
	}

	@Test
	public void test_isKaprekar() {
		assertEquals(isKaprekar(3), false);
		assertEquals(isKaprekar(5), false);
		assertEquals(isKaprekar(297), true);
		assertEquals(isKaprekar(0), true);
		assertEquals(isKaprekar(1), true);
	}

	String longestZero(String s) {
		int lmax = 0, nmax = 0, l = 0, n = 0;
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) == '0') {
				++n;
			} else {
				if (n > nmax) {
					lmax = l;
					nmax = n;
				}
				n = 0;
				l = i;
			}
		}
		lmax += 1;
		return nmax == 0 ? "" : s.substring(lmax, lmax + nmax);
	}

	@Test
	public void test_longestZero() {
		assertEquals(longestZero("01100001011000"), "0000");
		assertEquals(longestZero("100100100"), "00");
		assertEquals(longestZero("11111"), "");
	}

	boolean isPrime(int n) {
		assert n > 1;
		if (n == 2)
			return true;
		if (n % 2 == 0)
			return false;
		int m = (int) Math.sqrt(n) + 1;
		for (int i = 3; i < m; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	int nextPrime(int n) {
		while (!isPrime(n)) {
			++n;
		}
		return n;
	}

	@Test
	public void test_nextPrime() {
		assertEquals(nextPrime(12), 13);
		assertEquals(nextPrime(24), 29);
		assertEquals(nextPrime(11), 11);
	}
	boolean rightTriangle(int a, int b,int c) {
		a*=a;
		b*=b;
		c*=c;
		return a+b==c || a+c==b || c+b==a;
	}
	@Test
	public void test_r() {
		assertEquals(rightTriangle(3, 4, 5), true);
		assertEquals(rightTriangle(145, 105, 100), true);
		assertEquals(rightTriangle(70, 130, 110), false);
	}

}
