package net.eleritec.utils.lifecycle;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicReference;

import net.eleritec.utils.collection.MapUtil;

public class SingleExec {

	private static final Map<Runnable, Executor> TRACKER = new WeakHashMap<>();
	
	public static boolean runOnce(Runnable runnable) {
		Executor executor = getExecutor(runnable);
		return executor!=null && executor.runOnce();
	}
	
	private static Executor getExecutor(Runnable r) {
		synchronized (TRACKER) {
			return r==null? null: MapUtil.resolve(TRACKER, r, SingleExec::track);
		}
	}
	
	private static Executor track(Runnable runner) {
		return new Executor(runner);
	}
	
	private static class Executor implements Runnable {
		private AtomicReference<Boolean> success = new AtomicReference<Boolean>();
		private Runnable runnable;

		public Executor(Runnable runnable) {
			this.runnable = runnable;
		}

		@Override
		public void run() {
			try {
				runnable.run();
				success.set(true);
			}
			catch(Throwable t) {
				success.set(false);
			}
		}
		
		public boolean runOnce() {
			if(!isComplete()) {
				run();
			}
			return isSuccess();
		}

		public boolean isComplete() {
			return success.get()!=null;
		}
		
		public boolean isSuccess() {
			return isComplete() && success.get();
		}
		
		
	}
}
