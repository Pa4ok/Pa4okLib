package ru.pa4ok.library.util.sorting;

public class SelectSortable<E extends Number & Comparable<? super E>> implements ISortable<E>
{
	@Override
	public String getName() 
	{
		return "Selection";
	}

	@Override
	public void sort(E[] array)
	{
		for (int i = 0; i < array.length; i++) 
		{
	        E min = array[i];
	        
	        int minId = i;
	        for (int j = i+1; j < array.length; j++) 
	        {
	            if (array[j].compareTo(min) == -1)
	            {
	                min = array[j];
	                minId = j;
	            }
	        }

	        E temp = array[i];
	        array[i] = min;
	        array[minId] = temp;
	    }
	}
}
