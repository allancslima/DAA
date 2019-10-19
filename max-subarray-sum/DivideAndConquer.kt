/**
 * Complexity of O(n * lg n).
 */
fun sum(array: Array<Int>): Int = divideAndConquer(array, 0, array.size - 1)

fun divideAndConquer(array: Array<Int>, begin: Int, end: Int): Int {
	if (begin == end) {
		return array[begin]
	}
	val mid = (begin + end) / 2;
	var maxLeftSum = 0
	var maxRightSum = 0
	var sum = 0

	for (i in mid downTo begin) {
		sum += array[i]
		if (sum > maxLeftSum) maxLeftSum = sum
	}
	sum = 0

	for (i in (mid + 1)..end) {
		sum += array[i]
		if (sum > maxRightSum) maxRightSum = sum
	}
	return Math.max(
		Math.max(
			divideAndConquer(array, begin, mid),
			divideAndConquer(array, mid + 1, end)
		),
		maxLeftSum + maxRightSum
	)
}
