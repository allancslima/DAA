/**
 * Θ(lg n)
 */
fun fib(n: Int): Long {
    if (n < 2) return n.toLong()

    val base = arrayOf(
        arrayOf(1L, 1L),
        arrayOf(1L, 0L)
    )
    return pow(base, n)[0][1]
}

fun pow(base: Array<Array<Long>>, e: Int): Array<Array<Long>> {
    if (e == 1) return base

    if (e % 2 == 0) {
        val half = pow(base, e / 2)
        return multiply(half, half)
    } else {
        val half = pow(base, (e - 1) / 2)
        return multiply(multiply(half, half), base)
    }
}

fun multiply(a: Array<Array<Long>>, b: Array<Array<Long>>): Array<Array<Long>> {
    val result = arrayOf(
        arrayOf(0L, 0L),
        arrayOf(0L, 0L)
    )
    result[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0]
    result[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1]
    result[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0]
    result[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1]

    return result
}
