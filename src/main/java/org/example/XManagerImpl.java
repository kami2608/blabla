package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class XManagerImpl implements XManager {
    private List<Tour> tourList;
    private static final String FILE_NAME = "X.bin";

    public XManagerImpl() {
        this.tourList = readFromFile();
    }

    @Override
    public boolean addX(Tour x) {
        boolean added = tourList.add(x);
        writeToFile();
        return added;
    }

    @Override
    public boolean editX(Tour x) {
        for (int i = 0; i < tourList.size(); i++) {
            if (tourList.get(i).getProductId() == x.getProductId()) {
                tourList.set(i, x);
                writeToFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delX(Tour x) {
        return false;
    }


    public boolean delX(int x) {
        Tour X = searchXById(x);
        if (X != null) {
            boolean removed = tourList.remove(X);
            writeToFile();
        }
        return true;

    }

    @Override
    public List<Tour> searchX(String name) {
        List<Tour> result = new ArrayList<>();
        for (Tour tour : tourList) {
            if (tour.getProductName().toLowerCase().contains(name.toLowerCase())) {
                result.add(tour);
            }
        }
        return result;
    }

    public Tour searchXById(int id) {
        for (Tour tour : tourList) {
            if (tour.getProductId() == id) {
                return tour;
            }
        }
        return null;
    }

    @Override
    public List<Tour> sortedX(double price, boolean ascending) {
        List<Tour> sortedList = new ArrayList<>(tourList);
        sortedList.sort(new Comparator<Tour>() {
            @Override
            public int compare(Tour t1, Tour t2) {
                int priceComparison = Double.compare(t1.getProductPrice(), t2.getProductPrice());
                return ascending ? priceComparison : -priceComparison;
            }
        });
        return sortedList;
    }

    private void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tourList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Tour> readFromFile() {
        List<Tour> tours = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tours = (List<Tour>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, return an empty list
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tours;
    }

    public static void main(String[] args) {
        XManagerImpl manager = new XManagerImpl();

        // Thêm các tour vào kho
        Tour tour1 = new Tour(1, "Tour A", 1000.0, 20, "Paris", 5, "John Doe");
        Tour tour2 = new Tour(2, "Tour B", 2000.0, 15, "Tokyo", 7, "Jane Smith");
        Tour tour3 = new Tour(3, "Tour C", 1500.0, 10, "London", 6, "Mike Johnson");

        manager.addX(tour1);
        manager.addX(tour2);
        manager.addX(tour3);

        // Tìm kiếm tour theo tên
        List<Tour> searchResults = manager.searchX("Tour A");
        System.out.println("Search Results:");
        for (Tour tour : searchResults) {
            System.out.println(tour);
        }

        // Sắp xếp tour theo giá tăng dần
        List<Tour> sortedTours = manager.sortedX(0, true);
        System.out.println("Sorted Tours (Ascending):");
        for (Tour tour : sortedTours) {
            System.out.println(tour);
        }

        // Sửa thông tin tour
        Tour tour1Updated = new Tour(1, "Tour A Updated", 1100.0, 18, "Paris", 5, "John Doe");
        manager.editX(tour1Updated);
        System.out.println("After Edit:");
        for (Tour tour : manager.searchX("Tour A Updated")) {
            System.out.println(tour);
        }

        // Xóa tour
        manager.delX(tour1Updated);
        System.out.println("After Deletion:");
        for (Tour tour : manager.sortedX(0, true)) {
            System.out.println(tour);
        }
    }
}
