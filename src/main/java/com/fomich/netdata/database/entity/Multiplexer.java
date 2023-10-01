package com.fomich.netdata.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.ArrayList;
import java.util.List;

@NamedQuery(
        name = "Multiplexer.findByName",
        query = "select m from Multiplexer m where lower(m.name) = lower(:name2)"
) // NamedQuery имеет приоритет над PartTreeJpaQuery (по названию метода)

@Data
@ToString(exclude = "multiplexerChannels")
@EqualsAndHashCode(of = {"name", "serialNumber"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="multiplexer")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED) // Hibernate Envers. NOT_AUDITED - не аудировать зависимые сущности
public class Multiplexer extends AuditingEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    private Integer serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;

    @NotAudited // в коллекциях нужно ставить отдельно чтобы Envers не аудировал
    @Builder.Default
    @OneToMany(mappedBy = "multiplexer")
    private List<MultiplexerChannel> multiplexerChannels = new ArrayList<>();


}
