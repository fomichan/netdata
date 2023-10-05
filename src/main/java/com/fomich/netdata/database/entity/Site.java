package com.fomich.netdata.database.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.NotAudited;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name") // чтобы сравнмвать только по имени
@Builder
@Entity
@Table(name="site")
public class Site implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;


    @NotAudited
    @Builder.Default
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Multiplexer> multiplexers = new ArrayList<>();





}
