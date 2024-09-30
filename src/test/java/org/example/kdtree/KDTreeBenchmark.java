package org.example.kdtree;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Класс для бенчмаркинга K-d дерева с использованием JMH.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class KDTreeBenchmark {

    // Параметры бенчмарка: количество точек и размерность
    @Param({"1000", "10000", "100000", "1000000"})
    private int numPoints;

    @Param({"2", "5", "10"})
    private int dimensions;

    private List<Point> points;
    private KDTree kdTree;
    private Point target;

    /**
     * Метод подготовки данных перед бенчмарком.
     */
    @Setup(Level.Trial)
    public void setUp() {
        // Генерируем случайные точки
        points = generateRandomPoints(numPoints, dimensions);
        // Строим K-d дерево
        kdTree = new KDTree(points);
        // Генерируем случайную целевую точку
        target = generateRandomPoints(1, dimensions).get(0);
    }

    /**
     * Метод бенчмарка для поиска ближайшего соседа.
     *
     * @return Ближайшая точка
     */
    @Benchmark
    public Point benchmarkFindNearest() {
        return kdTree.findNearest(target);
    }

    /**
     * Метод для генерации списка случайных точек.
     *
     * @param numPoints  Количество точек
     * @param dimensions Размерность пространства
     * @return Список точек
     */
    private List<Point> generateRandomPoints(int numPoints, int dimensions) {
        List<Point> points = new ArrayList<>(numPoints);
        Random rand = new Random(12345); // Фиксированный seed для воспроизводимости

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