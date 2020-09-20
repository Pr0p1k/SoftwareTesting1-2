import org.apache.log4j.LogManager
import kotlin.math.pow

typealias T = Int
private val LOGGER = LogManager.getLogger("RADIX_SORT_TEST_LOGGER")

fun MutableCollection<T>.radixSort() {
    doSort()
}

fun Collection<T>.radixSorted(): Collection<Number> {
    return toMutableList().apply { doSort() }
}

private fun MutableCollection<T>.doSort() {
    if (size < 2) return
    LOGGER?.info("Started radix sort: $this")
    val capacity = computeMaxCapacity()
    for (i in 1..capacity) {
        val groups = mutableListOf<List<T>>()
        for (j in -9..9) {
            groups.add(filter { it.getDigit(i) == j })
        }
        clear()
        addAll(groups.flatten())
        LOGGER?.info("After step $i out of $capacity: $this")
    }
    LOGGER?.info("Radix sort has finished.")
}

private fun MutableCollection<T>.computeMaxCapacity(): Int {
    return map {
        var c = 1
        while (it / powOfTen(c) != 0) {
            c++
        }
        c
    }.max() ?: throw IllegalArgumentException("Can't compute max capacity: the collection is empty")
}

private fun T.getDigit(i: Int) = rem(powOfTen(i)).div(powOfTen(i - 1))

private fun powOfTen(i: Int) = 10.0.pow(i.toDouble()).toInt()
