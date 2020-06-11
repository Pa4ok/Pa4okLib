package ru.pa4ok.library.util.sorting;

public class HeapSortinger implements ISortinger
{
	@Override
	public String getName() 
	{
		return "Heap";
	}

	@Override
	public void sort(int[] array) 
	{
		if (array.length == 0) return;
	    int length = array.length;

	    for (int i = length / 2-1; i >= 0; i--)
	    {
	        heapify(array, length, i);
	    }

	    for (int i = length-1; i >= 0; i--) 
	    {
	        int temp = array[0];
	        array[0] = array[i];
	        array[i] = temp;

	        heapify(array, i, 0);
	    }
	}
	
	public void heapify(int[] array, int length, int i) 
	{  
	    int leftChild = 2*i+1;
	    int rightChild = 2*i+2;
	    int largest = i;

	    if (leftChild < length && array[leftChild] > array[largest]) 
	    {
	        largest = leftChild;
	    }

	    if (rightChild < length && array[rightChild] > array[largest]) 
	    {
	        largest = rightChild;
	    }

	    if (largest != i) 
	    {
	        int temp = array[i];
	        array[i] = array[largest];
	        array[largest] = temp;
	        heapify(array, length, largest);
	    }
	}
}
