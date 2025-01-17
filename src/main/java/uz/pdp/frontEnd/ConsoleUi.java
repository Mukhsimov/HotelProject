package uz.pdp.frontEnd;

import uz.pdp.frontEnd.menus.AuthMenu;
import uz.pdp.frontEnd.utill.Scan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConsoleUi {


    public static void main(String[] args) {



        System.out.println("Welcome to Tashkent Hotel");
        while (true) {
            int select = Scan.getInt("""
                    ================================
                    1. Log in
                    2. Sign Up
                    0. Exit
                    ================================
                    choice""");
            switch (select) {
                case 1 -> AuthMenu.logIn();
                case 2 -> AuthMenu.signUp();
                case 0 -> {
                    System.out.println("Bye, bye!");
                    return;
                }
                default -> System.out.println("Unexpected value: " + select);
            }
        }


        // hona boshagandan keyin tozalanishiga yarm kun vaqt
        // lux hona, oddiy hona.

        //
        /* Hotel project
         * 1. Admin menu
         *   1. show rooms
         *   2. book for template    random choose / user choose
         *   3. cansel booking
         *   4. active bookings
         *   5. history
         *
         * 2. user menu
         *   1. my bookings         // show all bookings
         *   2. active bookings
         *   3. booking
         *   4. cancel booking
         *   3. history
         *   5. balance
         *       1. show balance
         *       2. deposit
         *
         * */


    }

    private static LocalDate parseBirth(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}