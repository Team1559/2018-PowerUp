package org.usfirst.frc.team1559.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MathUtils {

	/**
	 * Maps a value from one range to another.
	 * @param value Value to be mapped.
	 * @param minI Lower bound of initial range.
	 * @param maxI Upper bound of initial range.
	 * @param minF Lower bound of final range.
	 * @param maxF Upper bound of final range.
	 * @return Mapped value.
	 */
	public static double map(double value, double minI, double maxI, double minF, double maxF) {
		return (value - minI) / (maxI - minI) * (maxF - minF) + minF;
	}

	/**
	 * Applies a function to every element in an array.
	 * @param function Function to be applied.
	 * @param array Array for function to be mapped on.
	 * @return List containing mapped elements.
	 */
	public static <T, R> List<R> map(Function<T, R> function, T[] array) {
		return Arrays.asList(array).stream().map(function).collect(Collectors.toList());
	}

	public static double sum(Number... values) {
		double sum = 0;
		int n = values.length;
		for (int i = 0; i < n; i++) {
			sum += values[i].doubleValue();
		}
		return sum;
	}
	
	public static double sum(List<? extends Number> values) {
		double sum = 0;
		int n = values.size();
		for (int i = 0; i < n; i++) {
			sum += values.get(i).doubleValue();
		}
		return sum;
	}

	public static double average(Number... values) {
		double sum = 0;
		int n = values.length;
		for (int i = 0; i < n; i++) {
			sum += values[i].doubleValue();
		}
		return sum / n;
	}
	
	public static double average(List <? extends Number> values) {
		double sum = 0;
		int n = values.size();
		for (int i = 0; i < n; i++) {
			sum += values.get(i).doubleValue();
		}
		return sum / n;
	}
}
