#include <iostream>
//TODO Prob1.4 include header files
#include "Point.h"
#include "Grid.h"


//class Point {
//    int x, y;
//public:
//    //TODO Prob1.1 initialize Point and print x and y
//     Point(int p, int q){x=p; y=q;}
//
//     int getX() const {return x;}
//
//     int getY() const {return y;}
//
//};
//
//class Grid {
//    int **grid;
//    int row, column;
//public:
//    //TODO Prob1.1 initialize Grid with zeros
//    Grid(int r, int c) {
//        row = r;
//        column = c;
//        grid = new int*[row]; // assign memory
//        for(int i=0; i<row; i++){
//            grid[i] = new int[column];
//        }
//        initialize_with_zeros();
//    }
//
//    void initialize_with_zeros() {
//        for(int i=0; i<row; i++){
//            for(int j=0; j<column; j++){
//                grid[i][j] = 0;
//            }
//        }
//    }
//
//    int getRow() const { return row; }
//
//    int getColumn() const { return column; }
//
//    int getAt(int r, int c) const { return grid[r][c]; }
//
//    void setAt(int r, int c, int v) {
//        grid[r][c] = v;
//    }
//
//    void printGrid() {
//        std::cout << "grid : " << std::endl;
//        for(int i=0; i<row; i++){
//            for(int j=0; j<column; j++){
//                std::cout << grid[i][j] << " ";
//            }
//            std::cout << std:: endl;
//        }
//
//    }
//
//    //TODO Prob1.2 create explicit copy constructor
//    Grid(Grid const &g):Grid(g.getRow(), g.getColumn()){
//        for(int i=0; i<row; i++){
//            for(int j=0; j<column; j++){
//                grid[i][j] = g.grid[i][j];
//            }
//        }
//    }
//
//    //TODO Prob1.3 Add proper clean-up code!
//     ~Grid(){
//        for(int i=0; i<row; i++){
//            delete [] grid[i];
//        }
//        delete [] grid;
//        std::cout << "Clean-up Grid" << std::endl;
//    }
//};


//TODO Prob1.2 print a grid with numbers incremented by 1 in the shape of the given Grid g
void printNumberGrid(Grid &g) {
    Grid new_grid(g);
    for (int i = 0; i < new_grid.getRow(); i++) {
        for (int j = 0; j < new_grid.getColumn(); j++) {
            new_grid.setAt(i, j, new_grid.getAt(i, j) + 1);
        }
    }
    new_grid.printGrid();
}

void problem1(){
    Point p(1,3);
    Grid g(2,3);

    std::cout << "x : " << p.getX() << ", y : " << p.getY() << std::endl;

    g.printGrid();

}

void problem2(){
    Grid g(2,3);
    g.setAt(0,0,1);
    g.printGrid();

    printNumberGrid(g);

    g.printGrid();
}

void problem3(){
    Grid g(2,3);
    g.printGrid();
}

/*
void exercise(){
    Grid g(2,3);
    g.printGrid();

    Point p1(1,0);
    Point p2(0,1);
    Point p3(3,3); // Invalid point

    g.mark_point(p1);
    g.mark_point(p2);
    g.mark_point(p3);

    g.printGrid();
}
*/

int main() {
    //problem1();
    //problem2();
    problem3();
    //exercise();
    return 0;
}
