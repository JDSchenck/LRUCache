
public class LRUMain {


    public static void main(String[] args) {
        LRULinkedCache<Integer, Integer> cache = new LRULinkedCache<Integer, Integer>(4);
        cache.LRUPut(1,5);
        System.out.println("cache after calling LRUPut (1,5): " + cache.toString());
        cache.LRUPut(2,2);
        System.out.println("cache after calling LRUPut (2,2): " + cache.toString());        
        cache.LRUPut(3,7);
        System.out.println("cache after calling LRUPut (3,7): " + cache.toString());        
        cache.LRUPut(4,9);
        System.out.println("cache after calling LRUPut (4,9): " + cache.toString());   
        cache.LRUPut(1,9);
        System.out.println("cache after calling LRUPut (1,9): " + cache.toString());  
        System.out.println("LRUGET(3) RETURNED: " + cache.LRUGet(3));
        System.out.println("CACHE AFTER CALLING LRUGET 3: " + cache.toString());
        cache.LRUPut(5,10);
        System.out.println("cache after calling LRUPut (5,10): " + cache.toString());    
        cache.LRUGet(4);
        System.out.println("CACHE AFTER CALLING LRUGet(4): " + cache.toString());
        cache.LRUGet(10);
        System.out.println("CACHE AFTER CALLING LRUGet(10): " + cache.toString());
    
    }
    
}
