import java.util.Scanner

fun newSquareMatrix(n: Int): Array<Array<Long>> {
    return Array<Array<Long>>(n) { Array<Long>(n) { 0 } }
}

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

fun multiplyMatrices(a: Array<Array<Long>>, b: Array<Array<Long>>): Array<Array<Long>> {
    val side = a.size

    if (side == 2) {
        return multiply2dMatrices(a, b)
    } else {
        val c11 = multiplyMatrices(quadrantFromMatrix(a, 1), quadrantFromMatrix(b, 1)) +
                  multiplyMatrices(quadrantFromMatrix(a, 2), quadrantFromMatrix(b, 3))

        val c12 = multiplyMatrices(quadrantFromMatrix(a, 1), quadrantFromMatrix(b, 2)) +
                  multiplyMatrices(quadrantFromMatrix(a, 2), quadrantFromMatrix(b, 4))

        val c21 = multiplyMatrices(quadrantFromMatrix(a, 3), quadrantFromMatrix(b, 1)) +
                  multiplyMatrices(quadrantFromMatrix(a, 4), quadrantFromMatrix(b, 3))

        val c22 = multiplyMatrices(quadrantFromMatrix(a, 3), quadrantFromMatrix(b, 2)) +
                  multiplyMatrices(quadrantFromMatrix(a, 4), quadrantFromMatrix(b, 4))

        return matrixFromQuadrants(c11, c12, c21, c22)
    }
}

fun multiply2dMatrices(a: Array<Array<Long>>, b: Array<Array<Long>>): Array<Array<Long>> {
    val c = newSquareMatrix(2)

    c[0][0] = a[0][0]*b[0][0] + a[0][1]*b[1][0]
    c[0][1] = a[0][0]*b[0][1] + a[0][1]*b[1][1]
    c[1][0] = a[1][0]*b[0][0] + a[1][1]*b[1][0]
    c[1][1] = a[1][0]*b[0][1] + a[1][1]*b[1][1]

    return c
}

fun quadrantFromMatrix(m: Array<Array<Long>>, quadrant: Int): Array<Array<Long>> {
    val quadrantSide = m.size / 2
    val startLine = when (quadrant) {
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
            quadrantMatrix[i][j] = m[startLine + i][startCol + j]
        }
    }
    return quadrantMatrix
}

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

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val n1 = scanner.nextInt()
    val m1 = scanner.nextInt()
    val n2 = scanner.nextInt()
    val m2 = scanner.nextInt()

    if (m1 != n2) {
        throw RuntimeException("M1 != N2")
    }
    val n = if (m1 % 2 != 0) m1 + 1 else m1
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
