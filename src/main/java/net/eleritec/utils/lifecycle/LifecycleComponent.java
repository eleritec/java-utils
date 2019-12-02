package net.eleritec.utils.lifecycle;

import static net.eleritec.utils.collection.CollectionUtil.listOf;
import static net.eleritec.utils.lifecycle.SingleExec.runOnce;

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
		LifecycleTracker.getOpenStatus(this).set(runOnce(()->open()));
		return (T) this;
	}
	
	default boolean isOpen() {
		return LifecycleTracker.getOpenStatus(this).get();
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
