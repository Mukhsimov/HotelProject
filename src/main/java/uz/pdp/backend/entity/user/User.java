package uz.pdp.backend.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.pdp.backend.entity.BaseModel;
import uz.pdp.backend.entity.user.cl.Wallet;
import uz.pdp.backend.enums.UserRole;
import uz.pdp.backend.enums.UserStatus;

import java.time.LocalDate;


@Getter
@ToString

public class User extends BaseModel {
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String email;
    @Setter
    private String password;
    private UserRole role;
    private Wallet wallet;
    @Setter
    private UserStatus status;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.wallet = new Wallet();
        this.role = UserRole.CUSTOMER;
        this.status = UserStatus.ACTIVE;
    }

    private User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = UserRole.ADMIN;
        this.status = UserStatus.ACTIVE;
    }



    private static User admin;
    public static User admin() {
        if (admin == null) {
            admin = new User("Admin", "Admin");
        }
        return admin;
    }

}
