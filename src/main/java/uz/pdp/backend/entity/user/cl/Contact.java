package uz.pdp.backend.entity.user.cl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Contact {
    private String passportID;
    private String phoneNumber;
    private String email;
}
