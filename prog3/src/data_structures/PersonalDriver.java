package data_structures;

public class PersonalDriver {
    public static void main(String[] args) {
        BinaryHeapPriorityQueue<Integer> bh = new BinaryHeapPriorityQueue<Integer>(20);

        System.out.println(bh.insert(1));
        System.out.println(bh.insert(1));
        System.out.println(bh.insert(1));
        System.out.println(bh.insert(1));
        System.out.println(bh.insert(1));
        System.out.println(bh.insert(5));
        System.out.println(bh.insert(5));
        System.out.println(bh.insert(10));
        System.out.println(bh.insert(10));
        System.out.println(bh.insert(10));
        System.out.println(bh.insert(5));
        System.out.println(bh.insert(10));
        System.out.println(bh.insert(5));
        System.out.println(bh.insert(10));
        System.out.println(bh.insert(5));
        System.out.println("-------------");
        bh.testPrint();
        System.out.println("size is: " + bh.size());
        System.out.println("-------------");
        System.out.println(bh.delete(10));
        //System.out.println(bh.remove());
        System.out.println("-------------");
        bh.testPrint();
        System.out.println("size is: " + bh.size());
        System.out.println("-------------");
        //System.out.println(bh.remove());
        //System.out.println("-------------");
        //bh.testPrint();


    }
}
