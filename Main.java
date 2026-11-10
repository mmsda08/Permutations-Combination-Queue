package p1;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    //Queue implementation
    static Queue<String> queue = new LinkedList<>();

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== PERMUTATION AND COMBINATION =====");
            System.out.println("[1] Add Elements to Queue");
            System.out.println("[2] Display Queue");
            System.out.println("[3] Generate Permutations");
            System.out.println("[4] Generate Combinations");
            System.out.println("[5] Exit");
            System.out.print("Enter choice: ");

            choice = input.nextInt();

            switch (choice) {
                case 1:
                    addElements();
                    break;
                case 2:
                    displayQueue();
                    break;
                case 3:
                    generatePermutations();
                    break;
                case 4:
                    generateCombinations();
                    break;
                case 5:
                    System.out.println("Program terminated.");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    // Accept elements and add them to the Queue
    static void addElements() {
        System.out.print("Enter number of elements to add: ");
        int n = input.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.print("Enter element " + i + ": ");
            queue.add(input.next());// enqueue
        }

        displayQueue();

        //Dequeue two elements
        if (queue.size() >= 2) {
            System.out.print("Dequeue two elements? (y/n): ");
            String answer = input.next();
            if (answer.equalsIgnoreCase("y")) {
                System.out.println("Dequeued: " + queue.poll());
                System.out.println("Dequeued: " + queue.poll());
                displayQueue();
            }
        }
    }

    //Display all elements in the Queue
    static void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        System.out.println("Queue: " + queue);
        System.out.println("Front element: " + queue.peek());
    }

    //Generate all possible permutations
    static void generatePermutations() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty. Add elements first.");
            return;
        }
        System.out.println("Queue: " + queue);

        System.out.print("\nEnter number of elements to arrange: ");
        int r = input.nextInt();

        List<String> items = new ArrayList<>(queue);
        if (r < 1 || r > items.size()) {
            System.out.println("r must be between 1 and " + items.size() + ".");
            return;
        }

        //Each queue entry is a partial arrangement stored as a list of indexes
        Queue<List<Integer>> work = new LinkedList<>();
        work.add(new ArrayList<>());
        int count = 0;
        System.out.println();

        while (!work.isEmpty()) {
            List<Integer> current = work.poll();

            if (current.size() == r) {              //complete arrangement
                System.out.println(toText(current, items));
                count++;
                continue;
            }

            for (int i = 0; i < items.size(); i++) {
                if (!current.contains(i)) {         //each element used once
                    List<Integer> next = new ArrayList<>(current);
                    next.add(i);
                    work.add(next);
                }
            }
        }

        System.out.println("\nTotal Permutations: " + count);
    }

    //Generate all possible combinations
    static void generateCombinations() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty. Add elements first.");
            return;
        }
        System.out.println("Queue: " + queue);

        System.out.print("\nEnter number of elements to select: ");
        int r = input.nextInt();

        List<String> items = new ArrayList<>(queue);
        if (r < 1 || r > items.size()) {
            System.out.println("r must be between 1 and " + items.size() + ".");
            return;
        }

        Queue<List<Integer>> work = new LinkedList<>();
        work.add(new ArrayList<>());
        int count = 0;
        System.out.println();

        while (!work.isEmpty()) {
            List<Integer> current = work.poll();

            if (current.size() == r) {              //complete selection
                System.out.println(toText(current, items));
                count++;
                continue;
            }

            //Only pick indexes after the last one chosen, so no duplicates (AB and BA)
            int start = current.isEmpty() ? 0 : current.get(current.size() - 1) + 1;
            for (int i = start; i < items.size(); i++) {
                List<Integer> next = new ArrayList<>(current);
                next.add(i);
                work.add(next);
            }
        }

        System.out.println("\nTotal Combinations: " + count);
    }

    //turn a list of indexes into text like "ABC"
    static String toText(List<Integer> indexes, List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (int i : indexes) {
            sb.append(items.get(i));
        }
        return sb.toString();
    }
}