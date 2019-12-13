package net.eleritec.utils.collection;

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
	
}
