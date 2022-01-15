package api;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import model.Customer;
import model.IRoom;
import model.Reservation;

import service.CustomerService;
import service.ReservationService;

/**
 * @author Ken Wu
 *
 */
public class HotelResource {

    // provide a static reference
    private static HotelResource INSTANCE;

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    // provide a static reference
    public static HotelResource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HotelResource();
        }
        return INSTANCE;
    }

    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(customerService.getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }
//
    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        final Customer customer = getCustomer(customerEmail);

        if (customer == null) {

            return Collections.emptyList();
        }

        return reservationService.getCustomersReservation(customer);
    }
//
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        Collection<IRoom> rooms = reservationService.findRooms(checkIn, checkOut);

        if (rooms == null) {
            Collection<IRoom> alternativeRooms = reservationService.findAlternativeRooms(checkIn, checkOut);
            if (alternativeRooms == null) {
                return null;
            }

            return alternativeRooms;
        }

        return rooms;
    }
}
