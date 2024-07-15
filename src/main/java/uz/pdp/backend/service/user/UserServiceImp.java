package uz.pdp.backend.service.user;

import uz.pdp.backend.entity.user.User;
import uz.pdp.backend.enums.Constants;
import uz.pdp.backend.enums.UserStatus;
import uz.pdp.backend.service.file.FileWriterAndLoader;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImp implements UserService {
    private static UserService userService;
    private final FileWriterAndLoader<User> writerAndLoader;


    public static UserService getInstance() {
        if (userService == null) return new UserServiceImp();
        return userService;
    }

    private UserServiceImp() {
        this.writerAndLoader = new FileWriterAndLoader<>(Constants.USERS_PATH);
    }

    @Override
    public void create(User user) {
        List<User> users = filter(user1 -> true);
        if (users == null || users.isEmpty()) {
            users = new ArrayList<>();
            users.add(User.admin());
        }
        users.add(user);
        writerAndLoader.fileWrite(users);
    }

    @Override
    public boolean update(User user) {
        List<User> users = new ArrayList<>(filter(user1 -> true));
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getID().equals(user.getID())) {
                users.set(i, user);
                writerAndLoader.fileWrite(users);
                return true;
            }
        }
        return false;
    }

    @Override
    public User get(String ID) {
        List<User> user = filter(temp -> temp.getID().equals(ID));
        if (user == null || user.isEmpty()) return null;
        return user.getFirst();
    }


    private List<User> filter(Predicate<User> filter) {
        List<User> users = writerAndLoader.fileLoader(User.class);
        if (users == null || users.isEmpty()){
            return new ArrayList<>(List.of(User.admin()));
        }else return users.stream()
                .filter(filter)
                .toList();
    }


    @Override
    public boolean delete(String ID) {
        List<User> users = filter(user1 -> true);
        return users.removeIf(user -> user.getID().equals(ID));
    }

    @Override
    public List<User> getList() {
        return filter(user1 -> true);
    }


    @Override
    public User login(String email, String password) {
        List<User> filter = filter(user -> (user.getEmail().equals(email) &&
                                            user.getPassword().equals(password) &&
                                            user.getStatus().equals(UserStatus.ACTIVE)));
        if (filter == null || filter.isEmpty()  ) return null;
        return filter.getFirst();
    }


    /*
     * 200: valid email
     * 510: already exist email
     * 550: wrong email entered
     * */
    @Override
    public boolean isValidEmail(String email) {
        Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email.trim());
        if (!matcher.matches()) {
            return false;
        }
        List<User> userList = writerAndLoader.fileLoader(User.class);
        if (userList == null) {
            return true;
        }
        for (User user : userList) {
            if (user.getEmail().equals(email.trim())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isValidPassword(String password) {
        Pattern PASSWORD_PATTERN = Pattern.compile(
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,16}$"
        );
        if (password == null) {
            return false;
        }

        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }


}
