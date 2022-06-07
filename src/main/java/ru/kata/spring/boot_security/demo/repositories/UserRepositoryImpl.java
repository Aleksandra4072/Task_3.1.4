package ru.kata.spring.boot_security.demo.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> allUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void removeUser(long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getById(long id) {
        return (User)entityManager.createQuery("select u from User u join fetch u.id where u.id=:usrId")
                .setParameter("usrId", id).getSingleResult();
    }

    @Override
    public User getByUsername(String username) {
        return (User)entityManager.createQuery("select u from User u join fetch u.username where u.username=:username")
                .setParameter("username", username).getSingleResult();
    }
}
