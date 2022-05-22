#include "Grid.h"

//TODO Prob1.4
class Grid {
    int **grid;
    int row, column;
public:
    //TODO Prob1.1 initialize Grid with zeros
    Grid(int r, int c) {
        row = r;
        column = c;
        grid = new int*[row]; // assign memory
        for(int i=0; i<row; i++){
            grid[i] = new int[column];
        }
        initialize_with_zeros();
    }

    void initialize_with_zeros() {
        for(int i=0; i<row; i++){
            for(int j=0; j<column; j++){
                grid[i][j] = 0;
            }
        }
    }

    int getRow() const { return row; }

    int getColumn() const { return column; }

    int getAt(int r, int c) const { return grid[r][c]; }

    void setAt(int r, int c, int v) {
        grid[r][c] = v;
    }

    void printGrid() {
        std::cout << "grid : " << std::endl;
        for(int i=0; i<row; i++){
            for(int j=0; j<column; j++){
                std::cout << grid[i][j] << " ";
            }
            std::cout << std:: endl;
        }

    }

    //TODO Prob1.2 create explicit copy constructor
    Grid(Grid const &g):Grid(g.getRow(), g.getColumn()){
        for(int i=0; i<row; i++){
            for(int j=0; j<column; j++){
                grid[i][j] = g.grid[i][j];
            }
        }
    }

    //TODO Prob1.3 Add proper clean-up code!
     ~Grid(){
        for(int i=0; i<row; i++){
            delete [] grid[i];
        }
        delete [] grid;
        std::cout << "Clean-up Grid" << std::endl;
    }
};