package ru.pa4ok.library.util;

public class SortUtil
{
    public static <E extends Comparable> void bubble(E[] array)
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

    public static <E extends Comparable> long bubbleWithTime(E[] array)
    {
        long startMills = System.currentTimeMillis();
        bubble(array);
        return System.currentTimeMillis() - startMills;
    }

    public static <E extends Comparable> void heap(E[] array)
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

    private static <E extends Comparable> void heapify(E[] array, int length, int i)
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

    public static <E extends Comparable> long heapWithTime(E[] array)
    {
        long startMills = System.currentTimeMillis();
        heap(array);
        return System.currentTimeMillis() - startMills;
    }

    public static <E extends Comparable> void insert(E[] array)
    {
        for (int i = 1; i < array.length; i++)
        {
            E current = array[i];
            int j = i - 1;

            while(j >= 0 && current.compareTo(array[j]) == -1)
            {
                array[j+1] = array[j];
                j--;
            }

            array[j+1] = current;
        }
    }

    public static <E extends Comparable> long insertWithTime(E[] array)
    {
        long startMills = System.currentTimeMillis();
        insert(array);
        return System.currentTimeMillis() - startMills;
    }

    public static <E extends Comparable> void merge(E[] array)
    {
        int left = 0;
        int right = array.length - 1;
        mergeSort(array, left, right);
    }

    private static <E extends Comparable> void mergeSort(E[] array, int left, int right)
    {
        if (right <= left) return;
        int mid = (left+right)/2;
        mergeSort(array, left, mid);
        mergeSort(array, mid+1, right);
        merge(array, left, mid, right);
    }

    private static <E extends Comparable> void merge(E[] array, int left, int mid, int right)
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

    public static <E extends Comparable> long mergeWithTime(E[] array)
    {
        long startMills = System.currentTimeMillis();
        merge(array);
        return System.currentTimeMillis() - startMills;
    }

    public static <E extends Comparable> void quick(E[] array)
    {
        int begin = 0;
        int end = array.length - 1;
        quickSort(array, begin, end);
    }

    private static <E extends Comparable> void quickSort(E[] array, int begin, int end)
    {
        if (end <= begin) return;
        int pivot = partition(array, begin, end);
        quickSort(array, begin, pivot-1);
        quickSort(array, pivot+1, end);
    }

    private static <E extends Comparable> int partition(E[] array, int begin, int end)
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

    public static <E extends Comparable> long quickWithTime(E[] array)
    {
        long startMills = System.currentTimeMillis();
        quick(array);
        return System.currentTimeMillis() - startMills;
    }

    public static <E extends Comparable> void select(E[] array)
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

    public static <E extends Comparable> long selectWithTime(E[] array)
    {
        long startMills = System.currentTimeMillis();
        select(array);
        return System.currentTimeMillis() - startMills;
    }
}
