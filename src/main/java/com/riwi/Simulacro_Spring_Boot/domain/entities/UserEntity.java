package com.riwi.Simulacro_Spring_Boot.domain.entities;

import java.util.List;

import com.riwi.Simulacro_Spring_Boot.utils.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Role role;

    // Courses
    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "userEntity",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Course> courses;

    // submissions
    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "userEntity",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Submission> submissions;

    // Mensajes
    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "userSender",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Message> messageSender;

    // Mensajes
    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "userReceiver",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Message> messageReceiver;
}
