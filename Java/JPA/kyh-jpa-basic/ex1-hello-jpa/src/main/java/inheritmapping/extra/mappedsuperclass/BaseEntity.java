package inheritmapping.extra.mappedsuperclass;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

/**
 * When 'extends' MappedSuperclass, Common columns are extended.
 */
// @MappedSuperclass
public abstract class BaseEntity {
    
    private String createdBy;
    private LocalDateTime createdTime;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedTime;
}
