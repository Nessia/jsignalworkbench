/*
 * MarkPaintInfo.java
 *
 * Created on 3 de agosto de 2007, 10:40
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import java.awt.Point;

/**
 *
 * @author Roman Segador
 */
public class MarkPaintInfo {
    private Point point;
    private Point startValue;
    private Point endValue;
    private int maxValueY;
    private int minValueY;
    private int width;
    private int height;

    public MarkPaintInfo(Point point, Point startValue, Point endValue,
                         int maxValueY, int minValueY, int width, int height) {
        this.point = point;
        this.startValue = startValue;
        this.endValue = endValue;
        this.maxValueY = maxValueY;
        this.minValueY = minValueY;
        this.width = width;
        this.height = height;
    }

    public Point getPoint() {
        return point;
    }

    public Point getStartValue() {
        return startValue;
    }

    public Point getEndValue() {
        return endValue;
    }

    public int getMaxValueY() {
        return maxValueY;
    }

    public int getMinValueY() {
        return minValueY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(!(obj instanceof MarkPaintInfo)){
            return false;
        }
        MarkPaintInfo mpi = (MarkPaintInfo)obj;
        if(mpi.getPoint().x != point.x || mpi.getPoint().y != point.y){
            return false;
        }
        if(mpi.getStartValue().x != startValue.x ||
             mpi.getStartValue().y != startValue.y){
            return false;
        }
        if(mpi.getEndValue().x != endValue.x ||
              mpi.getEndValue().y != endValue.y){
            return false;
        }
        return (
            mpi.getMaxValueY() == maxValueY &&
            mpi.getMinValueY() == minValueY &&
            mpi.getHeight() == height &&
            mpi.getWidth() == width);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

}
