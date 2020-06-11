package ru.pa4ok.library.util.sorting;

public class BubleSortinger implements ISortinger
{
	@Override
	public String getName() 
	{
		return "Buble";
	}

	@Override
	public void sort(int[] array) 
	{
		boolean sorted = false;
	    int temp;
	    
	    while(!sorted) 
	    {
	        sorted = true;
	        
	        for (int i = 0; i < array.length - 1; i++) 
	        {
	            if (array[i] > array[i+1]) 
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
