package cleancode.chapter3.s5;

import cleancode.chapter3.s4.Money;

public class HourlyEmployee extends Employee {

    public HourlyEmployee(EmployeeRecord r) {
    }

    @Override
    public boolean isPayday() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Money calculatePay() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deliverPay(Money pay) {
        // TODO Auto-generated method stub
        
    }

}
