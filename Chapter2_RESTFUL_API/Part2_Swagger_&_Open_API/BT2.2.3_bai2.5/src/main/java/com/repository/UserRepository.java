package com.repository;

import com.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    private final AtomicInteger counter = new AtomicInteger(0);
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public UserRepository() {
        addUser(new User(null, "John Doe"));
        addUser(new User(null, "Alice Nguyen"));
    }

    public User addUser(User user) {
        int id = counter.incrementAndGet();
        User saved = new User(id, user.getName());
        users.put(id, saved);
        return saved;
    }

    public List<User> findAll() {
        return users.values().stream()
                .sorted(Comparator.comparing(User::getId))
                .map(user -> new User(user.getId(), user.getName()))
                .toList();
    }

    public User findById(int id) {
        User user = users.get(id);
        return user == null ? null : new User(user.getId(), user.getName());
    }

    public User update(int id, String name) {
        User current = users.get(id);
        if (current == null) {
            return null;
        }
        User updated = new User(id, name);
        users.put(id, updated);
        return updated;
    }

    public boolean delete(int id) {
        return users.remove(id) != null;
    }
}
