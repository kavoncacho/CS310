package data_structures;

public class PersonalDriver {
    public static void main(String[] args) {
        UnorderedLinkedListPriorityQueue<Integer> uSLL = new UnorderedLinkedListPriorityQueue();
        OrderedLinkedListPriorityQueue<Integer> oSLL = new OrderedLinkedListPriorityQueue();

        oSLL.insert(5);
        oSLL.insert(6);
        oSLL.insert(29);
        oSLL.insert(16);
        oSLL.insert(30);
        oSLL.insert(33);
        oSLL.insert(1);
        oSLL.insert(13);
        oSLL.insert(1);
        oSLL.insert(6);
        oSLL.insert(10);
        oSLL.insert(2);
        oSLL.insert(4);
        oSLL.insert(56);
        oSLL.insert(2);
        oSLL.insert(87);
        oSLL.insert(12);

        //oSLL.testPrint();
        oSLL.remove();
        oSLL.remove();
        System.out.println("--------------");
        //oSLL.testPrint();
        System.out.println("---------------");
        System.out.println(oSLL.peek());

    }
}
