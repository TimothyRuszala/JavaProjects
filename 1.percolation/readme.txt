/******************************************************************************
 *  Name:     Tim Ruszala 
 *  NetID:    truszala 
 *  Precept:  P04A
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Operating system:  Mac OS Sierra
 *  Compiler:          algs4?
 *  Text editor / IDE: Dr. Java
 *
 *  Have you taken (part of) this course before: no
 *  Have you taken (part of) the Coursera course Algorithm, Part I: no
 *
 *  Hours to complete assignment (optional): 
 *
 ******************************************************************************/

Programming Assignment 1: Percolation


/******************************************************************************
 *  Describe how you implemented Percolation.java. How did you check
 *  whether the system percolates?
 *****************************************************************************/

 I used the WeightedQuickUnionUF data type to keep track of connections
between sites (for isFull() and percolates()) and I used a boolean[][]
to keep track of whether a site was open or closed. I also included a
virtual top and a virtual bottom component in the UF to efficiently determine
whether the system percolates or not at any given moment.

Opening a site and connecting it to open neighbor sites was done through
open(), and filling a site is tied with whether or not that site is
connected to the virtual top.


/******************************************************************************
 *  Perform computational experiments to estimate the running time of
 *  PercolationStats.java for values values of n and T when implementing
 *  Percolation.java with QuickFindUF.java.
 *
 *  To do so, fill in the two tables below. Each table must have 4-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one. Do not include
 *  data points that takes less than 0.1 seconds.
 *****************************************************************************/

(keep T constant).         (T = 1000)

 n          time (seconds)       
-------------------------
50             0.183
75             0.492
100            0.90
150            2.502
200            5.45     
300            17.22   



(keep n constant)               (n = 150)

 T          time (seconds)        
--------------------------
125           0.338       
250           0.653       
500           1.312   
1000          2.691    
2000          5.134    
3000          7.865    
4000          10.21     
6000          15.936
8000          20.154    


/******************************************************************************
 *  Using the empirical data from the above two tables, give a formula 
 *  (using tilde notation) for the running time (in seconds) of
 *  PercolationStats.java as function of both n and T, such as
 *
 *       ~ 5.3*10^-8 * n^5.0 * T^1.5
 *
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient to two significant digits.
 *
 *****************************************************************************/

QuickFindUF running time (in seconds) as a function of n and T:  ~ 

   ~ 3.6*10^-11 * n^2.8 * T^2.0
/******************************************************************************
 *  Repeat the previous two questions, but using WeightedQuickUnionUF
 *  (instead of QuickFindUF).
 *****************************************************************************/

(keep T constant).      (T = 1000)

 n    time (seconds)  
------------------------------
100     0.502   
200     2.176   
300     5.076   
400     9.649   
450     12.324   
        
500     15.232   
600     25.259




(keep n constant)              

 T          time (seconds)  
------------------------------
1000         1.229   
2000         2.292   
4000         4.551   
8000         9.07    
16000        17.962



WeightedQuickUnionUF running time (in seconds) as a function of n and T:  ~ 

~ 6.3*10^-12 * N^2.1 * T^2.0
 

/**********************************************************************
 *  How much memory (in bytes) does a Percolation object (which uses
 *  WeightedQuickUnionUF.java) use to store an N-by-N grid? Use the
 *  64-bit memory cost model from Section 1.4 of the textbook and use
 *  tilde notation to simplify your answer. Briefly justify your answers.
 *
 *  Include the memory for all referenced objects (deep memory).
 **********************************************************************/
Memory of a WeightedQuickUnionUF object:
Overhead: 16 bytes
one int: 4 bytes
two int[]: 2 * (8 + 24 + 4N) = 8N + 64

Memory of a Percolation object:
Overhead: 16 bytes
Four ints (problem size, virtual top and bottom component keys, and
count: 24 bytes
One boolean[][]: 8 + 24 + ((8+ 24 + N) * N) = N^2 + 32N + 32
One WeightedQuickUnionUF: 8 + (8N + 64) = 64N + 512
total: 16 + 24 + N^2 + 32N + 32 + 64N + 512 = N^2 + 96N + 576

Simplified answer: ~N^2 bytes
/******************************************************************************
 *  Known bugs / limitations
 *****************************************************************************/

My code is PERFECT! (Just kidding, but I don’t know about any bugs,
besides backwash.)
               

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
Lauren Pick’s office hours.
               
/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/

My empirical tests for constant T and changing n were very wonky both times I
did them. I redid each trial multiple times, and ultimately settled for a
rough average of the calculated exponents.



/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/
Glad you gave us a warm-up assignment- I haven’t taken COS since Freshman fall
(I’m a springy sophomore) so very much appreciated.