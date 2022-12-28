import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;
import org.junit.Test;

public class Task6 {
	public int bell(int n) {
		int[][] dyn = new int[2][n + 1];
		dyn[0][0] = 1;
		int i, j;
		for (int z = 1; z <= n; ++z) {
			i = z % 2;
			j = (i + 1) % 2;

			//S{n+1,k} = k*S{n,k} + S{n,k-1}
			dyn[i][0] = 0;
			for (int k = 1; k <= z; ++k) {
				dyn[i][k] = k * dyn[j][k] + dyn[j][k - 1];
			}
			// System.out.println(Arrays.toString(dyn[i]));
		}
		// System.out.println();
		return Arrays.stream(dyn[n % 2]).sum();
	}

	@Test
	public void test_bell() {
		assertEquals(1, bell(1));
		assertEquals(2, bell(2));
		assertEquals(5, bell(3));
		assertEquals(15, bell(4));
		assertEquals(52, bell(5));
		// assertArrayEquals(encrypt("Sunshine"), new int[] { 83, 34, -7, 5, -11, 1, 5,
		// -9 });
	}

	Pattern patternWord = Pattern.compile("^[^aeiouyAEIOUY]*");

	public String translateWord(String word) {
		if (word.length() == 0)
			return "";
		boolean isUpper = Character.isUpperCase(word.charAt(0));
		word = word.toLowerCase();
		var res = new StringBuilder();
		var m = patternWord.matcher(word);
		m.find();
		if (m.end() == 0) {
			res.append(word);
			res.append('y');
		} else {
			res.append(word.subSequence(m.end(), word.length()));
			res.append(word.subSequence(0, m.end()));
		}
		res.append("ay");
		if (isUpper) {
			res.setCharAt(0, Character.toUpperCase(res.charAt(0)));
		}
		return res.toString();
	}

	Pattern patternSent = Pattern.compile("\\w+");

	public String translateSentence(String sent) {
		if (sent.length() == 0)
			return "";
		var res = new StringBuilder();
		var m = patternSent.matcher(sent);
		int last = 0;
		while (m.find()) {
			res.append(sent.subSequence(last, m.start()));
			res.append(translateWord(m.group()));
			last = m.end();
		}
		res.append(sent.subSequence(last, sent.length()));
		return res.toString();
	}

	@Test
	public void test_translate() {
		assertEquals("agflay", translateWord("flag"));
		assertEquals("uttonbay", translateWord("button"));
		assertEquals("Appleyay", translateWord("Apple"));
		assertEquals("", translateWord(""));
		assertEquals("Iyay ikelay otay eatyay oneyhay afflesway.", translateSentence("I like to eat honey waffles."));
		assertEquals("Oday youyay inkthay ityay isyay oinggay otay ainray odaytay?",
				translateSentence("Do you think it is going to rain today?"));
	}

	boolean checkRange(String x) {
		int a = Integer.parseInt(x);
		return a >= 0 && a < 256;
	}

	Pattern rgb = Pattern.compile("rgb\\(([0-9]+),([0-9]+),([0-9]+)\\)");
	Pattern rgba = Pattern.compile("rgba\\(([0-9]+),([0-9]+),([0-9]+),(0|0\\.[0-9]*|1)\\)");

