package net.eleritec.utils.collection;


import static net.eleritec.utils.CompareUtil.ascender;
import static net.eleritec.utils.CompareUtil.descender;
import static net.eleritec.utils.collection.StreamUtil.list;
import static net.eleritec.utils.collection.StreamUtil.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Sorter<T> {

	private List<Comparator<T>> comparators = new ArrayList<>();
	
	@SafeVarargs
	public static <T, C extends Comparable<C>> Sorter<T> ascending(Function<T, ? extends Comparable<?>>...criteria) {
		return new Sorter<T>().asc(criteria);
	}

	@SafeVarargs
	public static <T, C extends Comparable<C>> Sorter<T> descending(Function<T, ? extends Comparable<?>>...criteria) {
		return new Sorter<T>().desc(criteria);
	}
	
	@SafeVarargs
	public final <C extends Comparable<C>> Sorter<T> asc(Function<T, ? extends Comparable<?>>...criteria) {
		comparators.addAll(list(map(f->ascender(f), criteria)));
		return this;
	}

	@SafeVarargs
	public final <C extends Comparable<C>> Sorter<T> desc(Function<T, ? extends Comparable<?>>...criteria) {
		comparators.addAll(list(map(f->descender(f), criteria)));
		return this;
	}
	
	public List<T> sort(Collection<T> items) {
		return doSort(CollectionUtil.asList(items));
	}

	public List<T> sortedCopy(Collection<T> items) {
		return doSort(new ArrayList<>(items));
	}
	
	private List<T> doSort(List<T> items) {
		List<Comparator<T>> criteria = new ArrayList<Comparator<T>>(comparators);
		Collections.sort(items, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return doCompare(criteria, o1, o2);
			}
		});
		return items;
	}
	
	private int doCompare(List<Comparator<T>> criteria, T left, T right) {
		for(Comparator<T> c: criteria) {
			int cmp = c.compare(left, right);
			if(cmp!=0) {
				return cmp;
			}
		}
		return 0;
	}
}
