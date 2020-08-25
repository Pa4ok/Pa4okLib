package ru.pa4ok.library.util.sorting;

public class QuickSortable<E extends Number & Comparable<? super E>> implements ISortable<E>
{
	@Override
	public String getName() 
	{
		return "Quick";
	}

	@Override
	public void sort(E[] array)
	{
		int begin = 0;
		int end = array.length - 1;
		quickSort(array, begin, end);
	}
	
	public void quickSort(E[] array, int begin, int end)
	{  
	    if (end <= begin) return;
	    int pivot = partition(array, begin, end);
	    quickSort(array, begin, pivot-1);
	    quickSort(array, pivot+1, end);
	}
	
	public int partition(E[] array, int begin, int end)
	{  
	    int pivot = end;
	    int counter = begin;
	    
	    for (int i = begin; i < end; i++) 
	    {
	        if (array[i].compareTo(array[pivot]) == -1)
	        {
	            E temp = array[counter];
	            array[counter] = array[i];
	            array[i] = temp;
	            counter++;
	        }
	    }
	    
	    E temp = array[pivot];
	    array[pivot] = array[counter];
	    array[counter] = temp;

	    return counter;
	}
}
