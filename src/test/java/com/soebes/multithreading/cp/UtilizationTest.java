package com.soebes.multithreading.cp;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Karl Heinz Marbaise
 */
public class UtilizationTest {

	@DataProvider(name = "test")
	public Object[][] createData() {
		return new Object[][] {
			// U_CPU, W, C
			{ 0.9, 1.0, 1.0 }, 
			{ 0.5, 1.0, 1.0 }, 
			{ 0.5, 0.5, 1.0 },
			{ 0.5, 2.0, 1.0 }, 
                        { 0.5, 3.0, 1.0 }, 
			{ 0.5, 10.0, 1.0 }, 
			{ 0.9, 10.0, 1.0 }, 
		};
	}

    public int calculateNumberOfThreads(double utilization, double waittime, double computetime) {
        int N_CPUS = Runtime.getRuntime().availableProcessors();

        double U_CPU = utilization; // (0..1) target CPU utilization
        double W = waittime; // wait time.
        double C = computetime; // compute time
        int threads = (int) (N_CPUS * U_CPU * (1.0 + (W / C)));
        return threads;
    }

    @Test(dataProvider = "test")
    public void xTest(double utilization, double waittime, double computetime) {
        int N_CPUS = Runtime.getRuntime().availableProcessors();

        double U_CPU = utilization; // (0..1) target CPU utilization
        double W = waittime; // wait time.
        double C = computetime; // compute time
        int N_THREADS = (int) (N_CPUS * U_CPU * (1.0 + (W / C)));

        System.out.println("utilization:" + utilization + " waittime:"
                + waittime + " computetime:" + computetime + " N_THREADS: "
                + N_THREADS);
    }

    public void waitMilliSeconds(long milliSeconds) {
        long start = System.currentTimeMillis();

        while ((System.currentTimeMillis() - start) < milliSeconds) {
            double s = Math.sin(System.currentTimeMillis());
            // The following line is intended to prevent any compiler from
            // optimizing this code away!
            if (String.valueOf(s).hashCode() == (int) s) {
                System.out.print(" ");
            }
        }

    }

    @Test(enabled = false)
    public void timingTest() {
        long start = System.currentTimeMillis();

        waitMilliSeconds(20000);

        long stop = System.currentTimeMillis();
        System.out.println("Runtime: " + (stop - start) + " ms");
    }
}
