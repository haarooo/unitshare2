package org.example.unitshare2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.unitshare2.dto.UserDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="user")
public class UserEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uno; //유저번호 PK

    @Column(nullable = false,unique = true,length = 20)
    private String id; //유저아이디

    @Column(nullable = false,length = 15)
    private String pwd; //유저비밀번호

    @Column(length = 15,unique = true)
    private String phone; //유저 전화번호


    @Column(length = 10,nullable = false,unique = true)
    private String name; //유저이름

    @Column(columnDefinition = "int default 0")
    private Integer point;  //유저 포인트

    @Column(columnDefinition = "tinyint default 0")
    private Integer userState; //유저 상태




    //userEntity->userDto로 변환
    public UserDto toDto() {
        return UserDto.builder()
                .uno(uno)
                .id(id)
                .phone(phone)
                .name(name)
                .point(point)
                .createDate(getCreateDate().toString())
                .updateDate(getUpdateDate().toString())
                .build();
    }
} //class end
