package net.eleritec.utils;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import net.eleritec.utils.collection.CollectionUtil;
import net.eleritec.utils.object.ClassUtil;

public class ThreadUtil {

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	
	public static void waitFor(Object obj) {
		if(obj!=null) {
			synchronized(obj) {
				try {
					obj.wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	public static ScheduledExecutorService newScheduledExecutorService(int cores, String threadName, boolean daemon) {
		return Executors.newScheduledThreadPool(cores, ThreadUtil.newThreadFactory(threadName, daemon));
	}
	
	public static ScheduledExecutorService newJobSchedule(String threadName) {
		return newScheduledExecutorService(1, threadName, true);
	}
	
	public static ExecutorService newWorkQueue(Class<?> owner, String name) {
		String queueName = format("%s-%s", owner.getSimpleName(), name);
		return ThreadUtil.newSingleThreadedExecutor(queueName, true);
	}
	
	public static ExecutorService newSingleThreadedExecutor(String threadName) {
		return Executors.newSingleThreadExecutor(new DefaultThreadFactory(threadName));
	}
	
	public static ExecutorService newSingleThreadedExecutor(String threadName, boolean daemon) {
		return Executors.newSingleThreadExecutor(new DefaultThreadFactory(threadName, daemon));
	}
	
	public static ExecutorService newFixedThreadPool(int threadCount, String name) {
		return newFixedThreadPool(threadCount, name, false);
	}
	
	public static ExecutorService newFixedThreadPool(int threadCount, String name, boolean daemon) {
		return Executors.newFixedThreadPool(threadCount, new DefaultThreadFactory(name, daemon));
	}
	
	public static ExecutorService newCachedThreadPool(String name) {
		return Executors.newCachedThreadPool(new DefaultThreadFactory(name));
	}
	
	public static ScheduledExecutorService newSingleThreadScheduledExecutor(String name, boolean daemon) {
		return Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory(name, daemon));
	}
	
	public static ThreadFactory newThreadFactory(String name, boolean daemon) {
		return new DefaultThreadFactory(name, daemon);
	}
	
    /**
     * The default thread factory
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;
        final boolean daemon;

        private DefaultThreadFactory(String name) {
        	this(name, false);
        }
        
        private DefaultThreadFactory(String name, boolean daemon) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() :
                                 Thread.currentThread().getThreadGroup();
            namePrefix = name + "-";
            this.daemon = daemon;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            t.setDaemon(this.daemon);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
    
    private static final Map<String, AtomicInteger> THREAD_COUNTERS = new HashMap<String, AtomicInteger>();
    
    public static String nextThreadName(String name) {
    	String threadName = null;
    	if(name!=null) {
	    	synchronized (THREAD_COUNTERS) {
				AtomicInteger counter = THREAD_COUNTERS.get(name);
				if(counter==null) {
					counter = new AtomicInteger();
					THREAD_COUNTERS.put(name, counter);
				}
				threadName = name + "-" + counter.incrementAndGet();
			}
    	}
    	return threadName;
    }

    public static void join(Thread thread) {
    	if(thread!=null) {
    		try {
				thread.join();
			} catch (InterruptedException e) {
			}
    	}
    }
    
    public static Thread startThread(Runnable runner, boolean daemon) {
    	Thread t = new Thread(runner);
    	t.setDaemon(daemon);
    	t.start();
    	return t;
    }
    
    public static <T> ThreadLocal<T> newThreadLocal(final Class<T> type, final Object...args) {
    	return new ThreadLocal<T>() {
			@Override
			protected T initialValue() {
				return ClassUtil.newInstance(type, args);
			}
    	};
    }

    public static <T> ThreadLocal<T> newThreadLocal(Object obj, final Object...args) {
    	@SuppressWarnings("unchecked")
		Class<T> type = obj==null? null: (Class<T>) obj.getClass();
    	return newThreadLocal(type, args);
    }    
    
    public static <T> List<Future<T>> invokeAll(ExecutorService executor, Collection<? extends Callable<T>> tasks) {
    	try {
			return executor.invokeAll(tasks);
		} catch (InterruptedException e) {
			return new ArrayList<Future<T>>();
		}
    }
    
    public static <T> T invokeAndWait(ExecutorService executor, Callable<T> callable) {
    	List<Callable<T>> tasks = new ArrayList<Callable<T>>();
    	tasks.add(callable);
    	Future<T> result = CollectionUtil.get(invokeAll(executor, tasks), 0);
    	try {
			return result==null? null: result.get();
		} catch (Exception e) {
			return null;
		}
    }
    
    public static void invokeAndWait(ExecutorService executor, final Runnable runnable) {
    	invokeAndWait(executor, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				runnable.run();
				return null;
			}
		});
    }
    
    public static <K, V> ThreadLocal<HashMap<K, V>> threadLocalHashMap() {
    	return new ThreadLocal<HashMap<K,V>>() {
			@Override
			protected HashMap<K, V> initialValue() {
				return new HashMap<K, V>();
			}
    		
    	};
    }
}