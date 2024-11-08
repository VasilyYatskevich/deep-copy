import org.example.Man
import org.example.deepCopy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Tests {
    @Test
    fun testManWithBooks() {
        val original = Man("John", 25, listOf("To Kill a Mockingbird", "Lord of the Flies"))
        val copy = deepCopy(original)!!

        assertFalse(copy === original)
        assertFalse(copy.favoriteBooks === original.favoriteBooks)
        assertEquals(copy, original)
    }

    @Test
    fun testPrimitive() {
        val original = 1
        val copy = deepCopy(original)

        assertTrue(copy === original)
        assertEquals(copy, original)
    }

    @Test
    fun testChar() {
        val original = ClassWithChars('c', arrayOf<Char>('a', '5', '#'))
        val copy = deepCopy(original)!!

        assertFalse(copy === original)
        assertTrue(copy.manyChars === original.manyChars)
        assertEquals(copy, original)
    }

    @Test
    fun testListOfPrimitives() {
        val original = listOf(1, 2, 100)
        val copy = deepCopy(original)

        assertEquals(original, copy)
        assertFalse(original === copy)
    }

    @Test
    fun testManNullable() {
        val original = Man(null, null, null)
        val copy = deepCopy(original)

        assertEquals(original, copy)
        assertFalse(original === copy)
    }

    @Test
    fun testListNullable() {
        val original = listOf(null ,null)
        val copy = deepCopy(original)

        assertEquals(original, copy)
        assertFalse(original === copy)
    }

    @Test
    fun testManSelfReference() {
        val original = Man("Narrator", 52, listOf("Fight Club"),
            Man("Tyler Durden", 52, listOf("Fight Club")))
        val copy = deepCopy(original)!!

        assertEquals(original, copy)
        assertFalse(original === copy)
        assertFalse(original.internalMan === copy.internalMan)
    }

    @Test
    fun testObjectWithMap() {
        val original = ClassWithMap(mapOf("key" to listOf("listValue1", "listValue2")))
        val copy = deepCopy(original)!!

        assertEquals(original, copy)
        assertFalse(original === copy)
        assertFalse(original.map === copy.map)
        assertFalse(original.map.get("key") === copy.map.get("key"))
    }

    @Test
    fun testFinalDefaultField() {
        val original = ClassWithFinalDefaultField()
        val copy = deepCopy(original)!!

        assertEquals(original.getDefaultField(), copy.getDefaultField())
        assertFalse(original === copy)
    }

    @Test
    fun testFinalNonDefaultField() {
        val original = ClassWithFinalNonDefaultField(100)
        val copy = deepCopy(original)!!

        assertEquals(original, copy)
        assertFalse(original === copy)
    }
}

data class ClassWithChars(val char: Char, val manyChars: Array<Char>)

data class ClassWithMap(val map: Map<String, List<String>>)

class ClassWithFinalDefaultField {
    private val defaultField: Int = 5;

    fun getDefaultField() = defaultField
}

data class ClassWithFinalNonDefaultField(private val finalField: Int)