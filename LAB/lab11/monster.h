#include <iostream>

#ifndef LAB11_MONSTER_H
#define LAB11_MONSTER_H

using namespace std;

typedef int hp_t;  // using hp_t = int;
typedef int dex_t;

enum MonsterType {
    waterMon = 0,
    fireMon = 1,
    grassMon = 2,
};

class Monster {
private:
    static int num_monsters;
protected:
    int id;
    string name;
    hp_t hp;
    hp_t damage;
    dex_t speed;
    MonsterType type;
    MonsterType critical_to;
public:
    Monster(string name, hp_t hp, hp_t damage, MonsterType type, MonsterType critical_to);
    hp_t get_hp() const;
    dex_t get_speed() const;
    MonsterType get_type() const;
    string get_name() const;

    void decrease_health(hp_t attack_damage);
    void attack(Monster *attacked_monster);
    virtual void critical_attack(Monster *attacked_monster);
};

class WaterMon : public Monster {
public:
    WaterMon();

    void critical_attack(Monster *attacked_monster);
};

class FireMon : public Monster {
public:
    FireMon();

    void critical_attack(Monster *attacked_monster);
};

class GrassMon : public Monster {
public:
    GrassMon();

    void critical_attack(Monster *attacked_monster);
};

std::ostream& operator<< (std::ostream& os, const Monster& monster);
#endif //LAB11_MONSTER_H
