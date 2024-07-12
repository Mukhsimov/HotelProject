package uz.pdp.frontEnd.menus;

import uz.pdp.backend.entity.user.User;
import uz.pdp.backend.entity.user.cl.Wallet;
import uz.pdp.backend.enums.UserRole;
import uz.pdp.backend.service.user.UserService;
import uz.pdp.frontEnd.utill.Scan;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static uz.pdp.frontEnd.ConsoleUi.userService;
import static uz.pdp.frontEnd.ConsoleUi.bookingService;
import static uz.pdp.frontEnd.ConsoleUi.curUser;

public class AuthMenu {



    public static void logIn(){
        System.out.println("Log in");
        String username = Scan.getStr("username");
        String password = Scan.getStr("password");
        User user = userService.LogIn(username, password);
        if (user == null) {
            System.out.println("username or password is wrong");
        } else if (user.getRole().equals(UserRole.ADMIN)) {
            curUser = user;
            AdminMenu.adminMenu();
        } else if (user.getRole().equals(UserRole.CUSTOMER)){
            curUser = user;
            UserMenu.userMenu();
        }


    }
    public static void signUp(){
        System.out.println("Registration");

        String firstName = Scan.getStr("Your first name");
        String lastName = Scan.getStr("Your last name");

        String username = Scan.getStr("Your username name");
        int validUsername = userService.isValidUsername(username);


        String password = Scan.getStr("enter new password \n(password length 8-16, 0-9, A-Z,a-z, !#$&* )");

        boolean validPassword = userService.isValidPassword(password);
        

        String password2 = Scan.getStr("enter password again");
        if (!password.equals(password2)){
            System.out.println("wrong passwords entered");
            signUp();
        }
        String birthdate = Scan.getStr("enter your birthday (dd.mm.yyyy)");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthday = LocalDate.parse(birthdate, formatter);


        User user = new User(firstName, lastName, username, password, birthday, new Wallet());
        userService.create(user);
        curUser = user;
        UserMenu.userMenu();
    }
}
