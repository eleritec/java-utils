package net.eleritec.utils.lifecycle;

import java.util.concurrent.atomic.AtomicReference;

public class StatusTracker {

	private AtomicReference<ExecutionStatus> status = new AtomicReference<>(ExecutionStatus.NOT_STARTED);
	
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
