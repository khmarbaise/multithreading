package com.soebes.multithreading.cp;

/**
 * @author Karl Heinz Marbaise
 */
public interface Computable<Argument, ReturnValue> {
    ReturnValue compute(Argument arg) throws InterruptedException;
}