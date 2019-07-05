package middleware.apachecommons.collections4;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @Title: CollectionUtilsDemo
 */
public class CollectionUtilsDemo {
	public static void main(String[] args) {
		List<String> list1 = new ArrayList<>();
		CollectionUtils.addIgnoreNull(list1, "a");
		CollectionUtils.addIgnoreNull(list1, null);
		System.out.println(list1);

		List<String> list2 = new ArrayList<>();
		list2.add("A");
		list2.add("C");
		list2.add("E");
		List<String> list3 = new ArrayList<>();
		list3.add("B");
		list3.add("D");
		list3.add("F");
		List<String> collate = CollectionUtils.collate(list2, list3);
		System.out.println(collate);

		List<String> strList = Arrays.asList("1", "2", "3");
		List<Integer> integerList = (List<Integer>) CollectionUtils.collect(strList, input ->
				Integer.parseInt(input)
		);
		System.out.println(integerList);

		List<Integer> integers = new ArrayList<>();
		integers.addAll(Arrays.asList(1, 2, 3, 4, 5));
		CollectionUtils.filter(integers, new Predicate<Integer>() {
			@Override
			public boolean evaluate(Integer object) {
				if (object % 2 == 0)
					return false;
				return true;
			}
		});
		System.out.println(integers);

		List<String> list4 = new ArrayList<>();
		boolean empty = CollectionUtils.isEmpty(list4);
		System.out.println(empty);

		List<String> list5 = Arrays.asList("A", "C", "B");
		List<String> list6 = Arrays.asList("A", "B");
		boolean subCollection = CollectionUtils.isSubCollection(list6, list5);
		System.out.println(subCollection);

		Collection<String> intersection = CollectionUtils.intersection(list5, list6);
		System.out.println(intersection);

	}
}
