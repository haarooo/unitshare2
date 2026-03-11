package org.example.unitshare2.repository;

import org.example.unitshare2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByIdAndPwd(String id, String pwd); // 로그인 // pk아닌 id,pwd 동시에 매개변수로 불러오기 위해서
}
