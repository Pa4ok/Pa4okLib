package ru.pa4ok.library.util.sorting;

public class InsertSortinger implements ISortinger
{
	@Override
	public String getName() 
	{
		return "Insertion";
	}

	@Override
	public void sort(int[] array) 
	{
		for (int i = 1; i < array.length; i++) 
		{
			int current = array[i];
			int j = i - 1;

			while(j >= 0 && current < array[j]) 
			{
				array[j+1] = array[j];
				j--;
			}
			
			array[j+1] = current;
		}
	}
}
