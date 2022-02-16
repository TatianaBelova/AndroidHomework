package course.androidhomework

import android.util.Log

interface Publication {
    var price: Int
    var wordCount: Int
    fun getType(): String
    val getAllInfo: String
        get() {
            return "Price: $price €, WordCont: $wordCount, Type: ${getType()}"
        }
}

class Book(override var price: Int, override var wordCount: Int) : Publication {
    override fun getType(): String {
        if (wordCount < 7500) {
            if (wordCount < 1000) {
                return "Flash Fiction"
            }
            return "Short Story"
        }
        return "Novel"
    }

    override fun equals(other: Any?): Boolean {
        return other is Book && ((price == other.price && wordCount == other.wordCount && getType() == other.getType()))
    }

    override fun hashCode(): Int {
        var result = price
        result = 31 * result + wordCount
        return result
    }
}

class Magazine(override var price: Int, override var wordCount: Int) : Publication {
    override fun getType(): String {
        return "Magazine"
    }
}

fun workWithBooks() {
    val firstBook = Book(123, 6800)
    val secondBook = Book(451, 9000)
    val oneMagazine = Magazine(90, 260)

    Log.i("allInfo", firstBook.getAllInfo)
    Log.i("allInfo", secondBook.getAllInfo)
    Log.i("allInfo", oneMagazine.getAllInfo)

    compareBooks(firstBook, secondBook)

    val thirdBook: Book? = null
    val forthBook: Book = secondBook

    buy(thirdBook ?: firstBook)
    buy(forthBook)

    val sum: (Int, Int) -> Unit = { first, second -> Log.i("sum", "Sum: ${first + second}") }

    sum(firstBook.price, secondBook.price)
}

fun compareBooks(firstBook: Book, secondBook: Book) {
    Log.i("comparison", "Compare by reference: ${firstBook === secondBook}")
    Log.i("comparison", "Compare by equals: ${firstBook.equals(secondBook)}")
}

fun buy(publication: Publication) {
    Log.i("buy", "The purchase is complete. The purchase amount was ${publication.price}")
}