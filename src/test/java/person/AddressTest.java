package person;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AddressTest {

    private Address address;

    @BeforeClass
    public void setUpClass() {
        address = new Address("Москва", "Тверская", 1, 11);
    }

    @Test
    public void testGetCity() {
        assertEquals(address.getCity(), "Москва");
    }

    @Test
    public void testGetStreet() {
        assertEquals(address.getStreet(), "Тверская");
    }

    @Test
    public void testGetHouse() {
        assertEquals(address.getHouse(), 1);
    }

    @Test
    public void testGetApartment() {
        assertEquals(address.getApartment(), 11);
    }
}