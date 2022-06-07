#include <iostream>
#include <vector>
#include <algorithm>
#include <map>

using namespace std;

void vector_example(){
    vector<int> vec;
    vec.push_back(10);
    vec.push_back(20);
    vec.push_back(30);
    vec.push_back(40);

    cout << "vector size = " << vec.size() << endl;
    vec.erase(vec.begin());
    cout << "vector size = " << vec.size() << endl;
    vec.erase(remove(vec.begin(), vec.end(), 20), vec.end());
    cout << "vector size = " << vec.size() << endl;
    for (int i: vec)
        cout << i << " " ;
}

void printmap(map <int, int> g){
    cout << "\tKEY\tELEM\n";
    for(auto itr=g.begin(); itr!=g.end(); itr++){
        cout << "\t" << itr->first << "\t" << itr->second << endl;
    }
    cout << endl;
}

void map_example(){
    map<int, int> dict1;

    dict1.insert(pair<int, int>(1, 40));
    dict1.insert(pair<int, int>(2, 30));
    dict1.insert(pair<int, int>(3, 60));
    dict1.insert(pair<int, int>(4, 20));
    dict1.insert(pair<int, int>(5, 50));

    printmap(dict1);

    map<int, int> dict2(dict1.begin(), dict1.end());

    dict2.erase(dict2.begin(), dict2.find(3));
    cout << "\ndict2 after removing until finding key = 3" << endl;
    printmap(dict2);

    dict2.erase(4);
    cout << "\ndict2.erase(4) : " << endl;
    printmap(dict2);
}

void algorithm_example(){
    vector<int> vec;
    vec.push_back(10);
    vec.push_back(30);
    vec.push_back(40);
    vec.push_back(5);

    for (int i: vec)
        cout << i << " ";
    cout << endl;

    sort(vec.begin(), vec.end());
    for (int i: vec)
        cout << i << " ";
}

int main(){
    vector_example();
    map_example();
    algorithm_example();

    return 0;
}