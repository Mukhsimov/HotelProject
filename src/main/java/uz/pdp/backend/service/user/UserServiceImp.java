package uz.pdp.backend.service.user;

import lombok.NonNull;
import uz.pdp.backend.entity.user.User;
import uz.pdp.backend.enums.Paths;
import uz.pdp.backend.service.file.FileWriterAndLoader;


import java.util.ArrayList;
import java.util.List;

public class UserServiceImp implements UserService {
    private static UserService userService;
    private final FileWriterAndLoader<User> writerAndLoader;


    public static UserService getInstance() {
        if (userService == null) return new UserServiceImp();
        return userService;
    }

    private UserServiceImp() {
        this.writerAndLoader = new FileWriterAndLoader<>(Paths.USERS_PATH);
    }

    @Override
    public void create(User user) {
        List<User> users = writerAndLoader.fileLoader(User.class);
        if (users == null) {
            users = new ArrayList<>();
            users.add(User.admin());
        }
        users.add(user);
        writerAndLoader.fileWrite(users);
    }

    @Override
    public boolean update(User user) {
        List<User> users = writerAndLoader.fileLoader(User.class);
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
        List<User> users = writerAndLoader.fileLoader(User.class);
        for (User user : users) {
            if (user.getID().equals(ID)) return user;
        }
        return null;
    }

    @Override
    public boolean delete(String ID) {
        List<User> users = writerAndLoader.fileLoader(User.class);
        for (User user : users) {
            if (user.getID().equals(ID)) {
                users.remove(user);
                writerAndLoader.fileWrite(users);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> getList() {
        return writerAndLoader.fileLoader(User.class);
    }


    @Override
    public User LogIn(String username, String password) {
        List<User> userList = writerAndLoader.fileLoader(User.class);
        if (userList == null) return null;
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) return user;
        }
        return null;
    }


    /*
    * 200: valid username
    * 510: already exist username
    * 550: wrong username entered
    * */
    @Override
    public int isValidUsername(String username) {
        if (username.isEmpty() || username.isBlank() || username.contains(" ") ||
            !Character.isAlphabetic(username.charAt(0)) ||
            username.length() < 4)
            return 550;

        List<User> userList = writerAndLoader.fileLoader(User.class);
        if (userList == null) return 200;
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return 510;
            }
        }
        return 200;
    }

    @Override
    public boolean isValidPassword(String password) {
        return true;
    }


}
