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

    private final EmployeeDao employeeDao;

    @Autowired
    public UserDetailServiceImpl(final EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }


    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        final Employee employee = findByLogin(login);

        if (employee == null)
            throw new UsernameNotFoundException("Unknown user: " + login);

//        login = firstName.lastName
        return User.builder()
                .username(login)
                .password(employee.getPassword())
                .roles(employee.getRole())
                .build();
    }

    private Employee findByLogin(final String login) {
        final String firstName = login.split("\\.")[0];
        final String lastName = login.split("\\.")[1];

        return employeeDao.findByFirstNameAndLastName(firstName, lastName);
    }

}
