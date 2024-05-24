package org.example;

import java.util.List;

public interface XManager {
    boolean addX(Tour x);
    boolean editX(Tour x);
    boolean delX(Tour x);
    List<Tour> searchX(String name);
    List<Tour> sortedX(double price, boolean ascending);
}


