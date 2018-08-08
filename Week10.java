/* Name: Nam Than
 * Date: 08-03-2018
 * Instructor: David Harden
 * Description: this program demonstrates the use of internal array inside a class to manage data. The previous StudentArrayUtilities is now able to
 * add and remove a student from its list as a stack through two new methods. The other methods of this class also become instance methods, thus 
 * eliminating the need to dereference from the array[index] like last week. 
 */

public class Week10 {
	public static void main (String[] args){
		int k;
	    Student student;
	      
	    Student[] myClass = { new Student("smith","fred", 95), 
	    	new Student("bauer","jack",123),
	    	new Student("jacobs","carrie", 195), 
	    	new Student("renquist","abe",148),
	    	new Student("3ackson","trevor", 108), 
	    	new Student("perry","fred",225),
	    	new Student("loceff","fred", 44), 
	    	new Student("stollings","pamela",452),
	    	new Student("charters","rodney", 295), 
	    	new Student("cassar","john",321),
	    };
	      
	    // instantiate a StudArrUtilObject
	    StudentArrayUtilities myStuds = new StudentArrayUtilities();
	      
	    // we can add stdunts manually and individually
	    myStuds.addStudent( new Student("bartman", "petra", 102) );
	    myStuds.addStudent( new Student("charters","rodney", 295));
	    	    
	    // if we happen to have an array available, we can add students in loop.
	    for (k = 0; k < myClass.length; k++)
	    	myStuds.addStudent( myClass[k] );
	    
	    System.out.println( myStuds.toString("Before: "));
	      
	    myStuds.arraySort();
	    System.out.println( myStuds.toString("Sorting by default: "));
	    
	    Student.setSortKey(Student.SORT_BY_FIRST);
	    myStuds.arraySort();
	    System.out.println( myStuds.toString("Sorting by first name: "));
	      
	    Student.setSortKey(Student.SORT_BY_POINTS);
	    myStuds.arraySort();
	    System.out.println( myStuds.toString("Sorting by total points: "));
	      
	    // test median
	    System.out.println("Median of evenClass = "
	         +  myStuds.getMedianDestructive() + "\n");
	      
	    // various tests of removing and adding too many students
	    for (k = 0; k < 100; k++) {
	    	if ( (student = myStuds.removeStudent()) != null)
	    		System.out.println("Removed " + student);
	        else {
	        	System.out.println("Empty after " + k + " removes.");
	            break;
	        }
	    }

	    for (k = 0; k < 100; k++) {
	    	if (!myStuds.addStudent(new Student("first", "last", 22))) {
	        	System.out.println("Full after " + k + " adds.");
	            break;
	        }
	    }
	} //end of main
   
}//end of week9

class Student {
   private String lastName;
   private String firstName;
   private int totalPoints;

   public static final String DEFAULT_NAME = "zz-error";
   public static final int DEFAULT_POINTS = 0;
   public static final int MAX_POINTS = 1000;
   
   public static final int SORT_BY_FIRST = 88;
   public static final int SORT_BY_LAST = 98;
   public static final int SORT_BY_POINTS = 108;
   
   private static int sortKey = SORT_BY_LAST;
   
   // constructor requires parameters - no default supplied
   public Student( String last, String first, int points) {
      if ( !setLastName(last) )
         lastName = DEFAULT_NAME;
      if ( !setFirstName(first) )
         firstName = DEFAULT_NAME;
      if ( !setPoints(points) )
         totalPoints = DEFAULT_POINTS;   
   }

   public String getLastName() { return lastName; }
   public String getFirstName() { return firstName; } 
   public int getTotalPoints() { return totalPoints; }
   public static int getSortKey () { return sortKey; }

   public boolean setLastName(String last) {
      if ( !validString(last) )
         return false;
      lastName = last;
      return true;
   }

   public boolean setFirstName(String first) {
      if ( !validString(first) )
         return false;
      firstName = first;
      return true;
   }

   public boolean setPoints(int pts) {
      if ( !validPoints(pts) )
         return false;
      totalPoints = pts;
      return true;
   }

