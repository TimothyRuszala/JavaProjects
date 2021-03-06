/******************************************************************************
 *  Name:      Tim Ruszala
 *  NetID:     truszala
 *  Precept:   P04A
 *
 *  Partner Name:     N/A
 *  Partner NetID:    N/A
 *  Partner Precept:  N/A
 *
 *  Hours to complete assignment (optional):
 *
 ******************************************************************************/

Programming Assignment 8: Burrows-Wheeler



/******************************************************************************
 *  List in table format which input files you used to test your program.
 *  Fill in columns for how long your program takes to compress and
 *  decompress these instances (by applying BurrowsWheeler, MoveToFront,
 *  and Huffman in succession). Also, fill in the third column for
 *  the compression ratio (number of bytes in compressed message 
 *  divided by the number of bytes in the message).
 *****************************************************************************/

File         Encoding Time    Decoding time      Compression ratio
------------------------------------------------------------------------
abra.txt      0m0.198s         0m0.205s        26 bytes / 12 bytes = 2.167
aesop.txt     0m0.564s         0m0.310s          102KB / 192KB = 0.53


/******************************************************************************
 *  Compare the results of your program (compression ratio and running
 *  time) on mobydick.txt to that of the most popular Windows
 *  compression program (pkzip) or the most popular Unix/Mac one (gzip).
 *  If you don't have pkzip, use 7zip and compress using zip format.
 *****************************************************************************/
Really fast! compare encoding taking ~2 seconds to 0.110 seconds. That's nearly 
    20 times faster. As for memory usage, gzip is also more efficient, turning 
    the 1191KB file into one with a size of 486KB. Compare 0.52 to 0.40. 
    Wow! That's the power of good algorithms!

/******************************************************************************
 *  Give the order of growth of the running time of each of the 6
 *  methods as a function of the input size n and the alphabet size R
 *  both in practice (on typical English text inputs) and in theory
 *  (in the worst case), e.g., n, n + R, n log n, n^2, or R n.
 *
 *  Include the time for sorting circular suffixes in Burrows-Wheeler
 *  transform().
 *****************************************************************************/

                                      typical            worst
---------------------------------------------------------------------
BurrowsWheeler transform()            nlogn              nlogn
BurrowsWheeler inverseTransform()     n + R              n + R
MoveToFront encode()                  n + R              nR
MoveToFront decode()                  n + R              nR
Huffman compress()                    n + R log R        n + R log R
Huffman expand()                      n                  n





/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
 None. Can't compress infinitely, of course.


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
 None.

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
 None. Had a little bit of trouble with reading the '-' or '+', actually.
    I'm still not sure why it doesn't work to read the character as a string
    and use "if (args[0] == "-")".


/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it. Additionally, you may include any suggestions
 *  for what to change or what to keep (assignments or otherwise) in future 
 *  semesters.
 *****************************************************************************/
    
    Thanks for the course!
    
