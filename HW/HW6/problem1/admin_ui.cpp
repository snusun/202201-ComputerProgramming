#include "admin_ui.h"
#include "shopping_db.h"

using namespace std;

AdminUI::AdminUI(ShoppingDB &db, std::ostream& os): UI(db, os) { }

void AdminUI::add_product(std::string name, int price) {
    // TODO: Problem 1.1
    if(price < 0) {
        cout << "ADMIN_UI: Invalid price." << endl;
    } else {
        db.add_product(name, price);
        cout << "ADMIN_UI: " << name << " is added to the database." << endl;
    }
}

void AdminUI::edit_product(std::string name, int price) {
    // TODO: Problem 1.1
    if(db.check_product(name)){
        if(price < 0) {
            cout << "ADMIN_UI: Invalid price." << endl;
        } else {
            db.edit_product(name, price);
            cout << "ADMIN_UI: " << name << " is modified from the database." << endl;
        }
    } else {
        cout << "ADMIN_UI: Invalid product name." << endl;
    }

}

void AdminUI::list_products() {
    // TODO: Problem 1.1
    cout << "ADMIN_UI: ";
    db.print_product();
}
