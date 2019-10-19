/**
 * Complexity of O(n^3).
 */
fun sum(array: Array<Int>): Int {
	var maxSum = 0

	for (i in 0 until array.size) {
		for (j in 0 until array.size) {
			var sum = 0

			for (k in i..j) sum = array[k]
			if (sum > maxSum) maxSum = sum
		}
	}
	return maxSum
}
