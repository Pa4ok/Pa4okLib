package ru.pa4ok.library.util.sorting;

public class MergeSortable<E extends Number & Comparable<? super E>> implements ISortable<E>
{
	@Override
	public String getName() 
	{
		return "Merge";
	}

	@Override
	public void sort(E[] array)
	{
		int left = 0;
		int right = array.length - 1;
		mergeSort(array, left, right);
	}
	
	public void mergeSort(E[] array, int left, int right)
	{
		if (right <= left) return;
	    int mid = (left+right)/2;
	    mergeSort(array, left, mid);
	    mergeSort(array, mid+1, right);
	    merge(array, left, mid, right);
	}
	
	public void merge(E[] array, int left, int mid, int right)
	{
	    int lengthLeft = mid - left + 1;
	    int lengthRight = right - mid;
	    E leftArray[] = (E[])new Object[lengthLeft];
	    E rightArray[] = (E[])new Object[lengthRight];

	    for (int i = 0; i < lengthLeft; i++)
	    {
	        leftArray[i] = array[left+i];
	    }
	    for (int i = 0; i < lengthRight; i++)
	    {
	        rightArray[i] = array[mid+i+1];
	    }

	    int leftIndex = 0;
	    int rightIndex = 0;

	    for (int i = left; i < right + 1; i++) 
	    {
	        if (leftIndex < lengthLeft && rightIndex < lengthRight) 
	        {
	            if (leftArray[leftIndex].compareTo(rightArray[rightIndex]) == -1)
	            {
	                array[i] = leftArray[leftIndex];
	                leftIndex++;
	            }
	            else 
	            {
	                array[i] = rightArray[rightIndex];
	                rightIndex++;
	            }
	        }
	        else if (leftIndex < lengthLeft) 
	        {
	            array[i] = leftArray[leftIndex];
	            leftIndex++;
	        }
	        else if (rightIndex < lengthRight) 
	        {
	            array[i] = rightArray[rightIndex];
	            rightIndex++;
	        }
	    }
	}
}
