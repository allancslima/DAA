/**
 * O(n * lg n).
 */
fun sort(array: Array<Int>) = quickSort(array, 0, array.size - 1)

fun quickSort(array: Array<Int>, begin: Int, end: Int) {
    if (begin < end) {
        val pivot = partition(array, begin, end)
        quickSort(array, begin, pivot - 1)
        quickSort(array, pivot + 1, end)
    }
}

fun partition(array: Array<Int>, begin: Int, end: Int): Int {
    val pivot = array[end]
    var i = begin - 1

    for (j in begin until end) {
        if (array[j] <= pivot) {
            swap(array, j, ++i)
        }
    }
    swap(array, end, i + 1)

    return i + 1
}

fun swap(array: Array<Int>, a: Int, b: Int) {
    var aux = array[a]
    array[a] = array[b]
    array[b] = aux
}
