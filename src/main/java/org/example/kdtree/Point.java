package org.example.kdtree;

import java.util.Arrays;

/**
 * Класс для представления точки в N-мерном пространстве.
 */
public class Point {
    private final double[] coords; // Массив координат точки

    /**
     * Конструктор, принимающий массив координат.
     *
     * @param coords Координаты точки
     */
    public Point(double... coords) {
        this.coords = coords;
    }

    /**
     * Вычисляет квадрат евклидова расстояния до другой точки.
     * Используем квадрат расстояния для избежания вычисления квадратного корня.
     *
     * @param other Другая точка
     * @return Квадрат расстояния между точками
     */
    public double distanceSquared(Point other) {
        double sum = 0;
        for (int i = 0; i < coords.length; i++) {
            double diff = this.coords[i] - other.coords[i];
            sum += diff * diff;
        }
        return sum;
    }

    /**
     * Получает координату по заданной оси.
     *
     * @param axis Индекс оси
     * @return Значение координаты по оси
     */
    public double getCoordinate(int axis) {
        return coords[axis];
    }

    /**
     * Возвращает количество измерений точки.
     *
     * @return Число измерений
     */
    public int getDimensions() {
        return coords.length;
    }

    @Override
    public String toString() {
        return "Point" + Arrays.toString(coords);
    }
}
