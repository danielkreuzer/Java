package swt6.it.logic.impl;

import org.springframework.transaction.annotation.Transactional;
import swt6.it.dao.interfaces.AddressDao;
import swt6.it.domain.Address;
import swt6.it.logic.interfaces.AddressManager;

@Transactional
public class AddressManagerImpl implements AddressManager {
    private AddressDao addressDao;

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public Address syncAddress(Address address) {
        return addressDao.save(address);
    }
}