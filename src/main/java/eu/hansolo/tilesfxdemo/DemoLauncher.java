package eu.hansolo.tilesfxdemo;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;


public class DemoLauncher {
    private static final RuntimeMXBean RUNTIME_MX_BEAN = ManagementFactory.getRuntimeMXBean();
    private static final long          NOW             = System.currentTimeMillis();


    public static void main(String[] args) {
        System.out.println("---------- DemoLauncher ----------");
        System.out.println("JVM Vendor               : " + RUNTIME_MX_BEAN.getVmVendor());
        System.out.println("JVM Name                 : " + RUNTIME_MX_BEAN.getVmName());
        System.out.println("JVM Version              : " + RUNTIME_MX_BEAN.getVmVersion());
        System.out.println("Start time (DemoLauncher): " + (NOW - RUNTIME_MX_BEAN.getStartTime()) + "ms");
        System.out.println("Uptime     (DemoLauncher): " + RUNTIME_MX_BEAN.getUptime() + "ms");
        Demo.main(args);
    }
}
