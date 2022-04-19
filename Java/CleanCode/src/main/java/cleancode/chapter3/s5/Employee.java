package cleancode.chapter3.s5;

import cleancode.chapter3.s4.Money;

public abstract class Employee {
    
    public abstract boolean isPayday();
    public abstract Money calculatePay();
    public abstract void deliverPay(Money pay);
}
