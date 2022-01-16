import api.HotelResource;

import model.IRoom;
import model.Reservation;

import java.util.Scanner;
import java.util.Date;
import java.util.Collection;

import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * @author Ken Wu
 *
 */
public class MainMenu {

    private static final String DATE_PATTERN = "MM/dd/yyyy";
    private static final HotelResource hotelResource = HotelResource.getInstance();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInput;

        menu();

        try {
            do {
                userInput = scanner.nextLine();

                if (userInput.equals("a")) {
                    findReserveRoom();
                    break;

                }else if (userInput.equals("b")) {
                    myReservations();
                    break;

                }else if (userInput.equals("c")) {
                    createAnAccount();
                    break;

                }else if (userInput.equals("d")) {
                    AdminMenu.adminMenu();
                    break;

                }else if (userInput.equals("e")) {
                    System.out.println("Exit the application.");

                }else {
                    System.out.println("Invalid action.\n");
                }
            } while (userInput.length() != 1 || !userInput.equals("e"));
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Please provide a valid number...");
        } finally {
            scanner.close();
        }
    }

    public static void menu() {
        System.out.println("\n Hello! This is a hotel reservation application!\n" +
                "Please select the number for the next step.\n" +
                "-----------------------------------------------------\n" +
                "a. Find and reserve a room\n" +
                "b. See my reservations\n" +
                "c. Create an account\n" +
                "d. Admin\n" +
                "e. Exit\n" +
                "-----------------------------------------------------\n");
    }

    public static void findReserveRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the check-in date: mm/dd/yyyy. Example: 03/20/2022.");
        Date checkInDate = parseDate(scanner);

        System.out.println("Please enter the check-out date: mm/dd/yyyy. Example: 03/25/2022.");
        Date checkOutDate = parseDate(scanner);

        if (checkInDate != null && checkOutDate != null) {
            Collection<IRoom> room = hotelResource.findARoom(checkInDate, checkOutDate);

            if (room.isEmpty()) {
                Collection<IRoom> alternativeRoom = hotelResource.findAlternativeRoom(checkInDate, checkOutDate);

                if (alternativeRoom.isEmpty()) {
                    System.out.println("Sorry, we don't have any rooms for you.");
                }else {
                    final Date alterCheckInDate = hotelResource.addDate(checkInDate);
                    final Date alterCheckOutDate = hotelResource.addDate(checkOutDate);
                    System.out.println("We only found the rooms between these dates...\n" +
                            "check-in date: " + alterCheckInDate +
                            "\n check-out date: " + alterCheckOutDate);

                    for (IRoom r : alternativeRoom) {
                        System.out.println(r);
                    }
                    reserveRoom(scanner, checkInDate, checkOutDate, alternativeRoom);
                }
            }else {
                for (IRoom r : room) {
                    System.out.println(r);
                }
                reserveRoom(scanner, checkInDate, checkOutDate, room);
            }
        }
    }

    public static Date parseDate(Scanner scanner) {
        try {
            return new SimpleDateFormat(DATE_PATTERN).parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Please provide the invalid date.");
            findReserveRoom();
        }

        return null;
    }

    public static void reserveRoom(Scanner scanner, Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Would you like to book a room?\n" +
                "Please type y or n.\n");
        final String bookForRoom = scanner.nextLine();

        if (bookForRoom.equals("y") || bookForRoom.equals("Y")) {
            System.out.println("Do you have an account?\n" +
                    "Please type y or n.\n");
            final String haveAccount = scanner.nextLine();

            if (haveAccount.equals("y") || haveAccount.equals("Y")) {
                System.out.println("Please enter your email: ");
                final String customerEmail = scanner.nextLine();

                if (hotelResource.getCustomer(customerEmail) == null) {
                    System.out.println("Error: we can't find the data.\n");
                }else {
                    System.out.println("Which room do you want to book?\n" +
                            "Please provide the room number.");

                    final String roomNumber = scanner.nextLine();
                    if (rooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))) {
                        final IRoom room = hotelResource.getRoom(roomNumber);

                        final Reservation reservation = hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
                        System.out.println("You successfully booked a room.\n" +
                                "Thank you!\n");
                        System.out.println(reservation);
                    }else {
                        System.out.println("We cannot find the room. The room number is invalid.");
                    }
                }

                menu();
            }else {
                System.out.println("Please create an account...");
                menu();
            }
        } else if (bookForRoom.equals("n") || bookForRoom.equals("N")) {
            menu();
        } else {
            reserveRoom(scanner, checkInDate, checkOutDate, rooms);
        }
    }

    public static void myReservations() {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your email: ");
        final String customerEmail = scanner.nextLine();

        printReservations(hotelResource.getCustomersReservations(customerEmail));
    }

    public static void printReservations(Collection<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("Sorry, we don't find any reservations.");
        }else {
            reservations.forEach(reservation -> System.out.println("\n" + reservation));
        }
    }

    public static void createAnAccount() {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your email: ");
        final String newEmail = scanner.nextLine();

        System.out.println("Please enter your first name: ");
        final String firstName = scanner.nextLine();

        System.out.println("Please enter your last name: ");
        final String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(newEmail, firstName, lastName);
            System.out.println("You successfully created an account.");

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createAnAccount();

        } finally {
            scanner.close();
        }

    }


}