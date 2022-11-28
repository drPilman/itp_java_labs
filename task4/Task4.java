import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Test;

public class Task4 {
	public String bessie(int n, int k, String input) {
		var res = new StringBuilder();
		int x = 0; // currunt line lenght
		for (var word : input.split(" ")) {
			if (word.length() > k) {
				throw new IllegalArgumentException(
						"слово '" + word + "' содержит больше максимального количества символов в одной строке");
			}
			if (x != 0 && x + word.length() <= k) {
				res.append(" ");
				x += word.length();
			} else {
				if (x != 0)
					res.append("\n");
				x = word.length();
			}
			res.append(word);
		}
		return res.toString();
	}

	@Test
	public void test_bessie() {
		assertEquals("hello my\nname is\nBessie\nand this\nis my\nessay",
				bessie(10, 7, "hello my name is Bessie and this is my essay"));
	}

	public String[] split(String s) {
		var res = new ArrayList<String>();
		int x = 0, j = 0;
		for (int i = 0, n = s.length(); i < n; i++) {
			switch (s.charAt(i)) {
			case '(':
				x += 1;
				break;
			case ')':
				x -= 1;
				if (x == 0) {
					res.add(s.substring(j, i + 1));
					j = i + 1;
				}
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
		String[] arr = new String[res.size()];
		return res.toArray(arr);
	}

	@Test
	public void test_split() {
		assertArrayEquals(split("()()()"), new String[] { "()", "()", "()" });
		assertArrayEquals(split("((()))"), new String[] { "((()))" });
		assertArrayEquals(split("((()))(())()()(()())"), new String[] { "((()))", "(())", "()", "()", "(()())" });
		assertArrayEquals(split("((())())(()(()()))"), new String[] { "((())())", "(()(()()))" });
	}

	public String toCamelCase(String s) {
		var res = new StringBuilder();
		boolean f = true;
		for (var word : s.split("[\\W_0-9]+")) {
			if (f) {
				f = false;
				res.append(word.charAt(0));
			} else
				res.append(Character.toUpperCase(word.charAt(0)));
			res.append(word.substring(1));
		}
		return res.toString();
	}

	public String toSnakeCase(String s) {
		var from = new StringBuilder(s);
		var res = new StringBuilder();
		int j = 0;
		for (int i = 0, n = from.length(); i < n; i++) {
			if (Character.isUpperCase(from.charAt(i))) {
				if (j != 0)
					res.append('_');
				res.append(from.subSequence(j, i));
				j = i;
			}
		}
		if (j != 0)
			res.append('_');
		res.append(from.subSequence(j, from.length()));
		return res.toString().toLowerCase();
	}

	@Test
	public void test_case() {
		assertEquals(toCamelCase("hello_edabit"), "helloEdabit");
		assertEquals(toSnakeCase("helloEdabit"), "hello_edabit");
		assertEquals(toCamelCase("is_modal_open"), "isModalOpen");
		assertEquals(toSnakeCase("getColor"), "get_color");
	}

	public String fmt(float value) {
		return String.format("$%.2f", value);
	}

	public String overTime(float[] data) {
		assert (data.length == 4);
		assert (data[0] < data[1]);
		if (data[0] >= 17)
			return fmt(data[2] * data[3] * (data[1] - data[0]));
		if (data[1] <= 17)
			return fmt(data[2] * (data[1] - data[0]));
		return fmt(data[2] * ((data[1] - 17) * data[3] + 17 - data[0]));
	}

	@Test
	public void test_overTime() {
		assertEquals(overTime(new float[] { 9, 17, 30, 1.5f }), "$240.00");
		assertEquals(overTime(new float[] { 16, 18, 30, 1.8f }), "$84.00");
		assertEquals(overTime(new float[] { 13.25f, 15, 30, 1.5f }), "$52.50");
	}

	public String BMI(String w, String h) {
		var w_s = w.split(" ");
		double w_d = Double.parseDouble(w_s[0]);
		w_d *= "kilos".equals(w_s[1]) ? 1 : 0.453592;

		var h_s = h.split(" ");
		double h_d = Double.parseDouble(h_s[0]);
		h_d *= "meters".equals(h_s[1]) ? 1 : 0.0254;
		double res = w_d / (h_d * h_d);

		if (res < 18.5) {
			return String.format("%.1f Underweight", res);
		}
		if (res < 25) {
			return String.format("%.1f Normal weight", res);
		}
		return String.format("%.1f Overweight", res);

	}

	@Test
	public void test_BMI() {
		assertEquals(BMI("205 pounds", "73 inches"), "27.0 Overweight");
		assertEquals(BMI("55 kilos", "1.65 meters"), "20.2 Normal weight");
		assertEquals(BMI("154 pounds", "2 meters"), "17.5 Underweight");
	}

	public int bugger(int x) {
		int i = 0;
		int a;
		while (x > 9) {
			++i;
			a = 1;
			while (x > 9) {
				a *= x % 10;
				x /= 10;
			}
			a *= x;
			x = a;
		}
		return i;
	}

	@Test
	public void test_bugger() {
		assertEquals(bugger(39), 3);
		assertEquals(bugger(999), 4);
		assertEquals(bugger(4), 0);
	}

	public String toStarShorthand(String s) {
		if (s.length() == 0)
			return s;
		var res = new StringBuilder();
		char a = s.charAt(0), x;
		int c = 1;
		for (int i = 1, n = s.length(); i < n; i++) {

			if (a == (x = s.charAt(i)))
				++c;
			else {
				res.append(a);
				if (c != 1) {
					res.append('*');
					res.append(c);
				}
				c = 1;
				a = x;
			}
		}
		res.append(a);
		if (c != 1) {
			res.append('*');
			res.append(c);
		}
		return res.toString();
	}

	@Test
	public void test_toStarShorthand() {
		assertEquals(toStarShorthand("abbccc"), "ab*2c*3");
		assertEquals(toStarShorthand("77777geff"), "7*5gef*2");
		assertEquals(toStarShorthand("abc"), "abc");
		assertEquals(toStarShorthand(""), "");
	}

	Pattern VOWELS_PATTERN = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE);

	public boolean doesRhyme(String s1, String s2) {
		var s1_m = VOWELS_PATTERN.matcher(s1.subSequence(s1.lastIndexOf(' '), s1.length()));
		var s2_m = VOWELS_PATTERN.matcher(s2.subSequence(s2.lastIndexOf(' '), s2.length()));

		var s1_b = new StringBuilder();
		var s2_b = new StringBuilder();

		while (s1_m.find())
			s1_b.append(s1_m.group().toLowerCase());
		while (s2_m.find())
			s2_b.append(s2_m.group().toLowerCase());

		return s1_b.compareTo(s2_b) == 0;
	}

	@Test
	public void test_doesRhyme() {
		assertEquals(doesRhyme("You are off to the races", "a splendid day."), false);
		assertEquals(doesRhyme("Sam I am!", "Green eggs and ham."), true);
		assertEquals(doesRhyme("Sam I am!", "Green eggs and HAM."), true);
		assertEquals(doesRhyme("and frequently do?", "you gotta move."), false);
	}

	public int count(long x, int c) {
		var s = String.valueOf(x);
		char old_char = s.charAt(0);
		int bit_arr = 0;
		if (c == 1)
			bit_arr = 1 << (old_char - '0');
		char current_char;
		int l = 1;

		for (int i = 1, n = s.length(); i < n; ++i) {
			current_char = s.charAt(i);
			if (old_char == current_char) {
				if (c == ++l)
					bit_arr |= 1 << (current_char - '0');
			} else {
				l = 1;
				old_char = current_char;
			}
		}
		return bit_arr;

	}

	public boolean trouble(long a, long b) {
		return (count(a, 3) & count(b, 2)) != 0;
	}

	@Test
	public void test_trouble() {
		assertEquals(trouble(451999277, 41177722899L), true);
		assertEquals(trouble(1222345, 12345), false);
		assertEquals(trouble(666789, 12345667), true);
		assertEquals(trouble(33789, 12345337), false);
	}

	public int countUniqueBooks(String s, char endBook) {
		boolean isBookOpen = false;
		char current_char;
		var s_b = new StringBuilder(s);
		var books = new HashSet<Integer>();
		int j = 0;
		for (int i = 0, n = s_b.length(); i < n; ++i) {
			if (s_b.charAt(i) == endBook) {
				if (isBookOpen) 
					books.addAll(s_b.subSequence(j, i).chars().boxed().toList());
				else
					j = i + 1;
				isBookOpen ^= true;
			}

		}
		return books.size();
	}

	@Test
	public void test_countUniqueBooks() {
		assertEquals(countUniqueBooks("AZYWABBCATTTA", 'A'), 4);
		assertEquals(countUniqueBooks("$AA$BBCATT$C$$B$", '$'), 3);
		assertEquals(countUniqueBooks("ZZABCDEF", 'Z'), 0);
	}
}
