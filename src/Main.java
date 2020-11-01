import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;


public class Main {

    public static void main(String[] args) throws IOException {
        zadanie1();
        zadanie2();
        zadanie3();
        zadanie4();
        zadanie5();
    }

    static void zadanie1(){
        //Zadanie 1 Napisz program mierzący czas tworzenia na wirtualnej maszynie Javy (JVM) listy 𝑛-
        //elementowej zawierającej liczby całkowite (od 1 do 𝑛) z wykorzystaniem:
        System.out.println("Zadanie 1:");
        int n = 50000000;
        //a) tablic
        long[] tab = new long[n];

        long start1 = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            tab[i - 1] = i;
        }
        double finish1 = (System.nanoTime() - start1) / 1000000000.0;
        System.out.printf("Tworzenie listy %d elementowej wyniosło: %f sekundy%n\n", n, finish1);

        //b) kolekcji ArrayList
        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        long start2 = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            arrayList.add(i);
        }
        double finish2 = (System.nanoTime() - start2) / 1000000000.0;
        System.out.printf("Tworzenie ArrayListy %d elementowej wyniosło: %f sekundy\n", n, finish2);

        //c) kolekcji ArrayList, podając jej maksymalny rozmiar podczas tworzenia
        ArrayList<Integer> arrayList1 = new ArrayList<>(n);

        long start3 = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            arrayList1.add(i);
        }
        double finish3 = (System.nanoTime() - start3) / 1000000000.0;
        System.out.printf("Tworzenie ArrayListy %d elementowej z podanym rozmiarem wyniosło: %f sekundy\n", n, finish3);

        //d) kolekcji LinkedList
        LinkedList<Integer> list = new LinkedList<>();

        long start4 = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }
        double finish4 = (System.nanoTime() - start4) / 1000000000.0;
        System.out.printf("Tworzenie Linkedlisty %d elementowej wyniosło: %f sekund\n", n, finish4);
    }
    static void zadanie2(){
        //Zadanie 2 Napisz program w Javie mierzący czas wyznaczania silni liczby naturalnej n.
        //Wynik wyświetl w notacji naukowej, a wartość 𝑛 dostosuj do możliwości swojego komputera.
        //Jaką największą wartość silni udało Ci się wyznaczyć? Dla jakiego 𝑛 i w jakim czasie?
        //Dlaczego zastosowanie jednego z typów prostych (np. long) mogłoby być nieodpowiednie?
        System.out.println("Zadanie 2:");

        int nSilnia = 250000;
        BigInteger temp = new BigInteger("1");
        BigInteger result = new BigInteger("1");

        long start5 = System.nanoTime();
        while(temp.intValue() <= nSilnia){
            result = result.multiply(temp);
            temp = temp.add(BigInteger.ONE);
        }
        double finish5 = (System.nanoTime() - start5) / 1000000000.0;

        NumberFormat numFormat = new DecimalFormat("0.####E0");

        System.out.printf("Czas obliczenia silni z %d wynosi %f sekund a wynik to:\n%s ",nSilnia,finish5,numFormat.format(result));

    }
    static void zadanie3(){
        //Zadanie 3 Wykorzystując Java Streams API, napisz program zliczający liczbę unikalnych słów w
        //podanym pliku tekstowym (ignorując znaki interpunkcyjne, wielkość liter, oraz słowa krótsze
        //niż 3 znaki). Następnie, uruchom swój program i wczytaj plik tekstowy macbeth.txt.
        System.out.println("\nZadanie 3:");
        String fileName = "C:\\Users\\kamil\\IdeaProjects\\PP_Lab1\\src\\macbeth.txt";
        long result = 0;

        try (Stream<String> stream = Files.lines(Paths.get(fileName))){

            result += stream.flatMap(e -> Arrays.stream(e.split("\\W+")))
                    .map(String::toLowerCase)
                    .filter(e -> e.length()>=3)
                    .distinct()
                    .count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);


    }
    static void zadanie4(){
        //Zadanie 4 Wykorzystując Java Streams API, napisz program wyświetlający 8 najczęściej występujących słów w podanym pliku tekstowym (ignorując znaki interpunkcyjne, wielkość liter,
        //oraz słowa krótsze niż 3 znaki).
        System.out.println("Zadanie 4:");
        String fileName = "C:\\Users\\kamil\\IdeaProjects\\PP_Lab1\\src\\macbeth.txt";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))){


            List<String> list = stream.flatMap(word -> Arrays.stream(word.split("\\W+")))
                    .map(String::toLowerCase)
                    .filter(word -> word.length()>=3)
                    .collect(Collectors.toList());

            Map<String, Integer> wordCounterUnsorted = list.stream()
                    .collect(toMap(w -> w, w -> 1, Integer::sum));

            Map<String, Integer> wordCounterSorted = wordCounterUnsorted.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .limit(8)
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

            System.out.println(wordCounterSorted);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    static void zadanie5(){
        //Zadanie 5 Napisz program w Javie mierzący czas sortowania tablic liczb całkowitych. W tym celu
        //zaimplementuj dwa algorytmy sortowania:
        System.out.println("Zadanie 5:");
        int n = 100000;
        Integer[] descendingTab = new Integer[n];
        Integer[] descendingTab1 = new Integer[n];
        Integer[] ascendingTab = new Integer[n];
        Integer[] ascendingTab1 = new Integer[n];
        //c) tablica wypełniona losowymi danymi
        Integer[] randomTab = new Integer[n];
        Integer[] randomTab1 = new Integer[n];
        for (int i = 0; i < n; i++) {
            randomTab[i] = randomTab1[i] = ascendingTab[i] =
                    ascendingTab1[i] = descendingTab[i] =
                            descendingTab1[i] = (int)(Math.random() * (n - 0 + 1) + 0);
        }
        //a) tablica posortowana malejąco
        Arrays.sort(descendingTab, Collections.reverseOrder());
        Arrays.sort(descendingTab1, Collections.reverseOrder());
        //b) tablica posortowana rosnąco
        Arrays.sort(ascendingTab);
        Arrays.sort(ascendingTab1);
        for(int x : descendingTab){
           System.out.print(x + " ");
        }
        System.out.println("\n");
        //(sortujemy rosnąco)
        long start = System.nanoTime();
        insertionSortAscending(descendingTab);
        double finish = (System.nanoTime() - start) / 1000000000.0;
        System.out.printf("\nSortowanie %d elementowej malejacej tablicy przez wstawianie rosnąco trwało %f sekund", n, finish);
        long start1 = System.nanoTime();
        mergeSortAscending(descendingTab1, 0, descendingTab.length - 1);
        double finish1 = (System.nanoTime() - start1) / 1000000000.0;
        System.out.printf("\nSortowanie %d elementowej malejacej tablicy przez scalanie rosnąco trwało %f sekund", n, finish1);
        for(int x : descendingTab){
            System.out.print(x + " ");
        }
        //(sortujemy malejąco)
        long start2 = System.nanoTime();
        insertionSortDescending(ascendingTab);
        double finish2 = (System.nanoTime() - start2) / 1000000000.0;
        System.out.printf("\nSortowanie %d elementowej rosnacej tablicy przez wstawianie malejąco trwało %f sekund", n, finish2);
        long start3 = System.nanoTime();
        mergeSortDescending(ascendingTab1, 0, ascendingTab1.length - 1);
        double finish3 = (System.nanoTime() - start3) / 1000000000.0;
        System.out.printf("\nSortowanie %d elementowej rosnacej tablicy przez scalanie malejąco trwało %f sekund", n, finish3);
        for(int x : ascendingTab1){
          System.out.print(x + " ");
        }
        // c) sortujemy rosnąco
        long start4 = System.nanoTime();
        insertionSortAscending(randomTab);
        double finish4 = (System.nanoTime() - start4) / 1000000000.0;
        System.out.printf("\nSortowanie %d elementowej randomowej tablicy przez wstawianie rosnąco trwało %f sekund", n, finish4);
        long start5 = System.nanoTime();
        mergeSortAscending(randomTab1, 0, randomTab1.length - 1);
        double finish5 = (System.nanoTime() - start5) / 1000000000.0;
        System.out.printf("\nSortowanie %d elementowej randomowej tablicy przez scalanie rosnąco trwało %f sekund", n, finish5);
    }

    //wstawianie
    static void insertionSortAscending(Integer[] arr){
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while(j >= 0 && arr[j] > key) {
                arr[j+1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    static void insertionSortDescending(Integer[] arr){
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while(j >= 0 && arr[j] < key) {
                arr[j+1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
    //scalanie
    static void mergeAscending(Integer[] arr, int l, int m, int r){
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int[] L = new int[n1];
        int[] R = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    static void mergeSortAscending(Integer[] arr, int l, int r){
        if (l < r) {
            // Find the middle point
            int m = (l + r) / 2;

            // Sort first and second halves
            mergeSortAscending(arr, l, m);
            mergeSortAscending(arr, m + 1, r);

            // Merge the sorted halves
            mergeAscending(arr, l, m, r);
        }
    }
    static void mergeDescending(Integer[] arr, int l, int m, int r){
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int[] L = new int[n1];
        int[] R = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] >= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    static void mergeSortDescending(Integer[] arr, int l, int r){
        if (l < r) {
            // Find the middle point
            int m = (l + r) / 2;

            // Sort first and second halves
            mergeSortDescending(arr, l, m);
            mergeSortDescending(arr, m + 1, r);

            // Merge the sorted halves
            mergeDescending(arr, l, m, r);
        }
    }
}
