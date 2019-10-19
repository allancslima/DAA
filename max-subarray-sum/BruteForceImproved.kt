/**
 * Complexity of O(n^2).
 */
fun sum(array: Array<Int>): Int {
	var maxSum = 0

	for (i in 0 until array.size) {
		var sum = 0

		for (j in i until array.size) {
			sum = array[j]
			if (sum > maxSum) maxSum = sum
		}
	}
	return maxSum
}
