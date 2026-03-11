package org.example.unitshare2.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.unitshare2.entity.UserEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
   private int uno;
   private String id;
   private String pwd;
   private String phone;
   private String name;
   private int point;
   private String createDate;
   private String updateDate;


   //userDto->userEntity로 변환하는 함수
   public UserEntity toEntity(){
      return UserEntity.builder()
              .uno(uno)
              .Id(id)
              .pwd(pwd)
              .phone(phone)
              .name(name)
              .point(point)
              .build();
   }

} //class end
