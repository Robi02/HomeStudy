package com.de4bi.study.jpa.jpatypes.domain;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Embeddable
public class Period {
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
