/**
 * This class represents a specific location in a 2D map. Coordinates are
 * integer values.
 **/
public class Location {
	/** X coordinate of this location. **/
	public final int xCoord;

	/** Y coordinate of this location. **/
	public final int yCoord;
	private final int hashCode;

	/** Creates a new location with the specified integer coordinates. **/
	public Location(int x, int y) {
		xCoord = x;
		yCoord = y;
		hashCode = x*40+y;
	}

	/** Creates a new location with coordinates (0, 0). **/
	public Location() {
		this(0, 0);
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return xCoord == other.xCoord && yCoord == other.yCoord;
	}
	
}
