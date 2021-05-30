package com.de4bi.study.restapiwithspring.accounts;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Account {
    
    @Id @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER) // 가져올 데이터가 적고, 매번 가져오기 때문에 EAGER사용
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;
}
