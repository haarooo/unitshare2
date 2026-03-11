package org.example.unitshare2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="user")
public class UserEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uno;


    @Column(nullable = false,unique = true,length = 20)
    private String id;

    @Column(nullable = false,length = 15)
    private String pwd;

    @Column(length = 15,unique = true)
    private String phone;


    @Column(length = 10,nullable = false,unique = true)
    private String name;

    @Column(columnDefinition = "default 0")
    private Integer point;

    @Column(columnDefinition = "tinyint default 0")
    private Integer userState;


}
