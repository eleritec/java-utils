package net.eleritec.utils.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SimpleFuture<T> implements Future<T> {

	private AtomicBoolean done = new AtomicBoolean();
	
	protected abstract T doGet(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return done.get();
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		T value = null;
		while(!isDone()) {
			try {
				value = get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
			} catch (TimeoutException e) {
			}
		}
		return value;
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		T value = doGet(timeout, unit);
		done.set(true);
		return value;
	}

}
