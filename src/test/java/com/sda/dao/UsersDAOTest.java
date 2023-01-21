package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class UsersDAOTest {

    private final UsersDAO usersDAO = new UsersDAO();

    @Test
    void testCreateHappyPath() {
        // given
        String expectedUsername = UUID.randomUUID().toString();

        User expectedUser = createUser(expectedUsername);

        // when
        usersDAO.create(expectedUser);

        // then
        User actualUser;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            actualUser = session.get(User.class, expectedUsername);
        }

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());

    }

    @Test
    void testDeleteByUsernameUserNotExist() {
        // given
        String nonExistingUsername = "NON EXISTING USER";

        // when
        boolean deleted = usersDAO.deleteByUsername(nonExistingUsername);

        // then
        Assertions.assertFalse(deleted);
    }

    @Test
    void testDeleteByUsernameUserExist() {
        // given
        String username = UUID.randomUUID().toString();
        User expectedUser = createUser(username);

        usersDAO.create(expectedUser);

        try (Session session = HibernateUtils.openSession()) {
            User user = session.get(User.class, username);
            Assertions.assertNotNull(user);
        }

        // when
        boolean deleted = usersDAO.deleteByUsername(username);

        // then
        Assertions.assertTrue(deleted);

        try (Session session = HibernateUtils.openSession()) {
            User user = session.get(User.class, username);
            Assertions.assertNull(user);
        }
    }

    @Test
    void testFindAllHappyPath() {
        // given
        String username1 = UUID.randomUUID().toString();
        String username2 = UUID.randomUUID().toString();

        List<User> expectedUsers = List.of(createUser(username1), createUser(username2));

        expectedUsers.forEach(usersDAO::create);

        // when
        List<User> actualUsers = usersDAO.findAll();

        // then
        Assertions.assertNotNull(actualUsers);
        Assertions.assertEquals(expectedUsers.size(), actualUsers.size());
        Assertions.assertIterableEquals(expectedUsers, actualUsers);
    }

    @Test
    void testFindUserByUsername() {
        // given
        String username = UUID.randomUUID().toString();
        User expectedUser = createUser(username);

        usersDAO.create(expectedUser);

        // when
        User actualUser = usersDAO.findByUsername(username);

        // then
        Assertions.assertNotNull(expectedUser);
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    @Test
    void testUpdateSuccess() {
        // give
        String username = UUID.randomUUID().toString();

        User user = createUser(username);
        usersDAO.create(user);

        User expectedUser = createUser(username);
        expectedUser.setName("changed name");
        expectedUser.setEmail("changed_email@gmail.com");

        // when
        usersDAO.update(expectedUser);

        //then
        User updatedUser;
        try (Session session = HibernateUtils.openSession()) {
            updatedUser = session.find(User.class, username);
        }

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(expectedUser, updatedUser);
        Assertions.assertEquals(expectedUser.getName(), updatedUser.getName());
        Assertions.assertEquals(expectedUser.getSurname(), updatedUser.getSurname());
        Assertions.assertEquals(expectedUser.getPassword(), updatedUser.getPassword());
        Assertions.assertEquals(expectedUser.getAge(), updatedUser.getAge());
        Assertions.assertEquals(expectedUser.getEmail(), updatedUser.getEmail());
    }

    @Test
    void testExistsByUsernameUserNotFound() {
        // give
        String nonExistingUsername = UUID.randomUUID().toString();

        // when
        boolean exists = usersDAO.existsByUsername(nonExistingUsername);

        //then
        Assertions.assertFalse(exists);
    }

    @Test
    void testExistsByUsernameUserExists() {
        // give
        String username = UUID.randomUUID().toString();
        User user = createUser(username);
        usersDAO.create(user);

        // when
        boolean exists = usersDAO.existsByUsername(username);

        //then
        Assertions.assertTrue(exists);
    }



    private static User createUser(String expectedUsername) {
        User expectedUser = User.builder()
                .username(expectedUsername)
                .password("password")
                .name("name")
                .surname("surname")
                .email("example@email.com")
                .age(30).build();
        return expectedUser;
    }
}
