#include <iostream>
#include "Point.h"

#ifndef GRID_H
#define GRID_H

class Grid {
    int **grid;
    int row, column;
public:
    //TODO Prob1.1 initialize Grid with zeros
    Grid(int r, int c);

    void initialize_with_zeros();

    int getRow() const;

    int getColumn() const;

    int getAt(int r, int c) const;

    void setAt(int r, int c, int v);

    void printGrid();

    }

    //TODO Prob1.2 create explicit copy constructor
    Grid(Grid const &g);

    //TODO Prob1.3 Add proper clean-up code!
    //~Grid();
};

#endif
