package org.example.unitshare2.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // 아이디 중복 체크
    boolean existsByLoginId(String id);

    // 전화번호 중복 체크
    boolean existsByPhone(String phone);
}
