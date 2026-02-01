package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(User user, User friend) {
        Set<Long> userFriends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();

        userFriends.add(friend.getId());
        friendFriends.add(user.getId());

        user.setFriends(userFriends);
        friend.setFriends(friendFriends);

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public void deleteFriend(User user, User friend) {
        Set<Long> userFriends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();

        userFriends.remove(friend.getId());
        friendFriends.remove(user.getId());

        user.setFriends(userFriends);
        friend.setFriends(friendFriends);

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public Set<Long> getAllFriends(User userOne, User userTwo) {
        Set<Long> commonFriends = new HashSet<>(userOne.getFriends());
        commonFriends.retainAll(userTwo.getFriends());

        return commonFriends;
    }
}
