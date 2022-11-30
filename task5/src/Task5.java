import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Test;
import org.apache.commons.codec.digest.DigestUtils;

public class Task5 {
	public int[] encrypt(String s) {
		int[] arr = s.chars().toArray();
		for (int i = arr.length - 1; i > 0; --i) {
			arr[i] -= arr[i - 1];
		}
		return arr;
	}

	public String decrypt(int[] arr) {
		var res = new StringBuilder();
		res.append((char) arr[0]);
		for (int i = 1; i < arr.length; ++i)
			res.append((char) (arr[i] += arr[i - 1]));

		return res.toString();
	}

	@Test
	public void test_crypt() {
		assertArrayEquals(encrypt("Hello"), new int[] { 72, 29, 7, 0, 3 });
		assertEquals(decrypt(new int[] { 72, 33, -73, 84, -12, -3, 13, -13, -68 }), "Hi there!");
		assertArrayEquals(encrypt("Sunshine"), new int[] { 83, 34, -7, 5, -11, 1, 5, -9 });
	}

	public boolean canMove(String piece, String from_s, String to_s) {
		Point from = new Point(from_s);
		Point to = new Point(to_s);
		var d = from.norm(to);
		switch (CHESS_PIECE.valueOf(piece)) {
		case Pawn:
			return to.x == from.x && to.y == from.y + 1;
		case Knight:
			return (d.x == 2 && d.y == 1) || (d.x == 1 && d.y == 2);
		case Bishop:
			return d.x == d.y;
		case Rook:
			return d.x == 0 || d.y == 0;
		case King:
			return (d.x + d.y == 1) || (d.x == 1 && d.y == 1);
		case Queen:
			return d.x == 0 || d.y == 0 || d.x == d.y;
		}
		return false;
	}

	@Test
	public void test_canMove() {
		assertEquals(canMove("Rook", "A8", "H8"), true);
		assertEquals(canMove("Bishop", "A7", "G1"), true);
		assertEquals(canMove("Queen", "C4", "D6"), false);
	}

	/*
	 * bad solution public boolean canComplete(String pattern, String s) { var
	 * pattern_b = new StringBuilder(); pattern_b.append(".*");
	 * pattern.chars().forEach(x -> { pattern_b.append((char)x);
	 * pattern_b.append(".*"); }); return Pattern.matches(pattern_b.toString(), s);
	 * }
	 */

	public boolean canComplete(String pattern, String s) {
		int j = 0, n1 = pattern.length();
		for (int i = 0, n2 = s.length(); i < n2 && j < n1; ++i) {
			if (s.charAt(i) == pattern.charAt(j))
				++j;
		}
		return j == n1;
	}

	@Test
	public void test_canComplete() {
		assertEquals(canComplete("butl", "beautiful"), true);
		assertEquals(canComplete("butlz", "beautiful"), false);
		assertEquals(canComplete("tulb", "beautiful"), false);
		assertEquals(canComplete("bbutl", "beautiful"), false);
	}

	public int sumDigProd(int... arr) {
		int x = Arrays.stream(arr).sum();
		int a;
		while (x > 9) {
			a = 1;
			while (x > 9) {
				a *= x % 10;
				x /= 10;
			}
			a *= x;
			x = a;
		}
		return x;

	}

	@Test
	public void test_sumDigProd() {
		assertEquals(sumDigProd(16, 28), 6);
		assertEquals(sumDigProd(0), 0);
		assertEquals(sumDigProd(1, 2, 3, 4, 5, 6), 2);
	}

	public int getMask(String s) {
		return s.chars().reduce(0, (result, c) -> {
			int i = "aeiou".indexOf(c);
			if (i != -1)
				result |= 1 << i;
			return result;
		});

	}

	public String[] sameVowelGroup(String[] input) {
		int mask = getMask(input[0]);
		return Arrays.stream(input).filter(word -> getMask(word) == mask).toArray(String[]::new);
	}

	@Test
	public void test_sameVowelGroup() {
		assertArrayEquals(sameVowelGroup(new String[] { "toe", "ocelot", "maniac" }), new String[] { "toe", "ocelot" });
		assertArrayEquals(sameVowelGroup(new String[] { "many", "carriage", "emit", "apricot", "animal" }),
				new String[] { "many" });
		assertArrayEquals(sameVowelGroup(new String[] { "hoops", "chuff", "bot", "bottom" }),
				new String[] { "hoops", "bot", "bottom" });
	}

