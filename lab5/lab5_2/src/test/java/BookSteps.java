import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

public class BookSteps {
    Library library = new Library();
    List<Book> foundBooks = new ArrayList<>();

    @DataTableType
    public Book defineBook(List<String> entry) {
        return new Book(entry.get(0), entry.get(1), entry.get(2));
    }

    @Given("I have the following books:")
    public void iHaveTheFollowingBooks(List<Book> books) {
        for (Book book : books) {
            library.addBook(book);
        }
    }

    @When("I search for books with the title {string}")
    public void iSearchForBooksWithTitle (String title) {
        foundBooks = library.findBooksByTitle(title);
        System.out.println("Found books: " + foundBooks);

    }

    @And("the book should be written by {string}")
    public void theBookShouldBeWrittenBy(String author) {
        for (Book book : foundBooks) {
            assert book.getAuthor().equals(author);
        }
    }

    @And("the book should be from the genre {string}")
    public void theBookShouldBeFromTheGenre(String genre) {
        for (Book book : foundBooks) {
            assert book.getGenre().equals(genre);
        }
    }

    @When("I search for books written by {string}")
    public void iSearchForBooksWrittenBy(String author) {
        foundBooks = library.findBooksByAuthor(author);
        System.out.println("Found books: " + foundBooks);
    }

    @And("the book should have the title {string}")
    public void theBookShouldHaveTheTitle(String title) {
        for (Book book : foundBooks) {
            assert book.getTitle().equals(title);
        }
    }

    @When("I search for books from the genre {string}")
    public void iSearchForBooksFromTheGenre(String genre) {
        foundBooks = library.findBooksByGenre(genre);
        System.out.println("Found books: " + foundBooks);
    }

    @Then("I should find {int} books")
    public void iShouldFindBooks(int arg0) {
        assert foundBooks.size() == arg0;
    }

    @And("I should find a book with the title {string}, written by {string}")
    public void iShouldFindABookWithTheTitleWrittenBy(String title, String author) {
        boolean found = false;
        for (Book book : foundBooks) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                found = true;
                break;
            }
        }
        assert found;
    }
}
