import java.util.Scanner

/**
 * Returns a matrix with the same number of rows and columns.
 *
 * @param n number of rows and columns.
 * @return NxN matrix of 0s.
 */
fun newSquareMatrix(n: Int): Array<Array<Long>> {
    return Array<Array<Long>>(n) { Array<Long>(n) { 0 } }
}

/**
 * Operator overloading as extension function to sum two matrices.
 *
 * @other matrix to be added.
 * @return sum of the matrices.
 */
operator fun Array<Array<Long>>.plus(other: Array<Array<Long>>): Array<Array<Long>> {
    val side = this.size
    val sum = newSquareMatrix(side)

    for (i in 0 until side) {
        for (j in 0 until side) {
            sum[i][j] = this[i][j] + other[i][j]
        }
    }
    return sum
}

/**
 * Operator overloading as extension function to subtract two matrices.
 *
 * @other matrix to be subtracted.
 * @return subtraction of the matrices.
 */
operator fun Array<Array<Long>>.minus(other: Array<Array<Long>>): Array<Array<Long>> {
    val side = this.size
    val sum = newSquareMatrix(side)

    for (i in 0 until side) {
        for (j in 0 until side) {
            sum[i][j] = this[i][j] - other[i][j]
        }
    }
    return sum
}

/**
 * Multiplies two square matrices of size 2^n, where n is the size of the matrix side.
 *
 * @param a matrix a.
 * @param b matrix b.
 * @return matrix of size 2^n.
 */
fun multiplyMatrices(a: Array<Array<Long>>, b: Array<Array<Long>>): Array<Array<Long>> {
    val side = a.size

    if (side == 2) {
        return multiply2dMatrices(a, b)
    } else {
        val m1 = multiplyMatrices(
            quadrantFromMatrix(a, 1) + quadrantFromMatrix(a, 4),
            quadrantFromMatrix(b, 1) + quadrantFromMatrix(b, 4)
        )
        val m2 = multiplyMatrices(
            quadrantFromMatrix(a, 3) + quadrantFromMatrix(a, 4),
            quadrantFromMatrix(b, 1)
        )
        val m3 = multiplyMatrices(
            quadrantFromMatrix(a, 1),
            quadrantFromMatrix(b, 2) - quadrantFromMatrix(b, 4)
        )
        val m4 = multiplyMatrices(
            quadrantFromMatrix(a, 4),
            quadrantFromMatrix(b, 3) - quadrantFromMatrix(b, 1)
        )
        val m5 = multiplyMatrices(
            quadrantFromMatrix(a, 1) + quadrantFromMatrix(a, 2),
            quadrantFromMatrix(b, 4)
        )
        val m6 = multiplyMatrices(
            quadrantFromMatrix(a, 3) - quadrantFromMatrix(a, 1),
            quadrantFromMatrix(b, 1) + quadrantFromMatrix(b, 2)
        )
        val m7 = multiplyMatrices(
            quadrantFromMatrix(a, 2) - quadrantFromMatrix(a, 4),
            quadrantFromMatrix(b, 3) + quadrantFromMatrix(b, 4)
        )
        val c11 = m1 + m4 - m5 + m7
        val c12 = m3 + m5
        val c21 = m2 + m4
        val c22 = m1 - m2 + m3 + m6

        return matrixFromQuadrants(c11, c12, c21, c22)
    }
}

/**
 * Multiplies two 2x2 matrices.
 *
 * @param a matrix a.
 * @param b matrix b.
 * @return 2x2 matrix.
 */
fun multiply2dMatrices(a: Array<Array<Long>>, b: Array<Array<Long>>): Array<Array<Long>> {
    val c = newSquareMatrix(2)

    c[0][0] = a[0][0]*b[0][0] + a[0][1]*b[1][0]
    c[0][1] = a[0][0]*b[0][1] + a[0][1]*b[1][1]
    c[1][0] = a[1][0]*b[0][0] + a[1][1]*b[1][0]
    c[1][1] = a[1][0]*b[0][1] + a[1][1]*b[1][1]

    return c
}

