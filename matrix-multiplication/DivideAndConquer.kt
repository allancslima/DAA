/**
 * Returns a matrix with the same number of rows and columns.
 *
 * @param n number of rows and columns.
 * @return NxN matrix of 0s.
 */
fun newSquareMatrix(n: Int): Array<Array<Int>> {
    return Array<Array<Int>>(n) { Array<Int>(n) { 0 } }
}

/**
 * Operator overloading as extension function to sum two matrices.
 *
 * @other matrix to be added.
 * @return sum of the matrices.
 */
operator fun Array<Array<Int>>.plus(other: Array<Array<Int>>): Array<Array<Int>> {
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
 * Multiplies two square matrices of size 2^n, where n is the size of the matrix side.
 *
 * @param a matrix.
 * @param b matrix.
 * @return matrix of size 2^n.
 */
fun multiplyMatrices(a: Array<Array<Int>>, b: Array<Array<Int>>): Array<Array<Int>> {
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

/**
 * Multiplies two 2x2 matrices.
 *
 * @param a matrix.
 * @param b matrix.
 * @return 2x2 matrix.
 */
fun multiply2dMatrices(a: Array<Array<Int>>, b: Array<Array<Int>>): Array<Array<Int>> {
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
 * @param quadrant quadrant position, that's 1, 2, 3 or 4.
 * @return specified quadrant.
 */
fun quadrantFromMatrix(m: Array<Array<Int>>, quadrant: Int): Array<Array<Int>> {
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

/**
 * Builds a matrix from the given quadrants.
 *
 * @param c11 left top quadrant.
 * @param c12 right top quadrant.
 * @param c21 left bottom quadrant.
 * @param c22 right bottom quadrant.
 * @return square matrix of side equal to quadrant side * 2.
 */
fun matrixFromQuadrants(
    c11: Array<Array<Int>>,
    c12: Array<Array<Int>>,
    c21: Array<Array<Int>>,
    c22: Array<Array<Int>>
): Array<Array<Int>> {
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
fun printMatrix(m: Array<Array<Int>>) {
    val n = m.size

    for (i in 0 until n) {
        for (j in 0 until n) {
            print("${m[i][j]} ")
        }
        println()
    }
}