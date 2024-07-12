package uz.pdp.backend.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.backend.entity.BaseModel;
import uz.pdp.backend.entity.user.cl.Address;
import uz.pdp.backend.entity.user.cl.Contact;
import uz.pdp.backend.entity.user.cl.Wallet;
import uz.pdp.backend.enums.UserRole;

import java.time.LocalDate;


@Getter
public class User extends BaseModel {
    private String firstName;
    private String lastName;
    private String username;
    @Setter
    private String password;
   // private LocalDate birthday;
    private UserRole role;
    private Wallet wallet;

    public User(String firstName, String lastName, String username, String password,
                LocalDate birthday,  Wallet wallet) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
     //   this.birthday = birthday;
        this.wallet = wallet;
        this.role = UserRole.CUSTOMER;
    }

    private User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.ADMIN;
    }



    private static User admin;
    public static User admin() {
        if (admin == null) {
            admin = new User("Admin", "Admin");
        }
        return admin;
    }

}
