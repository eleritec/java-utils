package net.eleritec.utils.lifecycle;

import static net.eleritec.utils.object.ClassUtil.defaultFactory;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicReference;

import net.eleritec.utils.collection.MapUtil;

public class StatusTracker {

	private static final Map<LifecycleComponent, StatusTracker> TRACKER = new WeakHashMap<>();
	
	private AtomicReference<ExecutionStatus> status = new AtomicReference<>(ExecutionStatus.NOT_STARTED);
	
	public static StatusTracker getStatus(LifecycleComponent component) {
		synchronized (TRACKER) {
			return MapUtil.resolve(TRACKER, component, defaultFactory(StatusTracker.class));
		}
	}
	
	public void setStatus(ExecutionStatus status) {
		this.status.set(status==null? ExecutionStatus.NOT_STARTED: status);
	}
	
	public ExecutionStatus getStatus() {
		return status.get();
	}
	
	public boolean isIdle() {
		return getStatus()!=ExecutionStatus.IN_PROGRESS;
	}
	
	public boolean isComplete() {
		return getStatus()==ExecutionStatus.COMPLETE;
	}
	
	public boolean inProgress() {
		return getStatus()==ExecutionStatus.IN_PROGRESS;
	}
	
	public boolean notStarted() {
		return getStatus()==ExecutionStatus.NOT_STARTED;
	}
	
	public boolean wasStarted() {
		return getStatus()!=ExecutionStatus.NOT_STARTED;
	}
	
}
