package swt6.it.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import swt6.it.domain.Address;
import swt6.it.logic.interfaces.AddressManager;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"applicationContextTest.xml"})
public class AddressManagerImplTest {

    @Autowired
    private AddressManager addressManager;

    @Test
    public void syncAddress() {
        Address address1 = new Address("Marktplatz 433", "4193", "Reichenthal");
        address1 =  addressManager.syncAddress(address1);
        assertNotNull(address1.getId());
    }
}