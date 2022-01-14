package service;

import model.IRoom;
import model.Reservation;
import model.Customer;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Date;
import java.util.Calendar;
import java.util.stream.Collectors;

/**
 * @author Ken Wu
 *
 */
public class ReservationService {

    // provide a static reference
    private static ReservationService INSTANCE;

    private static final int PLUS_DAYS = 10;

    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    // provide a static reference
    public static ReservationService getInstance() {
        if (INSTANCE == null){
            INSTANCE = new ReservationService();
        }
        return INSTANCE;
    }

    public void addRoom(final IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(final String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(final Customer customer, final IRoom room, final Date checkInDate, final Date checkOutDate) {
        final Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        Collection<Reservation> customerReservations = getCustomersReservation(customer);

        if (customerReservations == null) {
            customerReservations = new LinkedList<>();
        }

        //Linked List of customer reservations
        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);

        return reservation;
    }

    public Collection<IRoom> findRooms(final Date checkInDate, final Date checkOutDate) {
        return findAvailableRooms(checkInDate, checkOutDate);
    }

    public Collection<IRoom> findAlternativeRooms(final Date checkInDate, final Date checkOutDate) {
        return findAvailableRooms(addDate(checkInDate), addDate(checkOutDate));
    }

    public Date addDate(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, PLUS_DAYS);

        return calendar.getTime();
    }

    public Collection<IRoom> findAvailableRooms(final Date checkInDate, final Date checkOutDate) {
        final Collection<Reservation> allReservations = getAllReservation();
        final Collection<IRoom> notAvailableRooms = new LinkedList<>();

        for (Reservation reservation : allReservations) {
            if (reservationOverlaps(reservation, checkInDate, checkOutDate)) {
                notAvailableRooms.add(reservation.getRoom());
            }
        }

        // A stream is a sequence of objects that supports various methods which
        // can be pipelined to produce the desired result.
        // It takes input from the Collections, Arrays or I/O channels.

        // The filter method is used to select elements as per the Predicate passed as argument.
        // The collect method is used to return the result of the intermediate operations performed on the stream.
        return rooms.values().stream().filter(room -> notAvailableRooms.stream()
                .noneMatch(notAvailableRoom -> notAvailableRoom.equals(room)))
                .collect(Collectors.toList());
    }

    public boolean reservationOverlaps(final Reservation reservation, final Date checkInDate,
                                                 final Date checkOutDate) {
        return checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate());
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public void printAllReservation() {
        final Collection<Reservation> reservations = getAllReservation();

        if(reservations.isEmpty()) {
            System.out.println("There are no reservations found.");
        } else {
            for(Reservation reservation : reservations) {
                System.out.println(reservation + "\n");
            }
        }
    }

    public Collection<Reservation> getAllReservation() {
        final Collection<Reservation> allReservations = new LinkedList<>();

        for(Collection<Reservation> reservation : reservations.values()) {
            allReservations.addAll(reservation);
        }

        return allReservations;
    }
}
