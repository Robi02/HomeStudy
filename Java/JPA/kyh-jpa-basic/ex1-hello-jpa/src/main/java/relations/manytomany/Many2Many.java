package relations.manytomany;

import javax.persistence.EntityManager;

public class Many2Many {
    
    public static void work(EntityManager em) {
        /**
         * 실무에서 사용하기 정말 어렵고 좋지 않다.
         * 다대다(N:M)관계에서는 N:1 다대일 관계의 테이블을
         * 새로 생성하여 다루는것이 옳다.
         * 대학시절 배운 내용 그대로.
         * 
         * [ Product ] >--|- [ Product_Member ] -|--< [ Member ]
         *   P_ID              PM_ID                    M_ID
         *   ...               FK(P_ID)                 ...
         *                     FK(M_ID)
         *                     ...
         *   (N:1)                                      (N:1)
         */
    }
}
