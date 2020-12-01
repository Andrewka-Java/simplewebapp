package com.mastery.java.task.security;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Employee employee = findByLogin(login);

        if (employee == null)
            throw new UsernameNotFoundException("Unknown user: " + login);

//        login = firstName.lastName
        UserDetails user = User.builder()
                .username(login)
                .password(employee.getPassword())
                .roles(employee.getRole())
                .build();

        return user;
    }

    private Employee findByLogin(String login) {
        String firstName = login.split("\\.")[0];
        String lastName = login.split("\\.")[1];
        Employee employee = employeeDao.findByFirstNameAndLastName(firstName, lastName);

        return employee;
    }

}
