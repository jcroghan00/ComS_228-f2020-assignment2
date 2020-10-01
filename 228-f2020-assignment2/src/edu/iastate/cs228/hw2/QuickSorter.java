import java.util.Comparator;


/**
 * An implementation of {@link Sorter} that performs quick sort
 * to sort the list.
 * 
 * @author Jack Croghan
 */
public class QuickSorter extends Sorter
{
  @Override
  public void sort(WordList toSort, Comparator<String> comp) throws NullPointerException
  {
    quickSortRec(toSort, comp, 0, toSort.length() - 1);
  }

  private void quickSortRec(WordList list, Comparator<String> comp, int start, int end)
  {
    if(start < end)
    {
      int partition = partition(list, comp, start, end);

      quickSortRec(list, comp, start, partition - 1);
      quickSortRec(list, comp, partition + 1, end);
    }
  }

  private int partition(WordList list, Comparator<String> comp, int start, int end)
  {
    String pivot = list.getArray()[end];
    int i = start - 1;

    for(int j = start; j < end; ++j)
    {
      if(comp.compare(list.get(j), pivot) < 0){
        ++i;
        list.swap(j, i);
      }
    }
    list.swap(i + 1, end);
    return (i + 1);
  }
}
