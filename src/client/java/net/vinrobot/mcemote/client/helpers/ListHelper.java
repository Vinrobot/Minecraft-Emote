package net.vinrobot.mcemote.client.helpers;

import com.google.common.collect.Lists;

import java.util.List;

public final class ListHelper {
	public static <T extends Comparable<T>> List<T> sort(Iterable<T> iterable) {
		final List<T> list = Lists.newArrayList(iterable);
		list.sort(null);
		return list;
	}
}
