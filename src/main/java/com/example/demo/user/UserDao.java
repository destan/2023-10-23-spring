package com.example.demo.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class UserDao {

    private final EntityManager entityManager;

    List<User> search(String searchTerm) {

        searchTerm = searchTerm.replace("İ", "i"); // Example business logic

        final TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.fullName LIKE :name OR u.email LIKE :email", User.class);

        query.setParameter("name","%" + searchTerm + "%");
        query.setParameter("email","%" + searchTerm + "%");

        final List<User> resultList = query.getResultList();

        // query.getSingleResult(); Don't forget to handle those exceptions: NoResultException, NonUniqueResultException

        return resultList;
    }

}
