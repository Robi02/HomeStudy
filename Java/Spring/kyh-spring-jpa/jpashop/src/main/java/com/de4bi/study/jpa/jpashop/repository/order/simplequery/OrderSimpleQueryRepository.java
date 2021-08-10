package com.de4bi.study.jpa.jpashop.repository.order.simplequery;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    /**
     * @return JPQL fetch join + 응답을 DTO로 획득.
     * @apiNote V3버전과의 차이: select절에 필요한 데이터만 넣어서 조회성능을 증가시킨다. (생각보다 큰 성능 향상은 기대x)
     * @apiNote 단, V4가 V3보다 좋다는 것은 아니다. 재사용성(v3) vs 조회성능(v4)
     * 두 가지 특징을 상황에 맞춰 사용하는것이 좋다. (특히, 컬럼의 데이터가 아주 큰 경우!)
     * @apiNote 또, V4로 조회한 DTO는 @Entity가 아니므로 update등 필요 시 수동으로 처리해야 한다.
     */
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
            "select new com.de4bi.study.jpa.jpashop.repository.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
            " from Order o" +
            " join o.member m" +
            " join o.delivery d",
            OrderSimpleQueryDto.class
        ).getResultList();
    }
}
