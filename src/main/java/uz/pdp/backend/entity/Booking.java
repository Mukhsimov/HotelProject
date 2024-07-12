package uz.pdp.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@Getter

public class Booking extends BaseModel {
    private String customerID;
    private Integer floor;
    private Integer room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}