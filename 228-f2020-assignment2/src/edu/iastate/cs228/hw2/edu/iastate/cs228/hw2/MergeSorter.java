package edu.iastate.cs228.hw2;


import java.util.Comparator;


/**
 * An implementation of {@link Sorter} that performs merge sort
 * to sort the list.
 * 
 * @author Jack Croghan
 */
public class MergeSorter extends Sorter
{
  @Override
  public void sort(WordList toSort, Comparator<String> comp) throws NullPointerException
  {
    mergeSortRec(toSort, comp, 0, toSort.length() - 1);
  }

  private void mergeSortRec(WordList list, Comparator<String> comp, int start, int end)
  {
    if (start < end) {
      int mid = (start + end) / 2;
      mergeSortRec(list, comp, start, mid);
      mergeSortRec(list, comp, mid + 1, end);
      merge(list, comp, start, mid, end);
    }
  }

  private void merge(WordList list, Comparator<String> comp, int start, int mid, int end)
  {
    int leftIdx;
    int rightIdx;
    int i;

    String[] left = new String[(mid - start + 1)];
    String[] right = new String[end - mid];

    for (leftIdx = 0; leftIdx < left.length; leftIdx++)
      left[leftIdx] = list.getArray()[start + leftIdx];
    for (rightIdx = 0; rightIdx < right.length; rightIdx++)
      right[rightIdx] = list.getArray()[mid + rightIdx + 1];

    leftIdx = 0;
    rightIdx = 0;
    i = start;
    while (leftIdx < left.length && rightIdx < right.length) {
      if (comp.compare(left[leftIdx], right[rightIdx]) <= 0) {
        list.getArray()[i] = left[leftIdx];
        leftIdx++;
      }
      else {
        list.getArray()[i] = right[rightIdx];
        rightIdx++;
      }
      i++;
    }

    while (leftIdx < left.length) {
      list.getArray()[i] = left[leftIdx];
      leftIdx++;
      i++;
    }

    while (rightIdx < right.length) {
      list.getArray()[i] = right[rightIdx];
      rightIdx++;
      i++;
    }
  }
}
