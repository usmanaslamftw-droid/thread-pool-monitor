package com.usmanaslam.threadpoolmonitor.jmx;

import java.util.List;

public interface PoolMonitorMXBean {
    int getActivePoolCount();
    List<String> getPoolNames();
    String getPoolMetrics(String name);
}
