package swt6.it.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@Embeddable
public class AddressId implements Serializable {
    private String address;
    private String zipCode;

    public AddressId() {
    }

    public AddressId(String address, String zipCode) {
        this.address = address;
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressId addressId = (AddressId) o;
        return Objects.equals(address, addressId.address) &&
                Objects.equals(zipCode, addressId.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, zipCode);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AddressId.class.getSimpleName() + "[", "]")
                .add("address='" + address + "'")
                .add("zipCode='" + zipCode + "'")
                .toString();
    }
}
