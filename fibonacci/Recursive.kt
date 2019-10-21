/**
 * Θ(ϕ^n)
 */
fun fib(n: Int): Long {
    if (n < 2) return n.toLong()

    return fib(n - 1) + fib(n - 2)
}
