package com.fomich.netdata.database.entity;

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
@Table(name="module")
public class Module implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ModuleType moduleType;

    private Integer serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multiplexer_id")
    private Multiplexer multiplexer;

}
