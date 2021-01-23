package sorting;
import java.util.*;

/**
 * @author Michael Gulchuk
 * @version 1.0
 */
public class SortingAlgorithms {
    // how many groups to form
    private static final int GROUP_TOTAL = 15;
    private static final Node[] groups = new Node[GROUP_TOTAL];
    private static double boundSize = 0;
    private static int sum = 0;

    // variables to change random array
    private static final int ARRAY_SIZE = 20;
    private static final int LOW = 1;
    private static final int HIGH = 100;


    public static class Node{
        private final int value;
        private Node next;

        /**
         * @param value value of node
         * @param next next value in node
         */
        public Node(int value, Node next){
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + next +
                    '}';
        }
    }

    /**
     * @param bounds upper bounds array
     * @param arr an array to be sorted
     * @return an array of linked list nodes
     */
    public static Node[] formGroups(int[] bounds, int[] arr) {

        for (int value : arr) {
            for (int j = 0; j < GROUP_TOTAL; j++) {
                if (value <= bounds[j]) {
                    addGroup(value, j);
                    break;
                }
            }
        }

        return groups;
    }

    private static void addGroup(int num, int index) {

        Node temp = groups[index];

        if(groups[index] == null) {
            groups[index] = new Node(num, null);
        }
        else if(temp.value > num) {
            groups[index] = new Node(num, temp);
        }else{
            while(temp.next != null) {

                if(temp.next.value >= num) {
                   Node current = temp.next;
                   temp.next = new Node(num, current);
                   return;
                }

                temp = temp.next;
            }

            temp.next = new Node(num, null);
        }
    }


    /**
     * @param groups an array of linked list nodes
     * @param array an array to print the bounds
     */
    public static void print(Node[] groups, int[] array) {

        for (int i = 0; i < groups.length; i++) {
            Node temp = groups[i];
            System.out.print(i + "[" + (array[i] - sum) + "-" + array[i] + "]: ");

            if(temp == null){
                System.out.print("null");
            }

            while (temp != null) {
                if(temp.next != null) {
                    System.out.print(temp.value + " -> ");
                }
                else{
                    System.out.print(temp.value);
                }
                temp = temp.next;
            }

            System.out.println();
        }
    }

    /**
     * @param groups an array of linked list nodes
     * @param array an array to sort back into
     * @return a sorted array
     */
    public static int[] sort(Node[] groups, int[] array) {

        int count = 0;

        for (Node group : groups) {
            Node temp = group;
            while (temp != null) {
                array[count] = temp.value;
                temp = temp.next;
                count++;
            }
        }

        return array;
    }

    /**
     * @param arr an array to determine the upperBounds
     * @return an upperBound array
     */
    public static int[] getUpperBound(int[] arr) {
        int high = getMax(arr);
        int low = getMin(arr);

        boundSize = (double) (high - low + 1) / GROUP_TOTAL;
        sum = (int) boundSize;

        int bound = Math.round(low + sum);
        int[] upperBound = new int[GROUP_TOTAL];

        for (int i = 0; i < GROUP_TOTAL - 1; i++) {
            upperBound[i] = bound;
            bound = Math.round(bound + sum);
        }

        upperBound[GROUP_TOTAL - 1] = high;

        return upperBound;

    }

    /**
     * @param array an array to see of there are inversions
     * @return true or false if inversions exist
     */
    public static boolean hasInversions(int[] array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if(array[i] > array[j]){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param arr an array to find the min value
     * @return min value of the array
     */
    public static int getMax(int[] arr) {
        int max = arr[0];

        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    /**
     * @param arr an array to find the max value
     * @return max value of the array
     */
    public static int getMin(int[] arr) {
        int min = arr[0];

        for (int value : arr) {
            if (value < min) {
                min = value;
            }
        }

        return min;
    }

    /**
     * @param size size of array
     * @param min minimum value to generate array
     * @param max maximum value to generate array
     * @return random array made
     */
    public static int[] randomArray(int size, int min, int max) {
        Random random = new Random();
        int[] array = new int[size];

        for (int i = 0; i < array.length; i++) {
            array[i] = min + random.nextInt(max - min + 1);
        }

        return array;
    }

    /**
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        int[] array = randomArray(ARRAY_SIZE, LOW, HIGH);
        int[] upper = getUpperBound(array);

        System.out.println("Generating an array of size " + ARRAY_SIZE +
                " with elements in the range [" + LOW + "," + HIGH + "]");
        System.out.println("Original: " + Arrays.toString(array));
        System.out.println();

        System.out.println("Sorting with " + GROUP_TOTAL + " groups");
        System.out.println("Min/max: [" + getMin(array) + ", " + getMax(array) + "]");
        System.out.println("Group size: " + String.format("%.1f", boundSize));
        System.out.println("Group thresholds: " + Arrays.toString(upper));
        System.out.println();

        print(formGroups(upper, array), upper);

        System.out.println();
        System.out.println("Sorted: " + Arrays.toString(sort(groups, array)));
        System.out.println("Detected inversions? " + hasInversions(array));

    }

}
