package com.ejeek.back.action.diet;

import com.ejeek.back.enums.DietType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("DIET")
public class DietLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private DietType dietType;
    private String foodName;
    private Integer calories;
}
