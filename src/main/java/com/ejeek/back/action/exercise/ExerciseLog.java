package com.ejeek.back.action.exercise;

import com.ejeek.back.action.Action;
import com.ejeek.back.enums.DietType;
import com.ejeek.back.image.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("EXERCISE")
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private DietType dietType;
    private String foodName;
    private Integer calories;
    private String content;
}
