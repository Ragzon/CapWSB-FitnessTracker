package com.capgemini.wsb.fitnesstracker.statistics.api;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.persistence.*;

@Entity
@Table(name = "timed_statistics")
public class TimedStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "time")
    private int time;

    @Column(name = "total_distance")
    private double total_distance;
}
