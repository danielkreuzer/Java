package swt6.it.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.it.domain.Address;
import swt6.it.domain.AddressId;

import java.util.List;

@Repository
public interface AddressDao extends JpaRepository<Address, AddressId> {
    Address findAddressByCity(String city);
}
