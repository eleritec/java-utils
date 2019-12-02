package net.eleritec.utils.lifecycle;

import java.util.Map;
import java.util.WeakHashMap;

import net.eleritec.utils.collection.MapUtil;

public class LifecycleTracker {

	private static final Map<LifecycleComponent, StatusTracker> TRACKER = new WeakHashMap<>();
	
	static StatusTracker getOpenStatus(LifecycleComponent component) {
		synchronized (TRACKER) {
			return MapUtil.resolve(TRACKER, component, LifecycleTracker::createStatus);
		}
	}
	
	private static StatusTracker createStatus(LifecycleComponent component) {
		return new StatusTracker();
	}
}
