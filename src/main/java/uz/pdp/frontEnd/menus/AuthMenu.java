package uz.pdp.frontEnd.menus;

import uz.pdp.backend.entity.user.User;
import uz.pdp.backend.entity.user.cl.Wallet;
import uz.pdp.backend.enums.UserRole;
import uz.pdp.backend.service.mailing.MailingService;
import uz.pdp.backend.service.user.UserService;
import uz.pdp.backend.service.user.UserServiceImp;
import uz.pdp.frontEnd.ConsoleUi;
import uz.pdp.frontEnd.utill.Scan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import uz.pdp.frontEnd.utill.Context;


public class AuthMenu {
    private static final UserService userService = UserServiceImp.getInstance();
    private static final MailingService mailingService = new MailingService();


    public static void logIn() {
        System.out.println("Log in");
        String email = Scan.getStr("email");
        String password = Scan.getStr("password");
        User user = userService.login(email, password);
        if (user == null) {
            System.out.println("email or password is wrong");
        } else if (user.getRole().equals(UserRole.ADMIN)) {
            Context.setUser(user);
            AdminMenu.adminMenu();
        } else if (user.getRole().equals(UserRole.CUSTOMER)) {
            Context.setUser(user);
            UserMenu.userMenu();
        }
    }

    public static void signUp() {
        System.out.println("Registration");

        String firstName = Scan.getStr("Your first name");
        String lastName = Scan.getStr("Your last name");
        String email = getValidatedEmail();
        String password = getValidatedPassword();

        User user = new User(firstName, lastName, email, password);
        userService.create(user);
        Context.setUser(user);
        UserMenu.userMenu();
    }

    private static String getValidatedEmail() {
        for (int i = 0; i < 3; i++) {
            String email = Scan.getStr("Enter email");
            if (userService.isValidEmail(email)) {
                sendVerificationEmail(email);
                verifyEmail(email);
                return email;
            } else {
                System.out.println("You entered an invalid email");
            }
            if (i == 2) {
                throw new RuntimeException("Failed to enter a valid email");
            }
        }
        return null; // Handle this appropriately based on your application's needs
    }

    private static void sendVerificationEmail(String email) {
        mailingService.generateRandomNumber();
        mailingService.sendMessage(email, "Uzbekistan Hotel");
        System.out.println("Verification code sent to your email");
    }

    private static void verifyEmail(String email) {
        for (int i = 0; i < 3; i++) {
            if (mailingService.checkCode(Scan.getInt("verification code"))) {
                return;
            } else {
                System.out.println("Verification code is wrong");
            }
            if (i == 2) {
                throw new RuntimeException("Failed to verify email");
            }
        }
    }

    private static String getValidatedPassword() {
        for (int i = 0; i < 3; i++) {
            String password = Scan.getStr("Enter new password (8-16 characters, containing digits and letters)");
            if (userService.isValidPassword(password)) {
                String password2 = Scan.getStr("Enter password again");
                if (password.equals(password2)) {
                    return password;
                } else {
                    System.out.println("Passwords did not match");
                }
            } else {
                System.out.println("You entered a weak password");
            }
            if (i == 2) {
                throw new RuntimeException("Failed to set a valid password");
            }
        }
        return null; // Handle this appropriately based on your application's needs
    }


}
