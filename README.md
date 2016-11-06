# LRUCache
[Java] A least recently used (LRU) cache implementation of a linked list

Big-Oh Analysis of LRUGet and LRUPut method(s)

LRUPut:

In the beginning of this method the linked list is traversed by calling the findPos method and this call is of O(N) since it is a linear search of the linked list traversing from beginning to end. 

There are then four possible cases that are handled:
1)	The node with matching key is found in list.
2)	The list is empty.
3)	The node is not found, and the list is full.
4)	The list is not full and the node is not found.

Most of these cases, the three where the node is not found, involve creating a new node to be added to the list, which calls upon the findPos method again which O(N).

In conclusion, in the worst case, this method will perform at: O(2N) = O(N).

LRUGet:

In this method, the linked list is only traversed once in the beginning to determine if the passed in key is present in a node by calling the findPos method. This is of O(N).
This method will perform at worst O(N).
