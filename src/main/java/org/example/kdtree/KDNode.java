package org.example.kdtree;

import java.util.Comparator;
import java.util.List;

/**
 * Класс для представления узла K-d дерева.
 */
public class KDNode {
    private final Point point;  // Точка, хранящаяся в узле
    private final KDNode left; // Левый и правый потомки
    private final KDNode right;
    private final int axis; // Ось разделения на текущем уровне

    /**
     * Конструктор узла K-d дерева.
     *
     * @param points Список точек для построения дерева
     * @param depth  Текущая глубина дерева
     */
    public KDNode(List<Point> points, int depth) {
        if (points == null || points.isEmpty()) {
            // Если список точек пуст, узел не содержит данных
            point = null;
            left = null;
            right = null;
            axis = 0;
            return;
        }

        // Определяем ось разделения в зависимости от глубины
        int k = points.get(0).getDimensions();
        this.axis = depth % k;

        // Сортируем точки по выбранной оси
        points.sort(Comparator.comparingDouble(p -> p.getCoordinate(axis)));

        // Находим индекс медианы
        int medianIndex = points.size() / 2;
        // Устанавливаем текущую точку как медиану
        this.point = points.get(medianIndex);

        // Разделяем точки на левые и правые поддеревья
        List<Point> leftPoints = points.subList(0, medianIndex);
        List<Point> rightPoints = points.subList(medianIndex + 1, points.size());

        // Рекурсивно строим левое и правое поддеревья
        this.left = leftPoints.isEmpty() ? null : new KDNode(leftPoints, depth + 1);
        this.right = rightPoints.isEmpty() ? null : new KDNode(rightPoints, depth + 1);
    }

    /**
     * Рекурсивный метод для поиска ближайшего соседа.
     *
     * @param target          Целевая точка
     * @param best            Текущая ближайшая точка
     * @param bestDistSquared Квадрат расстояния до текущей ближайшей точки
     * @return Обновленная ближайшая точка
     */
    public Point nearest(Point target, Point best, double bestDistSquared) {
        if (this.point == null) {
            // Если узел пуст, возвращаем текущего лучшего кандидата
            return best;
        }

        // Вычисляем квадрат расстояния до текущей точки
        double dSquared = target.distanceSquared(this.point);
        Point currentBest = best;
        double currentBestDistSquared = bestDistSquared;

        // Если текущая точка ближе, обновляем лучшего кандидата
        if (dSquared < bestDistSquared) {
            currentBestDistSquared = dSquared;
            currentBest = this.point;
        }

        // Определяем, какое поддерево исследовать сначала
        KDNode nearBranch = null; // Ближайшее поддерево
        KDNode farBranch = null;  // Дальнее поддерево

        if (target.getCoordinate(axis) < this.point.getCoordinate(axis)) {
            nearBranch = this.left;
            farBranch = this.right;
        } else {
            nearBranch = this.right;
            farBranch = this.left;
        }

        // Рекурсивно ищем в ближайшем поддереве
        if (nearBranch != null) {
            currentBest = nearBranch.nearest(target, currentBest, currentBestDistSquared);
            currentBestDistSquared = target.distanceSquared(currentBest);
        }

        // Проверяем, нужно ли исследовать дальнее поддерево
        if (farBranch != null) {
            double axisDist = target.getCoordinate(axis) - this.point.getCoordinate(axis);
            if (axisDist * axisDist < currentBestDistSquared) {
                currentBest = farBranch.nearest(target, currentBest, currentBestDistSquared);
            }
        }

        return currentBest;
    }
}