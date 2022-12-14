package com.library.repository;

import com.library.domain.Role;
import com.library.domain.User;
import com.library.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    @Query("Select u from User u where u.isActive=true")
    List<User> findAll();
    @Query("select u from User u where u.id=:id and u.isActive=true ")
   Optional<User> findById(Long id);

    @Query("select new com.library.dto.UserDTO(user) from User user where user.isActive=true and (user.firstName= :query or user.lastName= :query or user.email= :query or user.phone= :query or :query is null)")
    Page<UserDTO> findUsersQueryOptionalSearchWithPage(@Param("query") Optional<String> query, Pageable pageable);

  /*  @Modifying
    @Query("UPDATE User u SET u.firstName=:firstName,u.lastName=:lastName, u.phone=:phone"
            + ", u.email=:email, u.address=:address, u.birthDate=:birthDate, u.isActive=:isActive," +
            " u.builtIn=:builtIn, u.resetPasswordCode=:resetPasswordCode, u.createDate=:createDate, u.score=:score WHERE u.id=:id")
    void update(@Param("id") Long id, @Param("firstName") String firstName ,@Param("lastName") String lastName,
                @Param("phone") String phoneNumber,@Param("email") String email,
                @Param("address") String address,@Param("birthDate") Date birthDate,
                @Param("isActive") Boolean isActive,@Param("builtIn") Boolean builtIn,
                @Param("resetPasswordCode") String resetPasswordCode, @Param("createDate") LocalDateTime createDate,
                @Param("score") Integer score); */

}


