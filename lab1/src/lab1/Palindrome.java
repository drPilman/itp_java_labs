package lab1;


/**
 * @param []args
 * print palindrom or not for each arg in args
 *
 */
public class Palindrome {
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String s = args[i];
			System.out.printf("%s %s palindrome%n",s,isPalindrome(s)?"is":"isn't");
		}

	}

	/**
	 * @param s - string
	 * @return reversed s
	 */
	public static String reverseString(String s) {
		String result = "";
		int len = s.length();
		for (int i = 0; i < len; i++) {
			result = s.charAt(i) + result;
		}
		return result;
	}

	/**
	 * @param s - string
	 * @return true if s is palindrome
	 */
	public static boolean isPalindrome(String s) {
		return s.equals(reverseString(s));
	}

}
