/******************************************************************************
 *  Name:     Tim Ruszala
 *  NetID:    truszala
 *  Precept:  P04A
 *
 *  Partner Name:       N/A
 *  Partner NetID:      N/A
 *  Partner Precept:    N/A
 *
 *  Hours to complete assignment (optional): 10
 *
 ******************************************************************************/

Programming Assignment 3: Autocomplete


/******************************************************************************
 *  Describe how your firstIndexOf() method in BinarySearchDeluxe.java
 *  to find the first index of a key that equals the search key.
 *****************************************************************************/
My firstIndexOf() method uses an extended Binary Search algorithm. First,
    I binary searched the array to find any instance of the key, then I
    continued the binary search to find the first instance of the key. Here's
    exactly how it works, starting with the lo, hi, and mid values calculated
    during the first binary search to find any instance of the key:
    
    Set hi to mid-1, in order to set the frame to search the part of the array
    that may have an earlier instance of the key. Then set mid as the middle
    value in the array, just as in ordinary binary search. If the value at mid
    is equal to the key, then set hi to mid-1 again to continue searching for
    an earlier instance. If the value at mid is not equal to the key, set lo
    to mid + 1, to check if you haven't missed the earliest instance.
    
    When lo >= hi, stop. Based on whether lo and hi have crossed or whether
    lo and hi are equal, we can deduce which index holds the last key with
    no more than one more comparison.


/******************************************************************************
 *  Identify which sorting algorithm (if any) that your program uses in the
 *  Autocomplete constructor and instance methods. Choose from the following
 *  options:
 *
 *    none, selection sort, insertion sort, mergesort, quicksort, heapsort
 *
 *  If you are using an optimized implementation, such as Arrays.sort(),
 *  select the principal algorithm.
 *****************************************************************************/

Autocomplete() : mergesort

allMatches() :  mergesort

numberOfMatches() :  none

/******************************************************************************
 *  What is the order of growth of the number of compares (in the worst case)
 *  that each of the operations in the Autocomplete data type make, as a
 *  function of the number of terms n and the number of matching terms m?
 *
 *  Recall that with order-of-growth notation, you should discard
 *  leading coefficients and lower-order terms, e.g., m^2 + m log n.
 *****************************************************************************/

Autocomplete(): n log n

allMatches(): log n + m log m

numberOfMatches(): log n




/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
No Term can have a weight smaller than - 2^63 or larger than 2^63 - 1.

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *
 *  Also include any resources (including the web) that you may
 *  may have used in creating your design.
 *****************************************************************************/
No help from any people, just textbook. Referenced general information from the
    internet, but didn't base any of my code off of anything I found online.

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
byPrefixOrder() was pretty tough for me, partially because I didn't think
    things through well enough at first, partially because I didn't have much
    of an understanding of what it meant for a variable or class or method to
    be "static."

/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/
No partner.

/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/

