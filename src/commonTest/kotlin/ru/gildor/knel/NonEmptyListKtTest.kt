@file:Suppress("unused", "ReplaceAssertBooleanWithAssertEquality")

package ru.gildor.knel

import kotlin.test.*

class NonEmptyListTest {

    @Test
    fun singletonNelListEquals() {
        assertTrue(nelOf(2) == nelOf(2))
        assertTrue(NonEmptyList(2) == NonEmptyList(2))
        assertTrue(nelOf(2) == NonEmptyList(2))
        assertFalse(nelOf(1) == nelOf(3))
        assertFalse(NonEmptyList(3) == NonEmptyList(1))
        assertFalse(nelOf(4) == NonEmptyList(1))
    }

    @Test
    fun nelList_Equals() {
        assertTrue(nelOf(1, 2) == nelOf(1, 2))
        assertTrue(NonEmptyList(1, listOf(2)) == NonEmptyList(1, listOf(2)))
        assertTrue(NonEmptyList(1, nelOf(2)) == NonEmptyList(1, nelOf(2)))
        assertTrue(nelOf(2, 1) == NonEmptyList(2, nelOf(1)))
        assertFalse(nelOf(1, 2) == nelOf(2, 1))
        assertFalse(nelOf(1, 2) == nelOf(2, 1))
        assertFalse(NonEmptyList(3) == NonEmptyList(1))
        assertFalse(nelOf(4) == NonEmptyList(1))
    }

    @Test
    fun singletonNelHashCode() {
        val nel = nelOf(1)
        assertEquals(nel.hashCode(), nel.hashCode(), "Non stable hash code")
        assertEquals(42, nelOf(11).hashCode())
        assertEquals(listOf(11).hashCode(), nelOf(11).hashCode())
        assertEquals(listOf(null).hashCode(), nelOf(null).hashCode())
        assertNotEquals(nelOf(5).hashCode(), nelOf(55).hashCode())
    }

    @Test
    fun nelToString() {
        assertEquals("[1]", nelOf(1).toString())
        assertEquals("[A]", NonEmptyList("A").toString())
        assertEquals("[a, b]", listOf('a', 'b').toString())
        assertEquals("[a, b]", nelOf('a', 'b').toString())
        assertEquals("[[1], [2, 3]]", nelOf(NonEmptyList(1L), NonEmptyList(2L, listOf(3))).toString())
    }

    @Test
    fun nelHashCode() {
        val nel = nelOf(1, 2, 3)
        assertEquals(nel.hashCode(), nel.hashCode(), "Non stable hash code")
        assertEquals(listOf(null, null).hashCode(), nelOf(null, null).hashCode())
        assertNotEquals(nelOf(1, 2, 3), nelOf(1, 2, 3, 4))
        assertNotEquals(nelOf(1, 2, 3), nelOf(3, 2, 1))
    }

    @Test
    fun singletonHead() {
        assertEquals(1, nelOf(1).head)
        assertEquals(null, nelOf<Int?>(null).head)

        assertEquals('a', nelOf('a', 'b', 'c').head)
    }

    @Test
    fun nelHead() {
        assertEquals(1, nelOf(1, 2).head)
    }

    @Test
    fun nelTail() {
        assertEquals(listOf(2), nelOf(1, 2).tail)
        assertEquals(listOf(1, 2, 3), listOf(1) + nelOf(1, 2, 3).tail)
        assertEquals(listOf("B", "C"), nelOf("A", "B", "C").tail)
        assertEquals(listOf(2, null), nelOf(1, 2, null).tail)
    }

    @Test
    fun singletonTail() {
        assertEquals(emptyList(), nelOf(1).tail)
        assertEquals(emptyList(), nelOf(null).tail)
    }

    @Suppress("DEPRECATION")
    @Test
    fun singletonDeprecatedMethods() {
        assertEquals(false, nelOf(1).isEmpty())
        assertEquals(false, NonEmptyList(Any()).isEmpty())
        assertEquals(1, nelOf(1).firstOrNull())
        assertEquals(null, nelOf(null).firstOrNull())
    }

    @Suppress("DEPRECATION")
    @Test
    fun nelDeprecatedMethods() {
        assertEquals(false, nelOf(1, 2, 3).isEmpty())
        assertEquals(false, NonEmptyList(1, listOf(2, 3)).isEmpty())
        assertEquals('a', nelOf('a', 'b', 'c').firstOrNull())
        assertEquals('a', NonEmptyList('a', nelOf('b', 'c')).firstOrNull())
        assertEquals(null, nelOf(null, 2, 3).firstOrNull())
    }

    @Test
    fun toNel() {
        assertEquals(null, emptyList<Int>().toNel())
        assertEquals(null, ArrayList<Int>().toNel())
        assertTrue(listOf(1).toNel() is NonEmptyList<Int>)
        assertEquals(nelOf("A", "B", "C"), listOf("A", "B", "C").toNel())
    }

    @Test
    fun toNelUnsafe() {
        assertEquals(nelOf('x', 'y', 'z'), listOf('x', 'y', 'z').toNelUnsafe())
        assertEquals(listOf(1), listOf(1).toNelUnsafe())
        val nel = listOf(1).toNelUnsafe()
        @Suppress("USELESS_IS_CHECK")
        assertTrue(nel is NonEmptyList<Int>)
        assertEquals(nelOf(1), nel)
        assertFailsWith<NoSuchElementException> {
            emptyList<String>().toNelUnsafe()
        }
    }

    @Test
    fun plus() {
        val list = nelOf(1) + nelOf(2)
        @Suppress("USELESS_IS_CHECK")
        assertTrue(list is NonEmptyList)
        assertEquals(nelOf(1, 2), list)
    }

    @Test
    fun plusElement() {
        val list = nelOf(1) + 2
        @Suppress("USELESS_IS_CHECK")
        assertTrue(list is NonEmptyList)
        assertEquals(nelOf(1, 2), list)

    }

}