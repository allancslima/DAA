/**
 * O(lg n).
 * Prerequisite: array must be sorted.
 */
fun search(x: Int, array: Array<Int>): Int = binarySearch(x, array, 0, array.size - 1)

fun binarySearch(x: Int, array: Array<Int>, begin: Int, end: Int): Int {
	if (begin > end) return -1

	val mid = (begin + end) / 2

	if (x == array[mid])
		return mid
	else if (x < array[mid])
		return binarySearch(x, array, begin, mid - 1)
	else
		return binarySearch(x, array, mid + 1, end)
}
