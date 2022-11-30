/**
 * 
 */
package tasks;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author drpilman
 *
 */
public class Task1 {

	/**
	 * @param divided
	 * @param divisor
	 * @return remainder of the division
	 */
	public static int remainder(int divided, int divisor) {
		return divided % divisor;
	}

	@Test
	public void test_remainder() {
		assertEquals(remainder(1, 3), 1);
		assertEquals(remainder(3, 4), 3);
		assertEquals(remainder(-9, 45), -9);
		assertEquals(remainder(5, 5), 0);
	}

	/**
	 * @param base   of triangle
	 * @param height of triangle
	 * @return trianle's area
	 */
	public double triArea(int base, int height) {
		return base * height / 2.0;
	}

	@Test
	public void test_triArea() {
		assertEquals((int)triArea(3, 2), 3);
		assertEquals((int)triArea(7, 4), 14);
		assertEquals((int)triArea(10, 10), 50);
	}

	/**
	 * @param chikens
	 * @param cows
	 * @param pigs
	 * @return legs count
	 */
	public int animals(int chikens, int cows, int pigs) {
		return 2 * chikens + 4 * cows + 4 * pigs;
	}

	@Test
	public void test_animals() {
		assertEquals(animals(2, 3, 5), 36);
		assertEquals(animals(1, 2, 3), 22);
		assertEquals(animals(5, 2, 8), 50);
	}

	/**
	 * @param prob
	 * @param prize
	 * @param pay
	 * @return ???????????? - big_secret
	 */
	public boolean profitableGamble(double prob, int prize, int pay) {
		return prob * prize > pay;
	}

	@Test
	public void test_profitableGamble() {
		assertEquals(profitableGamble(0.2, 50, 9), true);
		assertEquals(profitableGamble(0.9, 1, 2), false);
		assertEquals(profitableGamble(0.9, 3, 2), true);
	}

	/**
	 * @param N
	 * @param a
	 * @param b
	 * @return what you can do with a and b to get N
	 */
	public String operation(int N, int a, int b) {
		if (N == a + b)
			return "added";
		if (N == a - b) // && res == b - a ???
			return "subtraced";
		if (N == a * b)
			return "multiplied";
		if (N == a / b) // && res == b / a ???
			return "divided";
		return "none";
	}

	@Test
	public void test_operation() {
		assertEquals(operation(24, 15, 9), "added");
		assertEquals(operation(24, 26, 2), "subtraced");
		assertEquals(operation(15, 11, 11), "none");
	}
	
	/**
	 * @param c char
	 * @return ascii code of c
	 */
	public int ctoa(char c) {
		return c;
	}

	@Test
	public void test_ctoa() {
		assertEquals(ctoa('A'), 65);
		assertEquals(ctoa('m'), 109);
		assertEquals(ctoa('['), 91);
		assertEquals(ctoa('\\'), 92);
	}

	/**
	 * @param x
	 * result is int because one of x+1 or x is diveded by 2
	 * @return sum of (1+...+x)
	 */
	public int addUpTo(int x) {
		return (1 + x) * x / 2;
	}

	@Test
	public void test_addUpTo() {
		assertEquals(addUpTo(3), 6);
		assertEquals(addUpTo(10), 55);
		assertEquals(addUpTo(7), 28);
	}

	/**
	 * @param a
	 * @param b
	 * 
	 * c<a+b & a,c,b is natural numbers =>
	 * c=a+b-1
	 * 
	 * @return max c: a,b,c is sides of triangle
	 */
	public int nextEdge(int a, int b) {
		return a + b - 1;
	}

	@Test
	public void test_nextEdge() {
		assertEquals(nextEdge(8, 10), 17);
		assertEquals(nextEdge(5, 7), 11);
		assertEquals(nextEdge(9, 2), 10);
	}

	/**
	 * @param numbers array of int
	 * @return sum of (cubs of numbers)
	 */
	public int sumOfCubes(int[] numbers) {
		return Arrays.stream(numbers).map(x -> x * x * x).sum();
	}

	@Test
	public void test_sumOfCubes() {
		assertEquals(sumOfCubes(new int[] { 1, 5, 9 }), 855);
		assertEquals(sumOfCubes(new int[] { 3, 4, 5 }), 216);
		assertEquals(sumOfCubes(new int[] { 2 }), 8);
		assertEquals(sumOfCubes(new int[] {}), 0);
	}
	
	/**
	 * 
	 * @param a 
	 * @param b
	 * @param c
	 *  
	 * a_i = a*2^i
	 * ] a_i = a*2^i
	 * 
	 * a_{i+1}==a_i+a_i==a_i*2==(a*2^i)*2==a*2^{i+1} done
	 * 
	 * a*(2^b) == a<<b
	 * @return true if calculated val divede by 'c' without reminder
	 */
	public boolean abcmath(int a, int b, int c) {
		return (a<<b)%c==0;
		/*for(;b>0;--b) { ??????
			a+=a;
		}
		return a%c==0;*/
	}

	@Test
	public void test_abcmath() {
		assertEquals(abcmath(42,5,10),false);
		assertEquals(abcmath(5,2,1),true);
		assertEquals(abcmath(1,2,3),false);
	}

}
