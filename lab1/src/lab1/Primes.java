package lab1;

/**
 * print prime numbers that in inclusive range from 2 to 100
 */
public class Primes {

	public static void main(String[] args) {
		for (int i = 2; i <= 100; ++i) {
			if (isPrime(i)) {
				System.out.println(i);
			}
		}
	}

	/**
	 * @param n n - number (more than 1)
	 * 
	 * @result true if a number is prime
	 */
	public static boolean isPrime(int n) {
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

}
