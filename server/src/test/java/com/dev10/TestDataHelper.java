package com.dev10;

import com.dev10.models.User;

import java.util.List;

public class TestDataHelper {
    public static List<User> getAllUsers(){
        User user1 = new User();
        User user2 = new User();

        user1.setId(1);
        user1.setEmail("a@a.com");
        user1.setPassword("a");

        user2.setId(2);
        user2.setEmail("b@b.com");
        user2.setPassword("b");

        return List.of(
                user1,
                user2
        );
    }

    public static User getUserNotInDatabase(){
        User user = new User();
        user.setId(0);
        user.setEmail("c@c.com");
        user.setPassword("c");

        return user;
    }
}
