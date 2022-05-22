#include "Point.h"

//TODO Prob1.4

class Point {
    int x, y;
public:
    //TODO Prob1.1 initialize Point and print x and y
     Point(int p, int q){x=p; y=q;}

     int getX() const {return x;}

     int getY() const {return y;}

};