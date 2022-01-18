import api.AdminResource;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getINSTANCE();

    public static void adminMenu() {

        Scanner scanner = new Scanner(System.in);

        String userInput;

        menu();

        try {
            do {
                userInput = scanner.nextLine();

                if (userInput.equals("a")) {
                    showAllCustomers();

                }else if (userInput.equals("b")) {
                    showAllRooms();

                }else if (userInput.equals("c")) {
                    showAllReservations();

                }else if (userInput.equals("d")) {
                    addARoom();

                }else if (userInput.equals("e")) {
                    MainMenu.mainMenu();
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
        System.out.println("\nWelcome! Admin!\n" +
                "Please enter a letter for the next step.\n" +
                "-----------------------------------------------------\n" +
                "a. See all Customers\n" +
                "b. See all Rooms\n" +
                "c. See all Reservations\n" +
                "d. Add a Room\n" +
                "e. Back to Main Menu\n" +
                "-----------------------------------------------------\n");
    }

    public static void showAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("Sorry, we don't find any customers.");
            adminMenu();
        }else {
            // This is wrong way to print the data, since this will print the memory position.
            // for (Customer customer : customers) {
            // System.out.println(customer);
            // }

            adminResource.getAllCustomers().forEach(x -> System.out.println(x));
            adminMenu();
        }
    }

    public static void showAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("Sorry, we don't find any rooms.");
            adminMenu();
        }else {
            adminResource.getAllRooms().forEach(x -> System.out.println(x));
            adminMenu();
        }
    }

    public static void showAllReservations() {
        adminResource.displayAllReservations();
        adminMenu();
    }

    public static void addARoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please provide a room number: ");
        String roomNumber = scanner.nextLine();

        System.out.println("Please provide the room price per night: ");
        Double roomPrice = parseRoomPrice(scanner);

        System.out.println("Please provide the room type: \n" + "SINGLE or DOUBLE: ");
        RoomType roomType = parseRoomType(scanner);

        Room room = new Room(roomNumber, roomPrice, roomType);

        adminResource.addRoom(Collections.singletonList(room));
        System.out.println("You successfully created a room.");
        adminMenu();
    }

    public static Double parseRoomPrice(Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid room price...");
            return parseRoomPrice(scanner);
        }
    }

    public static RoomType parseRoomType(Scanner scanner) {
        try {
            return RoomType.roomType(scanner.nextLine());
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid room type...");
            return parseRoomType(scanner);
        }
    }
}