	public boolean validColor(String s) {
		var m = rgb.matcher(s);
		try {
			if (!m.matches()) {
				m = rgba.matcher(s);
				if (!m.matches()) {
					return false;
				}
				double a = Double.parseDouble(m.group(4));
				if (a < 0 || a > 1)
					return false;
			}
			for (int i = 1; i < 4; ++i)
				if (!checkRange(m.group(i)))
					return false;
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@Test
	public void test_validColor() {
		assertEquals(true, validColor("rgba(1,0,0,0)"));
		assertEquals(false, validColor("rgb(0,,0)"));
		assertEquals(false, validColor("rgb(255,256,255)"));
		assertEquals(true, validColor("rgba(0,0,0,0.123456789)"));

	}

	Pattern param = Pattern.compile("[\\?\\&]((\\w+)\\=[^\\&]+)");

	public String stripUrlParams(String url, String[] paramsToStrip) {
		int i = url.indexOf('?');
		if (i == -1)
			return url;

		var res = new StringBuilder(url.subSequence(0, i));

		var m = param.matcher(url);
		int last = 0;
		var params = new HashMap<String, String>();
		while (m.find()) {
			params.put(m.group(2), m.group(1));
		}
		// System.out.println(params);
		for (String s : paramsToStrip) {
			params.remove(s);
		}
		if (params.size() == 0) {
			return res.toString();
		}
		res.append('?');

		for (var s : params.values()) {
			res.append(s);
			res.append('&');
		}
		res.deleteCharAt(res.length() - 1);
		return res.toString();
	}

	public String stripUrlParams(String url) {
		return stripUrlParams(url, new String[] {});
	}

	@Test
	public void test_stripUrlParams() {
		assertEquals("https://edabit.com?a=2&b=2", stripUrlParams("https://edabit.com?a=1&b=2&a=2"));
		assertEquals("https://edabit.com?a=2", stripUrlParams("https://edabit.com?a=1&b=2&a=2", new String[] { "b" }));
		assertEquals("https://edabit.com", stripUrlParams("https://edabit.com", new String[] { "b" }));
	}

	static record Word(int start, int length) {
	};

	public void getMax(LinkedList<Word> from, LinkedList<Word> to) {
		if (from.size() == 0)
			return;
		var WordM = new Word(0, 0);
		for (Word w : from) {
			if (w.length() > WordM.length())
				WordM = w;
		}
		from.remove(WordM);
		to.add(WordM);
	}

	public String[] getHashTags(String s) {
		s = s.toLowerCase();

		var r = new LinkedList<Word>();
		var res = new LinkedList<Word>();
		// start, length
		var m = patternSent.matcher(s);
		while (m.find()) {
			r.add(new Word(m.start(), m.end() - m.start()));
		}
		for (int i = 0; i < 3; ++i)
			getMax(r, res);
		String result[] = new String[res.size()];
		var it = res.iterator();
		Word v;
		for (int i = 0; i < result.length; ++i) {
			v = it.next();
			result[i] = "#" + s.substring(v.start(), v.start() + v.length());
		}
		return result;
	}

	@Test
	public void test_getHashTags() {
		assertArrayEquals(new String[] { "#avocado", "#became", "#global" },
				getHashTags("How the Avocado Became the Fruit of the Global Trade"));

		assertArrayEquals(new String[] { "#christmas", "#probably", "#will" },
				getHashTags("Why You Will Probably Pay More for Your Christmas Tree This Year"));
		assertArrayEquals(new String[] { "#surprise", "#parents", "#fruit" },
				getHashTags("Hey Parents, Surprise, Fruit Juice Is Not Fruit"));
		assertArrayEquals(new String[] { "#visualizing", "#science" }, getHashTags("Visualizing Science"));
	}

	boolean onlyone(HashSet<Integer> posl, int x) {
		boolean one = false;
		int a = -1;
		for (int b : posl) {
			if (a!=(x-b) && b*2!=x && posl.contains(x - b)) {
				if (one)
					return false;
				one = true;
				a=b;
			}
		}
		return one;
	}

	int ulam(int n) {
		assert (n > 0);
		if (n < 5)
			return n;
		var posl = new HashSet<Integer>();
		posl.addAll(Arrays.asList(1, 2, 3, 4));
		int max = 5;
		for (int i = 4; i < n; ++max)
		{
			if (onlyone(posl, max)) {
				posl.add(max);
				++i;
			}
		}
			
		return max-1;
	}

	@Test
	public void test_ulam() {
		assertEquals(4, ulam(4));
		assertEquals(16, ulam(9));
		assertEquals(1856, ulam(206));
	}

	public String longestNonrepeatingSubstring(String s) {
		s = s.toLowerCase();
		int mask = 0;
		int nmask;
		int start = 0;
		int length = 0;
		int startm = 0;
		int lengthm = 0;
		for (int i = 0, l = s.length(); i < l; ++i) {
			nmask = 1 << (s.charAt(i) - 'a');
			if ((nmask & mask) == 0) {
				mask |= nmask;
				++length;
			} else {
				if (length > lengthm) {
					startm = start;
					lengthm = length;
				}
				start = i;
				length = 1;
				mask = nmask;
			}
		}
		if (length > lengthm) {
			startm = start;
			lengthm = length;
		}
		return s.substring(startm, startm + lengthm);
	}

	@Test
	public void test_longestNonrepeatingSubstring() {
		assertEquals("abc", longestNonrepeatingSubstring("abcabcbb"));
		assertEquals("a", longestNonrepeatingSubstring("aaaaaa"));
		assertEquals("abcde", longestNonrepeatingSubstring("abcde"));
		assertEquals("abcd", longestNonrepeatingSubstring("abcda"));
	}
	
	/*
	1 	I
	5 	V
	10 	X
	50 	L
	100 	C 
	500 	D
	1000 	M
	*/
	char roman[] = {'I','V','X','L','C','D','M'};
	String convertToRoman(int n) {
		var res =new StringBuilder();
		for (int i=0,d;n!=0;n/=10,i+=2) {
			switch (d=n%10) {
			case 4: 
				res.insert(0,roman[i+1]);
				res.insert(0,roman[i]);
				break;
			case 9:
				res.insert(0,roman[i+2]);
				res.insert(0,roman[i]);
				break;
			default:
				for (int j=0,l=d%5;j<l;++j) res.insert(0,roman[i]);
				if (d>4) res.insert(0,roman[i+1]);
			}
			
		}
		return res.toString();
	}
	@Test
	public void test_convertToRoman() {
		assertEquals("II",convertToRoman(2));
		assertEquals("XII",convertToRoman(12));
		assertEquals("XVI",convertToRoman(16));
		assertEquals("IX",convertToRoman(9));
		assertEquals("V",convertToRoman(5));
		/*for (int i=1;i<100;++i)
			System.out.println(convertToRoman(i));*/
	}
				
	Pattern elem = Pattern.compile("([\\+\\-\\*\\/]|\\d+)");
	enum Operators {
		ADD(1),MUL(2),DIV(2),SUB(1),NONE(0);
		public final int prior;
		Operators(int x){prior = x;}
		static Operators valueOf(char c) {
			switch (c) {
			case '+': return ADD;
			case '-': return SUB;
			case '*': return MUL;
			case '/': return DIV;
			default: return NONE;
			}
		}
		Double calc(Double a, Double b) {
			switch (this) {
			case ADD:
				return b+a;
			case DIV:
				return b/a;
			case MUL:
				return b*a;
			case SUB:
				return b-a;
			default:
				throw new IllegalArgumentException("wrong operator");
			}
		}
		
	}
	void doOneOp(Stack<Operators> operators, Stack<Double> operands) {
		operands.push(operators.pop().calc(operands.pop(),operands.pop()));
	}
	Double calc(String s) {
		var operands = new Stack<Double>();
		var operators = new Stack<Operators>();
		var m = elem.matcher(s);
		Operators p;
		String w;
		boolean flag = m.find();
		while (flag) {
			w=m.group();
			if (w.length()==1 && (p = Operators.valueOf(w.charAt(0)))!=Operators.NONE) {
				if (operators.size()==0 || operators.peek().prior<p.prior) {
					operators.push(p);
					flag = m.find();
				}else {
					doOneOp(operators,operands);
				}
			}else {
				operands.push(Double.parseDouble(w));
				flag = m.find();
			}
		}
		while (operators.size()!=0) {
			doOneOp(operators,operands);
		}
		if (operands.size()==1) return operands.pop();
		throw new IllegalArgumentException("wrong syntax");
	}
	
	boolean formula(String s) {
		String []w = s.split("=");
		Double m = calc(w[0]);
		for (int i=1;i<w.length;++i) {
			if (Math.abs(m-calc(w[i]))>1e-10D) return false;
		}
		return true;
	}
	@Test
	public void test_formula() {
		assertEquals(true,formula("6 * 4 = 24"));
		assertEquals(false,formula("18 / 17 = 2"));
		assertEquals(true,formula("18 / 17 = 1+18/17-1"));

		assertEquals(false, formula("16 * 10 = 160 = 14 + 120"));
		assertEquals(true, formula("16 * 10 = 160 =320/2"));
	}
	
	boolean checkIsPalindrome(String s) {
		int l = s.length();
		for (int i = 0, l2=l/2; i < l2; ++i) {
			if (s.charAt(i)!=s.charAt(l-i-1)) return false;
		}
		return true;
	}
	
	public boolean palindromeDescendant(int n){
		String s = String.valueOf(n);
		if (s.length()==1) return false;
		if (checkIsPalindrome(s)) return true;
		if (s.length()%2==1) return false;
		int x;
		var res = new StringBuilder();
		while (n!=0) {
			x=n%10;
			n/=10;
			res.insert(0,x+n%10);
			n/=10;
		}
		//System.out.println(res.toString());
		return palindromeDescendant(Integer.parseInt(res.toString()));
	}
	@Test
	public void test_palindromeDescendant() {
		assertEquals(true,palindromeDescendant(11211230));
		assertEquals(true,palindromeDescendant(13001120));
		assertEquals(true,palindromeDescendant(23336014));
		assertEquals(true,palindromeDescendant(11));
	}
}
