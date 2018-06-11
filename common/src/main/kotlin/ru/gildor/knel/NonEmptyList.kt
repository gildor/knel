@file:Suppress("RedundantVisibilityModifier", "FunctionName", "NOTHING_TO_INLINE", "DeprecatedCallableAddReplaceWith", "unused")

package ru.gildor.knel

public interface NonEmptyList<out T> : List<T> {
    val head: T
    val tail: List<T>

    @Deprecated("Useless check. NonEmptyList never empty")
    override fun isEmpty(): Boolean = false

    @Deprecated(
            "First element of NonEmptyList always exist. Use head or first() instead",
            ReplaceWith(expression = "head")
    )
    fun firstOrNull(): Boolean = false
}

public fun <T> NonEmptyList(head: T): NonEmptyList<T> = SingletonNonEmptyList(head)

public fun <T> NonEmptyList(head: T, tail: List<T>): NonEmptyList<T> {
    return if (tail.isEmpty()) {
        SingletonNonEmptyList(head)
    } else {
        NonEmptyListImpl(head, tail)
    }
}

public fun <T> nelOf(head: T): NonEmptyList<T> = SingletonNonEmptyList(head)

public fun <T> nelOf(head: T, vararg tail: T): NonEmptyList<T> {
    return if (tail.isEmpty()) {
        SingletonNonEmptyList(head)
    } else {
        NonEmptyListImpl(head, tail.toList())
    }
}

public inline fun <T> List<T>.toNel(): NonEmptyList<T>? {
    return if (isEmpty()) null else NonEmptyList(first(), drop(1))
}

public inline fun <T> List<T>.toNelUnsafe(): NonEmptyList<T> {
    return toNel() ?: throw NoSuchElementException("List is empty")
}

private class NonEmptyListImpl<out T>(
    override val head: T,
    override val tail: List<T> = emptyList()
) : NonEmptyList<T> {

    private val all = if (tail.isEmpty()) {
        listOf(head)
    } else {
        mutableListOf(head).apply { addAll(tail) }
    }

    override val size: Int = all.size



    override fun contains(element: @UnsafeVariance T): Boolean {
        return if (element == head) true else all.contains(element)
    }

    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean = all.containsAll(elements)

    override fun get(index: Int): T = if (index == 0) head else all[index]

    override fun indexOf(element: @UnsafeVariance T): Int {
        return if (element == head) 0 else all.indexOf(element)
    }

    override fun iterator(): Iterator<T> = all.iterator()

    override fun lastIndexOf(element: @UnsafeVariance T): Int = all.lastIndexOf(element)

    override fun listIterator(): ListIterator<T> = all.listIterator()

    override fun listIterator(index: Int): ListIterator<T> = all.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> = all.subList(fromIndex, toIndex)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is List<*>) return false

        return other == this
    }

    override fun hashCode(): Int {
        return all.hashCode()
    }

    override fun toString(): String = "NonEmptyList($head)"
}

private class SingletonNonEmptyList<out T>(
        override val head: T
) : NonEmptyList<T> {
    override val size = 1

    override val tail: List<T> = emptyList()

    override fun contains(element: @UnsafeVariance T): Boolean = head == element

    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean {
        return elements.size == 1 && elements.first() == head
    }

    override fun get(index: Int): T = head

    override fun indexOf(element: @UnsafeVariance T): Int {
        return if (element == head) 0 else -1
    }

    override fun iterator(): Iterator<T> = SingleListIterator(head)

    override fun lastIndexOf(element: @UnsafeVariance T): Int {
        return if (element == head) 0 else -1
    }

    override fun listIterator(): ListIterator<T> = SingleListIterator(head)

    override fun listIterator(index: Int): ListIterator<T> {
        if (index < 0 || index > size) throw IndexOutOfBoundsException("Index: $index")

        return SingleListIterator(head)
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        if (fromIndex > 0 || toIndex > 0) {
            throw IndexOutOfBoundsException("Index: $fromIndex to $toIndex, Size: 1")
        }
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != List::class) return false

        other as List<*>

        return other == this
    }

    override fun hashCode(): Int {
        return head?.hashCode() ?: 0
    }

    override fun toString(): String = "NonEmptyList($head)"
}

private class SingleListIterator<T>(val value: T) : ListIterator<T> {
    private var hasNext = true

    override fun hasNext(): Boolean = hasNext

    override fun hasPrevious(): Boolean = false

    override fun next(): T {
        if (!hasNext) throw NoSuchElementException()
        hasNext = false
        return value
    }

    override fun nextIndex(): Int = 0

    override fun previous(): Nothing = throw NoSuchElementException()

    override fun previousIndex(): Int = -1
}
