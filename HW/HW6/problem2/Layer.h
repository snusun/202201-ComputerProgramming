//
// Created by triom on 2022-05-16.
//

#ifndef PROBLEM2_LAYER_H
#define PROBLEM2_LAYER_H

#include <cstdint>
#include <string>

class Scene;
class Layer {
public:

    Layer(std::string layername, int Width, int Height, uint8_t* data);
    Layer(std::string layername, const Layer* layer);
    ~Layer();
    std::string getName();

    int getW();
    int getH();
    int resizeLayer( int newWidth,  int newHeight);

    int getXbias();
    int getYbias();
    int setBias(int x_bias, int y_bias);

    uint8_t getdata(int x, int y, int channel, int* status);
    void setdata(int x, int y, int channel, uint8_t data, int* status);

    bool visable;

private:
    int Width, Height, x_bias, y_bias;
    std::string layername;
    uint8_t* RGBA; // This is data array of layer image
};


#endif //PROBLEM2_LAYER_H
