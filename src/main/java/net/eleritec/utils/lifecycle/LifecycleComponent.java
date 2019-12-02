package net.eleritec.utils.lifecycle;

import static net.eleritec.utils.collection.CollectionUtil.listOf;

public interface LifecycleComponent {

	void open();
	
	@SuppressWarnings("unchecked")
	default <T extends LifecycleComponent> T startIf(boolean criteria) {
		if(criteria) {
			start();
		}
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	default <T extends LifecycleComponent> T start() {
		if(!isOpen()) {
			StatusTracker tracker = LifecycleTracker.getOpenStatus(this);
			
			synchronized (tracker) {
				if(tracker.wasStarted()) {
					return (T)this;
				}
				tracker.setStatus(ExecutionStatus.IN_PROGRESS);
			}
			
			synchronized(tracker) {
				if(tracker.inProgress()) {
					try {
						open();
						tracker.setStatus(ExecutionStatus.COMPLETE);
					}
					catch(RuntimeException e) {
						tracker.setStatus(ExecutionStatus.NOT_STARTED);
						throw e;
					}
				}
			}
		}
		return (T) this;
	}
	
	default boolean isOpen() {
		StatusTracker tracker = LifecycleTracker.getOpenStatus(this);
		synchronized(tracker) {
			return tracker.isComplete();
		}
	}
	
	@SafeVarargs
	public static <T> void start(T...items) {
		listOf(items, LifecycleComponent.class).forEach(item->item.start());
	}
	
	@SafeVarargs
	public static <T> void startIf(boolean criteria, T...items) {
		if(criteria) {
			start(items);
		}
	}
}