	public boolean validateCard(String card) {
		int index_check = card.length();
		int parity = index_check % 2;
		int x, sum = 0;
		--index_check;
		for (int i = 0; i < index_check; ++i) {
			x = card.charAt(i) - '0';
			if (i % 2 == parity) {
				x *= 2;
				if (x > 9)
					x = 1 + x % 10;

			}
			sum += x;
		}
		return 10 - sum % 10 == card.charAt(index_check) - '0';
	}

	@Test
	public void test_validateCard() {
		assertEquals(validateCard("1234567890123456"), false);
		assertEquals(validateCard("1234567890123452"), true);
	}

	String digits[] = new String[] { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
			"eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
	String dozens[] = new String[] { "twenty ", "thirty ", "fourty ", "fifty ", "sixty ", "seventy ", "eighty ",
			"ninety " };

	public String numToEng(int value) {
		if (value == 0)
			return "zero";
		var res = new StringBuilder();
		int s_100 = value / 100;
		int s_011 = value - s_100 * 100;
		int s_001 = value % 10;
		if (s_100 != 0) {
			res.append(digits[s_100 - 1]);
			res.append(" hundred ");
		}
		if (s_011 < 20) {
			res.append(digits[s_011 - 1]);
		} else {
			res.append(dozens[s_011 / 10 - 2]);
			if (s_001 != 0)
				res.append(digits[s_001 - 1]);
		}
		int n = res.length() - 1;
		if (res.charAt(n) == ' ')
			res.deleteCharAt(n);
		return res.toString();
	}

	@Test
	public void test_numToEng() {
		assertEquals(numToEng(0), "zero");
		assertEquals(numToEng(18), "eighteen");
		assertEquals(numToEng(126), "one hundred twenty six");
		assertEquals(numToEng(909), "nine hundred nine");
	}

	public String getSha256Hash(String s) {
		return DigestUtils.sha256Hex(s);

	}

	@Test
	public void test_getSha256Hash() {
		assertEquals(getSha256Hash("password123"), "ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f");
		assertEquals(getSha256Hash("Fluffy@home"), "dcc1ac3a7148a2d9f47b7dbe3d733040c335b2a3d8adc7984e0c483c5b2c1665");
		assertEquals(getSha256Hash("Hey dude!"), "14f997f08b8ad032dcb274198684f995d34043f9da00acd904dc72836359ae0f");
	}

	char checkArr[][] = new char[][] { "in".toCharArray(), "the".toCharArray(), "of".toCharArray(),
			"and".toCharArray() };

	public boolean checkArticle(char[] word, int l) {
		int j;
		boolean f;
		for (int i = 0; i < 4; ++i)
			if (checkArr[i].length == l)
			{
				f=true;
				for (j=0;j<l;++j)
				{
					if (word[j]!=checkArr[i][j]) f=false;
				}
					
				if (f) return false;
			}
		return true;

	}

	public void Upper(int j,int i, StringBuilder res, char[] word) {
		boolean flag = false;
		if (i - j == 2 || i - j == 3) {
			res.getChars(j, i, word, 0);
			if (checkArticle(word, i - j))
				flag=true;
		}else 
			flag=true;
		
		if (flag) res.setCharAt(j, Character.toUpperCase(res.charAt(j)));
	}

	public String correctTitle(String s) {
		var res = new StringBuilder(s.toLowerCase());
		int j = -1, n = res.length();
		char word[] = new char[3];
		for (int i = 0; i < n; ++i) {
			if (!Character.isLetter(res.charAt(i))) {
				j++;
				Upper(j,i, res, word);
				j = i;
			}
		}
		if (j + 1 != n) {
			Upper(j,n, res, word);
		}
		return res.toString();

	}

	@Test
	public void test_correctTitle() {
		assertEquals(correctTitle("jON SnoW, kINg IN thE noRth."), "Jon Snow, King in the North.");
		assertEquals(correctTitle("sansa stark, lady of winterfell."), "Sansa Stark, Lady of Winterfell.");
		assertEquals(correctTitle("TYRION LANNISTER, HAND OF THE QUEEN."), "Tyrion Lannister, Hand of the Queen.");
	}
}

class Point {
	int x, y;

	Point(String s) {
		this(s.charAt(0) - 'A', s.charAt(1) - '0');
	}

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	Point norm(Point other) {
		return new Point(Math.abs(x - other.x), Math.abs(y - other.y));
	}
}

enum CHESS_PIECE {
	// "пешка", "конь", "слон", "Ладья", "Ферзь"и " король"
	Pawn, Knight, Bishop, Rook, Queen, King
}
