package edu.iastate.cs228.hw2;


import java.util.Comparator;


/**
 * An implementation of {@link Sorter} that performs quick sort
 * to sort the list.
 * 
 * @author
 */
public class QuickSorter extends Sorter
{
  @Override
  public void sort(WordList toSort, Comparator<String> comp) throws NullPointerException{
    // TODO
  }

  private void quickSortRec(WordList list, Comparator<String> comp, int start, int end){
    // TODO
  }

  private int partition(WordList list, Comparator<String> comp, int start, int end){
    // TODO
    return start;
  }
}
