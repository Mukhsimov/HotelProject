package uz.pdp.frontEnd.menus;

import uz.pdp.frontEnd.utill.Scan;

import static uz.pdp.frontEnd.ConsoleUi.curUser;
import static uz.pdp.frontEnd.ConsoleUi.bookingService;
import static uz.pdp.frontEnd.ConsoleUi.userService;

public class UserMenu {

    public static void userMenu() {

        while (true) {
            System.out.println("""
                    User Menu
                    ================================
                    1. My bookings
                    2. Active Bookings
                    3. Booking
                    4. Cancel Booking
                    5. History
                    6. Balance
                    7. Profile
                    0. Log out
                    ================================""");
            int command = Scan.getInt("choice");

            switch (command) {
                case 1 -> {
                    myBookings();
                }
                case 2 -> {
                    activeBookings();
                }
                case 3 -> {
                    booking();
                }
                case 4 -> {
                    cancelBooking();
                }
                case 5 -> {
                    history();
                }
                case 6 -> {
                    balance();
                }
                case 7 -> {
                    profile();
                }
                case 0 -> {
                    System.out.println("Thank you bye, bye!");
                    curUser = null;
                    return;
                }
                default -> {
                    System.out.println("Command not found, please try again!");
                }
            }
        }
    }

    private static void booking() {

    }

    private static void cancelBooking() {

    }

    private static void history() {

    }

    private static void balance() {
        // show balance
        // deposit
    }

    private static void profile() {

    }

    private static void activeBookings() {

    }

    private static void myBookings() {

    }
}
