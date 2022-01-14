package model;

import model.RoomType;

/**
 * @author Ken Wu
 *
 */
public interface IRoom {
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();
}
