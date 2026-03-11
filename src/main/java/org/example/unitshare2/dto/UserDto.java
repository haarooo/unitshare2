package org.example.unitshare2.dto;

public class UserDto {
   private int uno;
   private String id;
   private String pwd;
   private String phone;
   private String name;
   private int point;

   public UserDto(){}
   public UserDto(String name, String phone) {
      this.name = name;
      this.phone = phone;
   }// 0213 수정

   public UserDto(int uno, String id, String pwd, String phone, String name ,int point) {
      this.uno = uno;
      this.id = id;
      this.pwd = pwd;
      this.phone = phone;
      this.name = name;
      this.point = point;
   }


   public int getUno() {return uno;}
   public void setUno(int uno) {this.uno = uno;}
   public String getId() {return id;}
   public void setId(String id) {this.id = id;}
   public String getPwd() {return pwd;}
   public void setPwd(String pwd) {this.pwd = pwd;}
   public String getPhone() {return phone;}
   public void setPhone(String phone) {this.phone = phone;}
   public String getName() {return name;}
   public void setName(String name) {this.name = name;}
   public int getPoint() {return point;}
   public void setPoint(int point) {this.point = point;}

   @Override
   public String toString() {
      return "UserDto{" +
              "uno=" + uno +
              ", id='" + id + '\'' +
              ", pwd='" + pwd + '\'' +
              ", phone='" + phone + '\'' +
              ", name='" + name + '\'' +
              ", point=" + point +
              '}';
   }
}
