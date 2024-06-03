package edu.ihm.td1.livraison.userFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserMap {
    private static int id = 0;
    private static final HashMap<Integer, User> USERS = new HashMap<>();

    static {
        addUser(new ClientFactory().build());
        addUser(new LivreurFactory().build());
        addUser(new SavFactory().build());
    }

    public static void addUser(User user) {
        USERS.put(id++, user);
    }

    public static User getUser(int id) {
        return USERS.get(id);
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(USERS.values());
    }
}
