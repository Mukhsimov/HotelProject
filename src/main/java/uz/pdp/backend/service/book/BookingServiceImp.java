package uz.pdp.backend.service.book;

import lombok.extern.java.Log;
import uz.pdp.backend.entity.Booking;
import uz.pdp.backend.entity.hotel.Hotel;
import uz.pdp.backend.enums.BookingStatus;
import uz.pdp.backend.enums.Paths;
import uz.pdp.backend.service.file.FileWriterAndLoader;
import uz.pdp.frontEnd.utill.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;


@Log
public class BookingServiceImp implements BookingService {
    private final FileWriterAndLoader<Booking> writerAndLoader;
    private static BookingService bookingService;

    public BookingServiceImp() {
        this.writerAndLoader = new FileWriterAndLoader<>(Paths.BOOKING_PATH);
    }

    public static BookingService getInstance() {
        if (bookingService == null) return new BookingServiceImp();
        else return bookingService;
    }

    @Override
    public void create(Booking booking) {
        List<Booking> bookings = writerAndLoader.fileLoader(Booking.class);
        if (bookings == null) bookings = new ArrayList<>();
        bookings.add(booking);
        writerAndLoader.fileWrite(bookings);
    }


    @Override
    public boolean update(Booking booking) {
        List<Booking> bookings = filterBooking(temp -> true);
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getID().equals(booking.getID())) {
                bookings.set(i, booking);
                writerAndLoader.fileWrite(bookings);
                return true;
            }
        }
        return false;
    }

    @Override
    public Booking get(String ID) {
        List<Booking> bookings = filterBooking((booking -> booking.getID().equals(ID)));
        if (bookings == null || bookings.isEmpty()) return null;
        else return bookings.getFirst();
    }

    @Override
    public boolean delete(String ID) {
        List<Booking> bookings = writerAndLoader.fileLoader(Booking.class);
        boolean b = bookings.removeIf(book -> book.getID().equals(ID));
        writerAndLoader.fileWrite(bookings);
        return b;
    }

    @Override
    public List<Booking> getList() {
        return writerAndLoader.fileLoader(Booking.class);
    }


    @Override
    public List<Booking> getUserBooks(String userID) {
        return filterBooking(book -> book.getCustomerID().equals(userID));
    }


    @Override
    public List<Booking> getUserActiveBooks(String userID) {
        return filterBooking(book -> book.getCustomerID().equals(userID) &&
                book.getCheckOutDate().isAfter(LocalDate.now()));
    }

    @Override
    public List<Booking> getUserHistory(String userID) {
        return filterBooking(book -> book.getCustomerID().equals(userID) &&
                                     book.getCheckOutDate().isBefore(LocalDate.now()));
    }

    @Override
    public List<Booking> getRoomBookings(int room, int floor) {
        return filterBooking(book -> book.getRoom().equals(room) &&
                                     book.getFloor().equals(floor));
    }


    @Override
    public List<Booking> getAllActiveBooks() {
        return filterBooking(book -> book.getStatus().equals(BookingStatus.ACTIVE));
    }

    @Override
    public List<Booking> getHistory() {
        return filterBooking(book -> book.getCheckOutDate().isBefore(LocalDate.now()));
    }


    private List<Booking> filterBooking(Predicate<Booking> filter) {
        List<Booking> bookings = writerAndLoader.fileLoader(Booking.class);
        if (bookings == null || bookings.isEmpty()){
            return new ArrayList<>();
        }
        return bookings.stream()
                .filter(filter)
                .toList();
    }



    /*
     *  booking creat update qilinishidan oldin booking check qilinishi kere
     * */
    public void checkBooking(Booking booking) {
        Hotel hotel = Context.getHotel();
        if (booking.getFloor() > hotel.getFloor() || booking.getFloor() < 1) {
            log.log(Level.WARNING, "floor {0} out of bounds", booking.getFloor());
            return;
        }
        if (booking.getRoom() > hotel.getRoom() || booking.getRoom() < 1) {
            log.log(Level.WARNING, "room {0} out of bounds", booking.getRoom());
            return;
        }
        if (booking.getCheckInDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("you cannot book passed days!");
        }
        if (booking.getCheckInDate().isAfter(booking.getCheckOutDate())) {
            throw new RuntimeException("start date should be before end date!");
        }
        List<Booking> earliestBookings = getRoomBookings(booking.getRoom(), booking.getFloor());
        if (earliestBookings
                .stream()
                .anyMatch(temp -> {
                    String customerID = temp.getCustomerID();
                    LocalDate fromDate = temp.getCheckInDate();
                    LocalDate toDate = temp.getCheckOutDate();
                    if (booking.getCheckInDate().isAfter(fromDate) && booking.getCheckInDate().isBefore(toDate))
                        return true;
                    if (booking.getCheckOutDate().isBefore(toDate) && booking.getCheckOutDate().isBefore(fromDate)) {
                        return true;
                    }
                    return booking.getCheckInDate().isBefore(fromDate) && booking.getCheckOutDate().isAfter(toDate);
                })) {
            throw new RuntimeException("This date already taken by another user");
        }
    }


}
