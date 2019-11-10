#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void merge(int *array, int begin, int mid, int end)
{
    int n_left = mid - begin + 1;
    int n_right = end - mid;

    int *left = (int*) malloc(sizeof(int) * n_left);
    int *right = (int*) malloc(sizeof(int) * n_right);

    memcpy(left, array + begin, sizeof(int) * n_left);
    memcpy(right, array + begin + n_left, sizeof(int) * n_right);

    int left_index = 0;
    int right_index = 0;
    int i;

    for (i = begin; i <= end; i++) {
        if (left_index >= n_left) {
            array[i] = right[right_index++];
        }
        else if (right_index >= n_right) {
            array[i] = left[left_index++];
        }
        else if (right[right_index] < left[left_index]) {
            array[i] = right[right_index++];
        }
        else {
            array[i] = left[left_index++];
        }
    }
    free(left);
    free(right);
}

void merge_sort(int *array, int begin, int end)
{
    if (begin < end) {
        int mid = (begin + end) / 2;

        merge_sort(array, begin, mid);
        merge_sort(array, mid + 1, end);
        merge(array, begin, mid, end);
    }
}
