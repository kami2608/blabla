package org.example;

import java.util.List;

public interface TourManager {
    boolean addTour(Tour t);
    boolean editTour(Tour t);
    boolean delTour(Tour t);
    List<Tour> searchTour(String name);
    List<Tour> sortedTour(double price);
}