   public static boolean setSortKey (int key) {
	   if (key != SORT_BY_FIRST && key != SORT_BY_LAST && key != SORT_BY_POINTS) {
		   return false;
	   }	   
	   sortKey = key;
	   return true;
   }

   
   
   
   
   
   // could be an instance method and, if so, would take one parameter
   public static int compareTwoStudents( Student firstStud, Student secondStud) {
      int result;
      
      switch (sortKey) {
    	  case SORT_BY_FIRST:
    		  result = firstStud.firstName.compareToIgnoreCase(secondStud.firstName);
    		  break;
    	  case SORT_BY_POINTS:
    		  result = firstStud.totalPoints - secondStud.totalPoints;
    		  break;
    	  default:
    		  result = firstStud.lastName.compareToIgnoreCase(secondStud.lastName);
    		  break;
      }
      return result;
   }
   
   
   
   
   
   
   public String toString() {
      String resultString;

      resultString = " "+ lastName 
         + ", " + firstName
         + " points: " + totalPoints
         + "\n";
      return resultString;
   }

   private static boolean validString( String testStr ) {
      if (
            testStr != null && testStr.length() > 0 
            && Character.isLetter(testStr.charAt(0))
         )
         return true;
      return false;
   }

   private static boolean validPoints( int testPoints ) {
      if (testPoints >= 0 && testPoints <= MAX_POINTS)
         return true;
      return false;
   }
}






class StudentArrayUtilities {
   
	public static final int MAX_STUDENTS = 20;
	
	private Student[] theArray;
	private int numStudents;
	
	StudentArrayUtilities () {
		numStudents = 0;
		theArray = new Student[MAX_STUDENTS];
	}
	
	public boolean addStudent(Student stud) {
		if (numStudents == MAX_STUDENTS || stud == null)
			return false;
		theArray[numStudents++] = stud;
		return true;
	}
	
	public Student removeStudent() {
		if (numStudents == 0)
			return null;
		return theArray[--numStudents];
	}
	
	public String toString(String title) {
		
		String output = title+"\n";

		// build the output string from the individual Students:
		for (int k = 0; k < lengthWithoutNull(); k++) 
			output += " " + theArray[k].toString();
		return output;
	}
   
   
   
   
   
   
	public double getMedianDestructive () {
		double median;
		int arrayLength;
		int clientKey;
	   
		arrayLength = lengthWithoutNull();
		clientKey = Student.getSortKey();
	   
		if (arrayLength == 0)
			median = 0.0;	
		else if (arrayLength == 1)
			median = theArray[0].getTotalPoints();
		else {
			Student.setSortKey(Student.SORT_BY_POINTS);
			arraySort(); //sort the array before finding median
			if (arrayLength % 2 == 0)     
				median = (theArray[arrayLength/2].getTotalPoints() + theArray[arrayLength/2-1].getTotalPoints())/2.;
			else	median = theArray[(arrayLength)/2].getTotalPoints();
		}
		Student.setSortKey(clientKey);
		return median;
	}

   
   
   
   
   
   // returns true if a modification was made to the array
   private boolean floatLargestToTop(int top) {
      boolean changed = false;
      Student temp;

      // compare with client call to see where the loop stops
      for (int k = 0; k < top; k++)
         if ( Student.compareTwoStudents(theArray[k], theArray[k + 1]) > 0 ) {
            temp = theArray[k];
            theArray[k] = theArray[k + 1];
            theArray[k + 1] = temp;
            changed = true;
         }
      return changed;
   }

   
   
   
   
   
   // public callable arraySort() - assumes Student class has a compareTo()
   public void arraySort() {
      for (int k = 0; k < lengthWithoutNull(); k++)
         // compare with method def to see where inner loop stops
         if ( !floatLargestToTop(lengthWithoutNull() - 1 - k) )
            return;
   }
   
   
   private int lengthWithoutNull() {
	   int len=0;
	   for (int m = 0; m < theArray.length; m++)
		   if (theArray[m]!=null)
			   len++;
	   return len;
   }
   
}