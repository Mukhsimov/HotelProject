package uz.pdp.backend.service.hotel;

import uz.pdp.backend.entity.Booking;
import uz.pdp.backend.service.book.BookingService;
import uz.pdp.backend.service.book.BookingServiceImp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HotelService {
    private final BookingService bookingService;
    private static HotelService hotelService;

    public static HotelService getInstance() {
        if (hotelService == null) return new HotelService();
        return hotelService;
    }

    private HotelService() {
        bookingService = BookingServiceImp.getInstance();
    }

    public boolean isRoomBusy(int room, int floor, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> roomBookings = bookingService.getRoomBookings(room, floor);
        for (Booking booking : roomBookings) {
            if (checkIn.isBefore(booking.getCheckOutDate()) &&
                checkOut.isAfter(booking.getCheckInDate()))
                return false;
        }
        return true;
    }

    public LocalDate whenRoomAvailable(int room, int floor){
        List<Booking> roomBookings = bookingService.getRoomBookings(room, floor);
        LocalDate result = LocalDate.MIN;
        for (Booking roomBooking : roomBookings) {
            if (result.isAfter(roomBooking.getCheckOutDate()))result = roomBooking.getCheckOutDate();
        }
        return result;
    }





}
