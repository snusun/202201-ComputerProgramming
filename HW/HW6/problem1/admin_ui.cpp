#include "admin_ui.h"
#include "shopping_db.h"

using namespace std;

AdminUI::AdminUI(ShoppingDB &db, std::ostream& os): UI(db, os) { }

void AdminUI::add_product(std::string name, int price) {
    // TODO: Problem 1.1
    if(price < 0) {
        os << "ADMIN_UI: Invalid price." << endl;
        //cout << "ADMIN_UI: Invalid price." << endl;
    } else {
        db.add_product(name, price);
        os << "ADMIN_UI: " << name << " is added to the database." << endl;
//        cout << "ADMIN_UI: " << name << " is added to the database." << endl;
    }
}

void AdminUI::edit_product(std::string name, int price) {
    // TODO: Problem 1.1
    if(db.check_product(name)){
        if(price < 0) {
            os << "ADMIN_UI: Invalid price." << endl;
        } else {
            db.edit_product(name, price);
            os << "ADMIN_UI: " << name << " is modified from the database." << endl;
        }
    } else {
        os << "ADMIN_UI: Invalid product name." << endl;
    }

}

void AdminUI::list_products() {
    // TODO: Problem 1.1
    os << "ADMIN_UI: ";
    vector<Product*> products = db.get_products();

    if(products.size()==0){
        os << "Products: []" << endl;
        return;
    }
    os << "Products: [";

    for(int i=0; i<products.size()-1; i++){
        auto product = products.at(i);
        os << "(" << product->name << ", " << product->price << "), ";
    }

    os << "(" << products.at(products.size()-1)->name << ", " << products.at(products.size()-1)->price << ")]" << endl;
}
