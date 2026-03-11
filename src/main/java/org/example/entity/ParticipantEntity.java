package org.example.entity;


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
@Table(name="participant")
public class ParticipantEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tno;

    private Integer status;

    @JoinColumn(name = "pno")
    @OneToMany
    private List<ProductEntity> productEntityList;


    @JoinColumn(name ="uno")
    @OneToOne
    private UserEntity userEntity;

}
