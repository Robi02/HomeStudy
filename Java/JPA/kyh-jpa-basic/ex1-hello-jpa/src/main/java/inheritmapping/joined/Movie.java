package inheritmapping.joined;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
// @Entity
@DiscriminatorValue("M") // Make superclass's 'DTYPE' column value as "M"
public class Movie extends Item {
    
    private String director;
    private String actor;
}
