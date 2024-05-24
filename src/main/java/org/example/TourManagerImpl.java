package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TourManagerImpl implements TourManager {
    private List<Tour> tourList;

    public TourManagerImpl() {
        tourList = new ArrayList<>();
    }

    @Override
    public boolean addTour(Tour t) {
        return tourList.add(t);
    }

    @Override
    public boolean editTour(Tour t) {
        for (int i = 0; i < tourList.size(); i++) {
            if (tourList.get(i).getProductId() == t.getProductId()) {
                tourList.set(i, t);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delTour(Tour t) {
        return tourList.remove(t);
    }

    @Override
    public List<Tour> searchTour(String name) {
        List<Tour> result = new ArrayList<>();
        for (Tour tour : tourList) {
            if (tour.getProductName().toLowerCase().contains(name.toLowerCase())) {
                result.add(tour);
            }
        }
        return result;
    }

    @Override
    public List<Tour> sortedTour(double price) {
        List<Tour> sortedList = new ArrayList<>(tourList);
        Collections.sort(sortedList, new Comparator<Tour>() {
            @Override
            public int compare(Tour t1, Tour t2) {
                int priceComparison = Double.compare(t1.getProductPrice(), t2.getProductPrice());
                if (priceComparison != 0) {
                    return priceComparison;
                }
                return Integer.compare(t1.getDuration(), t2.getDuration());
            }
        });
        return sortedList;
    }

    public static void main(String[] args) {
        TourManagerImpl manager = new TourManagerImpl();

        // Thêm các tour vào danh sách
        Tour tour1 = new Tour(1, "Tour A", 1000.0, 20, "Paris", 5, "John Doe");
        Tour tour2 = new Tour(2, "Tour B", 2000.0, 15, "Tokyo", 7, "Jane Smith");
        Tour tour3 = new Tour(3, "Tour C", 1500.0, 10, "London", 6, "Mike Johnson");

        manager.addTour(tour1);
        manager.addTour(tour2);
        manager.addTour(tour3);

        // Tìm kiếm tour theo tên
        List<Tour> searchResults = manager.searchTour("Tour A");
        System.out.println("Search Results:");
        for (Tour tour : searchResults) {
            System.out.println(tour);
        }

        // Sắp xếp tour theo giá và thời gian
        List<Tour> sortedTours = manager.sortedTour(0);
        System.out.println("Sorted Tours:");
        for (Tour tour : sortedTours) {
            System.out.println(tour);
        }

        // Sửa thông tin tour
        Tour tour1Updated = new Tour(1, "Tour A Updated", 1100.0, 18, "Paris", 5, "John Doe");
        manager.editTour(tour1Updated);
        System.out.println("After Edit:");
        for (Tour tour : manager.searchTour("Tour A Updated")) {
            System.out.println(tour);
        }

        // Xóa tour
        manager.delTour(tour1Updated);
        System.out.println("After Deletion:");
        for (Tour tour : manager.sortedTour(0)) {
            System.out.println(tour);
        }
    }
}

