import java.util.Comparator;


/**
 * An abstract class representing an object that can sort {@link WordList}s
 * and gather statistics on the sorting process.
 * 
 * @author Jack Croghan
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class Sorter
{
  /**
   * The total number of words sorted by this sorter so far.
   * Only modified in {@link #sortWithStatistics(WordList, Comparator, int)
   * sortWithStatistics()}, and retrieved from {@link #getTotalWordsSorted()}.
   */
  private int totalWordsSorted;

  /**
   * The total time, in milliseconds, used by this sorter to run sorts.
   * Only modified in {@link #sortWithStatistics(WordList, Comparator, int)
   * sortWithStatistics()}, and retrieved from {@link #getTotalSortingTime()}.
   */
  private long totalSortingTime;

  /**
   * The total number of comparisons made by this sorter.
   * Only modified in {@link #sortWithStatistics(WordList, Comparator, int)
   * sortWithStatistics()}, and retrieved from {@link #getTotalComparisons()}.
   */
  private long totalComparisons;


  /**
   * Constructs and initializes the sorter to have no history of performing
   * any sorts.
   */
  public Sorter()
  {
    totalWordsSorted = 0;
    totalSortingTime = 0;
    totalComparisons = 0;
  }


  /**
   * Sorts the given {@code WordList} using the given {@code Comparator}.
   * The contents of the given list <b>are</b> modified.
   * 
   * @param toSort
   *   the list to sort
   * @param comp
   *   the comparator to use to compare elements of the list
   * @throws NullPointerException
   *   if either of {@code toSort} or {@code comp} are {@code null}
   */
  public abstract void sort(WordList toSort, Comparator<String> comp) throws NullPointerException;
  
  /**
   * Repeatedly {@linkplain #sort(WordList, Comparator) sorts} copies of the
   * given {@code WordList} using the given {@code Comparator}, until the total
   * number of words sorted <i>by this method call</i> reaches or exceeds the
   * indicated amount.
   * The contents of the given list <b>are not</b> modified.
   * Exactly once per each call to {@code sortWithStatistics()}, write the 
   * sorted list to disk in a file named according to the class name (use
   * {@code getClass().getSimpleName()}) with a <t>.txt</t> extension.
   * 
   * Timing is performed such that only calls to {@code sort()} are timed, so
   * that cloning and I/O time is not counted.
   * 
   * @param toSort
   *   the list to sort copies of
   * @param comp
   *   the comparator to use to compare elements of the list
   * @param totalToSort
   *   the minimum number of words to sort in this method; the actual number of
   *   words sorted will be the smallest multiple of {@code toSort.length()}
   *   that is greater than or equal to {@code totalToSort}
   * @throws NullPointerException
   *   if either of {@code toSort} or {@code comp} are {@code null}
   * @throws IllegalArgumentException
   *   if {@code totalToSort} is negative
   */
  public void sortWithStatistics(WordList toSort, Comparator<String> comp, int totalToSort) throws NullPointerException, IllegalArgumentException
  {
    totalWordsSorted = 0;
    totalSortingTime = 0;

    CountingComparator comparator = new CountingComparator(comp);

    while(totalWordsSorted < totalToSort)
    {
      WordList tempList = toSort.clone();

      long startTime = java.lang.System.nanoTime();
      //noinspection unchecked
      sort(tempList, comparator);
      long endTime = java.lang.System.nanoTime();
      double timeMilli = (endTime - startTime) / 1000000.00;

      totalSortingTime += timeMilli;

      totalWordsSorted += toSort.getArray().length;
    }
    totalComparisons = comparator.getCount();
  }

  /**
   * Returns the name of the sorter.
   * 
   * @return
   *   the name of the sorter
   */
  public String getName()
  {
    return this.getClass().getSimpleName();
  }

  /**
   * Returns the total number of words that this sorter has sorted within
   * {@link #sortWithStatistics(WordList, Comparator, int)
   * sortWithStatistics()} so far.
   * 
   * @return
   *   the total number of words that this sorter has sorted within
   *   {@code sortWithStatistics()}
   */
  public int getTotalWordsSorted()
  {
    return totalWordsSorted;
  }

  /**
   * Returns the total amount of time, in milliseconds, that this sorter has
   * used sorting within {@link #sortWithStatistics(WordList, Comparator, int)
   * sorterWithStatistics()}.
   * 
   * @return
   *   the total amount of time, in milliseconds, that this sorter has used
   *   sorting within {@code sorterWithStatistics()}
   */
  public double getTotalSortingTime()
  {
    return totalSortingTime;
  }

  /**
   * Returns the total number of comparisons that this sorter has performed
   * while sorting within {@link #sortWithStatistics(WordList, Comparator, int)
   * sorterWithStatistics()}.
   * 
   * @return
   *   the total number of comparisons that this sorter has performed while
   *   sorting within {@code sorterWithStatistics()}
   */
  public long getTotalComparisons()
  {
    return totalComparisons;
  }



  /**
   * A wrapper {@code Comparator} that counts how many comparisons have been
   * performed.
   *
   * @param <T>
   *   the type of objects compared
   */
  /* already completed */
  @SuppressWarnings("unused")
  private static  class CountingComparator<T> implements Comparator<T>{
    /**
     * The comparator used to perform comparisons.
     */
    private final Comparator<? super T>  wrapped;

    /**
     * The number of comparisons performed.
     */
    private long count;


    /**
     * Creates a {@code CountingComparator} that wraps the given comparator,
     * using it to perform comparisons.
     * 
     * @param wrapped
     *   the comparator to wrap
     * @throws NullPointerException
     *   if {@code backing} is {@code null}
     */
    public
    CountingComparator(Comparator<? super T> wrapped)  throws NullPointerException
    {
      if (null == wrapped)
      {
        throw new NullPointerException();
      }


      this.wrapped = wrapped;
      this.count = 0;
    }


    /**
     * Returns the number of comparisons performed by this comparator since
     * construction or the last call to {@link #reset()}.
     * 
     * @return
     *   the number of comparisons performed
     */
    public long getCount(){
      return count;
    }

    /**
     * Resets the comparison counter to zero.
     */
    @SuppressWarnings("unused")
    public void reset(){
      count = 0;
    }

    @Override
    public int compare(T a, T b){
      ++count;
      return wrapped.compare(a, b);
    }
  }
}
