package ru.pa4ok.library.util.sorting;

public class BubbleSortable<E extends Number & Comparable<? super E>> implements ISortable<E>
{
	@Override
	public String getName() 
	{
		return "Bubble";
	}

	@Override
	public void sort(E[] array)
	{
		boolean sorted = false;
	    E temp;
	    
	    while(!sorted) 
	    {
	        sorted = true;
	        
	        for (int i = 0; i < array.length - 1; i++) 
	        {
	            if (array[i].compareTo(array[i+1]) == 1)
	            {
	                temp = array[i];
	                array[i] = array[i+1];
	                array[i+1] = temp;
	                sorted = false;
	            }
	        }
	    }
	}
}
