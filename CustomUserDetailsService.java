package com.oosd.vstudent.security;

import com.oosd.vstudent.models.Student;
import com.oosd.vstudent.services.DatabaseService;
import org.hibernate.dialect.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DatabaseService databaseService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Student> student = databaseService.getStudentRepository().findByUsername(s);
        student.orElseThrow(() -> new UsernameNotFoundException("Not found user : " + s));
        return student.map(CustomUserDetails::new).get();
    }
}
