package uz.pdp.frontEnd.menus;

import uz.pdp.frontEnd.utill.Context;
import uz.pdp.frontEnd.utill.Scan;

import static uz.pdp.frontEnd.controller.BookingController.*;


public class AdminMenu {
    public static void adminMenu(){
        while (true){
            switch (Scan.getInt("""
                    ==============================
                    Admin menu
                    1 booking
                    2 cancel booking
                    3 history
                    4 active bookings
                    0 log out
                    ==============================
                    choose""")){
                case 0->{
                    System.out.println("bye bye " + Context.getUser().getFirstName());
                    Context.setUser(null);
                    return;
                }
                case 1-> addBooking();
                case 2-> cancelBooking(Scan.getStr("enter booking id"));
                case 3-> history();
                case 4-> activeBookings();
            }
        }
    }
}
