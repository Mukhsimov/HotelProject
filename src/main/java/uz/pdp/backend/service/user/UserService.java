package uz.pdp.backend.service.user;

import uz.pdp.backend.entity.user.User;
import uz.pdp.backend.service.BaseService;

public interface UserService extends BaseService<User> {
    User login(String email, String password);

    boolean isValidEmail(String email);

    boolean isValidPassword(String password);

}
