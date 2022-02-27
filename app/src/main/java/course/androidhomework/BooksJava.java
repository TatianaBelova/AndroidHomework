package course.androidhomework;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.function.BiConsumer;

public class BooksJava {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void workWithBooksJava() {
        BookJava firstBook = new BookJava(123, 6800);
        BookJava secondBook = new BookJava(451, 9000);
        MagazineJava oneMagazine = new MagazineJava(90, 260);

        Log.i("java", firstBook.getAllInfo());
        Log.i("java", secondBook.getAllInfo());
        Log.i("java", oneMagazine.getAllInfo());

        compareBooks(firstBook, secondBook);

        BookJava thirdBook = null;

        if (thirdBook != null) buy(thirdBook);
        buy(secondBook);

        BiConsumer<Integer, Integer> sum = (first, second) -> Log.i("java", "Sum: " + (first + second));

        sum.accept(firstBook.getPrice(), secondBook.getPrice());
    }

    public void compareBooks(@NonNull BookJava firstBook, @NonNull BookJava secondBook) {
        Log.i("java", "Compare by reference: " + (firstBook == secondBook));
        Log.i("java", "Compare by equals: " + firstBook.equals(secondBook));
    }

    void buy(@NonNull PublicationJavaInterface publication) {
        Log.i("java", "The purchase is complete. The purchase amount was " + publication.getPrice());
    }
}

interface PublicationJavaInterface {
    int getPrice();

    int getWordCount();

    String getType();

    default String getAllInfo() {
        return "Price: " + getPrice() + "€, WordCont: " + getWordCount() + ", Type: " + getType();
    }
}

class BookJava implements PublicationJavaInterface {
    BookJava(int newPrice, int newWordCount) {
        price = newPrice;
        wordCount = newWordCount;
    }

    private final int price;
    private final int wordCount;

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public String getType() {
        if (wordCount < 7500) {
            if (wordCount < 1000) {
                return "Flash Fiction";
            }
            return "Short Story";
        }
        return "Novel";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BookJava)) {
            return false;
        }
        BookJava book = (BookJava) obj;
        return price == book.price && wordCount == book.wordCount && getType().equals(book.getType());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + price;
        result = 31 * result + wordCount;
        result = 31 * result + getType().hashCode();
        return result;
    }
}

class MagazineJava implements PublicationJavaInterface {
    MagazineJava(int newPrice, int newWordCount) {
        price = newPrice;
        wordCount = newWordCount;
    }

    private final int price;
    private final int wordCount;

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public String getType() {
        return "Magazine";
    }
}