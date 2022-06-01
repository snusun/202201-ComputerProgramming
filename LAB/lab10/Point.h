#include <iostream>

#ifndef POINT_H
#define POINT_H

class Point {
    int x, y;
public:
    //TODO Prob1.1 initialize Point and print x and y
    Point(int p, int q);

    int getX() const;

    int getY() const;
};

#endif
