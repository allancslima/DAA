/**
 * O(n).
 */
fun search(x: Int, array: Array<Int>): Int {
    for (i in 0 until array.size) {
        if (x == array[i]) return i
    }
    return -1
}
