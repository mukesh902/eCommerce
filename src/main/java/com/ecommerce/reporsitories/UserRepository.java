package com.ecommerce.reporsitories;

import com.ecommerce.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByMobileNumber(String mobileNumber);

    @Query("SELECT u FROM Users u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.emailId) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "u.mobileNumber LIKE CONCAT('%', :keyword, '%')")
    List<Users> searchUsers(@Param("keyword") String keyword);

}
