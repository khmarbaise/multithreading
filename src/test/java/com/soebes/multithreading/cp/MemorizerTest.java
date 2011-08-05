package com.soebes.multithreading.cp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.soebes.multithreading.cp.DoSomethingTimeConsuming;
import com.soebes.multithreading.cp.Index;
import com.soebes.multithreading.cp.VersionRange;

/**
 * @author Karl Heinz Marbaise
 */
public class MemorizerTest {
	private static final Logger LOGGER = Logger.getLogger(MemorizerTest.class);

	public VersionRange createRange(int start, int end) {
		List<String> versionRange = new ArrayList<String>();
		for (int i = start; i < end; i++) {
			versionRange.add(Integer.toString(i));
		}
		VersionRange v = new VersionRange(versionRange);
		return v;
	}

	// @Test
	// public void xTest() {
	// double U_CPU = 0.9; // (0..1) target CPU utilization
	// int N_CPUS = Runtime.getRuntime().availableProcessors();
	// double W = 1; //wait time.
	// double C = 1; //compute time
	// int N_THREADS = (int)(N_CPUS * U_CPU * ( 1.0 + (W/C)));
	//
	// LOGGER.info("N_CPUS:" + N_CPUS);
	// LOGGER.info("N_THREADS:" + N_THREADS);
	//
	// }

	@Test
	public void firstStart() throws InterruptedException, ExecutionException {
		ExecutorService exec = Executors.newFixedThreadPool(5);
		ExecutorCompletionService<Index> execCompletion = new ExecutorCompletionService<Index>(
				exec);

		// ArrayList<DoSomethingTimeConsuming> taskList = new
		// ArrayList<DoSomethingTimeConsuming>();

		List<Future<Index>> startedTasks = new ArrayList<Future<Index>>();
		List<Future<Index>> stoppedTasks = new ArrayList<Future<Index>>();

		List<Future<Index>> producerTasks = new ArrayList<Future<Index>>();

		for (int i = 0; i < 1000; i++) {
			int start = i * 100 + 1;
			int ende = i * 100 + 100;
			LOGGER.info("start:" + start + " ende:" + ende);
			DoSomethingTimeConsuming task = new DoSomethingTimeConsuming(
					new Long(i), createRange(start, ende));
			// taskList.add(task);

			LOGGER.info("exec.submit(" + i + " task");
			startedTasks.add(execCompletion.submit(task));

		}

		// Check if all started tasks have been finished.
		while (stoppedTasks.size() < startedTasks.size()) {
			// No not yet.
			Future<Index> result = execCompletion.poll();
			if (result == null) {
				// LOGGER.info("No task has stopped.");
				// Nothing stopped yet.
				continue;
			}
			stoppedTasks.add(result);
			producerTasks.add(result);
			LOGGER.info("Task has stopped.");

			if (producerTasks.size() > 8) {
				//
				LOGGER.info("We got at least 8 producer tasks. Run mergeIndex()");
				// mergeIndex (producerTasks);
				producerTasks = new ArrayList<Future<Index>>();
			}
		}

		// Are there some stopped task left over?
		if (producerTasks.size() > 0) {
			LOGGER.info("We got at least 1 producer tasks. Run mergeIndex()");
			//
			// mergeIndex (producerTasks);
		}

	}
}
