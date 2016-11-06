/**
 * 
 * @author esahe2
 *
 * @param <K> The type of the key
 * @param <V> The type of the value
 */


public class LRULinkedCache<K,V> 
{
	
	
	
	
	/*************
	 *	attributes
	 ************/
	
	/** current number of items in cache */
	private int theSize;
	
	/** The capacity of cache. */
	private int capacity;
	
	/** reference to list header node */
	private CacheNode<K,V> head;
	
	/** reference to list tail node */
	private CacheNode<K,V> tail;
	
		/***************
	 *	constructors
	 **************/

	/**
	 *	return a new, empty cache with a given capacity
	 */
	public LRULinkedCache(int capacity)
	{
		this.capacity=capacity;
		// empty this LinkedList
		clear();
	}


	/**********
	 *	methods
	 *********/


	
	/**************************************
	 *	methods inherited from class Object
	 *************************************/

	/**
	 *	return a String representation of the LinkedList
	 *
	 *	items are listed in order from beginning to end in comma-delimited fashion
	 */
        @Override
	public String toString()
	{
		String s = "";
		
		CacheNode<K,V> current= head.next;
		while(current!=tail)
		{
			s+= "("+current.key +"," +current.value+")";
			if (current.next!= tail)
				s+=",";
			current= current.next;
		}
		
		return s;
	}


	/**********************************************
	 *	methods inherited from interface Collection
	 *********************************************/
	
	

	/**
	 *	empty the LRUCache
	 *	size will be set to zero
	 */
	public void clear()
	{
		// reset header node
		head = new CacheNode<K,V>(null,null,null ,null);
		// reset tail node
        tail = new CacheNode<K,V>(null,null,head, null);
        head.next=tail;
        tail.previous=head;
        // reset size to 0
		theSize = 0;
		
		
	}


	/**
	 *	return the number of items in the cache
	 */
	public int size()
	{
		return theSize;
	}


	/**
	 *	return true if the cache is empty
	 */
	public boolean isEmpty()
	{
		return theSize == 0;
	}

		
	
	/**
	 *	This operation returns the value for a given key in the cache. It returns null if the data is not currently in the cache.
	 *  It also moves the data that is accessed to the end of the list and inserts it before tail
	 */
	public V LRUGet(K key)
	{
		
            // if the node is found in the list already, return that node
            if (findPos(key) != null)
            {
                CacheNode<K,V> foundNode = findPos(key);
                CacheNode<K,V> previousNeighbor = foundNode.previous;
                CacheNode<K,V> nextNeighbor = foundNode.next;
                
                // break the pointers, remove foundNode from current position
                nextNeighbor.previous = previousNeighbor;
		previousNeighbor.next = nextNeighbor;
                
                // replace foundNode at end of list since LRU node
                CacheNode<K,V> lastNode = tail.previous;
                lastNode.next = foundNode;
                tail.previous = foundNode;
                foundNode.next = tail;
                
                // return the found key node's value
                return foundNode.value;
                
            }
           // no node with specified key found, return null
            return null;
	}
        
        private CacheNode<K,V> findPos(K passedKey){
		// traverse search from beginning of this LinkedList O(N)
                for(CacheNode<K,V> p = head; p!=null; p = p.next)
                {
                    if (passedKey.equals(p.key))
                    {
                        return p;
                    }
                }
                return null;
        }
        
        public boolean Contains(K key){
            if (findPos(key) != null)
            {
                return true;
            }
            else
            {
                return false;
            }
                
        }
        
	
	
	/**
	 * Puts a new node with key and value in the cache and adds it to the end of the cache
	 * If the cache is full, it removes the first node (The least recently used node)before adding the new node.
	 * If a node with the given key already exists in the cache, it updates the value for the key and moves the node with the key to the
	 * end of the cache
	 * 
         * @param key
	 * @param value
         * 
	 */
	public void LRUPut(K key, V value)
	{
           
            
            if (findPos(key) != null)
            {

                // node with key is already in the cache, update the value
                CacheNode<K,V> foundNode = findPos(key);
                foundNode.value = value;

                
                // reposition accessed node to end of list
                CacheNode<K,V> previousNeighbor = foundNode.previous;
                CacheNode<K,V> nextNeighbor = foundNode.next;
                
                // break the pointers, remove foundNode from current position
                nextNeighbor.previous = previousNeighbor;
		previousNeighbor.next = nextNeighbor;
                
                // replace foundNode at end of list since LRU node
                CacheNode<K,V> lastNode = tail.previous;
                lastNode.next = foundNode;
                tail.previous = foundNode; 
                foundNode.next = tail;                                                          
            }
            
            // no node with key found, proceed
            
            // if list empty
            
            if (isEmpty())
            {
             CacheNode<K,V> newNode = new CacheNode(key, value, head , tail); 
             head.next = newNode;
             tail.previous = newNode;
             theSize++;                   
            }
            
            // if the list is full and not found
            if (theSize == capacity && findPos(key)==null)
            {
                // remove the first node, least used
                removeFirst();
                
                // new cache node
                CacheNode<K,V> lastNode = tail.previous;
                CacheNode<K,V> newLast = new CacheNode(key, value, lastNode, tail);
                tail.previous = newLast;
                lastNode.next =  newLast;                
            }
  
            // if not full, and not found, simply add to end of list
            if (theSize < capacity && findPos(key) == null && !isEmpty())
            {
                // new cache node
                CacheNode<K,V> lastNode = tail.previous;
                CacheNode<K,V> newLast = new CacheNode(key, value, lastNode, tail);
                tail.previous = newLast;
                lastNode.next =  newLast;

                theSize++;
                
            }
		
	}// end LRUPut()
        
        public void removeFirst(){
            CacheNode<K,V> firstNode = head.next;
            
            CacheNode<K,V> nextNeighbor = firstNode.next;
            
            nextNeighbor.previous = head;
            head.next = nextNeighbor;
        }
	
	
	
	
	/**
	 *	Nested class CacheNode
	 *
	 *	encapsulates the fundamental building block of a LRU cache node
	 *	contains a key and value, as well as references to both the next and previous nodes
	 *	in the cache
	 *K is the type of the key and V is the type of value
	 */
	private static class CacheNode<K,V>
	{

		/*************
		 *	attributes
		 ************/
            K key;
            V value;
            CacheNode<K,V> next;
            CacheNode<K,V> previous;
            
            public CacheNode(K passedKey, V passedValue, CacheNode<K,V> previousNode, CacheNode<K,V> nextNode){
                key = passedKey;
                value = passedValue;
                previous = previousNode;
                next = nextNode;
            }				
	}// End nested class CacheNode
	
}// End class LRULinkedCache
