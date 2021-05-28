

public class Sorter {
    /* Note: Iterative merge sort would probably use a postorder tree traversal I think */

    /* Wrapper function to provide correct arguments and an Auxiliary array to recSort
    Provides great interface for user - just provide array to sort */
    public void recursiveSort(Comparable[] arr){
        recSort(arr,  new Comparable[arr.length], 0, arr.length - 1);//create one array that we use (by reference(Java)) the whole time. You can create it in the merge method like the on on geeks for geeks, but this is not ideal since you create array objects multiple times which deteriorates performance and uses memory. The method we use is the one used by Hackerrank and Sedgewick.
    }

    private void recSort(Comparable[] arr1,Comparable[] storage,  int lo, int hi){
        if(lo >= hi){//base case 1 - subArray is trivially sorted
            return;//You can insertion sort when hi - lo <= 15 and then return/or do nothing(same thing).
                   // This achieves a 10 - 15% performance improvement per Sedgewick
        }
        else{
            int mid = (lo + hi) / 2;
            recSort(arr1, storage,  lo, mid);
            recSort(arr1,storage, mid + 1, hi);
            merge(arr1, storage, lo, mid, hi);
        }
    }

    private void merge(Comparable[] arr, Comparable[] storage, int lo, int mid, int hi){
        int i = lo;//index that iterates from left end to mid(when merging) and hi(when copying into storage[])
        int j = mid + 1;//index for right subarray when merging
        int k = lo;//index to put values in actual arr

        /* For the indexes we are dealing with lo - mid and mid - hi for this particular merge.
           We need to copy the values down into this storage[] because while merging the array
           we can erase values when assigning a value to a position. Mergesort does not work like other sorts(insertion, selection, quick)
           where you can just switch positions and progressively get more sorted.
           Extra details/ attempts to understand - With Merging, once we add an index from a subarray to the actual array we move on(increment).
           If we were to switch positions the value that was moved to that position will not be evaluated and sorted now since we incremented the index.
           it might be possible to make an in place mergesort, but it is complicated
         */
        for(;i <= hi; i++)storage[i] = arr[i];//copy subArrays we are merging into storage[]

        i = lo;//reset i to use again

        /* Merge */

        while(i <= mid && j <= hi){//go through each subArray until you completely iterated over one of them
            if(storage[i].compareTo(storage[j]) < 0){// i < j then put i into the actual array as we go we put higher and higher values as we increment k
                arr[k++] = storage[i++];
            }
            else{
                arr[k++] = storage[j++];// j < i put j in arr[k]
            }
        }

        if(j > hi){
            // The right subarray was exhausted(all of its elements were placed into arr[]) put the rest of left subarray in
            for(;i <= mid;i++){
                arr[k++] = storage[i];
            }
        }
        else{
            //left exhausted, put rest of right subarray in
            for(;j <= hi;j++){
                arr[k++] = storage[j];
            }
        }
        //TODO RENT JAKE, date, nate, yu pay, hw DA, internships (rosman) apply, study cs + skills, gemara,
            //7 wake 1, 6, 7, 5, 2
    }

    public void insertionSort(Comparable[] arr){
        for(int i = 1; i < arr.length; i++){
            for(int j = i;j > 0 && arr[j].compareTo(arr[j - 1]) < 0; j--) {
                    Comparable temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
            }
        }
    }








