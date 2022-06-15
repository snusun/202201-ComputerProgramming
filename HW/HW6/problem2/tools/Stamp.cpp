//
// Created by triom on 2022-05-17.
//
#include <cmath>
#include "Stamp.h"
Stamp::Stamp() {
    this->stamplayer = NULL;
}
Stamp::~Stamp() {
    if(stamplayer)
        delete stamplayer;
}

int Stamp::cropLayer(Layer *layer, int xmin, int ymin, int xmax, int ymax) {
    //TODO: Problem 2.4
    return 0;
}

int Stamp::StampOnLayer(Layer *layer, int xbias, int ybias) {
    //TODO: Problem 2.4
    return 0;
}
