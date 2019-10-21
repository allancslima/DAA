/**
 * Θ(n)
 */
fun fib(n: Int): Long {
    if (n < 2) return n.toLong()
    
    var f1: Long = 1
    var f2: Long = 1
    var f: Long = 2

    for (i in 3..n) {
        f = f1 + f2
        f1 = f2
        f2 = f
    }
    return f
}
