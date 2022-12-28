import java.util.LinkedList;
public class URLPool {
    private final LinkedList<URLDepthPair> opened = new LinkedList<URLDepthPair>();
    private final LinkedList<URLDepthPair> closed = new LinkedList<URLDepthPair>();
    
    private int numWaiters = 0;
    private boolean flag = true;
    
    public void stop() {
    	flag = false;
    }
    
    public boolean isWork() {
    	return flag;
    }
    
    public synchronized URLDepthPair get() { 
        while (flag && opened.isEmpty()) {
            numWaiters++;
            try {
                wait();
            } catch (InterruptedException e) {
            	return null;
            }
            numWaiters--;
        }
        if (opened.isEmpty()) return null;
        return opened.removeFirst();
    }
    
    public synchronized boolean add(URLDepthPair pair) {
        if (!opened.contains(pair) && !closed.contains(pair)) {
            opened.add(pair);
            notify();
            return true;
        }
        return false;
    }
    
    public synchronized void finish(URLDepthPair pair) {
        if (!closed.contains(pair)) {
            closed.add(pair);
        }
    }
    
    public LinkedList<URLDepthPair> getClosed() {
        return closed;
    }
    
    public int getWaiters() {
        return numWaiters;
    }

}