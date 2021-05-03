package hellojpa;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table
public class MemberOld {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    // 아무것도 없어도 됨
    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    // 최신버전 H2에서는 LocalDate 지원
    private LocalDate createdDate2;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob // 큰 데이터 (문자-char[],String,... 인 경우 CLOB, 그 외 BLOB 타입)
    private String description;

    @Transient // 매핑하지 않음
    private int temp;
}
