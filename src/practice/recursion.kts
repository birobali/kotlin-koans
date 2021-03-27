import java.math.BigInteger
import kotlin.reflect.KProperty
import kotlin.system.measureTimeMillis

tailrec fun factorialTailRec(n: Int, result: BigInteger = 1.toBigInteger()): BigInteger =
    if (n <= 0) result else factorialTailRec(n - 1, result * n.toBigInteger())

println(factorialTailRec(5)) //120

fun factorialIterative(n: Int) =
    (1..n).fold(BigInteger("1")) { product, e ->
        product * e.toBigInteger()
    }

println(factorialIterative(5)) //120

fun sumOfElements(elements: List<Int>, index: Int = elements.size - 1): Int =
    if (index < 0) 0 else elements[index] + sumOfElements(elements, index - 1)


tailrec fun sumOfElementsTailRec(elements: List<Int>, index: Int = elements.size - 1, res: Int = 0): Int =
    if (index < 0) res else sumOfElementsTailRec(elements, index - 1, res + elements[index])

println(" elapsed: " + measureTimeMillis { print("sumOfElementsTailRec result: " + sumOfElementsTailRec((1..4).toList())) } + " ms")
println(" elapsed: " + measureTimeMillis { print("sumOfElements result: " + sumOfElements((1..4).toList())) } + " ms")

fun fibonacci(n: Int): Long = when (n) {
    0, 1 -> 1L
    else -> fibonacci(n - 2) + fibonacci(n - 1)
}

println(" elapsed: " + measureTimeMillis { print("fibonacci result: " + fibonacci(3)) } + " ms")

fun <T, R> ((T) -> R).memoize(): ((T) -> R) {
    val cache = mutableMapOf<T, R>()

    return { n: T -> cache.getOrPut(n) { this(n) } }
}

lateinit var fib: (Int) -> Long

fib = { n: Int ->
    when (n) {
        0, 1 -> 1L
        else -> fib(n - 1) + fib(n - 2)
    }
}.memoize()

println(" elapsed: " + measureTimeMillis { print("fibonacci memoized result: " + fib(40)) } + " ms")

class Memoize<T, R>(val func: (T) -> R) {
    private val cache = mutableMapOf<T, R>()

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = { n: T ->
        cache.getOrPut(n) { func(n) }
    }
}


val fibDelegate: (Int) -> Long by Memoize { n: Int ->
    when (n) {
        0, 1 -> 1L
        else -> fibDelegate(n - 1) + fibDelegate(n - 2)
    }
}

println(" elapsed: " + measureTimeMillis { print("fibonacci delegate result: " + fibDelegate(40)) } + " ms")