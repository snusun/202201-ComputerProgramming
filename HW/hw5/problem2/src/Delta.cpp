#include "Delta.h"
#include "vector"
#include "cmath"

using namespace std;

Delta::Delta() : delta_unit_(NULL), num_data_(0), data_bit_width_(0), delta_bit_width_(0) {}

Delta::~Delta() {
    if(delta_unit_){
        delete[] delta_unit_;
    }
}

void Delta::Print(std::ostream& os) const{
    os << data_bit_width_ << " ";
    os << delta_bit_width_ << " ";
    os << num_data_ << " ";
    os << base_data_ << " ";
    for(int i=0; i<num_data_-1; i++){
        os << delta_unit_[i];
    }
    os<<endl;
}

std::ostream& operator<<(std::ostream& os, const DeltaUnit& du){
    // TODO: problem 2.3
    return os << "(dt " << du.delta << ")";
}

void Delta::Encode(const char* file_name){
    // TODO: problem 2.4
    vector<DeltaUnit> *vectorPtr = new vector<DeltaUnit>(0);
    int max_bit = 0;
    int compare_num=0;

    string str;
    int i=0;
    ifstream file(file_name);
    while (getline(file, str)){
        // str
        if(i==0) {
            data_bit_width_ = stoi(str);
        } else if(i==1){
            num_data_ = stoi(str);
        } else if(i==2){
            base_data_ = stoi(str);
            compare_num = base_data_;
        } else {
            int num = stoi(str);
            int delta = num - compare_num;
            int delta_bit = ceil( log2(  abs( delta ) +1 ) ) +1;
            if(max_bit < delta_bit) max_bit = delta_bit;
            DeltaUnit new_delta;
            new_delta.delta = delta;
            vectorPtr->push_back(new_delta);
            compare_num = num;
        }
        i++;
    }

    file.close();

    delta_bit_width_ = max_bit;
    DeltaUnit *ptr = vectorPtr->data();
    delta_unit_ = ptr;
}

double Delta::Evaluate(const char* file_name) {
    // TODO: problem 2.5
    string str;
    int i=0;
    ifstream file(file_name);
    while (getline(file, str)){
        i++;
    }

    int before_com = i * data_bit_width_;
    //data bit-width + (# of data -1) * delta bit-width + # of header element * data bit-width
    int after_com = data_bit_width_ + (num_data_ - 1) * delta_bit_width_ + 3 * data_bit_width_;

    double ratio = (double) before_com / after_com;
    ratio = (int)(ratio*100 + .5);

    return (double)ratio/100;

    return 0;
}
