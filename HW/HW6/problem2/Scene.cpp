//
// Created by triom on 2022-05-16.
//
#define STB_IMAGE_IMPLEMENTATION
#define STB_IMAGE_WRITE_IMPLEMENTATION

#include <cmath>
#include "Scene.h"
#include "stb_image.h"
#include "stb_image_write.h"

Scene::Scene(int Width, int Height) {
    //Feel free to add more initailzition to this constructor
    this->Width = Width;
    this->Height = Height;
}

Scene::~Scene() {
    //Feel free to modifiy this destructor as you wish
}

int Scene::addLayerfromFile(std::string filename, std::string layername) {
    //TODO: Problem 2.1
}

int Scene::addLayerfromLayer(std::string layername, std::string baselayername) {
}

int Scene::removeLayer(std::string layername) {
    //TODO: Problem 2.1
}

Layer *Scene::selectLayer(std::string layername) {
    //TODO: Problem 2.1
}

int Scene::changeLayerOrder(std::string layername, int order) {
    //TODO: Problem 2.1
}

int Scene::changeCanvasSize(int Width, int Height) {
    //TODO: Problem 2.1
}

int Scene::saveScene(std::string filename) {
    //TODO: Problem 2.2
}