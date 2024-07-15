package uz.pdp.backend.service.book;

import uz.pdp.backend.entity.Booking;
import uz.pdp.backend.service.BaseService;

import java.util.List;
import java.util.function.Predicate;

public interface BookingService extends BaseService<Booking> {

    List<Booking> getUserBooks(String userID);
    List<Booking> getUserActiveBooks(String userID);
    List<Booking> getUserHistory(String userID);

    List<Booking> getRoomBookings(int room, int floor);

    void checkBooking(Booking booking);

    List<Booking> getAllActiveBooks();

    List<Booking> getHistory();

}
