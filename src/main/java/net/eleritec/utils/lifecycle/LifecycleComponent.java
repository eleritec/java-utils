package net.eleritec.utils.lifecycle;

import static net.eleritec.utils.collection.CollectionUtil.listOf;
import static net.eleritec.utils.lifecycle.SingleExec.runOnce;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LifecycleComponent {

	private AtomicBoolean isOpen = new AtomicBoolean();
	
	protected abstract void open();
	
	public <T extends LifecycleComponent> T startIf(boolean criteria) {
		if(criteria) {
			start();
		}
		return self();
	}
	
	public <T extends LifecycleComponent> T start() {
		isOpen.set(runOnce(()->open()));
		return self();
	}
	
	public boolean isOpen() {
		return isOpen.get();
	}
	
	@SuppressWarnings("unchecked")
	private <T extends LifecycleComponent> T self() {
		return (T) this;
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
