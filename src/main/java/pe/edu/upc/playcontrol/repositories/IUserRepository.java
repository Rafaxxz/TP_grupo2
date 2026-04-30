package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.playcontrol.entities.Users;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {

    Users findOneByUsername(String username);

    @Query("select count(u.username) from Users u where u.username = :username")
    int buscarUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "insert into roles (rol, user_id) VALUES (:rol, :user_id)", nativeQuery = true)
    void insRol(@Param("rol") String authority, @Param("user_id") Long user_id);
}
