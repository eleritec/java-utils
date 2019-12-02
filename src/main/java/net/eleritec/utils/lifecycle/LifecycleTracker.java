package net.eleritec.utils.lifecycle;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import net.eleritec.utils.collection.MapUtil;

public class LifecycleTracker {

	private static final Map<LifecycleComponent, AtomicBoolean> TRACKER = new WeakHashMap<>();
	
	static AtomicBoolean getOpenStatus(LifecycleComponent component) {
		synchronized (TRACKER) {
			return MapUtil.resolve(TRACKER, component, c->new AtomicBoolean());
		}
	}
}
