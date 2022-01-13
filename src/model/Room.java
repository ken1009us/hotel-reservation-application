/**
 * @author Ken Wu
 *
 */

package model;

public class Room implements IRoom{
    public String roomNumber;
    public Double price;
    public RoomType enumeration;

    public Room (String roomNumber, Double price, RoomType enumeration) {
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
        if (price == 0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + ", " + enumeration + " bed, Room price: $" + price;
    }
}
