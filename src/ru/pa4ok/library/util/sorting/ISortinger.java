package ru.pa4ok.library.util.sorting;

import java.util.Date;

public interface ISortinger
{
	public String getName();
	
	public void sort(int[] array);

	public default long sortWithTime(int[] array)
	{
		long startMills = (new Date()).getTime();
		this.sort(array);
		return ((new Date()).getTime() - startMills);
	}
}
