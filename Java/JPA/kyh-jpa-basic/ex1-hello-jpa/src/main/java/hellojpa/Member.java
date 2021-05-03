package hellojpa;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
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
/*@TableGenerator(
    name = "MEMBER_SEQ_GENERATOR",
    table = "MY_SEQUCENCES",
    pkColumnValue = "MEMBER_SEQ", allocationSize = 1
)*/
/*@SequenceGenerator(
    name = "member_seq_generator",
    sequenceName = "member_seq",
    allocationSize = 50 // DB시퀀서로 고유값 생성 시 50개씩 미리 생성하여 사용
)*/
@Table(name = "Member")
public class Member {

    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    // @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * 1) IDENTITY : 기본 키 생성을 DB에 위임 (따라서, em.persist(obj)를 호출함과 동시에 DB에 insert수행됨)
     * 2) SEQUENCE : DB시퀀서를 생성하여 수행
     * 3) TABLE    : 시퀀서 테이블을 생성하여 매핑 수행(성능저하 가능성 위험)
     */
    private Long id;

    private String name;
}
