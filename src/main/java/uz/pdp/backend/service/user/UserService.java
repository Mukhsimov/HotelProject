package uz.pdp.backend.service.user;

import uz.pdp.backend.entity.user.User;
import uz.pdp.backend.service.BaseService;

public interface UserService extends BaseService<User> {
    User LogIn(String username, String password);

    int isValidUsername(String username);

    boolean isValidPassword(String password);

}
