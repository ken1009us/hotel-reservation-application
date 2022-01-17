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
                    break;

                }else if (userInput.equals("b")) {
                    showAllRooms();
                    break;

                }else if (userInput.equals("c")) {
                    showAllReservations();
                    break;

                }else if (userInput.equals("d")) {
                    addARoom();
                    break;

                }else if (userInput.equals("e")) {
                    MainMenu.menu();
                    break;
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
        System.out.println("\n Welcome! Admin!\n" +
                "Please select the number for the next step.\n" +
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
        }else {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    public static void showAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("Sorry, we don't find any rooms.");
        }else {
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        }
    }

    public static void showAllReservations() {
        adminResource.displayAllReservations();
    }

    public static void addARoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please provide a room number: ");
        String roomNumber = scanner.nextLine();

        System.out.println("Please provide the room price per night: ");
        Double roomPrice = parseRoomPrice(scanner);

        System.out.println("Please provide the room type: \n" + "1 for single bed, 2 for double bed: ");
        RoomType roomType = parseRoomType(scanner);

        Room room = new Room(roomNumber, roomPrice, roomType);

        adminResource.addRoom(Collections.singletonList(room));
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
            return RoomType.valueOfLabel(scanner.nextLine());
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid room type...");
            return parseRoomType(scanner);
        }
    }
}
