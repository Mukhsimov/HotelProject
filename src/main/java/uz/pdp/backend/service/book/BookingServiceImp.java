package uz.pdp.backend.service.book;

import uz.pdp.backend.entity.Booking;
import uz.pdp.backend.enums.Paths;
import uz.pdp.backend.service.file.FileWriterAndLoader;

import java.util.List;

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
        bookings.add(booking);
        writerAndLoader.fileWrite(bookings);
    }

    @Override
    public boolean update(Booking booking) {
        List<Booking> bookings = writerAndLoader.fileLoader(Booking.class);
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getID().equals(booking.getID())) {
                bookings.set(i, booking);
                return true;
            }
        }
        return false;
    }

    @Override
    public Booking get(String ID) {
        List<Booking> bookings = writerAndLoader.fileLoader(Booking.class);
        for (Booking booking : bookings) {
            if (booking.getID().equals(ID)) return booking;
        }
        return null;
    }

    @Override
    public boolean delete(String ID) {
        List<Booking> bookings = writerAndLoader.fileLoader(Booking.class);
        return bookings.removeIf(book -> book.getID().equals(ID));
    }

    @Override
    public List<Booking> getList() {
        return writerAndLoader.fileLoader(Booking.class);
    }
}
