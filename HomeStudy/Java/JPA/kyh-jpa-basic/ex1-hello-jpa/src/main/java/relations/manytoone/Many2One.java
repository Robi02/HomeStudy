package relations.manytoone;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Many2One {
    
    public static void work(EntityManager em){
        
        Member member = new Member();
        ItemOld item = new ItemOld();
        MemberItem order = new MemberItem();

        member.setName("TestMemberA");
        em.persist(member);

        item.setName("BulBuk");
        item.setStock(5);
        item.setPrice(3000);
        em.persist(item);

        order.setDealDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        order.setItem(item);
        order.setMember(member);
        em.persist(order);

        em.flush();
        em.clear();

        MemberItem findOrder = em.find(MemberItem.class, order.getId());
        log.info("Member : " + findOrder.getMember().getName());
        log.info("Item : " + findOrder.getItem().getName());
    }
}
