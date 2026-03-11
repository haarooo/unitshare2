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



    private Integer uno; //fk
}
