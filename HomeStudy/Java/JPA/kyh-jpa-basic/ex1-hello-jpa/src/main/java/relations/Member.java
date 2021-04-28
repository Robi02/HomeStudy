package relations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    
    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    // 연관관계 편의 메서드를 작성하는것을 권장함
    // (Team에 넣어도 되고, Member에 넣어도 되지만, 둘 중 한곳에만 넣는것을 추천하고 무한루프를 조심!)
    public void changeTeam(Team team) {
        setTeam(team);
        team.getMembers().add(this);
    }
}
