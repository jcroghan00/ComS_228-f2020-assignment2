import java.io.FileNotFoundException;
import java.util.Comparator;


/**
 * An class that compares various methods of sorting.
 * 
 * @author Jack Croghan
 */
public
class
SorterFramework
{
  /**
   * Loads data necessary to run the sorter statistics output, and runs it.
   * The only logic within this method should be that necessary to use the
   * given file names to create the {@link AlphabetComparator},
   * {@link WordList}, and sorters to use, and then using them to run the
   * sorter statistics output.
   * 
   * @param args
   *   an array expected to contain two arguments:
   *    - the name of a file containing the ordering to use to compare
   *      characters
   *    - the name of a file containing words containing only characters in the
   *      other file
   */
  public static void main(String[] args) throws FileNotFoundException
  {
    String alphabetList = args[0];
    String wordList = args[1];

    Alphabet alphabet;
    AlphabetComparator comparator;
    WordList words;

    alphabet = new Alphabet(alphabetList);
    words = new WordList(wordList);
    comparator = new AlphabetComparator(alphabet);
    Sorter[] sorters = {new QuickSorter(), new MergeSorter(), new InsertionSorter()};

    SorterFramework toRun = new SorterFramework(sorters, comparator, words, 1000000);
    toRun.run();
  }


  /**
   * The comparator to use for sorting.
   */
  private final Comparator<String> comparator;

  /**
   * The words to sort.
   */
  private final WordList words;

  /**
   * The array of sorters to use for sorting.
   */
  private final Sorter[] sorters;

  /**
   * The total amount of words expected to be sorted by each sorter.
   */
  private final int totalToSort;


  /**
   * Constructs and initializes the SorterFramework.
   * 
   * @param sorters
   *   the array of sorters to use for sorting
   * @param comparator
   *   the comparator to use for sorting
   * @param words
   *   the words to sort
   * @param totalToSort
   *   the total amount of words expected to be sorted by each sorter
   * @throws NullPointerException
   *   if any of {@code sorters}, {@code comparator}, {@code words}, or
   *   elements of {@code sorters} are {@code null}
   * @throws IllegalArgumentException
   *   if {@code totalToSort} is negative
   */
  public
  SorterFramework(Sorter[] sorters, Comparator<String> comparator,
                  WordList words, int totalToSort)
    throws NullPointerException,
           IllegalArgumentException
  {
    this.sorters = sorters;
    this.comparator = comparator;
    this.words = words;
    this.totalToSort = totalToSort;
  }


  /**
   * Runs all sorters using
   * {@link Sorter#sortWithStatistics(WordList, Comparator, int)
   * sortWithStatistics()}, and then outputs the following information for each
   * sorter:
   *  - the name of the sorter
   *  - the length of the word list sorted each time
   *  - the total number of words sorted
   *  - the total time used to sort words
   *  - the average time to sort the word list
   *  - the number of elements sorted per second
   *  - the total number of comparisons performed
   */
  public
  void
  run()
  {
    for(int i = 0; i < 3; ++i)
    {
      sorters[i].sortWithStatistics(words, comparator, totalToSort);

      int totalRuns = totalToSort / words.length();
      double averageTime = sorters[i].getTotalSortingTime() / totalRuns;
      double compPerSec = sorters[i].getTotalComparisons() / (sorters[i].getTotalSortingTime() / 1000.00);

      System.out.println("Sorter: " + sorters[i].getName());
      System.out.println("Word List Length: " + words.length());
      System.out.println("Words Sorted: " + sorters[i].getTotalWordsSorted());
      System.out.println("Total Sorting Time: " + sorters[i].getTotalSortingTime() + " ms");
      System.out.println("Average Time Per List: " + averageTime + " ms");
      System.out.println("Comparisons per Second: " + compPerSec);
      System.out.println("Total Number of Comparisons: " + sorters[i].getTotalComparisons());
      System.out.println(" ");
    }
  }
}
