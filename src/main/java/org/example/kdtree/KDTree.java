package org.example.kdtree;

import java.util.List;

/**
 * Класс для представления K-d дерева.
 */
public class KDTree {
    // Корневой узел дерева
    private final KDNode root;

    /**
     * Конструктор K-d дерева.
     *
     * @param points Список точек для построения дерева
     */
    public KDTree(List<Point> points) {
        this.root = new KDNode(points, 0);
    }

    /**
     * Метод для поиска ближайшего соседа к заданной точке.
     *
     * @param target Целевая точк
     * @return Ближайшая точка в дереве
     */
    public Point findNearest(Point target) {
        return root.nearest(target, null, Double.MAX_VALUE);
    }
}
