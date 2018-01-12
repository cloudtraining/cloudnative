package demo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DeviceServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testIdGenerator() {
        DeviceService deviceService  = new DeviceService ();
        assertNotNull(deviceService.idGenerator());
    }
}