    public void selectionSort(Comparable[] arr){//go by index instead of value
        for(int i = 0; i < arr.length; i++){
            int minIndex = i;//the index of the minimum value element in the rest of the array
            for(int j = i; j < arr.length;j++){//find the minIndex
                if(arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            //swap(arr, i, minIndex);
            Comparable temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    private void swap(Comparable[] arr, int index1, int index2){
        Comparable temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    //public void shellSort, iterative mergeSort, iterative qSort
    public void shellSort(Comparable[] arr){//Sedgewicks style but implemented myself after I readabout alg.
        int n = arr.length;
        int h = 1;
        while(h <(n/3)) h = 3 * h + 1;
        while(h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && arr[j].compareTo(arr[j - h]) < 0; j -= h) {
                    Comparable temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
            h = h/3;
        }
    }
    public void quickSort(Comparable[] arr){
        recQSort(arr, 0, arr.length - 1);
    }
    private void recQSort(Comparable[] arr, int lo, int hi){
        if(lo >= hi){
            return;
        }
        Comparable pivot = arr[lo];// lo is arbitrary
        int ind = partition(arr, lo, hi, pivot);
        recQSort(arr, lo, ind - 1);
        recQSort(arr, ind, hi);

    }
    private int partition(Comparable[] arr, int lo, int hi, Comparable pivot){
        int leftIndex = lo;
        int rightIndex = hi;

        while(leftIndex <= rightIndex){
            while(arr[leftIndex].compareTo(pivot) < 0)leftIndex++;
            while(arr[rightIndex].compareTo(pivot) > 0)rightIndex--;
            //swap(arr, leftIndex, rightIndex);

            if(leftIndex <= rightIndex){
                swap(arr, leftIndex++, rightIndex--);
            }

        }
        return leftIndex;//?
    }
    /* Test the Recursive MergeSort Correctness and Performance */
    /*
    public void heapSort(Comparable[] arr) {
        int n = arr.length;
        boolean secondChildGreater = false;
        //construct a maxPQ
        /*BUILD PQHEAP
        for (int i = n / 2; i > 0; i--) {
            System.out.println("start");
            int j = i;
            while(j < n && j*2 < n) {
                System.out.println("beg");
                secondChildGreater = greaterChild(arr, j * 2, (j * 2) + 1);
                if(secondChildGreater) {
                    if (arr[(j * 2) + 1].compareTo(arr[j]) > 0) {
                        Comparable temp = arr[j];
                        arr[j] = arr[(j * 2) + 1];
                        arr[(j * 2) + 1] = temp;
                        j = (j * 2) + 1;
                        System.out.println("Second");
                    }
                    else{break;}//b/c if this child which is greater is not greater than parent than parent is in right position
                }
                else if(arr[j * 2].compareTo(arr[j]) > 0){
                    Comparable temp = arr[j];
                    arr[j] = arr[(j * 2) + 1];
                    arr[(j * 2) + 1] = temp;
                    j = j * 2;
                    System.out.println("first");
                }
                else{
                    System.out.println("break");
                    break;
                }//both children are <= parent no need to sink
            }
        }

        /*BUILD PQHEAP*/

        /*while(n > 1){
            Comparable max = arr[0];
            arr[0] = arr[n - 1];
            arr[n - 1] = max;
            n--;

            for(int j = 1; j < n && j * 2  < n; j *= 2) {
                secondChildGreater = greaterChild(arr, j * 2, j * 2 + 1);
                if(secondChildGreater) {
                    if (arr[j].compareTo(arr[j * 2 + 1]) < 0) {
                        Comparable temp = arr[j];
                        arr[j] = arr[j * 2 + 1];
                        arr[j * 2 + 1] = temp;
                        j++;
                    }
                }
                else if(arr[j].compareTo(arr[j * 2]) < 0){
                    Comparable temp = arr[j];
                    arr[j] = arr[j * 2 + 1];
                    arr[j * 2 + 1] = temp;
                }
            }
        }


    }
    */
    private boolean greaterChild (Comparable[] arr, int i,int j){
        if (arr[j].compareTo(arr[i]) > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public static void main(String[] args){

        Integer[] testarr = new Integer[1000];
        for(int j = 0;j < 1000; j++ )testarr[j] = (int)(Math.random() * 1000);
        Integer[] testarr2 = new Integer[2000];
        for(int j = 0;j < 2000; j++ )testarr2[j] = (int)(Math.random() * 2000);
        Sorter s = new Sorter();
        long time1 = System.nanoTime();
        s.shellSort(testarr);
        long time2 = System.nanoTime();
        long firstTime = time2 - time1;
        for(int i = 0; i < testarr.length; i++){
            System.out.println(testarr[i] + ", ");
        }
        long time3 = System.nanoTime();
        s.shellSort(testarr2);
        long time4 = System.nanoTime();
        long secondTime = time4 - time3;
        for(int i = 0; i < testarr2.length; i++){
            System.out.println(testarr2[i] + ", ");
        }
        System.out.println("The performance ratio when input size is doubled is: " + (double)(secondTime)/(double)firstTime);
    }
}
