package uz.pdp.frontEnd.controller;

import com.sun.tools.javac.Main;
import uz.pdp.backend.entity.Booking;
import uz.pdp.backend.entity.user.User;
import uz.pdp.backend.entity.user.cl.Wallet;
import uz.pdp.backend.enums.UserStatus;
import uz.pdp.backend.service.book.BookingService;
import uz.pdp.backend.service.book.BookingServiceImp;
import uz.pdp.backend.service.mailing.MailingService;
import uz.pdp.backend.service.user.UserService;
import uz.pdp.backend.service.user.UserServiceImp;
import uz.pdp.frontEnd.ConsoleUi;
import uz.pdp.frontEnd.menus.AuthMenu;
import uz.pdp.frontEnd.menus.UserMenu;
import uz.pdp.frontEnd.utill.Context;
import uz.pdp.frontEnd.utill.Scan;

import java.util.List;


public class UserController {
    private static final UserService userService = UserServiceImp.getInstance();

    public static void deposit() {
        showBalance();
        Wallet wallet = Context.getUser().getWallet();
        wallet.depositUZS(Scan.getDouble("enter the deposit amount"));
        System.out.println("Your balance has been topped up");
        userService.update(Context.getUser());
    }

    public static void showBalance() {
        Wallet wallet = Context.getUser().getWallet();
        System.out.println("================================\n" +
                           "your Balance\n" +
                           wallet.getUZS() + " UZS\n" +
                           "================================");
    }

    public static void withdraw() {
        double money = Scan.getDouble("enter the amount to withdraw the money");
        Wallet wallet = Context.getUser().getWallet();
        wallet.withdrawUZS(money);
        System.out.println("money was successfully withdrawn");
        userService.update(Context.getUser());
    }

    // user


    public static void showBooks(List<Booking> userActiveBooks) {
        System.out.println("==============================");
        int i = 1;
        for (Booking booking : userActiveBooks) {
            if (!booking.isTemporary()) {
                User user = userService.get(booking.getCustomerID());
                System.out.println(i + ". full name: " + user.getFirstName() + "  " + user.getLastName());

            }else {
                System.out.println(i + ". full name: " + booking.getFullName());
            }
            System.out.println( "Floor: " + booking.getFloor() + "\tRoom:" + booking.getRoom()
                               + "\nFrom: " + booking.getCheckInDate() + "\tTo: " + booking.getCheckOutDate()
                               + "\nStatus: " + booking.getStatus() + '\n');
            i++;
        }
        System.out.println("==============================");
    }


    public static void deleteAccount() {
        User user = Context.getUser();
        user.setStatus(UserStatus.DELETED);
        userService.update(user);
        Context.setUser(null);
        System.out.println("your account deleted!");
        ConsoleUi.main(new String[]{});
    }

    public static void editPassword() {
        String password = Scan.getStr("Enter new password (8-16 characters, containing digits and letters)");
        if (userService.isValidPassword(password)) {
            String password2 = Scan.getStr("Enter password again");
            if (password.equals(password2)) {
                Context.getUser().setPassword(password);
                userService.update(Context.getUser());
                System.out.println("your password is changed");
            } else System.out.println("You did not enter the same password");


        } else System.out.println("You entered an incorrect password");
    }

    public static void editEmail() {
        String email = Scan.getStr("enter new email address");
        boolean validEmail = userService.isValidEmail(email);
        if (validEmail) {
            MailingService mailingService = new MailingService();
            mailingService.generateRandomNumber();
            mailingService.sendMessage(email, "Uzbekistan Hotel");
            System.out.println("verification code is sent your email");

            if (mailingService.checkCode(Scan.getInt("verification code"))) {
                Context.getUser().setEmail(email);
                userService.update(Context.getUser());
                System.out.println("your email is changed");
            }

        }

    }

    public static void editSurname() {
        String lastName = Scan.getStr("last name");
        Context.getUser().setLastName(lastName);
        userService.update(Context.getUser());
        System.out.println("your surname is changed");
    }

    public static void editName() {
        String name = Scan.getStr("first name");
        Context.getUser().setFirstName(name);
        userService.update(Context.getUser());
        System.out.println("your name is changed");
    }


    public static void showProfile() {
        User user = Context.getUser();
        System.out.printf("""
                        ================================
                        Profile
                        first name: %s
                        last name: %s
                        email: %s
                        ================================""",
                user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
