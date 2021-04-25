package chapter5.geneticalgorithms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

public class ListCompression extends Chromosome<ListCompression> {
    private static final List<String> ORIGINAL_LIST = List.of("Michael", "Sarah", "Joshua", "Narine", "David", "Sajid", "Meleanie", "Danieal", "Wei", "Dean", "Brian", "Murat", "Lisa");

    private final List<String> myList;
    private final Random random;

    public ListCompression(List<String> list) {
        myList = new ArrayList<>(list);
        random = new Random();
    }

    public static ListCompression randomInstance() {
        ArrayList<String> tempList = new ArrayList<>(ORIGINAL_LIST);
        Collections.shuffle(tempList);
        return new ListCompression(tempList);
    }

    private int bytesCompressed() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
            objectOutputStream.writeObject(myList);
            objectOutputStream.close();
            return byteArrayOutputStream.size();
        } catch (IOException ioException) {
            System.out.println("Could not compress list!");
            ioException.printStackTrace();
            return 0;
        }
    }

    @Override
    public double fitness() {
        return 1.0 / bytesCompressed();
    }

    @Override
    public List<ListCompression> crossover(ListCompression other) {
        ListCompression child1 = new ListCompression(new ArrayList<>(myList));
        ListCompression child2 = new ListCompression(new ArrayList<>(myList));
        int index1 = random.nextInt(myList.size());
        int index2 = random.nextInt(other.myList.size());
        String s1 = myList.get(index1);
        String s2 = myList.get(index2);
        int index3 = myList.indexOf(s2);
        int index4 = other.myList.indexOf(s1);
        Collections.swap(child1.myList, index1, index3);
        Collections.swap(child1.myList, index2, index4);
        return List.of(child1, child2);
    }

    @Override
    public void mutate() {
        int index1 = random.nextInt(myList.size());
        int index2 = random.nextInt(myList.size());
        Collections.swap(myList, index1, index2);
    }

    @Override
    public ListCompression copy() {
        return new ListCompression(new ArrayList<>(myList));
    }

    @Override
    public String toString() {
        return "Order: " + myList + " Bytes: " + bytesCompressed();
    }

    public static void main(String[] args) {
        ListCompression originalOrder = new ListCompression(ORIGINAL_LIST);
        System.out.println(originalOrder);
        ArrayList<ListCompression> initialPopulation = new ArrayList<>();
        final int POPULATION_SIZE = 100;
        final int GENERATIONS = 100;
        final double THRESHOLD = 1.0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            initialPopulation.add(ListCompression.randomInstance());
        }
        GeneticAlgorithm<ListCompression> geneticAlgorithm = new GeneticAlgorithm<>(
                initialPopulation, 0.2, 0.7, GeneticAlgorithm.SelectionType.TOURNAMENT);
        ListCompression result = geneticAlgorithm.run(GENERATIONS, THRESHOLD);
        System.out.println(result);
    }
}
