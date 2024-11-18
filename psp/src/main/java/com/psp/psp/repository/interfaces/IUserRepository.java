package com.psp.psp.repository.interfaces;

import com.psp.psp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
}
