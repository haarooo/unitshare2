package org.example.unitshare2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="loginstate")
public class LoginStateEnitty extends BaseTime{
    @Id
    private Integer logNo;

    @Column(columnDefinition = "tinyint default 0")
    private Integer userState;


    @JoinColumn(name = "uno")
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UserEntity> userEntityList;
}
