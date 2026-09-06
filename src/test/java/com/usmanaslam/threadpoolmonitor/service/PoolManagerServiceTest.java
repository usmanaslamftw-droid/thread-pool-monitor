package com.usmanaslam.threadpoolmonitor.service;

import com.usmanaslam.threadpoolmonitor.dto.PoolConfigRequest;
import com.usmanaslam.threadpoolmonitor.model.ManagedPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PoolManagerServiceTest {

    private PoolManagerService service;

    @BeforeEach
    void setUp() {
        service = new PoolManagerService();
    }

    @Test
    void testCreateAndGetPool() {
        PoolConfigRequest req = new PoolConfigRequest("testPool", 2, 4, 100, "test");
        service.createPool(req);
        ManagedPool pool = service.getPool("testPool");
        assertNotNull(pool);
        assertEquals("testPool", pool.getName());
    }
}
