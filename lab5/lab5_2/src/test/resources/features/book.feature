Feature: Book search
  Customers should be able to quickly find books by title, author, or genre.
  Background:
    Given I have the following books:
      | title              | author           | genre    |
      | The Catcher in the Rye | J.D. Salinger | Fiction  |
      | The Great Gatsby       | F. Scott Fitzgerald | Fiction  |
      | The Orient Express     | Agatha Christie | Mystery  |

  Scenario: Search for books by title
    When I search for books with the title 'The Catcher in the Rye'
    Then I should find 1 books
      And the book should be written by 'J.D. Salinger'
      And the book should be from the genre 'Fiction'

  Scenario: Search for books by author
    When I search for books written by 'J.D. Salinger'
    Then I should find 1 books
      And the book should have the title 'The Catcher in the Rye'
      And the book should be from the genre 'Fiction'

  Scenario: Search for books by genre
    When I search for books from the genre 'Fiction'
    Then I should find 2 books
      And I should find a book with the title 'The Catcher in the Rye', written by 'J.D. Salinger'
      And I should find a book with the title 'The Great Gatsby', written by 'F. Scott Fitzgerald'