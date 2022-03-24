import java.util.*;

public class SmoothSort {
    private int[] numbers;
    private int max;
    private int iterationCount;
    private Stack<Integer> heapDegrees;

    public SmoothSort() {
        int INIT_LEN = 10;
        numbers = new int[INIT_LEN];
        numbers[0] = 1;
        numbers[1] = 1;
        max = 1;
        heapDegrees = new Stack<>();
    }


    public void sort(LinkedList<Integer> list) {
        int[] temp = new int[list.size()];
        int i = 0;
        for (Integer cur : list) temp[i++] = cur;
        sort(temp);
        list.clear();
        for (int cur : temp) list.add(cur);
    }


    public void sort(int[] array) {
        iterationCount = 0;
        for (int i = 0; i < array.length; i++) {
            increaseHeapCount(heapDegrees);
            leonardoHeapify(array, heapDegrees.size() - 1, i);
        }
        for (int i = array.length - 1; i >= 0; i--) {
            int removed = decreaseHeapCount(heapDegrees);
            if (removed == 1) continue;
            int right = i - 1;
            int left = i - 1 - leonardoNumber(heapDegrees.elementAt(heapDegrees.size() - 1));
            leonardoHeapify(array, heapDegrees.size() - 2, left);
            leonardoHeapify(array, heapDegrees.size() - 1, right);
        }
    }


    private int decreaseHeapCount(Stack<Integer> stack) {
        int p = stack.pop();
        if (p == 2) {
            stack.push(1);
            stack.push(1);
        }
        if (p > 2) {
            stack.push(p - 1);
            stack.push(p - 2);
        }
        return p;
    }


    private void increaseHeapCount(Stack<Integer> stack) {
        int curDegree = 1;
        if (stack.size() >= 2) {
            int first = stack.pop();
            int second = stack.pop();
            if (first == second - 1 || (first == second && second == 1)) {
                curDegree = second + 1;
            } else {
                stack.push(second);
                stack.push(first);
            }
        }
        stack.push(curDegree);
    }


    private int[] sortRoots(int[] array, int curDegreePos, int curRootPos) {
        int prevRootPos;
        while (true) {
            if (curDegreePos == 0) break;

            int child1Pos = curRootPos;
            int child2Pos = curRootPos;

            if (heapDegrees.elementAt(curDegreePos) >= 2) {
                child1Pos = curRootPos - 1;
                int child1Deg = heapDegrees.elementAt(curDegreePos) - 2;
                child2Pos = child1Pos - leonardoNumber(child1Deg);
            }

            prevRootPos = curRootPos - leonardoNumber(heapDegrees.elementAt(curDegreePos));
            iterationCount++;
            if (array[prevRootPos] > array[curRootPos]
                    && array[prevRootPos] > array[child1Pos] && array[prevRootPos] > array[child2Pos]) {
                swap(array, curRootPos, prevRootPos);
            } else break;

            curRootPos = prevRootPos;
            curDegreePos--;
        }
        return new int[]{curDegreePos, curRootPos};
    }


    private void sifting(int[] array, int curDegreePos, int curRootPos) {
        int deg = heapDegrees.elementAt(curDegreePos);
        while (true) {
            if (deg <= 1) break;

            int child1Pos = curRootPos - 1;
            int child2Pos = child1Pos - leonardoNumber(deg - 2);

            iterationCount++;
            if (array[curRootPos] < array[child1Pos] && array[child1Pos] >= array[child2Pos]) {
                swap(array, curRootPos, child1Pos);
                curRootPos = child1Pos;
                deg -= 2;
            } else if (array[curRootPos] < array[child2Pos] && array[child2Pos] > array[child1Pos]) {
                swap(array, curRootPos, child2Pos);
                curRootPos = child2Pos;
                deg -= 1;
            } else break;
        }
    }


    void leonardoHeapify(int[] array, int curDegreePos, int curRootPos) {
        int[] data = sortRoots(array, curDegreePos, curRootPos);

        curDegreePos = data[0];
        curRootPos = data[1];
        sifting(array, curDegreePos, curRootPos);
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private int leonardoNumber(int degree) {
        if (numbers.length == degree) {
            float INC_FACTOR = 1.5f;
            int suitableLen = Math.max((int) (numbers.length * INC_FACTOR), degree + 1);
            int[] newArray = new int[suitableLen];
            for (int i = 0; i < numbers.length; i++)
                newArray[i] = numbers[i];
            numbers = newArray;
        }

        for (int i = max; i < degree; i++)
            numbers[i + 1] = numbers[i] + numbers[i - 1] + 1;

        return numbers[degree];
    }


    public int getLastSortIterationCount() {
        return iterationCount;
    }
}