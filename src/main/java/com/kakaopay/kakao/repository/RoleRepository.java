
package com.kakaopay.kakao.repository;

import com.kakaopay.kakao.model.user.Role;
import com.kakaopay.kakao.model.user.RoleNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleNames roleName);
}

