package uz.pdp.frontEnd.utill;

import uz.pdp.backend.entity.hotel.Hotel;
import uz.pdp.backend.entity.user.User;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;


public class Context {

    private static User user;
    public static User getUser() {
        return user;
    }
    public static void setUser(User currentUser) {
        user = currentUser;
    }
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static Hotel currentHotel = new Hotel("Uzbekistan", 17, 15);
    public static Hotel getHotel() {
        return currentHotel;
    }
    public static void setHotel(Hotel hotel) {
        currentHotel = hotel;
    }

}
