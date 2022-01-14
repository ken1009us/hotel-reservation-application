package model;

/**
 * @author Ken Wu
 *
 */
public class FreeRoom extends Room {

    public FreeRoom (final String roomNumber, final RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);
    }

    @Override
    public String toString() {
        return "Free Room: " + super.toString();
    }
}
