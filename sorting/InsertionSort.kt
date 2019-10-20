/**
 * O(n^2).
 */
fun sort(array: Array<Int>) {
    var key: Int
    var j: Int

    for (i in 1 until array.size) {
        key = array[i]
        j = i - 1

        while (j >= 0 && array[j] > key) {
            array[j + 1] = array[j]
            j--
        }
        array[j + 1] = key
    }
}
