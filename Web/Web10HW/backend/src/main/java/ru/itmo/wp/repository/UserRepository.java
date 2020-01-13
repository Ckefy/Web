package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.wp.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET passwordSha=SHA1(CONCAT('1be3db47a7684152', ?2)) WHERE id=?1", nativeQuery = true)
    void updatePasswordSha(long id, String password);

    @Query(value = "SELECT * FROM user WHERE login=?1 AND passwordSha=SHA1(CONCAT('1be3db47a7684152', ?2))", nativeQuery = true)
    User findByLoginAndPassword(String login, String password);

    User findByLogin(String login);

    User findById(long id);
}
