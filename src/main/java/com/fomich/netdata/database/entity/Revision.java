package com.fomich.netdata.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;


// Hibernate Envers
// Смотреть что именно изменил тот или иной пользователь
// См. Подключается аннотацией в AuditConfiguration
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RevisionEntity //из org.hibernate.envers.RevisionEntity
public class Revision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private Integer id;

    @RevisionTimestamp
    private Long timestamp;
}
