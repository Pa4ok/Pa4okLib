package ru.pa4ok.library.util.sorting;

public class InsertSortable<E extends Number & Comparable<? super E>> implements ISortable<E>
{
	@Override
	public String getName() 
	{
		return "Insertion";
	}

	@Override
	public void sort(E[] array)
	{
		for (int i = 1; i < array.length; i++) 
		{
			E current = array[i];
			int j = i - 1;

			while(j >= 0 && current.compareTo(array[j]) == -1)
			{
				array[j+1] = array[j];
				j--;
			}
			
			array[j+1] = current;
		}
	}
}
