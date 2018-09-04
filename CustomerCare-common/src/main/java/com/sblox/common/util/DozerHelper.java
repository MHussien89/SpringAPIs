package com.sblox.common.util;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;

public class DozerHelper {

	private DozerHelper() {
		super();
	}

	/**
	 * Constructs new list instance of destinationClass and performs mapping
	 * between from source
	 * 
	 * @param mapper
	 * @param source
	 * @param destType
	 * 
	 * @return List of mapping object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> destType) {

		final List<U> dest = new ArrayList<U>();

		for (T element : source) {
			if (element == null) {
				continue;
			}
			dest.add(mapper.map(element, destType));
		}

		// finally remove all null values if any
		List s1 = new ArrayList();
		s1.add(null);
		dest.removeAll(s1);

		return dest;
	}

	/**
	 * Constructs new instance of destinationClass and performs mapping between
	 * from source
	 * 
	 * @param mapper
	 * @param source
	 * @param destType
	 * @param mapId
	 * 
	 * @return List of mapping object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> destType, String mapId) {

		final List<U> dest = new ArrayList<U>();

		for (T element : source) {
			if (element == null) {
				continue;
			}
			dest.add(mapper.map(element, destType, mapId));
		}

		// finally remove all null values if any
		List s1 = new ArrayList();
		s1.add(null);
		dest.removeAll(s1);

		return dest;
	}

}
