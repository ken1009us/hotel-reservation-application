package api;

import java.util.List;
import java.util.Collection;

import model.Customer;
import model.IRoom;

import service.CustomerService;
import service.ReservationService;

/**
 * @author Ken Wu
 *
 */
public class AdminResource {

    //provide a static reference
    private static AdminResource INSTANCE;

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static AdminResource getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new AdminResource();
        }
        return INSTANCE;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom r : rooms) {
            reservationService.addRoom(r);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
