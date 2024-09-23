package com.mstr.util;

import java.util.Comparator;
import java.util.TreeMap;

public class DisplayNameComparator implements Comparator<Object>
{
    public int compare(Object o1, Object o2) {
        String s1 = "";
        String s2 = "";
        if((o1 instanceof TreeMap) && (o2 instanceof TreeMap)){
            s1 = (String) ((TreeMap<?, ?>) o1).get("title");
            s2 = (String)  ((TreeMap<?, ?>) o2).get("title");
        }
        return s1.compareTo(s2);
    }
}
