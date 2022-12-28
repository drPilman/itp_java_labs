import java.util.regex.Pattern;
import java.net.MalformedURLException;
import java.net.URL;

public class URLDepthPair {
	private String s;
	private int depth;
	public URL url;

	static final Pattern urlPattern = Pattern.compile("^http://[^/]+/");

	public boolean check() {
		return urlPattern.matcher(s).find();
	}

	public URLDepthPair(String url, int depth) throws MalformedURLException {
		this.s = url;
		this.depth = depth;
		this.url = new URL(url);
	}

	public String getUrl() {
		return s;
	}

	public int getDepth() {
		return depth;
	}

	public String toString() {
		return "Ссылка: (" + s + ", " + depth + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof URLDepthPair p) {
			return s.equals(p.s);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return s.hashCode();
	}

}
