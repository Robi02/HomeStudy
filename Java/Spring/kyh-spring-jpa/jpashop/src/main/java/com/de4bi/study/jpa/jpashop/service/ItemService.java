package com.de4bi.study.jpa.jpashop.service;

import java.util.List;

import com.de4bi.study.jpa.jpashop.domain.item.Book;
import com.de4bi.study.jpa.jpashop.domain.item.Item;
import com.de4bi.study.jpa.jpashop.repository.ItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    //
    // [ JPA에서 Update를 구현하는 "두 가지" 방법 ]
    // 
    // 1. 변경 감지에 의한 데이터 변경 (권장)
    //
    // -> 변경 감지는 조금 번거롭지만, 병합(merge)의 null업데이트의 경우는 없으므로 더 안전하다.
    //
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.change(name, price, stockQuantity);
    }

    //
    // 2.병합(merge) 사용 -> 준영속 상태의 Entity를 영속 상태로 변경 (주의!)
    // (준영속 상태: DB에 저장되어 있지만, EntityContext에서 detach되어 관리되지 않는 객체들. -> new 등으로 생성한 경우.)
    //
    //  1) 파라미터로 넘어온 준영속 엔티티의 식별자 값으로 1차 캐시에서 엔티티 조회.
    //  2) 만약, 1차 캐시에 엔티티가 없으면 DB에서 엔티티를 조회하여 1차 캐시에 저장.
    //  3) 조회한 영속 엔티티에 파라미터로 넘어온 엔티티의 값을 채워 넣음.
    //  4) 영속 상태인 merged엔티티를 반환.
    //
    //  [!!] merge를 사용하면 모든 필드의 데이터가 다 교체된다. (null로 교체되는걸 정말 주의해야 함.)
    //  [!]  merge가 반환하는 mergedItem객체가 영속성 콘텍스트에서 관리되고,
    //       파라미터로 넘긴 bookParam 준영속 엔티티이므로 관리되지 않는것에 주의하자.
    //       (bookParam.setXX를 해도 자동 update가 수행되지 않음.)
    //
    @Transactional
    public void updateItemByMerge(Long itemId, Book bookParam) {
        Item mergedItem = itemRepository.merge(bookParam);
    }
}
