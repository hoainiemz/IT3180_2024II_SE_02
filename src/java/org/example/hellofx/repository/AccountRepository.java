package org.example.hellofx.repository;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsernameAndPasswordHash(String username, String passwordHash);
    Optional<Account> findByUserId(int userId);

    @Modifying
    @Transactional
    @Query("UPDATE Account SET passwordHash = :newPassword WHERE username = :username")
    int updatePasswordByUsername(@Param("username") String username, @Param("newPassword") String newPassword);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Modifying
    @Transactional
    @Query("UPDATE Account SET role = :role, email = :email, phone = :phone WHERE userId = :userId")
    int updateRowByUserId(
            @Param("userId") Integer userId,
            @Param("role") AccountType role,
            @Param("email") String email,
            @Param("phone") String phone
    );
}
