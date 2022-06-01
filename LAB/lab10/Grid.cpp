#include "Grid.h"

//TODO Prob1.4

//TODO Prob1.1 initialize Grid with zeros

int Grid::count;

Grid::Grid(int r, int c) : row(r), column(c) {
    //setCount(0);
    //Grid::count = 0;
    //static int count = 0;
    count = 0;
    grid = new int *[row]; // assign memory
    for (int i = 0; i < row; i++) {
        grid[i] = new int[column];
    }
    initialize_with_zeros();
}

void Grid::initialize_with_zeros() {
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            grid[i][j] = 0;
        }
    }
}

int Grid::getRow() const { return row; }

int Grid::getColumn() const { return column; }

int Grid::getAt(int r, int c) const { return grid[r][c]; }

void Grid::setAt(int r, int c, int v) { grid[r][c] = v; }

void Grid::printGrid() {
    std::cout << "grid : " << std::endl;
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            std::cout << grid[i][j] << " ";
        }
        std::cout << std::endl;
    }
}

void Grid::mark_point(Point p) {
    if(p.getY()>=0 && p.getY()<row && p.getX()>=0 && p.getX()<column){
        //setCount(getCount()+1);
        grid[p.getY()][p.getX()] = ++Grid::count;//getCount();
    }
}

//TODO Prob1.2 create explicit copy constructor
Grid::Grid(const Grid &g) : Grid(g.getRow(), g.getColumn()) {
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            grid[i][j] = g.grid[i][j];
        }
    }
}


//TODO Prob1.3 Add proper clean-up code!
Grid::~Grid() {
    for (int i = 0; i < row; i++) {
        delete[] grid[i];
    }
    delete[] grid;
    std::cout << "Clean-up Grid" << std::endl;
}
