package cleancode.chapter3.s4;

public class Payroll {
    
    private static final int COMMISIONED = 0;
    private static final int HOURLY = 1;
    private static final int SALARITED = 2;

    /**
     * 이 메서드의 문제점.
     * 1) 메서드가 길다 (새 직원 유형을 추가하면 더 길어질 것이다)
     * 2) '한 가지' 작업만 수행하지 않는다
     * 3) SRP(Single Responsibility Principle - 단일 책임 원칙)를 위반한다 -> 코드를 변경할 이유가 여럿이기 때문
     * 4) OCP(Open Closed Principle - 개방 폐쇠 원칙)를 위반한다 -> 새 직원 유형을 추가할 때마다 코드를 변경하기 때문
     */
    public Money calculatePay(Employee e) throws InvalidEmployeeType {
        switch (e.type) {
            case COMMISIONED:
                return calculateCommissionedPay(e);
            case HOURLY:
                return calculateHourlyPay(e);
            case SALARITED:
                return calculateSalariedPay(e);
            default:
                throw new InvalidEmployeeType(e.type);
        }
    }

    private Money calculateSalariedPay(Employee e) {
        return null;
    }

    private Money calculateHourlyPay(Employee e) {
        return null;
    }

    private Money calculateCommissionedPay(Employee e) {
        return null;
    }
}
