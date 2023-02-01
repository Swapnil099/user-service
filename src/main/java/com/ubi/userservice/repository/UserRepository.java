package com.ubi.userservice.repository;

import com.ubi.userservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(
            value = "SELECT * FROM user_details",
            nativeQuery = true)
    Page<User> getAllUser(Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE username = ?1",
            nativeQuery = true)
    Page<User> getAllUserByUsername(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE is_enabled = ?1",
            nativeQuery = true)
    Page<User> getAllUserByIsEnabled(Boolean fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE first_name = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByFirstName(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE last_name = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByLastName(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE concat_ws(' ',first_name,last_name) like CONCAT('%', CONCAT(?1, '%')))",
            nativeQuery = true)
    Page<User> getAllUserByFullName(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE middle_name = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByMiddleName(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE nationality = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByNationality(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE gender = ?1)",
            nativeQuery = true)
        Page<User> getAllUserByGender(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE aadhar_card_number = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByAadhar(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE address like CONCAT('%', CONCAT(?1, '%')))",
            nativeQuery = true)
    Page<User> getAllUserByAddress(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE age = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByAge(Integer fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE blood_group = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByBloodGroup(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE contact_number = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByContactNumber(String fieldQuery,Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE dob = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByDOB(Date fieldQuery, Pageable page);

    @Query(
            value = "SELECT * FROM user_details WHERE user_details.contact_id IN (SELECT id FROM contact_info WHERE email = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByEmail(String fieldQuery, Pageable page);

    @Query(
            value = "    SELECT * FROM user_details WHERE role_id IN ( SELECT id FROM role WHERE role_type = ?1)",
            nativeQuery = true)
    Page<User> getAllUserByRole(String fieldQuery, Pageable page);

}
