//
// Created by triom on 2022-05-16.
//
#define STB_IMAGE_RESIZE_IMPLEMENTATION

#include "stb_image_resize.h"
#include "Layer.h"

Layer::Layer(std::string layername, int Width, int Height, uint8_t *data) {
    this->layername = layername;
    this->Width = Width;
    this->Height = Height;
    this->RGBA = data;
    this->visable =true;
    this->x_bias = 0;
    this->y_bias = 0;
}

Layer::Layer(std::string layername, const Layer * layer) {
    //TODO: Problem 2.1
}

Layer::~Layer() {
    //TODO: Problem 2.1
}

int Layer::resizeLayer(int newWidth, int newHeight) {
    // TODO: Problem 2.1
}

uint8_t Layer::getdata(int x, int y, int channel, int* status) {
    // TODO: Problem 2.1
}

void Layer::setdata(int x, int y, int channel, uint8_t data, int* status) {
    // TODO: Problem 2.1
}

std::string Layer::getName() {
    return layername;
}
int Layer::getW() {
    return Width;
}
int Layer::getH() {
    return Height;
}
int Layer::getXbias() {
    return x_bias;
}
int Layer::getYbias() {
    return y_bias;
}
int Layer::setBias(int x_bias, int y_bias) {
    this->x_bias = x_bias;
    this->y_bias = y_bias;
    return 0;
}