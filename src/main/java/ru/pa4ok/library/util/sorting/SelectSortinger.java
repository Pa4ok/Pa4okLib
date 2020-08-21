package ru.pa4ok.library.util.sorting;

public class SelectSortinger implements ISortinger
{
	@Override
	public String getName() 
	{
		return "Selection";
	}

	@Override
	public void sort(int[] array) 
	{
		for (int i = 0; i < array.length; i++) 
		{
	        int min = array[i];
	        
	        int minId = i;
	        for (int j = i+1; j < array.length; j++) 
	        {
	            if (array[j] < min) 
	            {
	                min = array[j];
	                minId = j;
	            }
	        }
	        
	        // ������
	        int temp = array[i];
	        array[i] = min;
	        array[minId] = temp;
	    }
	}
}
