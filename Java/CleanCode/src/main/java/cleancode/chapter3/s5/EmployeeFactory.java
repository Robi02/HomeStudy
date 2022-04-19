package cleancode.chapter3.s5;

import cleancode.chapter3.s4.InvalidEmployeeType;

public interface EmployeeFactory {
    public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType;
}
