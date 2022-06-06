#include "header.h"

#define CALL_LabelComponent(x, y, returnLabel) { stack[stack_idx] = x; \
stack[stack_idx+1] = y; stack[stack_idx+2] = returnLabel; stack_idx += 3; goto START; }
#define RETURN { stack_idx -= 3;                \
                 switch (stack[stack_idx+2])    \
                 {                       \
                 case 1 : goto RETURN1;  \
                 case 2 : goto RETURN2;  \
                 case 3 : goto RETURN3;  \
                 case 4 : goto RETURN4;  \
                 default: return;        \
                 }                       \
               }

void LabelOnePixel(const uint8_t *input, uint8_t *output, unsigned short *stack,
                   unsigned short width, unsigned short height,
                   int labelNo, unsigned short x, unsigned short y) {
    stack[0] = x;
    stack[1] = y;
    stack[2] = 0;
    int stack_idx = 3;
    int index;

    START:

    index = stack[stack_idx-3] + width * stack[stack_idx-2];
    if (input[index] == 255) RETURN;
    if (output[index] != 255) RETURN;
    output[index] = labelNo * 50;

    if (stack[stack_idx-3] > 0) CALL_LabelComponent(stack[stack_idx-3] - 1, stack[stack_idx-2], 1);
    RETURN1:

    if (stack[stack_idx-3] < width - 1) CALL_LabelComponent(stack[stack_idx-3] + 1, stack[stack_idx-2], 2);
    RETURN2:

    if (stack[stack_idx-2] > 0) CALL_LabelComponent(stack[stack_idx-3], stack[stack_idx-2] - 1, 3);
    RETURN3:

    if (stack[stack_idx-2] < height - 1) CALL_LabelComponent(stack[stack_idx-3], stack[stack_idx-2] + 1, 4);
    RETURN4:

    RETURN;
}

void Labeling(uint8_t *input_image, uint8_t *output_image, int width, int height) {
    // TODO: problem 1.5
    memset(output_image, 255, width * height * sizeof(uint8_t));

    auto *stack = (unsigned short *) malloc(3 * sizeof(unsigned short) * (width * height + 1));

    int labelNo = 0;
    int index = -1;
    for (unsigned short y = 0; y < height; y++) {
        for (unsigned short x = 0; x < width; x++) {
            index++;
            if (input_image[index] == 255) continue;
            if (output_image[index] != 255) continue;
            LabelOnePixel(input_image, output_image, stack, width, height, labelNo++, x, y);
        }
    }

    free(stack);
}