/**
 * O(n * lg n).
 */
fun sort(array: Array<Int>) = mergeSort(array, 0, array.size - 1)

fun mergeSort(array: Array<Int>, begin: Int, end: Int) {
    if (begin < end) {
        val mid = (begin + end) / 2

        mergeSort(array, begin, mid)
        mergeSort(array, mid + 1, end)
        merge(array, begin, mid, end)
    }
}

fun merge(array: Array<Int>, begin: Int, mid: Int, end: Int) {
    val left = array.slice(begin..mid)
    val right = array.slice((mid + 1)..end)
    var topLeft = 0
    var topRight = 0

    for (i in begin..end) {
        array[i] =
            if (topLeft >= left.size) {
                right[topRight++]
            }
            else if (topRight >= right.size) {
                left[topLeft++]
            }
            else if (left[topLeft] < right[topRight]) {
                left[topLeft++]
            }
            else {
                right[topRight++]   
            }
    }
}
