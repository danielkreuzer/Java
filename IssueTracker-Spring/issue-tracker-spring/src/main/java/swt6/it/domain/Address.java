package swt6.it.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.StringJoiner;


//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Address {
    @AttributeOverride(name="address", column = @Column(name = "address"))
    @AttributeOverride(name="zipCode", column = @Column(name = "zipCode"))
    @EmbeddedId
    private AddressId id;
    @Column(nullable = false)
    private String city;

    // Navigation relation not useful
//    @OneToMany(mappedBy = "address", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private Set<Employee> employees = new HashSet<>();

    public Address() {
    }

    public Address(String address, String zipCode, String city) {
        this(new AddressId(address, zipCode), city);
    }

    public Address(AddressId id, String city) {
        this.id = id;
        this.city = city;
    }

    public AddressId getId() {
        return id;
    }

    public void setId(AddressId id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("city='" + city + "'")
                .toString();
    }
}
