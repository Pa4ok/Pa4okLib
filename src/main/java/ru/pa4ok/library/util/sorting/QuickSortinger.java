package ru.pa4ok.library.util.sorting;

public class QuickSortinger implements ISortinger
{
	@Override
	public String getName() 
	{
		return "Quick";
	}

	@Override
	public void sort(int[] array) 
	{
		int begin = 0;
		int end = array.length - 1;
		quickSort(array, begin, end);
	}
	
	public void quickSort(int[] array, int begin, int end) 
	{  
	    if (end <= begin) return;
	    int pivot = partition(array, begin, end);
	    quickSort(array, begin, pivot-1);
	    quickSort(array, pivot+1, end);
	}
	
	public int partition(int[] array, int begin, int end) 
	{  
	    int pivot = end;
	    int counter = begin;
	    
	    for (int i = begin; i < end; i++) 
	    {
	        if (array[i] < array[pivot]) 
	        {
	            int temp = array[counter];
	            array[counter] = array[i];
	            array[i] = temp;
	            counter++;
	        }
	    }
	    
	    int temp = array[pivot];
	    array[pivot] = array[counter];
	    array[counter] = temp;

	    return counter;
	}
}
