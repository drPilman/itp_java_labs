
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Location, Integer> items = new HashMap<>();
		// insert
		items.put(new Location(), 56);
		// find
		Integer count = items.get(new Location());
		System.out.print(count);

	}

}