/**
 * Returns the quadrant corresponding to the given position of a 2^n matrix.
 *
 * @param m matrix.
 * @param quadrant
 *        quadrant position, it can be 1, 2, 3 or 4 where:
 *        1 to top left quadrant;
 *        2 to top right quadrant;
 *        3 to bottom left quadrant;
 *        4 to bottom right quadrant.
 *
 * @return specified quadrant.
 */
fun quadrantFromMatrix(m: Array<Array<Long>>, quadrant: Int): Array<Array<Long>> {
    val quadrantSide = m.size / 2
    val startRow = when (quadrant) {
        1, 2 -> 0
        3, 4 -> quadrantSide
        else -> -1
    }
    val startCol = when (quadrant) {
        1, 3 -> 0
        2, 4 -> quadrantSide
        else -> -1
    }
    val quadrantMatrix = newSquareMatrix(quadrantSide)

    for (i in 0 until quadrantSide) {
        for (j in 0 until quadrantSide) {
            quadrantMatrix[i][j] = m[startRow + i][startCol + j]
        }
    }
    return quadrantMatrix
}

/**
 * Builds a matrix from the given quadrants.
 *
 * @param c11 top left quadrant.
 * @param c12 top right quadrant.
 * @param c21 bottom left quadrant.
 * @param c22 bottom right quadrant.
 * @return square matrix of side equal to quadrant side * 2.
 */
fun matrixFromQuadrants(
    c11: Array<Array<Long>>,
    c12: Array<Array<Long>>,
    c21: Array<Array<Long>>,
    c22: Array<Array<Long>>
): Array<Array<Long>> {
    val quadrantSide = c11.size
    val matrixSide = quadrantSide * 2
    val matrix = newSquareMatrix(matrixSide)

    for (i in 0 until quadrantSide) {
        for (j in 0 until quadrantSide) {
            matrix[i][j] = c11[i][j]
            matrix[i][j + quadrantSide] = c12[i][j]
            matrix[i + quadrantSide][j] = c21[i][j]
            matrix[i + quadrantSide][j + quadrantSide] = c22[i][j]
        }
    }
    return matrix
}

/**
 * Prints a given square matrix.
 *
 * m matrix.
 */
fun printMatrix(m: Array<Array<Long>>, rows: Int, cols: Int) {
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (j < cols - 1) {
                print("${m[i][j]} ")
            } else {
                println("${m[i][j]}")
            }
        }
    }
}

/**
 * Returns the next 2^e value from given input.
 *
 * @param n starting number.
 * @return next 2^e value from parameter n.
 */
fun nextPowerOfTwo(n: Int): Int {
    var e = 1
    var p = 2

    while (n > p) {
        p = Math.pow(2.0, (++e).toDouble()).toInt()
    }
    return p
}

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val n1 = scanner.nextInt()
    val m1 = scanner.nextInt()
    val n2 = scanner.nextInt()
    val m2 = scanner.nextInt()

    if (m1 != n2) {
        throw RuntimeException("M1 != N2")
    }
    val max = Math.max(Math.max(n1, m2), m1)
    val n = nextPowerOfTwo(max)
    val a = newSquareMatrix(n)
    val b = newSquareMatrix(n)

    for (i in 0 until n1) {
        for (j in 0 until m1) {
            a[i][j] = scanner.nextLong()
        }
    }
    for (i in 0 until n2) {
        for (j in 0 until m2) {
            b[i][j] = scanner.nextLong()
        }
    }
    scanner.close()

    val start = System.currentTimeMillis()
    printMatrix(multiplyMatrices(a, b), n1, m2)
    val end = System.currentTimeMillis()

    println("\nTime: ${(end - start) / 1000.0} seconds")
}
