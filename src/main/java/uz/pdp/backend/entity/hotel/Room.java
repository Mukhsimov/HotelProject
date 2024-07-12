package uz.pdp.backend.entity.hotel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.pdp.backend.enums.RoomStatus;

import java.io.Serializable;


@Getter
@ToString
public class Room implements Serializable {
    private final Integer roomNumber;
    @Setter
    private RoomStatus status;
    public Room(Integer roomNumber) {
        this.roomNumber = roomNumber;
        this.status = RoomStatus.EMPTY;
    }
}
