package inheritmapping.singletable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
// @Entity
public class Book extends Item {
    
    private String author;
    private String isbn;
}
