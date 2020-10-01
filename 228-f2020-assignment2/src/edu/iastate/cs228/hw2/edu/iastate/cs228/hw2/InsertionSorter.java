package edu.iastate.cs228.hw2;


import java.util.Comparator;


/**
 * An implementation of {@link Sorter} that performs insertion sort
 * to sort the list.
 * 
 * @author Jack Croghan
 */
public class InsertionSorter extends Sorter
{
  @Override
  public void sort(WordList toSort, Comparator<String> comp) throws NullPointerException
  {
    int i, j;
    String temp;

    for (i = 1; i < toSort.length(); i++)
    {
      temp = toSort.get(i);
      j = i - 1;

      while (j >= 0 && comp.compare(toSort.get(j), temp) > 0)
      {
        toSort.set(j + 1, toSort.get(j));
        j = j - 1;
      }
      toSort.set(j + 1, temp);
    }
  }
}
