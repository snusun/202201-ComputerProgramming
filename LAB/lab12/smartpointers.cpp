#include <iostream>
#include <memory>

using namespace std;

class Test{
public:
    int test_id;
    shared_ptr<Test> cyclic_ref;
    weak_ptr<Test> weak;
    Test(int id) : test_id(id){
        cout << "constructed: " << test_id << endl;
    }
    ~Test() { cout << "destructed: " << test_id << endl; }

    shared_ptr<Test> get_shared_ptr() {shared_ptr<Test>(this);}

    void set_weak(weak_ptr<Test> weak_pointer){
        weak = weak_pointer;
    }
};

class Test_revised: public Test, public enable_shared_from_this<Test_revised> {
public:
    Test_revised(int id) : Test(id){}
    ~Test_revised() {}

    shared_ptr<Test_revised> get_shared_ptr() {return shared_from_this();}
};

void print_unique(){
    unique_ptr<Test> test_unique1(new Test(1));
    unique_ptr<Test> test_unique2 = make_unique<Test>(2);
    //unique_ptr<Test> test_unique3 = test_unique2;
    cout << "id: " << test_unique1->test_id << endl;
    cout << "id: " << test_unique2->test_id << endl;
}

shared_ptr<Test> test_shared(){
    shared_ptr<Test> test_shared1(new Test(1));
    shared_ptr<Test> test_shared2 = make_shared<Test>(2);
    shared_ptr<Test> test_shared3 = test_shared2;
    cout << "id : " << test_shared1->test_id << endl;
    cout << "id : " << test_shared2->test_id << endl;

    return test_shared3;
}

void print_shared(){
    shared_ptr<Test> ptr = test_shared();
    cout << "id: " << ptr->test_id << endl;
}

void print_shared2(){
    Test* T = new Test(1);
    shared_ptr<Test> test_shared1(T);
    shared_ptr<Test> test_shared2(T);
    cout << "test_shared1 use count: " << test_shared1.use_count() << endl;
    cout << "test_shared2 use count: " << test_shared2.use_count() << endl;
}

void print_shared3_1(){
    shared_ptr<Test> test_shared1 = make_shared<Test>(1);
    shared_ptr<Test> test_shared2 = test_shared1->get_shared_ptr(); // Undefined behavior
}
void print_shared3_2(){
    shared_ptr<Test_revised> test_shared1 = make_shared<Test_revised>(1);
    shared_ptr<Test_revised> test_shared2 = test_shared1->get_shared_ptr();

    cout << test_shared1.use_count() << endl;
    cout << test_shared2.use_count() << endl;
}

void print_shared4(){
    shared_ptr<Test> test_shared1 = make_shared<Test>(1);
    shared_ptr<Test> test_shared2 = make_shared<Test>(2);

    test_shared1->cyclic_ref = test_shared2;
    test_shared2->cyclic_ref = test_shared1;
}

void print_weak(){
    shared_ptr<Test> test_shared1 = make_shared<Test>(1);
    {
        shared_ptr<Test> test_shared2 = make_shared<Test>(2);

        test_shared1->set_weak(test_shared2);
        test_shared2->set_weak(test_shared1);

        cout << "use_count 1: " << test_shared1.use_count() << endl;
        cout << "use_count 2: " << test_shared2.use_count() << endl;

        if (auto exist = test_shared1->weak.lock()){
            cout << "accessible" << endl;
        } else{
            cout << "not accessible" << endl;
        }
    }
    if (auto exist = test_shared1->weak.lock()){
        cout << "accessible" << endl;
    } else{
        cout << "not accessible" << endl;
    }
}

int main() {
    //cout << "<Test unique_ptr>" << endl;
    //print_unique();

    //cout << "\n<Test shared_ptr>" << endl;
    //print_shared();

    //cout << "\n<Problem1: Two shared_ptr created independently>" << endl;
    //print_shared2();

    //cout << "\n<Problem2: pointer pointing itself>" << endl;
    //print_shared3_1();
    //print_shared3_2();

    //cout << "\n<Problem3: cyclic reference>" << endl;
    //print_shared4();

    //cout << "\n<Test weak_ptr>" << endl;
    //print_weak();

    return 0;
}
