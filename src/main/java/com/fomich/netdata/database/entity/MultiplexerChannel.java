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
@Table(name="multiplexer_channel")
public class MultiplexerChannel implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multiplexer_id")
    private Multiplexer multiplexer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;


    public void setMultiplexer(Multiplexer multiplexer) {
        this.multiplexer = multiplexer;
        this.multiplexer.getMultiplexerChannels().add(this);
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
        this.channel.getMultiplexerChannels().add(this);
    }
}
