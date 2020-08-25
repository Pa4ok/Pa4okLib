package ru.pa4ok.library.util.sorting;

public class HeapSortable<E extends Number & Comparable<? super E>> implements ISortable<E>
{
	@Override
	public String getName() 
	{
		return "Heap";
	}

	@Override
	public void sort(E[] array)
	{
		if (array.length == 0) return;
	    int length = array.length;

	    for (int i = length / 2-1; i >= 0; i--)
	    {
	        heapify(array, length, i);
	    }

	    for (int i = length-1; i >= 0; i--) 
	    {
	        E temp = array[0];
	        array[0] = array[i];
	        array[i] = temp;

	        heapify(array, i, 0);
	    }
	}
	
	public void heapify(E[] array, int length, int i)
	{  
	    int leftChild = 2*i+1;
	    int rightChild = 2*i+2;
	    int largest = i;

	    if (leftChild < length && array[leftChild].compareTo(array[largest]) == 1)
	    {
	        largest = leftChild;
	    }

	    if (rightChild < length && array[rightChild].compareTo(array[largest]) == 1)
	    {
	        largest = rightChild;
	    }

	    if (largest != i) 
	    {
	        E temp = array[i];
	        array[i] = array[largest];
	        array[largest] = temp;
	        heapify(array, length, largest);
	    }
	}
}
