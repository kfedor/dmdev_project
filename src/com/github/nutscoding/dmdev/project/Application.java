package com.github.nutscoding.dmdev.project;

import com.github.nutscoding.dmdev.project.dao.UserDetailsDao;
import com.github.nutscoding.dmdev.project.dao.UsersDao;
import com.github.nutscoding.dmdev.project.entity.UserDetails;
import com.github.nutscoding.dmdev.project.entity.Users;

public class Application {
    public static void main(String[] args) {

    saveTest();
//    deleteTest();
    }

    private static void saveTest(){
        UserDetailsDao userDetailsDao = UserDetailsDao.getInstance();
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName("Test");
        userDetails.setLastName("Testov");
        userDetails.setEmail("test@test.com");
        userDetails.setAge(15);
        userDetails.setGender("undefined");

        UserDetails savedUserDetails = userDetailsDao.save(userDetails);

        UsersDao usersDao = UsersDao.getInstance();
        Users user = new Users();
        user.setLogin("testLogin");
        user.setPassword("testPassword");
        user.setUserTypeId(2);
        user.setUserDetails(savedUserDetails);

        Users savedUser = usersDao.save(user);

        System.out.println(savedUser);
    }

    private static void deleteTest(){
        UserDetailsDao userDetailsDao = UserDetailsDao.getInstance();
        boolean isDeleted = userDetailsDao.delete(6);
        System.out.println(isDeleted);
    }
}
