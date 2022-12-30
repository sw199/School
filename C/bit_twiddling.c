/*
 * Scott Wedge
 * October 11, 2020
 *
 * program does not require any input. run with command:
 *      $ ./bits 
 *
 */

#include <stdio.h>


// function func1
// computes the bitwise AND of two ints
// legal operators:
//      ~ and |
// parameters:
//      int x, int value 
//      int y, int value
// example:
//      func1(5, 6); equals 4. (0101 & 0110 = 0100)
// 
// math steps: ~(~x | ~y)
//  
//                ~x == 1010 
//                ~y == 1001
//       1010 | 1001 == 1011
//             ~1011 == 0100
//
int func1(int x, int y);

int func1(int x, int y) {
    return ~(~x|~y);
}

// function func2
// computes the bit-wise exclusive-or of two integers.
// legal operators:
//      ~ and &
// parameters:
//      int x, int value
//      int y, int value
// example:
//      func2(4, 5); equals 1. (0100 ^ 0101 = 0001)
// 
// math steps: 
//      
//                x == 0100
//                y == 0101
//      0100 & 0101 == 0100
//            ~0100 == 1011
//      1011 & 0101 == 0001
//
int func2(int x, int y);

int func2(int x, int y) {
    x = (x&y); // x now equals 0100
    x = ~x;    // x now equals 1011
    x = (x&y); // x now equals 0001
    return x;
}


// function func3()
// returns 1 if any odd-numbered bit in an int is set to 1
// parameters:
//      int x, int value
// example:
//      func3(0x5); should return 0
//      func3(0x7); should return 1
// notes:
//      0x5 == 0101 , 0x7 == 0111
//      0xa == 1010
//   !!:
//      if eval is 0, !0 == 1, !1 == 0, return 0
//      if eval is >0, !1 == 0, !0 == 1, return 1 
//
//      The equation !!((x | (x >> 4)) & 0xa); will solve this
//          puzzle for only 4-bits. In order to solve the puzzle
//          larger numbers the equation can be extended:
//          !!((x | (x >> 8)) & 0xaa);
//          !!((x | (x >> 8) | (x >> 16)) & 0xaa);
//          !!((x | (x >> 8) | (x >> 16) | (x >> 24)) & 0xaa);
//           
int func3(int x);

int func3(int x) {
    return !!((x | (x >> 4)) & 0xa);
    
}


// functions func6()
// swaps the m'th byte and n'th byte of an integer
// assumptions:
//      0 <= n <= 3
//      0 <= m <= 3
// parameters:
//      int x, integer value
//      int m, position of m'th byte of int
//      int n, position of n'th byte of int
// example:
//      func6(0x12345678, 1, 3); should return 0x56341278
//      func6(0xDEADBEEF, 0, 2); should return 0xDEEFBEAD
// notes:
//      0x12345678 == 0001 0010 0011 0100 0101 0110 0111 1000
//      0x56341278 == 0101 0110 0011 0100 0001 0010 0111 1000
//      0xDEADBEEF == 1101 1110 1010 1101 1011 1110 1110 1111
//      0xDEEFBEAD == 1101 1110 1110 1111 1011 1110 1010 1101
//            0xff == 0000 0000 0000 0000 0000 0000 1111 1111
//            
//
int func6(int x, int m, int n);

int func6(int x, int m, int n) {
    int o = 0;
    m = m << 3; // if m=1, m is now 00001000 (dec = 8)
    n = n << 3; // if n=3, n is now 00011000 (dec = 24)
    o = 0xff & ((x >> m) ^ (x >> n)); 
    x = x ^ (o << m);
    x = x ^ (o << n);
    return x;
}


// function main()
//
int main() {
    //function1:
    printf("function1_(5, 6) == %d\n", func1(5, 6));

    //function2:
    printf("function2_(4, 5) == %d\n", func2(4, 5));
    
    //function3:
    printf("function3_(0x5) == %x\n", func3(0x5));
    printf("function3_(0x7) == %x\n", func3(0x7));
    
    //function6:
    printf("function6_0x12345678 == 0x%x\n", func6(0x12345678, 1, 3));
    printf("function6_0xDEADBEEF == 0x%X\n", func6(0xDEADBEEF, 0, 2));
}
