//Scott Wedge
//2020

//This program plays a game called Towers of Hanoi where the object of the game
// is to rotate a stack of disks from on rod to another while obeying the following rules;
// 1) Only one disk can move at a time, 2) Each move consists of taking the upper disk 
// from one of the stacks and placing it on top of another stack or on an empty rod, 
// and 3) No larger disk may be placed on top of a smaller disk.

#include <iostream> //for console output

using namespace std;
void hanoiTower(int, string, string, string); //declares hanoiTower method

int main() {
    
    int disk = 4; //amount of disks on tower
    hanoiTower(disk, "1", "2", "3"); //disks # and the 3 towers
    return 0;

}

//Accepts disks and tower variables to play the game
void hanoiTower(int disk, string T1, string T2, string T3) {
    if (disk == 1) {
        cout << "Transfer disk 1 from tower " << T1 << " to tower " << T2 << endl;
        return;
    }
    hanoiTower(disk - 1, T1, T3, T2); //recursive method call
    cout << "Transfer disk " << disk << " from tower " << T1 << " to tower " << T2 << endl;
    hanoiTower(disk - 1, T3, T2, T1);
}
