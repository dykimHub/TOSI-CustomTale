package com.ssafy.tosi.customTale;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customtales")
@Getter
@Setter
public class CustomTale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customtaleid")
    private int customTaleId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userid")
//    private User user;
    @Column(name = "userid")
    private int userId;
    private String title;
    private String content;

    @Column(name = "opened")
    private boolean opened; //공개여부
    private String thumbnail; //썸네일주소
    private int time; //재생시간

}