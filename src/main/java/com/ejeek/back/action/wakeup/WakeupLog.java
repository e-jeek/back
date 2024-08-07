package com.ejeek.back.action.wakeup;

import com.ejeek.back.action.Action;
import com.ejeek.back.image.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("WAKEUP")
public class WakeupLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime wakeupTime;
    private String content;

}
