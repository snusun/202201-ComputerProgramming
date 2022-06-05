#include "RunLength.h"
#include "vector"
#include "cmath"

using namespace std;
RunLength::RunLength() : run_length_unit_(NULL), num_data_(0), data_bit_width_(0), length_bit_width_(0) {}
RunLength::~RunLength() {
    if(run_length_unit_){
        delete[] run_length_unit_;
    }
}
void RunLength::set_data_bit_width(int data_bit_width) {
    length_bit_width_ = data_bit_width;
}

void RunLength::Print(std::ostream& os) const{
    os << data_bit_width_ << " ";
    os << length_bit_width_ << " ";
    os << num_data_ << " ";
    for(int i=0; i<num_data_; i++){
        os<<run_length_unit_[i];
    }
    os<<endl;
}

std::ostream& operator<<(std::ostream& os, const RunLengthUnit& rlu){
    // TODO: problem 2.1
    return os << "(" << rlu.data << " " << rlu.length << ")";
}

void RunLength::Encode(const char* file_name){
    // TODO: problem 2.2
    vector<RunLengthUnit> *vectorPtr = new vector<RunLengthUnit>(0);
    int now_num = 0;
    int now_count = 0;

    string str;
    int i=0;
    ifstream file(file_name);
    while (getline(file, str)){
        // str
        if(i==0) {
            data_bit_width_ = stoi(str);
        } else if(i==1){

        } else {
            int num = stoi(str);
            if(now_num!=num || now_count + 1 > (pow(2, length_bit_width_))){
                RunLengthUnit new_unit;
                if(now_count!=0){
                    //printf("add\n");
                    new_unit.data = now_num;
                    new_unit.length = now_count;
                    vectorPtr->push_back(new_unit);
                }
                now_num = num;
                now_count = 1;
            } else {
                now_count++;
            }
        }
        i++;
    }

    RunLengthUnit new_unit;
    new_unit.data = now_num;
    new_unit.length = now_count;
    vectorPtr->push_back(new_unit);

    file.close();

    int size = vectorPtr->size();
    RunLengthUnit *ptr = vectorPtr->data();
    run_length_unit_ = ptr;
    num_data_ = size;
}

double RunLength::Evaluate(const char* file_name) {
    // TODO: problem 2.5
    string str;
    int i=0;
    ifstream file(file_name);
    while (getline(file, str)){
        i++;
    }

    int before_com = i * data_bit_width_;
    int after_com = num_data_ * (data_bit_width_ + length_bit_width_) + 3 * data_bit_width_;

    double ratio = (double) before_com / after_com;
    ratio = (int)(ratio*100 + .5);

    return (double)ratio/100;
}
