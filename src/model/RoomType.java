package model;

/**
 * @author Ken Wu
 *
 */
public enum RoomType {
    SINGLE,
    DOUBLE;

    public static RoomType roomType(String type) {
        if (type.equals("SINGLE")) {
            return RoomType.SINGLE;
        }else if (type == "DOUBLE") {
            return RoomType.DOUBLE;
        }
        throw new IllegalArgumentException();
    }
}
