package org.example.kdtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Задаем количество точек и размерность
        int numPoints = 1000000;
        int dimensions = 5;

        // Генерируем случайные точки
        List<Point> points = generateRandomPoints(numPoints, dimensions);

        // Строим K-d дерево и измеряем время построения
        long startTime = System.currentTimeMillis();
        KDTree kdTree = new KDTree(points);
        long buildTime = System.currentTimeMillis() - startTime;
        System.out.println("Время построения K-d дерева: " + buildTime + " ms");

        // Генерируем случайную целевую точку
        Point target = generateRandomPoints(1, dimensions).get(0);

        // Ищем ближайшего соседа и измеряем время поиска
        startTime = System.nanoTime();
        Point nearest = kdTree.findNearest(target);
        long searchTime = System.nanoTime() - startTime;
        System.out.println("Целевая точка: " + target);
        System.out.println("Ближайший сосед: " + nearest);
        System.out.println("Время поиска: " + searchTime / 1e6 + " ms");
    }

    /**
     * Метод для генерации списка случайных точек.
     *
     * @param numPoints  Количество точек
     * @param dimensions Размерность пространства
     * @return Список точек
     */
    private static List<Point> generateRandomPoints(int numPoints, int dimensions) {
        List<Point> points = new ArrayList<>(numPoints);
        Random rand = new Random();

        for (int i = 0; i < numPoints; i++) {
            double[] coords = new double[dimensions];
            for (int d = 0; d < dimensions; d++) {
                coords[d] = rand.nextDouble() * 1000;
            }
            points.add(new Point(coords));
        }

        return points;
    }
}