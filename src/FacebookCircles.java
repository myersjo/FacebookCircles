/**
 * Class FacebookCircles: calculates the statistics about the friendship circles in facebook data.
 *
 * @ Jordan Myers
 *
 * @version 01/12/15 02:03:28
 */
public class FacebookCircles {
	private final int numberOfFacebookUsers;
	
	private int numberOfCircles;
	private int[] connectedComponentID;   // parent of node at location; i.e. cCID[i] is parent of i
	private int[] sizeOfTree;			  // size of tree rooted at given node
	private int sizeOfLargestCircle;

  /**
   * Constructor
   * @param numberOfFacebookUsers : the number of users in the sample data.
   * Each user will be represented with an integer id from 0 to numberOfFacebookUsers-1.
   */
  @SuppressWarnings("unchecked")
public FacebookCircles(int numberOfFacebookUsers) {
    this.numberOfFacebookUsers = numberOfFacebookUsers;
    this.numberOfCircles=numberOfFacebookUsers;
    connectedComponentID = new int[numberOfFacebookUsers];
    createConnectedComponents();
    sizeOfTree = new int [numberOfFacebookUsers];
    for (int i=0; i < sizeOfTree.length; i++) {
    	sizeOfTree[i] = 1;
    }
    sizeOfLargestCircle = 1;
  }

  /**
   * creates a friendship connection between two users, represented by their corresponding integer ids.
   * @param user1 : int id of first user
   * @param user2 : int id of second  user
   */
  public void friends( int user1, int user2 ) {
	  union(user1, user2);
  }
  
  /**
   * @return the number of friend circles in the data already loaded.
   */
  public int numberOfCircles() {
    return numberOfCircles;
  }
  
  private void createConnectedComponents() {
	  for (int i =0; i<numberOfFacebookUsers; i++) {
		  connectedComponentID[i] = i;
	  }
  }
  
  public int find(int i)
  {
	  while (i != connectedComponentID[i]) {
		  connectedComponentID[i] = connectedComponentID[connectedComponentID[i]];
		  i = connectedComponentID[i];
	  }
	  return i;
  }
  
  public void union(int p, int q)
  {
	  int i = find(p);
	  int j = find(q);
	  if (i == j) 
		  return;
	  numberOfCircles--;
	  if (sizeOfTree[i] < sizeOfTree[j]) { 
		  connectedComponentID[i] = j; 
		  sizeOfTree[j] += sizeOfTree[i]; 
		  if (sizeOfTree[j] > sizeOfLargestCircle)
			  sizeOfLargestCircle = sizeOfTree[j];
	  }
	  else { 
		  connectedComponentID[j] = i; 
		  sizeOfTree[i] += sizeOfTree[j];
		  if (sizeOfTree[i] > sizeOfLargestCircle)
			  sizeOfLargestCircle = sizeOfTree[i];
	  } 
	  return;
  }

  /**
   * @return the size of the largest circle in the data already loaded.
   */
  public int sizeOfLargestCircle() {
	  return sizeOfLargestCircle;
  }

  /**
   * @return the size of the median circle in the data already loaded.
   */
  public int sizeOfAverageCircle() {
	int sumSizes = 0;
    for (int i = 0; i < numberOfFacebookUsers; i++) {
    	if (connectedComponentID[i] == i) {			// if a root
    		sumSizes+= sizeOfTree[i];
    	}
    }
    return (sumSizes/numberOfCircles());
  }

  /**
   * @return the size of the smallest circle in the data already loaded.
   */
  public int sizeOfSmallestCircle() {
    int smallest = Integer.MAX_VALUE;
    for (int i = 0; i < numberOfFacebookUsers; i++) {
    	if (connectedComponentID[i] == i && sizeOfTree[i]<smallest) {			// if a root
    		smallest = sizeOfTree[i];
    	}
    }
    return smallest;
  }

}