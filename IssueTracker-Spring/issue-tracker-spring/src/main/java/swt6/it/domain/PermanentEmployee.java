package swt6.it.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PE")
public class PermanentEmployee extends Employee {
    private static final long serialVersionUID = 1L;
    private double salary;

    public PermanentEmployee() {

    }

    public PermanentEmployee(String firstName, String lastName, LocalDate dateOfBirth) {
        super(firstName, lastName, dateOfBirth);
    }

    public PermanentEmployee(String firstName, String lastName, LocalDate dateOfBirth, double salary) {
        super(firstName, lastName, dateOfBirth);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString() + ", salary=" + salary;
    }
}
