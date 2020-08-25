package ru.pa4ok.library.util.sorting;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public interface ISortable<E extends Number & Comparable<? super E>>
{
	public String getName();
	
	public void sort(E[] array);

	public default long sortWithTime(E[] array)
	{
		long startMills = (new Date()).getTime();
		this.sort(array);
		return ((new Date()).getTime() - startMills);
	}
}
