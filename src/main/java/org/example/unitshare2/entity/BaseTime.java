package org.example.unitshare2.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass //엔티티의 상속용도 클래스
@Getter
@EntityListeners(AuditingEntityListener.class) //해당 엔티티 자동 감사 적용
public class BaseTime {
    @CreatedDate
    private String createDate;

    @LastModifiedDate
    private String updateDate;
}
