package uz.pdp.frontEnd.controller;


import uz.pdp.backend.entity.Booking;
import uz.pdp.backend.entity.user.User;
import uz.pdp.backend.enums.BookingStatus;
import uz.pdp.backend.enums.UserRole;
import uz.pdp.backend.service.book.BookingService;
import uz.pdp.backend.service.book.BookingServiceImp;
import uz.pdp.frontEnd.utill.Context;
import uz.pdp.frontEnd.utill.Scan;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static uz.pdp.frontEnd.controller.UserController.showBooks;
import static uz.pdp.frontEnd.utill.Context.formatter;


public class BookingController {
    private static final BookingService bookingService = BookingServiceImp.getInstance();

    public static void addBooking() {
        Booking booking = new Booking();

        if (Context.getUser().getRole().equals(UserRole.ADMIN)) {
            booking.setFullName(Scan.getStr("enter full name of user"));
            booking.setTemporary(true);

        } else {
            booking.setCustomerID(Context.getUser().getID());
        }

        booking.setFloor(Scan.getInt("enter floor number"));
        booking.setRoom(Scan.getInt("enter room number"));
        try {
        LocalDate start = LocalDate.parse(Scan.getStr("enter start date as (dd.mm.yyyy)"), formatter);
        LocalDate end = LocalDate.parse(Scan.getStr("enter end date as (dd.mm.yyyy)"), formatter);
        booking.setCheckInDate(start);
        booking.setCheckOutDate(end);

        } catch (DateTimeParseException e) {
            System.out.println("You wrong date entered");
            return;
        }
        booking.setStatus(BookingStatus.ACTIVE);
        bookingService.checkBooking(booking);
        bookingService.create(booking);
    } //    admin/user


    public static void editBooking() {
        String id = Scan.getStr("enter booking id to edit");
        Booking booking = bookingService.get(id);

        if (booking == null) throw new RuntimeException("booking is not found");

        int floor = Scan.getInt("enter floor number");
        int room = Scan.getInt("enter room number");
        LocalDate fromDate = (LocalDate.parse(Scan.getStr("enter start date as (dd.mm.yyyy)"), formatter));
        LocalDate toDate = (LocalDate.parse(Scan.getStr("enter end date as (dd.mm.yyyy)"), formatter));

        booking.setFloor(floor);
        booking.setRoom(room);
        booking.setCheckInDate(fromDate);
        booking.setCheckOutDate(toDate);
        booking.setStatus(BookingStatus.MODIFIED);
        bookingService.checkBooking(booking);
        bookingService.update(booking);
        System.out.println("booking updated successfully");
    } //    user

    public static void deleteBooking() {
        String id = Scan.getStr("enter booking id to delete");

        User user = Context.getUser();
        Booking booking = bookingService.get(id);

        if (!user.getRole().equals(UserRole.ADMIN))
            if (!user.getID().equals(booking.getCustomerID()))
                throw new RuntimeException("booking is not found");


        boolean delete = bookingService.delete(id);
        if (delete) {
            System.out.println("booking is deleted");
        }
        throw new RuntimeException("booking is not found");
    }   //  admin/user


    public static void cancelBooking(String id) {
        Booking booking = bookingService.get(id);
        if (booking == null){
            System.out.println("booking is not found");
            return;
        }
        if (!Context.getUser().getRole().equals(UserRole.ADMIN))
            if (!booking.getCustomerID().equals(Context.getUser().getID()))
                throw new RuntimeException("Booking not found");
        booking.setStatus(BookingStatus.CANCELED);
        bookingService.update(booking);
    }   //    admin/user

    public static void history() {
        List<Booking> userHistory = new ArrayList<>();
        if (Context.getUser().getRole().equals(UserRole.ADMIN))
            userHistory = bookingService.getHistory();
        else if (Context.getUser().getRole().equals(UserRole.CUSTOMER))
            userHistory = bookingService.getUserHistory(Context.getUser().getID());
        if (userHistory.isEmpty()) System.out.println("histories is empty");
        else{
            System.out.println("History");
            showBooks(userHistory);
        }
    }   //   admin/user


    public static void activeBookings() {
        List<Booking> activeBookings = new ArrayList<>();
        if (Context.getUser().getRole().equals(UserRole.ADMIN)) {
            activeBookings = bookingService.getAllActiveBooks();
        } else if (Context.getUser().getRole().equals(UserRole.CUSTOMER)) {
            activeBookings = bookingService.getUserActiveBooks(Context.getUser().getID());
        }
        if (activeBookings.isEmpty()) {
            System.out.println("active bookings are not yet available");
        } else {
            showBooks(activeBookings);
            System.out.println("Active Bookings");
        }
    }   //  admin/User

    public static void myBookings() {
        List<Booking> userAllBookings = bookingService.getUserBooks(Context.getUser().getID());
        if (userAllBookings.isEmpty()) System.out.println("you haven't occupied the room yet");
        else {
            System.out.println("All Bookings");
            showBooks(userAllBookings);
        }

    }   // user
}
