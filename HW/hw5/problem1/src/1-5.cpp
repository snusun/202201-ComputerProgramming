#include "header.h"

void LabelOnePixel(const uint8_t *input, uint8_t *output, int *stack,
                   int width, int height,
                   int labelNo, int x, int y) {
    stack[0] = x;
    stack[1] = y;
    stack[2] = 0;
    int stack_idx = 3;
    int index;

    START:

    index = stack[stack_idx - 3] + width * stack[stack_idx - 2];
    if (input[index] == 255 || output[index] != 255) {
        stack_idx -= 3;
        switch (stack[stack_idx + 2]) {
            case 1 :
                goto RETURN1;
            case 2 :
                goto RETURN2;
            case 3 :
                goto RETURN3;
            case 4 :
                goto RETURN4;
            default:
                return;
        }
    }
    output[index] = labelNo * 50;

    if (stack[stack_idx - 3] > 0) {
        stack[stack_idx] = stack[stack_idx - 3] - 1;
        stack[stack_idx + 1] = stack[stack_idx - 2];
        stack[stack_idx + 2] = 1;
        stack_idx += 3;
        goto START;
    }
    RETURN1:

    if (stack[stack_idx - 3] < width - 1) {
        stack[stack_idx] = stack[stack_idx - 3] + 1;
        stack[stack_idx + 1] = stack[stack_idx - 2];
        stack[stack_idx + 2] = 2;
        stack_idx += 3;
        goto START;
    }
    RETURN2:

    if (stack[stack_idx - 2] > 0) {
        stack[stack_idx] = stack[stack_idx - 3];
        stack[stack_idx + 1] = stack[stack_idx - 2] - 1;
        stack[stack_idx + 2] = 3;
        stack_idx += 3;
        goto START;
    }
    RETURN3:

    if (stack[stack_idx - 2] < height - 1) {
        stack[stack_idx] = stack[stack_idx - 3];
        stack[stack_idx + 1] = stack[stack_idx - 2] + 1;
        stack[stack_idx + 2] = 4;
        stack_idx += 3;
        goto START;
    }
    RETURN4:

    stack_idx -= 3;
    switch (stack[stack_idx + 2]) {
        case 1 :
            goto RETURN1;
        case 2 :
            goto RETURN2;
        case 3 :
            goto RETURN3;
        case 4 :
            goto RETURN4;
        default:
            return;
    }
}

void Labeling(uint8_t *input_image, uint8_t *output_image, int width, int height) {
    // TODO: problem 1.5
    memset(output_image, 255, width * height * sizeof(uint8_t));
    auto *stack = (int *) malloc(3 * sizeof(int) * (width * height + 1));

    int labelNo = 0;
    int index = -1;
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            index++;
            if (input_image[index] == 255 || output_image[index] != 255) continue;
            LabelOnePixel(input_image, output_image, stack, width, height, labelNo++, x, y);
        }
    }

    free(stack);
}