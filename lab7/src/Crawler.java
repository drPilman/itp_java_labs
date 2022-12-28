import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Crawler implements Runnable {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private final URLPool pool;
	private int maxDepth;

	static final Pattern hrefPattern = Pattern.compile("<a href=[\"|\'](\\w+://[^\"\']+/[^\\\"\\']+)");

	public Crawler(URLPool pool, int maxDepth) {
		this.pool = pool;
		this.maxDepth = maxDepth;
	}

	public static void main(String[] args) {
		try {
			if (args.length != 3) {
				System.out.print("wrong number of arguments");
				return;
			}
			int depth = Integer.parseInt(args[1]);
			int threadsnum = Integer.parseInt(args[2]);
			URLDepthPair firstPair= new URLDepthPair(args[0], 0);
			
			URLPool pool = new URLPool();
			pool.add(firstPair);
			Thread[] threads = new Thread[threadsnum];
			for (int i = 0; i < threadsnum; i++) {
				Crawler task = new Crawler(pool, depth);
				threads[i] = new Thread(task);
				threads[i].start();
				System.out.printf("%s started\n", threads[i].getName());
			}
			while (pool.getWaiters() != threadsnum) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					
				}
			}
			System.out.println("Stop all");
			pool.stop();
			
			for (URLDepthPair pair : pool.getClosed()) {
				System.out.println(pair);
			}
			for (var thread: threads)
			  thread.interrupt();
			/*Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			System.out.print(threadSet);*/
			return;
		}catch (IOException e) {
			System.out.print("wrong arguments");
			return;
		}
		
	}

	private boolean close() {
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Ошибка закрытия сокета");
			return false;
		}
		return true;
	}

	private LinkedList<String> getLines(URLDepthPair url) {
		LinkedList<String> list = new LinkedList<String>();
		//System.out.println("GET "+ url.url.getPath() +" HTTP/1.1");
		//System.out.println("Host: "+ url.url.getHost());
		
		out.print("GET "+ url.url.getPath() +" HTTP/1.1\r\n");
		out.print("Host: "+ url.url.getHost()+"\r\n");
		out.print("Connection: close\r\n");
		/*out.println("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:108.0) Gecko/20100101 Firefox/108.0");
		out.println("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*//*;q=0.8");
		out.println("Accept-Language: en-US,en;q=0.5");
		out.println("Accept-Encoding: gzip, deflate");
		out.println("Referer: http://neverssl.com/");
		out.println("Connection: keep-alive");
		out.println("Upgrade-Insecure-Requests: 1");
		out.println("If-Modified-Since: Wed, 29 Jun 2022 00:23:22 GMT");
		out.println("If-None-Match: \"8be-5e28b29291e10-gzip\"");*/
		out.print("\r\n");
		out.flush();
		String temp;
		try {
			while ((temp = in.readLine()) != null) {
				//System.out.print(',');
				list.add(temp);
			}

			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println("Ошибка во время считывания");
			e.printStackTrace();
		}
		return list;
	}

	public LinkedList<URLDepthPair> getLinks(URLDepthPair url) {
		if (!url.check())
			return null;
		LinkedList<String> listRead = null;
		try {
			socket = new Socket(url.url.getHost(), 80);
			socket.setSoTimeout(1000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());

			listRead = getLines(url);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			while (!close()) {
			}
		}
		LinkedList<URLDepthPair> pairs = new LinkedList<URLDepthPair>();
		URLDepthPair newURL;
		Matcher m;
		for (String line : listRead) {
			m = hrefPattern.matcher(line);

			//System.out.println(line);
			while (m.find()) {
				//System.out.print('.');
				try {
					newURL = new URLDepthPair(m.group(1), url.getDepth() + 1);
				} catch (MalformedURLException e) {
					continue;
				}

				if (newURL.check())
					pairs.add(newURL);
			}
		}

		System.out.print(pairs.size());
		return pairs;
	}

	@Override
	public void run() {
		URLDepthPair pair;
		LinkedList<URLDepthPair> result;
		while (pool.isWork()) {
			System.out.print('!');
			pair = pool.get();
			if (pair == null)
				return;
			if (pair.getDepth() > maxDepth) {
				continue;
			}
			result = getLinks(pair);
			for (URLDepthPair i : result) {
				pool.add(i);
			}
			pool.finish(pair);

		}
	}

}
