import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * A class representing an ordering of characters that can be queried to know
 * the position of a given character.
 * 
 * @author Jack Croghan
 */
public class Alphabet
{
  /**
   * A lookup table containing characters and their positions.
   * Sorted by the character of each entry.
   */
  private final CharAndPos[] lookup;


  /**
   * Constructs and initializes the ordering to have exactly the ordering of
   * the elements in the given array.
   * 
   * @param ordering
   *   the array containing the characters, in the ordering desired
   * @throws NullPointerException
   *   if {@code ordering} is {@code null}
   */
  public Alphabet(char[] ordering) throws NullPointerException
  {
    lookup = new CharAndPos[ordering.length];

    for (int i = 0; i < ordering.length; ++i){
      lookup[i] = new CharAndPos(ordering[i], i);
    }

  }

  /**
   * Constructs and initializes the ordering by reading from the indicated
   * file. The file is expected to have a single character on each line, and
   * the ordering in the file is the order that will be used.
   * 
   * @param filename
   *   the name of the file to read
   * @throws NullPointerException
   *   if {@code filename} is {@code null}
   * @throws FileNotFoundException
   *   if the file cannot be found
   */
  public Alphabet(String filename) throws NullPointerException, FileNotFoundException
  {
    File file = new File(filename);
    Scanner scnr1 = new Scanner(file);

    int i = 0;
    while(scnr1.hasNextLine()){
      ++i;
      scnr1.nextLine();
    }

    scnr1.close();
    lookup = new CharAndPos[i];

    Scanner scnr2 = new Scanner(file);

    i = 0;
    while(scnr2.hasNextLine()){
      lookup[i] = new CharAndPos(scnr2.nextLine().charAt(0), i);
      ++i;
    }
    
    scnr2.close();

    lookupSort(lookup, 0, lookup.length - 1);
  }


  /**
   * Returns true if and only if the given character is present in the
   * ordering.
   * 
   * @param c
   *   the character to test
   * @return
   *   true if and only if the given character is present in the ordering
   */
  public boolean isValid(char c)
  {
    return binarySearch(c) >= 0;
  }

  /**
   * Returns the position of the given character in the ordering.
   * Returns a negative value if the given character is not present in the
   * ordering.
   * 
   * @param c
   *   the character of which the position will be determined
   * @return
   *   the position of the given character, or a negative value if the given
   *   character is not present in the ordering
   */
  public int getPosition(char c)
  {
    return binarySearch(c);
  }

  /**
   * Returns the index of the entry containing the given character within the
   * lookup table {@link #lookup}.
   * Returns a negative value if the given character does not have an entry in
   * the table.
   * 
   * @param toFind
   *   the character for which to search
   * @return
   *   the index of the entry containing the given character, or a negative
   *   value if the given character does not have an entry in the table
   */
  private int binarySearch(char toFind)
  {
    int start = 0;
    int end = lookup.length - 1;
    int mid;

    while(start <= end)
    {
      mid = (start + end) / 2;
      if(lookup[mid].character < toFind){
        start = mid + 1;
      }
      else if(lookup[mid].character > toFind){
        end = mid - 1;
      }
      else{
        return lookup[mid].position;
      }
    }
    return -1;
  }

  /**
   * Sorts the characters in the lookup table so that they may be easily found by the binary search.
   * Modifies the array of CharAndPos objects given to it.
   *
   * @param arr
   *  the array to be modified
   * @param start
   *  the start point of the array
   * @param end
   *  the end point of the array
   */
  public void lookupSort(CharAndPos[] arr, int start, int end)
  {
    if (start < end) {
      int mid = (start + end) / 2;
      lookupSort(arr, start, mid);
      lookupSort(arr, mid + 1, end);
      merge(arr, start, mid, end);
    }
  }

  /**
   * Merges the smaller lists created by the lookupSort back together in a sorted manner.
   * The array given to this function is directly modified.
   *
   * @param arr
   *  the array to be merged back together
   * @param start
   *  the start point of the array
   * @param mid
   *  the mid point of the array
   * @param end
   *  the end point of the array
   */
  private void merge(CharAndPos[] arr, int start, int mid, int end)
  {
    CharAndPos[] temp = new CharAndPos[end - start + 1];

    int i = start;
    int j = mid + 1;
    int k = 0;

    while(i <= mid && j <= end) {
      if(arr[i].character <= arr[j].character){
        temp[k] = arr[i];
        ++k;
        ++i;
      }
      else {
        temp[k] = arr[j];
        ++k;
        ++j;
      }
    }

    while(i <= mid) {
      temp[k] = arr[i];
      ++k;
      ++i;
    }

    while(j <= end) {
      temp[k] = arr[j];
      ++k;
      ++j;
    }

    for (i = start; i <= end; ++i) {
      arr[i] = temp[i - start];
    }
  }


  /**
   * A PODT class containing a character and a position.
   * Used as the entry type within {@link Alphabet#lookup lookup}.
   */
  /* already completed */
  @SuppressWarnings("SpellCheckingInspection")
  private static class CharAndPos{
    /**
     * The character of the entry.
     */
    public final char character;

    /**
     * The position of the entry in the ordering.
     */
    public final int position;


    /**
     * Constructs and initializes the entry with the given values.
     * 
     * @param character
     *   the character of the entry
     * @param position
     *   the position of the entry
     */
    public
    CharAndPos(char character, int position)
    {
      this.character = character;
      this.position = position;
    }


    @Override
    public
    boolean
    equals(Object obj)
    {
      if (null == obj || this.getClass() != obj.getClass())
      {
        return false;
      }

      CharAndPos o = (CharAndPos) obj;

      return this.character == o.character && this.position == o.position;
    }

    @Override
    public int hashCode(){
      return character ^ position;
    }

    @Override public String toString(){
      return "{" + character + ", " + position + "}";
    }
  }
}
