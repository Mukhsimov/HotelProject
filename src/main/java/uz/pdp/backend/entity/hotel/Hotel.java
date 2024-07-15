package uz.pdp.backend.entity.hotel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.*;

@Getter
@Setter
@AllArgsConstructor
public class Hotel implements Serializable {

    private String name;
    private Integer floor;
    private Integer room;

}