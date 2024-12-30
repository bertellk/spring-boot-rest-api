package com.berkaytell.repository;

import com.berkaytell.dto.authentication.AuthorityDto;
import com.berkaytell.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNameAndIsDeletedFalse(String userName);

    @Query(value = """
            SELECT NEW com.berkaytell.dto.authentication.AuthorityDto(aa.description, ap.pageUrl, e.endpoint)
            FROM User u
            JOIN u.role r
            JOIN r.authItem ai
            JOIN ai.endpoint e
            JOIN ai.authPage ap
            JOIN ai.authAction aa
            WHERE u.userName = :userName
            """)
    List<AuthorityDto> getAuthoritiesAssociatedWithUser(@Param("userName") String userName);
}
