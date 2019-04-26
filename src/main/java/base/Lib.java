package base;

import java.util.ArrayList;

public class Lib {

    public static int max(ArrayList<Integer> ints){
        int temp=0;
        for (int i : ints){
            temp = i>temp ? i : temp;
        }

        return temp;
    }

    //FIXME this is a terrible horible no good very bad way to do this
    public static int mode(ArrayList<Integer> ints){
        int[] mags = new int[10];
        int tempIn = 0;
        int tempVal = 0;

        for (int i : ints){
            mags[i]++;
        }

        for(int i=0; i<mags.length; i++){
            tempIn = tempVal>mags[i] ? tempIn : i;
        }

        return tempIn;
    }

}