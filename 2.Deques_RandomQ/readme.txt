/******************************************************************************
 *  Name:             Timothy Ruszala
 *  NetID:            truszala
 *  Precept:          P04A
 *
 *  Partner Name:     N/A
 *  Partner NetID:    N/A 
 *  Partner Precept:  N/A 
 *
 *  Hours to complete assignment (optional):
 *
 ******************************************************************************/

Programming Assignment 2: Deques and Randomized Queues


/******************************************************************************
 *  Explain briefly how you implemented the randomized queue and deque.
 *  Which data structure did you choose (array, linked list, etc.)
 *  and why?
 *****************************************************************************/
I used a linked list for the deque first because I wasn't very familiar with
    linked lists, and second because I felt like it might be easiest to add
    or subtract an element to either end. I altered the linked list to link
    both forward and backwards to make it easier to remove the end node. I also
    made sure to remove references to unused nodes in order to avoid loitering.
    
I used a resizing array for the second first of all because I had become fairly
    familiar with linked lists over the course of making the deque and wanted
    to get to know arrays better instead. But beyond that, because the 
    assignment suggested that we would need to think in terms of amortized
    runtime, and because I was already familiar with the amortized runtime of
    a resizing array, it seemed to be the better option. Specifically, my
    randomized queue stored enqueued objects in the next open spot in the array.
    To dequeue, I selected a random index and returned the element in that
    index, replacing the hole with the element at the end of the queue. I
    resized when necessary.

/******************************************************************************
 *  How much memory (in bytes) do your data types use to store n items
 *  in the worst case? Use the 64-bit memory cost model from Section
 *  1.4 of the textbook and use tilde notation to simplify your answer.
 *  Briefly justify your answers and show your work.
 *
 *  Do not include the memory for the items themselves (as this
 *  memory is allocated by the client and depends on the item type)
 *  or for any iterators, but do include the memory for the references
 *  to the items (in the underlying array or linked list).
 *****************************************************************************/

Randomized Queue:   ~  _____  bytes

Deque:              ~  _____  bytes

-------------------------Work---------------------------
    Memory of Deque:
         16 (overhead)
          8 (two node references, "first" and "last")
          4 (one int for "count")
       +48n (48 bytes for each Node, consisting of:
               24 for overhead, and 3x8=24 bytes for three item references to
               two Nodes and an Item)
          4 (padding)
       -----
      = ~48n + 32 bytes
    
    Memory of RandomizedQueue:
         16 (overhead)
          4 (int n)
   32n + 24 (Item[] a consisting in the worst case (1/4 full) of enough room for
             4n 8-byte references, summing up to 32n bytes, plus a 24 byte
             array overhead.
          4 (padding)
       -----
      = ~ 32n + 48 bytes
              


/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
None that I know of.

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
Charlie Murphy's Office Hours


/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
Trouble finding a memory-efficient solution for RandomizedQueue, and Lots and 
   lots of bugs with Deque. 


/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/