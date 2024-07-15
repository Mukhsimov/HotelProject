package uz.pdp.frontEnd.menus;

import uz.pdp.frontEnd.ConsoleUi;
import uz.pdp.frontEnd.utill.Context;
import uz.pdp.frontEnd.utill.Scan;

import java.util.concurrent.Executors;

import static uz.pdp.frontEnd.controller.BookingController.*;
import static uz.pdp.frontEnd.controller.UserController.*;

public class UserMenu {


    public static void userMenu() {

        while (true) {
            System.out.println("""
                    ================================
                    User Menu
                    1. My bookings
                    2. Active Bookings
                    3. Booking
                    4. Cancel Booking
                    5. History
                    6. Wallet
                    7. Settings
                    0. Log out
                    ================================""");

            switch (Scan.getInt("choice")) {
                case 1 -> myBookings();
                case 2 -> activeBookings();
                case 3 -> addBooking();
                case 4 -> cancelBooking(Scan.getStr("enter booking id"));
                case 5 -> history();
                case 6 -> balance();
                case 7 -> settings();
                case 0 -> {
                    System.out.println("Thank you bye, bye!");
                    Context.setUser(null);
                    System.exit(0);
                }
                default -> System.out.println("Command not found, please try again!");
            }
        }
    }

    public static void balance() {
        while (true) {
            System.out.println("""
                    ================================
                    Wallet
                    1. Show balance
                    2. Deposit
                    3. Withdraw money
                    0. Back to menu
                    ================================""");


            switch (Scan.getInt("choice")) {
                case 1 -> showBalance();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 0 -> {
                    UserMenu.userMenu();
                    return;
                }
                default -> throw new IllegalStateException("Command not found, please try again!");
            }
        }
    }

    public static void settings() {
        while (true) {
            switch (Scan.getInt("""
                    ================================
                    Settings
                    1. Show profile
                    2. Change name
                    3. Change surname
                    4. edit email
                    5. Change password
                    6. Delete account
                    0. Back to menu
                    ================================
                    choice""")) {
                case 1 -> showProfile();
                case 2 -> editName();
                case 3 -> editSurname();
                case 4 -> editEmail();
                case 5 -> editPassword();
                case 6 -> deleteAccount();
                case 0 -> {
                    UserMenu.userMenu();
                    return;
                }
            }
        }
    }


}
