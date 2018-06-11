package ru.gildor.knel

import kotlin.test.*

class NonEmptyListTest {

    @Test
    fun `singletone NonEmptyList comparasion`() {
        assertTrue(nelOf(2) == nelOf(2))
        assertTrue(NonEmptyList(42) == NonEmptyList(42))
        assertFalse(NonEmptyList(42) == NonEmptyList(2))
        assertFalse(nelOf(2) == nelOf(1))
        assertTrue(nelOf(1) == listOf(1))
        assertTrue(listOf(2) == nelOf(2))
        assertTrue(NonEmptyList(1) == listOf(1))
        assertTrue(listOf(2) == NonEmptyList(2))
    }


}