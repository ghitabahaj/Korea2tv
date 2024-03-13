package  com.youcode.korea2tv.repositories;

import  com.youcode.korea2tv.models.entity.Role;
import com.youcode.korea2tv.models.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(RoleType name);
}
