package com.example.herehere.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Builder
    public User(String nickName, String email){
        this.nickName = nickName;
        this.email = email;
    }

}
