/******************************************************************************
 *  Name:     Tim Ruszala
 *  NetID:    truszala
 *  Precept:  P04A
 *
 *  Partner Name:       N/A
 *  Partner NetID:      N/A
 *  Partner Precept:    N/A
 *
 *  Hours to complete assignment (optional): I always lose track!
 *
 ******************************************************************************/

Programming Assignment 5: Kd-Trees


/******************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 *****************************************************************************/
    I used the Node structure suggested by the checklist: Point2D key, generic
    value, holds a RectHV for the range search, and holds references to two
    children.
    
/******************************************************************************
 *  Describe your method for range search in a kd-tree.
 *****************************************************************************/
    I created a stack, which I return as the Iterable<Point2D>. Then I
    use a recursive helper function, also called range(), to a) check whether 
    the current point is within the query rectangle's range, and b) then 
    call range() on whichever child's rectangle of the current node
    intersects the query rectangle. This way I search all points which may
    be in the query rectangle, and avoid searching those which I know are not.

/******************************************************************************
 *  Describe your method for nearest neighbor search in a kd-tree.
 *****************************************************************************/
    I use a recursive function, nearest(), for the nearest neighbor search.
    For each Node I a) check whether its point is closer to the query point
    than the current nearest, b) determine whether the query point is 
    left/bottom or right/top of the current Node, c) recursively call nearest()
    on the lb / rt child, depending on step b described above, and d) determine
    whether the nearest point could possibly be in the non-searched child's
    subtree, and if so, call nearest() on that child.

/******************************************************************************
 *  How many nearest neighbor calculations can your brute-force
 *  implementation perform per second for input100K.txt (100,000 points)
 *  and input1M.txt (1 million points), where the query points are
 *  random points in the unit square?
 *
 *  Show your work by providing, for each input size (100K and 1M),
 *  how many calls to nearest your timing test made and how much time
 *  it took the test to complete.
 *  (Do not count the time to read in the points or to build the 2d-tree.)
 *
 *  Repeat the question but with the 2d-tree implementation.
 *****************************************************************************/

                       calls to nearest() per second
                     brute force               2d-tree
                     ---------------------------------
input100K.txt  
          5,000 calls / 13.021 seconds   50,000,000 calls / 7.732 seconds
               384 calls per second          6.47E6 calls per second

input1M.txt        
            25 calls / 1.662 seconds      50,000,000 calls / 4.2 seconds
                15 calls per second          1.19E7 calls per second
    
   



/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
 None known!

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
 General conceptual help from classmate Yunzi Shi, who directed me to a
    lecture slide she thought might help me with points().

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
points(), range(), nearest(), basically everything...

/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/
N/A



/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on  how helpful the class meeting was and on how much you learned 
 * from doing the assignment, and whether you enjoyed doing it.
 *****************************************************************************/
