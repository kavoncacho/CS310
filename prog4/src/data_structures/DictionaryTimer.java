/*  DictionaryTimer.java
    A sample class to assist in performing empirical timing tests
    for your project #3.
    You may need to reduce the value of iterations to a lower count
    depending on the abilities of the machine used for timing.  If
    timing takes too long, try ierations = 7

    CS310 Spring 2018
    Alan Riggins
*/

package data_structures;

public class DictionaryTimer {
    public static void main(String[] args) {
        int iterations = 14; // number of timing tests
        int workSize = 1000000; // size of add/delete cycle loop
        int structureSize = 100; // initial size of PQ, doubles with each iteration
        int loopStructureSize = structureSize; // helper var
        int maxSize = workSize;

        long start = 0, stop = 0;
        long[] putTimes = new long[iterations]; // arrays to hold results
        long[] deleteTimes = new long[iterations];
        long[] getTimes = new long[iterations];

        DictionaryADT<Integer, Integer> pq = //new Hashtable<Integer, Integer>(512000);
                //new BinarySearchTree<Integer, Integer>();
        new BalancedTreeDictionary<Integer, Integer>();

        int[] arr = new int[maxSize];
        for (int i = 0; i < maxSize; i++)
            arr[i] = (i + 1);
        for (int i = 0; i < maxSize; i++) {
            int index = (int) (maxSize * Math.random());
            int tmp = arr[i];
            arr[i] = arr[index];
            arr[index] = tmp;
        }

        for (int i = 0; i < iterations; i++) {
            // build structure first
            pq.clear();
            start = System.currentTimeMillis(); // capture time to build
            for (int j = 0; j < structureSize; j++) {
                pq.put(arr[j], arr[j]);
            }
            stop = System.currentTimeMillis();
            putTimes[i] = (stop - start);

            // time for get operations
            start = System.currentTimeMillis();
            for (int j = 0; j < workSize; j++) {
                pq.get(arr[j]);
            }
            stop = System.currentTimeMillis();
            getTimes[i] = (stop - start);

            // time for removal (dequeue) operations
            start = System.currentTimeMillis();
            for (int j = 0; j < workSize; j++) {
                pq.delete(arr[j]);
            }
            stop = System.currentTimeMillis();
            deleteTimes[i] = (stop - start);
            structureSize <<= 1;
            System.out.println("Loop " + (i + 1) + " of " + iterations + " finished.");
        }

        // print results
        int tmp = loopStructureSize;
        System.out.println("\nINSERTION TIMES:");
        for (int i = 0; i < iterations; i++) {
            System.out.println("n=" + tmp + "  Time: " + putTimes[i]);
            tmp <<= 1;
        }
        tmp = loopStructureSize;
        System.out.println("\nRemoval TIMES:");
        for (int i = 0; i < iterations; i++) {
            System.out.println("n=" + tmp + "  Time: " + deleteTimes[i]);
            tmp <<= 1;
        }
        tmp = loopStructureSize;
        System.out.println("\nGet TIMES:");
        for (int i = 0; i < iterations; i++) {
            System.out.println("n=" + tmp + "  Time: " + getTimes[i]);
            tmp <<= 1;
        }
    }
}
