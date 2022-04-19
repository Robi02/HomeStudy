package cleancode.chapter3.s5;

import cleancode.chapter3.s4.InvalidEmployeeType;

public class EmployeeFactoryImpl implements EmployeeFactory {

    private static final int COMMISSIONED = 0;
    private static final int HOURLY = 1;
    private static final int SALARIED = 2;

    @Override
    public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType {
        switch (r.type) {
            case COMMISSIONED:
                return new CommisionedEmployee(r);
            case HOURLY:
                return new HourlyEmployee(r);
            case SALARIED:
                return new SalariedEmployee(r);
            default:
                throw new InvalidEmployeeType(r.type);
        }
    }
    
}
