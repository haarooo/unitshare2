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
@Table(name="product")
public class ProductEntity extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pno;

    @Column(columnDefinition = "varchar(50)",nullable = false)
    private String pname;

    @Column(nullable = false)
    private Integer pprice;

    @Column(columnDefinition = "varchar(30)",nullable = false)
    private String pcontent;


    @Column(columnDefinition = "longtext")
    private String openchat;
    @Column(nullable = false,length = 10)
    private Integer people;


    @JoinColumn(name = "uno")
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //유저가 삭제되면 프로덕트 테이블도 자동 삭제/수정

    private UserEntity userEntity;  //유저번호 FK //유저하나당 여러개의 상품을 가진다.
}
