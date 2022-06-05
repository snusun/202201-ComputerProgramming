#include "header.h"

#define CALL_LabelComponent(x,y,returnLabel) { STACK[SP] = x; \
STACK[SP+1] = y; STACK[SP+2] = returnLabel; SP += 3; goto START; }
#define RETURN { SP -= 3;                \
                 switch (STACK[SP+2])    \
                 {                       \
                 case 1 : goto RETURN1;  \
                 case 2 : goto RETURN2;  \
                 case 3 : goto RETURN3;  \
                 case 4 : goto RETURN4;  \
                 default: return;        \
                 }                       \
               }
#define X (STACK[SP-3])
#define Y (STACK[SP-2])

void LabelComponent(unsigned short* STACK, unsigned short width, unsigned short height,
                    uint8_t *input, uint8_t *output, int labelNo, unsigned short x, unsigned short y)
{
    STACK[0] = x;
    STACK[1] = y;
    STACK[2] = 0;  /* return - component is labelled */
    int SP   = 3;
    int index;

    START: /* Recursive routine starts here */

    index = X + width*Y;
    if (input [index] == 255) RETURN;   /* This pixel is not part of a component */
    if (output[index] != 255) RETURN;   /* This pixel has already been labelled  */
    output[index] = labelNo;

    if (X > 0) CALL_LabelComponent(X-1, Y, 1);   /* left  pixel */
    RETURN1:

    if (X < width-1) CALL_LabelComponent(X+1, Y, 2);   /* right pixel */
    RETURN2:

    if (Y > 0) CALL_LabelComponent(X, Y-1, 3);   /* upper pixel */
    RETURN3:

    if (Y < height-1) CALL_LabelComponent(X, Y+1, 4);   /* lower pixel */
    RETURN4:

    RETURN;
}

void Labeling(uint8_t *input_image, uint8_t *output_image, int width, int height) {
    // TODO: problem 1.5
    std::cout << "HI" << std::endl;
    memset(output_image, 255, width*height*sizeof(uint8_t));

//    unsigned short* STACK = (unsigned short*) malloc(3*sizeof(unsigned short)*(width*height + 1));
//
//    int labelNo = 0;
//    int index   = -1;
//    for (unsigned short y = 0; y < height; y++)
//    {
//        for (unsigned short x = 0; x < width; x++)
//        {
//            index++;
//            if (input_image[index] == 255) continue;   /* This pixel is not part of a component */
//            if (output_image[index] != 255) continue;   /* This pixel has already been labelled  */
//            /* New component found */
//            labelNo++;
//            LabelComponent(STACK, width, height, input_image, output_image, labelNo, x, y);
//        }
//    }
//
//    free(STACK);
}