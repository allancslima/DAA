/**
 * O(n^3)
 */
fun multiply(a: Array<Array<Int>>, b: Array<Array<Int>>): Array<Array<Int>> {
    val linesOfA = a.size
    val columnsOfA = a.first().size
    val linesOfB = b.size
    val columnsOfB = b.first().size

    if (columnsOfA != linesOfB) {
        throw IllegalArgumentException("The number of columns of A must be equal to the number of lines of B.")
    }
    val result = Array<Array<Int>>(linesOfA) { Array<Int>(columnsOfB) { 0 } }

    for (i in 0 until linesOfA) {
        for (j in 0 until columnsOfB) {
            for (k in 0 until columnsOfA) {
                result[i][j] += a[i][k] * b[k][j]
            }
        }
    }
    return result
}
