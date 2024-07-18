package com.ejeek.back.action.diet;

import com.ejeek.back.action.Action;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DietLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "action_id")
    private Action action;
    private String name;
    private Integer duration;
    private Integer calories;
    private String content;
}
