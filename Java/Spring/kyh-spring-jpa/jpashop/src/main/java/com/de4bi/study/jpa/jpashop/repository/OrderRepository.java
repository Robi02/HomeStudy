package com.de4bi.study.jpa.jpashop.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.de4bi.study.jpa.jpashop.domain.Order;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    /**
     * [#1] 동적 쿼리 작성 - 무식한 방법 사용 (권장하지 않음)
     *  :: Raw String -> JPQL
     */
    public List<Order> findAllByString(OrderSearch orderSearch) {

        // 전체 가져오기 (정적 쿼리지만 딱히 조정할건 없음)
        // return em.createQuery("select o from Order o join o.member m")
        //         .setMaxResults(1000)
        //         .getResultList();

        // 조건 가져오기 (정적 쿼리, null등에 대한 방어가 안됨)
        // return em.createQuery(
        //         "select o from Order o join o.member m" + 
        //         " where o.status = :status" +
        //         " and m.name like :name", Order.class)
        //         .setParameter("status", orderSearch.getOrderStatus())
        //         .setParameter("name", orderSearch.getMemberName())
        //         .setMaxResults(1000)
        //         .getResultList();

        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            }
            else {
                jpql += "and";
            }
            jpql += " o.status = :status";
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            }
            else {
                jpql += "and";
            }
            jpql += " m.name = :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);
        
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    /**
     * [#2] 동적 쿼리 작성 - JPA Criteria 사용 (JPA표준 스팩이지만 권장하지 않음)
     *  :: Criteria -> JPQL
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);
        
        List<Predicate> criteria = new ArrayList<>();
        
        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    /**
     * [#3] 동적 쿼리 작성 - JqueryDSL 라이브러리 사용 (권장)
     */
    public List<Order> findAll(OrderSearch orderSearch) {
        // 추후 강의에서 사용법 강의함
        return null;
    }

    /**
     * @return JPQL fetch join 을 사용하여 획득. (simple-orders V3)
     */
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
            "select o from Order o" + 
                " join fetch o.member m" +
                " join fetch o.delivery d"
            , Order.class
        ).getResultList();
    }

    /**
     * @return JPQL fetch join 을 사용하여 획득. (V3)
     */
    public List<Order> findAllWithItem() {
        return em.createQuery(
            "select distinct o from Order o" +      // distinct를 안넣으면 중복된 값이 딸려나온다.
             " join fetch o.member m" +             // JPQL에서 distinct 예약어는, PK값으로 중복을 체크하여 제거한다. (DB에서의 distinct와는 조금 다름.)
             " join fetch o.delivery d" +           // 
             " join fetch o.orderItems oi" +        // [!] 1:N을 fetch join 하는 순간, DB paging이 불가능해진다. (limit, offset sql쿼리가 실행되지 않음)
             " join fetch oi.item i", Order.class   // 가져온 결과를 메모리로 가져와서 페이징하게 된다. 매우 큰 부하가 발생 가능. (하이버네이트에서 WARN로그 출력됨)
        ).getResultList();
    }
}
