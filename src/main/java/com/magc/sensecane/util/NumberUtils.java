package com.magc.sensecane.util;

public class NumberUtils {

	public static <T extends Number & Comparable<T>> int compare(T a, T b) {
	    return a.compareTo(b);
	}
	
	public static <T extends Number & Comparable<T>> T divide(T a, T b) {
		return (T) a.getClass().cast(a.doubleValue() / b.doubleValue());
	}
	
	public static <T extends Number & Comparable<T>> T substract(T a, T b) {
		return (T) a.getClass().cast(a.doubleValue() - b.doubleValue());
	}
}
