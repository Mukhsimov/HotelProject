package uz.pdp.backend.entity.hotel;

import lombok.Getter;
import uz.pdp.backend.enums.Paths;

import java.io.*;

@Getter
public class Hotel implements Serializable {

    private final Room[][] rooms;
    private static Hotel hotel;

    private Hotel() {
        rooms = new Room[9][11];
        initializeRooms();
    }


    public static Hotel getInstance() {
        if (hotel == null) {
            hotel = loadHotelFromFile();
        }
        return hotel;
    }

    private void initializeRooms() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 11; j++) {
                int roomNumber = (i + 1) * 100 + (j + 1);
                rooms[i][j] = new Room(roomNumber);
            }
        }

    }

    public Room getRoom(int floor, int roomNumber) {
        return rooms[floor][roomNumber];
    }


    private static Hotel loadHotelFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Paths.HOTEL_DATA))) {
            return (Hotel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new Hotel();
        }
    }

    public void saveHotelToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Paths.HOTEL_DATA))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
