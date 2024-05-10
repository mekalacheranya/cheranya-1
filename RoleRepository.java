package com.oosd.vstudent.repositories;

import com.oosd.vstudent.models.Role;
import com.oosd.vstudent.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findRoleByRole(@Param("role") String role);
}
