package net.eleritec.utils.collection;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class ListIndex<T> {

	private int index;
	private T value;
	
	public ListIndex(int index, T value) {
		this.index = index;
		this.value = value;
	}

	public int index() {
		return index;
	}

	public T value() {
		return value;
	}
	
	public <V> V evenOdd(V even, V odd) {
		return index%2==0? even: odd;
	}

	public static <T, R> List<R> map(Collection<T> items, Function<ListIndex<T>, R> tx) {
		return StreamUtil.list(StreamUtil.indices(items).map(tx));
	}
	
}
