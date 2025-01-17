package com.project.sidefit.domain.entity;

import com.project.sidefit.domain.entity.time.BaseTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Portfolio extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    private String title;

    @Column(name = "portfolio_url")
    private String url;

    public Portfolio(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
