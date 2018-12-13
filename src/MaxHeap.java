import java.util.Arrays;

public class MaxHeap {
    private int[] heap;
    private int length;

    public MaxHeap(int[] arr, int length) {
        this.heap = buildHeap(arr, length);
        this.length = length;
    }

    private static int[] buildHeap(int[] arr, int length) {
        for (int i = length / 2 - 1; i >= 0; i--) {
            sinkHeap(arr, length, i);
        }
        return arr;
    }

    private static void sinkHeap(int[] heap, int length, int index) {
        int currIndex = index;
        while (isHasChild(heap, length, currIndex)) {
            int leftChildIndex = getLeftChildIndex(currIndex);
            int rightChildIndex = getRightChildIndex(currIndex);
            int largerChildIndex = -1;

            if (leftChildIndex < length && heap[leftChildIndex] > heap[currIndex]) {
                largerChildIndex = leftChildIndex;
            }

            // if right child exists, left child must exist too
            if (rightChildIndex < length && heap[rightChildIndex] > heap[leftChildIndex]) {
                largerChildIndex = rightChildIndex;
            }

            // swap with largest child only if it is larger than parent
            if (largerChildIndex != -1) {
                swap(heap, currIndex, largerChildIndex);
                currIndex = largerChildIndex;
            } else {
                break;
            }
        }
    }

    public void insert(int value) {
        // double heap capacity if it is reached
        if (this.length + 1 > this.heap.length) {
            this.heap = resize(this.heap);
        }

        // insert as leftest leaf
        this.heap[this.length] = value;
        this.length++;

        // swim up
        int currIndex = this.length - 1;
        while (!isRoot(currIndex)) {
            int parentIndex = getParentIndex(currIndex);
            // swap with parent if child is larger
            if (this.heap[currIndex] > this.heap[parentIndex]) {
                swap(this.heap, currIndex, parentIndex);
                currIndex = parentIndex;
            }
        }
    }

    // precondition: at least one element in heap
    public int extractMax() {
        int rootNode = this.heap[0];
        this.heap[0] = this.heap[this.length - 1];
        // mark as default value 0
        this.heap[this.length - 1] = 0;
        this.length--;

        // sink root node
        sinkHeap(this.heap, this.length, 0);
        return rootNode;
    }

    public int size() {
        return this.length;
    }

    private static int[] resize(int[] arr) {
        return Arrays.copyOf(arr, arr.length * 2);
    }

    public static void main(String[] args) {
        // sinkHeap works
        // int[] heap = {1,10,11,9,8,7,6};
        // sinkHeap(heap, 7, 0);

        //build heap works
        // int[] arr = { 1, 2, 3, 4, 5, 6, 7};
        // MaxHeap mh = new MaxHeap(arr, 7);

        // insert works
        // int[] arr = {5, 4, 3};
        // MaxHeap mh = new MaxHeap(arr, 3);
        // mh.insert(10);

        // extractMax works
        // int[] arr = {10, 5, 3, 4};
        // MaxHeap mh = new MaxHeap(arr, 4);
        // int max = mh.extractMax();

        // extractMax works with one element
        int[] arr = {10};
        MaxHeap mh = new MaxHeap(arr, 1);
        int max = mh.extractMax();
    }

    private static int getLeftChildIndex(int index) {
        return (index + 1) * 2 - 1;
    }

    private static int getRightChildIndex(int index) {
        return (index + 1) * 2;
    }

    private static int getParentIndex(int index) {
        return (index + 1) / 2 - 1;
    }

    // checks if node at heap[i] has one or more children
    private static boolean isHasChild(int[] heap, int length, int index) {
        int leftChildIndex = getLeftChildIndex(index);
        return leftChildIndex < length;
    }

    private static boolean isRoot(int index) {
        return getParentIndex(index) < 0;
    }

    // precondition: both indexOne and indexTwo are within range of arr length
    private static void swap(int[] arr, int indexOne, int indexTwo) {
        int aux = arr[indexOne];
        arr[indexOne] = arr[indexTwo];
        arr[indexTwo] = aux;
    }

}
