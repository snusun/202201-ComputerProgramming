#include <algorithm>
#include "restaurant_app.h"

#include <iostream>
#include <iomanip>


void RestaurantApp::rate(string target, int rate) {
    shared_ptr<vector<int>> restaurant = find_restaurant(target);
    if (restaurant == nullptr) {
        vector<int> rateVec;
        rateVec.push_back(rate);
        shared_ptr<vector<int>> vecPtr = std::make_shared<vector<int>>(rateVec);
        restaurants.insert({target, vecPtr});
    } else {
        restaurant->push_back(rate);
        std::sort(restaurant->begin(), restaurant->end());
    }
}


void RestaurantApp::list() {
    auto iter = restaurants.begin();
    while (iter != restaurants.end()) {
        std::cout << iter->first << " ";
        ++iter;
    }
    std::cout << std::endl;
}


void RestaurantApp::show(string target) {
    shared_ptr<vector<int>> restaurant = find_restaurant(target);
    if (restaurant == nullptr) {
        std::cout << target << " does not exist." << std::endl;
    } else {
        vector<int> vec = *restaurant;
        auto iter = vec.begin();
        while (iter != vec.end()) {
            std::cout << *iter.base() << " ";
            ++iter;
        }
        std::cout << std::endl;
    }
}


void RestaurantApp::ave(string target) {
    shared_ptr<vector<int>> restaurant = find_restaurant(target);
    if (restaurant == nullptr) {
        std::cout << target << " does not exist." << std::endl;
    } else {
        double sum = 0;
        int cnt = 0;
        vector<int> vec = *restaurant;
        auto iter = vec.begin();
        while (iter != vec.end()) {
            sum += *iter.base();
            ++iter;
            cnt++;
        }
        sum /= cnt;
        float value = (int) (sum * 100 + .5);
        std::cout << (float) value / 100 << std::endl;
    }
}


void RestaurantApp::del(string target, int rate) {
    shared_ptr<vector<int>> restaurant = find_restaurant(target);
    if (restaurant == nullptr) {
        std::cout << target << " does not exist." << std::endl;
    } else {
        restaurant->erase(remove(restaurant->begin(), restaurant->end(), rate), restaurant->end());
    }
}


void RestaurantApp::cheat(string target, int rate) {
    shared_ptr<vector<int>> restaurant = find_restaurant(target);
    if (restaurant == nullptr) {
        std::cout << target << " does not exist." << std::endl;
    } else {
        restaurant->erase(std::remove_if(restaurant->begin(), restaurant->end(), [&rate](const int & i) { return i < rate; }), restaurant->end());
    }
}


shared_ptr<vector<int>> RestaurantApp::find_restaurant(string target) {
    auto it = restaurants.find(target);
    if (it == restaurants.end()) {
        return nullptr;
    }
    return restaurants[target];
}
