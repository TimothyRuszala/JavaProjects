/******************************************************************************
 *  Name:     Tim Ruszala
 *  NetID:    truszala
 *  Precept:  P04A
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Hours to complete assignment (optional):
 *
 ******************************************************************************/

Programming Assignment 7: Seam Carving


/******************************************************************************
 *  Describe concisely your algorithm to compute the horizontal and
 *  vertical seam. 
 *****************************************************************************/
To compute the vertical seam, I use an adjusted version of Dijkstra's shortest
    path algorithm. I began by calculating an array which corresponded to the
    picture and held the energy of each pixel. Then I added each pixel index to
    an IndexMinPQ, with the pixel index as the index and the pixel's energy
    as the value. Using Dijkstra, I looked at the index with the lowest
    distance, calculated as the energy of the index plus the distance of its
    parent. I added indices to the minPQ following Dijkstra's algorithm,
    but instead of looking at all of a vertex's edges in a graph, I looked only
    at three pixels, one directly down, one down-left, and one down-right. When
    I reached the bottom row I finished and knew I had the shortest path from
    top to bottom. I reconstructed the path using a vertTo[] array which
    I had created in accordance with Dijkstra.
    
    For horizontal seams, I just transposed the picture sideways, updating
    height and width, and called FindVerticalSeam(). Then I reversed my
    transposition / updates.


/******************************************************************************
 *  Describe what makes an image ideal for this seamCarving algorithm and what
 *   kind of image would not work well.
 *****************************************************************************/
An image which would work well is one which has large, homogenous sections of
    color. For example, if there is a large blue sky, then the algorithm would
    probably remove seams from the blue sky, which would hardly be noticed.
    But if the picture is active throughout, if it has many details, then
    removing a seam is more likely to deform interesting details of the image,
    which is contrary to the purpose of this algorithm.


    
/******************************************************************************
 *  Give a formula (using tilde notation) for the running time (in seconds)
 *  required to reduce the image by one row and a second formula for the
 *  running time to reduce the image by one column. Both should be functions
 *  of W and H. Reducing the image by one row/column involves exactly one
 *  call to the appropriate find() method and one call to the corresponding
 *  remove() method.
 * 
 *  Justify your answer experimentally. To do so, fill in the two tables
 *  below. Each table must have 4-10 data points. Do not include data points
 *  that takes less than 0.5 seconds. To dampen system effects, you may 
 *  perform many trials for a given value of W and H and average the results.
 *  
 *  For the leading coefficients and exponents, use 2 digits after the
 *  decimal point. Show your calculations.
 *****************************************************************************/

(keep W constant)

    (W = 5000)
 H           Row removal time (seconds)     Column removal time (seconds)
--------------------------------------------------------------------------
...
1250          .98                            0.61
2500          1.82                           1.23
3750          3.15                           1.72
5000          3.94                           2.22
7500          5.46                           3.23
10000         7.72                           4.70



(keep H constant)

    (H = 2000)
 W           Row removal time (seconds)     Column removal time (seconds)
--------------------------------------------------------------------------
.
...
2000         0.76                            0.48* (not used to calc avg)
4000         1.24                            0.82
6000         1.78                            1.17
8000         2.72                            1.56
12000        3.54                            2.46
16000        4.97                            3.25


Running time to remove one row as a function of both W and H:  
    ~ 4.87x10^-7 * (W^0.93) * (H^0.94)
    
    // finding exponents
    T(2*W, H) / T(W, H) = (2^x)c(W^x)(H^y) / c(W^x)(H^y) = 2^x
    T(W, 2*H) / T(W, H) = (2^x)c(W^x)(H^y) / c(W^x)(H^y) = 2^x
    
    Average T(2*W, H) / T(W, H) = 1.90
    2^x = 1.90
    x = 0.93
    
    Average T(W, 2*H) / T(W, H) = 1.92
    2^y = 1.92
    y = 0.94
    
    // finding coefficient
    T(W,H) = c(W^x)(H^y)
    7.72 = c(5000^0.93)(10000^0.94)
    c = 4.87x10^-7
    
   

Running time to remove one column as a function of both W and H:  
    ~ (1.04*10^-7) * (W^1.02) * (H^0.96)

    // finding exponents
    T(2*W, H) / T(W, H) = (2^x)c(W^x)(H^y) / c(W^x)(H^y) = 2^x
    T(W, 2*H) / T(W, H) = (2^x)c(W^x)(H^y) / c(W^x)(H^y) = 2^x
    
    Average T(2*W, H) / T(W, H) =
    2^x = 2.03
    x = 1.02
    
    Average T(W, 2*H) / T(W, H) = 
    2^y = 1.95
    y = 0.96
    
    // finding coefficient
    T(W,H) = c(W^x)(H^y)
    3.23 = c(5000^1.02)(7500^0.96)
    c = 1.04*10^-7




/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
Lab TAs

/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/
No partner.

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
How to store energy, and a tiny bug in relax which took me forever to find
    ( I used <= instead of <).

/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/
    I like assignments with pictures.