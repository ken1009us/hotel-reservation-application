package model;

import java.util.Objects;

/**
 * @author Ken Wu
 *
 */
public class Room implements IRoom{
    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;

    public Room (final String roomNumber, final Double price, final RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Double getRoomPrice() {
        return price;
    }

    public RoomType getRoomType() {
        return enumeration;
    }

    public boolean isFree() {
        return this.price != null && this.price.equals(0.0);
    }

    @Override
    public String toString() {
        return "Room Number: " + this.roomNumber
                + "\n  Room Price: " + this.price
                + "\n  Room Type: " + this.enumeration;
    }
}
