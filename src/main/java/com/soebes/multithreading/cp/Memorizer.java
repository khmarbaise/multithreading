package com.soebes.multithreading.cp;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

/**
 * @author Karl Heinz Marbaise
 */
public class Memorizer<Argument, ReturnValue> implements Computable<Argument, ReturnValue> {
    private static final Logger LOGGER = Logger.getLogger(Memorizer.class);

    private final ConcurrentMap<Argument, Future<ReturnValue>> cache = new ConcurrentHashMap<Argument, Future<ReturnValue>>();
    private final Computable<Argument, ReturnValue> c;

    public Memorizer(Computable<Argument, ReturnValue> c) {
        this.c = c;
    }

    public ReturnValue compute(final Argument arg) throws InterruptedException {
        while (true) {
            Future<ReturnValue> f = cache.get(arg);
            if (f == null) {
                Callable<ReturnValue> eval = new Callable<ReturnValue>() {
                    public ReturnValue call() throws InterruptedException {
                        return c.compute(arg);
                    }
                };
                FutureTask<ReturnValue> ft = new FutureTask<ReturnValue>(eval);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                LOGGER.error("ExecutionException:", e);
            }
        }
    }
}