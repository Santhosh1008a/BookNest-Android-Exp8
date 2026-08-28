package com.example.exp5notification.data

object BookRepository {
    private val books = mutableListOf<Book>()

    init {
        // Fiction
        addBook("1", "Pride and Prejudice", "Jane Austen", "Fiction", "A classic novel of manners...", "https://www.gutenberg.org/cache/epub/1342/pg1342.cover.medium.jpg", 4.8f, 1813, 278, 15, false, "https://www.gutenberg.org/ebooks/1342")
        addBook("2", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "The story of the mysteriously wealthy Jay Gatsby...", "https://www.gutenberg.org/cache/epub/64317/pg64317.cover.medium.jpg", 4.5f, 1925, 180, 0, false, "https://www.gutenberg.org/ebooks/64317")
        addBook("3", "Moby Dick", "Herman Melville", "Fiction", "The voyage of the whaling ship Pequod...", "https://www.gutenberg.org/cache/epub/2701/pg2701.cover.medium.jpg", 4.2f, 1851, 635, 42, false, "https://www.gutenberg.org/ebooks/2701")
        addBook("4", "Little Women", "Louisa May Alcott", "Fiction", "The lives of four sisters...", "https://www.gutenberg.org/cache/epub/514/pg514.cover.medium.jpg", 4.7f, 1868, 449, 0, false, "https://www.gutenberg.org/ebooks/514")
        addBook("5", "Jane Eyre", "Charlotte Brontë", "Fiction", "The experiences of its eponymous heroine...", "https://www.gutenberg.org/cache/epub/1260/pg1260.cover.medium.jpg", 4.6f, 1847, 500, 0, false, "https://www.gutenberg.org/ebooks/1260")

        // Fantasy
        addBook("6", "Alice's Adventures in Wonderland", "Lewis Carroll", "Fantasy", "A girl named Alice falls through a rabbit hole...", "https://www.gutenberg.org/cache/epub/11/pg11.cover.medium.jpg", 4.5f, 1865, 192, 38, false, "https://www.gutenberg.org/ebooks/11")
        addBook("7", "Grimms' Fairy Tales", "The Brothers Grimm", "Fantasy", "A collection of German fairy tales...", "https://www.gutenberg.org/cache/epub/2591/pg2591.cover.medium.jpg", 4.3f, 1812, 320, 0, false, "https://www.gutenberg.org/ebooks/2591")
        addBook("8", "The Blue Fairy Book", "Andrew Lang", "Fantasy", "The first in a series of Fairy Books...", "https://www.gutenberg.org/cache/epub/503/pg503.cover.medium.jpg", 4.4f, 1889, 390, 0, false, "https://www.gutenberg.org/ebooks/503")
        addBook("9", "Peter Pan", "J.M. Barrie", "Fantasy", "The boy who wouldn't grow up...", "https://www.gutenberg.org/cache/epub/16/pg16.cover.medium.jpg", 4.6f, 1911, 240, 0, false, "https://www.gutenberg.org/ebooks/16")
        addBook("10", "The King of the Golden River", "John Ruskin", "Fantasy", "A fairy tale set in Stiria...", "https://www.gutenberg.org/cache/epub/701/pg701.cover.medium.jpg", 4.0f, 1841, 64, 0, false, "https://www.gutenberg.org/ebooks/701")

        // Mystery
        addBook("11", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "Mystery & Thriller", "Twelve stories featuring the consulting detective...", "https://www.gutenberg.org/cache/epub/1661/pg1661.cover.medium.jpg", 4.9f, 1892, 307, 0, false, "https://www.gutenberg.org/ebooks/1661")
        addBook("12", "The Mysterious Affair at Styles", "Agatha Christie", "Mystery & Thriller", "The first detective novel by Agatha Christie...", "https://www.gutenberg.org/cache/epub/863/pg863.cover.medium.jpg", 4.7f, 1920, 296, 0, false, "https://www.gutenberg.org/ebooks/863")
        addBook("13", "The Secret Adversary", "Agatha Christie", "Mystery & Thriller", "A detective fiction novel featuring Tommy and Tuppence...", "https://www.gutenberg.org/cache/epub/1155/pg1155.cover.medium.jpg", 4.5f, 1922, 320, 0, false, "https://www.gutenberg.org/ebooks/1155")
        addBook("14", "The Woman in White", "Wilkie Collins", "Mystery & Thriller", "Considered among the first mystery novels...", "https://www.gutenberg.org/cache/epub/583/pg583.cover.medium.jpg", 4.4f, 1859, 648, 0, false, "https://www.gutenberg.org/ebooks/583")
        addBook("15", "The Sign of the Four", "Arthur Conan Doyle", "Mystery & Thriller", "The second novel featuring Sherlock Holmes...", "https://www.gutenberg.org/cache/epub/2097/pg2097.cover.medium.jpg", 4.6f, 1890, 129, 0, false, "https://www.gutenberg.org/ebooks/2097")

        // Science Fiction
        addBook("16", "The Time Machine", "H.G. Wells", "Science Fiction", "A scientist travels into the far future...", "https://www.gutenberg.org/cache/epub/35/pg35.cover.medium.jpg", 4.3f, 1895, 118, 74, false, "https://www.gutenberg.org/ebooks/35")
        addBook("17", "War of the Worlds", "H.G. Wells", "Science Fiction", "An alien invasion of Earth...", "https://www.gutenberg.org/cache/epub/36/pg36.cover.medium.jpg", 4.5f, 1898, 192, 0, false, "https://www.gutenberg.org/ebooks/36")
        addBook("18", "Frankenstein", "Mary Shelley", "Science Fiction", "A scientist creates a sapient creature...", "https://www.gutenberg.org/cache/epub/84/pg84.cover.medium.jpg", 4.7f, 1818, 280, 0, false, "https://www.gutenberg.org/ebooks/84")
        addBook("19", "20,000 Leagues Under the Sea", "Jules Verne", "Science Fiction", "The adventures of Captain Nemo...", "https://www.gutenberg.org/cache/epub/164/pg164.cover.medium.jpg", 4.4f, 1870, 480, 0, false, "https://www.gutenberg.org/ebooks/164")
        addBook("20", "A Journey to the Center of the Earth", "Jules Verne", "Science Fiction", "A descent into the hollow Earth...", "https://www.gutenberg.org/cache/epub/18857/pg18857.cover.medium.jpg", 4.2f, 1864, 320, 0, false, "https://www.gutenberg.org/ebooks/18857")

        // Adding demo "The Hobbit" with custom data for test case 2 & 3
        addBook("hobbit", "The Hobbit", "J.R.R. Tolkien", "Fantasy", "In a hole in the ground there lived a hobbit...", "https://m.media-amazon.com/images/I/710u70S685L.jpg", 4.9f, 1937, 310, 62, true, "https://www.standardebooks.org")

        // Romance
        addBook("21", "Sense and Sensibility", "Jane Austen", "Romance", "The lives and loves of the Dashwood sisters...", "https://www.gutenberg.org/cache/epub/161/pg161.cover.medium.jpg", 4.5f, 1811, 400, 0, false, "https://www.gutenberg.org/ebooks/161")
        addBook("22", "Emma", "Jane Austen", "Romance", "A young woman who loves matchmaking...", "https://www.gutenberg.org/cache/epub/158/pg158.cover.medium.jpg", 4.7f, 1815, 480, 0, false, "https://www.gutenberg.org/ebooks/158")
        addBook("23", "Persuasion", "Jane Austen", "Romance", "Anne Elliot's second chance at love...", "https://www.gutenberg.org/cache/epub/105/pg105.cover.medium.jpg", 4.6f, 1817, 240, 0, false, "https://www.gutenberg.org/ebooks/105")
        addBook("24", "The Age of Innocence", "Edith Wharton", "Romance", "New York high society in the 1870s...", "https://www.gutenberg.org/cache/epub/284/pg284.cover.medium.jpg", 4.3f, 1920, 300, 0, false, "https://www.gutenberg.org/ebooks/284")
        addBook("25", "Wuthering Heights", "Emily Brontë", "Romance", "The intense love between Heathcliff and Catherine...", "https://www.gutenberg.org/cache/epub/768/pg768.cover.medium.jpg", 4.4f, 1847, 400, 0, false, "https://www.gutenberg.org/ebooks/768")

        // Self-Help
        addBook("26", "Meditations", "Marcus Aurelius", "Self-Help", "Personal writings of the Roman Emperor...", "https://www.gutenberg.org/cache/epub/264/pg264.cover.medium.jpg", 4.8f, 180, 200, 0, false, "https://www.gutenberg.org/ebooks/264")
        addBook("27", "As a Man Thinketh", "James Allen", "Self-Help", "A self-help book published in 1903...", "https://www.gutenberg.org/cache/epub/242/pg242.cover.medium.jpg", 4.5f, 1903, 80, 0, false, "https://www.gutenberg.org/ebooks/242")
        addBook("28", "The Art of War", "Sun Tzu", "Self-Help", "Ancient Chinese military treatise...", "https://www.gutenberg.org/cache/epub/132/pg132.cover.medium.jpg", 4.7f, -500, 100, 0, false, "https://www.gutenberg.org/ebooks/132")
        addBook("29", "Siddhartha", "Hermann Hesse", "Self-Help", "A novel about the spiritual journey of a man...", "https://www.gutenberg.org/cache/epub/2500/pg2500.cover.medium.jpg", 4.6f, 1922, 160, 0, false, "https://www.gutenberg.org/ebooks/2500")
        addBook("30", "Walden", "Henry David Thoreau", "Self-Help", "Reflections upon simple living in natural surroundings...", "https://www.gutenberg.org/cache/epub/205/pg205.cover.medium.jpg", 4.2f, 1854, 352, 0, false, "https://www.gutenberg.org/ebooks/205")

        // Technology
        addBook("31", "The Cathedral and the Bazaar", "Eric S. Raymond", "Technology", "Essays on software engineering methods...", "https://upload.wikimedia.org/wikipedia/en/2/29/The_Cathedral_and_the_Bazaar_cover.jpg", 4.8f, 1999, 250, 0, false, "http://www.catb.org/~esr/writings/cathedral-bazaar/")
        addBook("32", "The Soul of a New Machine", "Tracy Kidder", "Technology", "The development of a new minicomputer...", "https://images-na.ssl-images-amazon.com/images/I/51v1zE9L2HL.jpg", 4.6f, 1981, 300, 0, false, "https://archive.org")
        addBook("33", "Hackers", "Steven Levy", "Technology", "Heroes of the Computer Revolution...", "https://images-na.ssl-images-amazon.com/images/I/91t%2BvN7zH8L.jpg", 4.7f, 1984, 460, 0, false, "https://archive.org")
        addBook("34", "The New Turing Omnibus", "A.K. Dewdney", "Technology", "66 Excursions in Computer Science...", "https://images-na.ssl-images-amazon.com/images/I/51r2F7X7KFL.jpg", 4.5f, 1989, 480, 0, false, "https://archive.org")
        addBook("35", "Code: The Hidden Language", "Charles Petzold", "Technology", "The secret language of computer hardware...", "https://images-na.ssl-images-amazon.com/images/I/51-mY9P-XCL.jpg", 4.9f, 1999, 400, 0, false, "https://archive.org")

        // History
        addBook("36", "The History of the Peloponnesian War", "Thucydides", "History", "Historical account of the war...", "https://www.gutenberg.org/cache/epub/7142/pg7142.cover.medium.jpg", 4.4f, -400, 600, 0, false, "https://www.gutenberg.org/ebooks/7142")
        addBook("37", "The Rise and Fall of the Roman Empire", "Edward Gibbon", "History", "Historical work on the Roman Empire...", "https://www.gutenberg.org/cache/epub/890/pg890.cover.medium.jpg", 4.6f, 1776, 1200, 0, false, "https://www.gutenberg.org/ebooks/890")
        addBook("38", "The Prince", "Niccolò Machiavelli", "History", "16th-century political treatise...", "https://www.gutenberg.org/cache/epub/1232/pg1232.cover.medium.jpg", 4.5f, 1532, 100, 0, false, "https://www.gutenberg.org/ebooks/1232")
        addBook("39", "The History of England", "David Hume", "History", "Hume's great work on English history...", "https://www.gutenberg.org/cache/epub/19214/pg19214.cover.medium.jpg", 4.2f, 1754, 800, 0, false, "https://www.gutenberg.org/ebooks/19214")
        addBook("40", "Narrative of the Life of Frederick Douglass", "Frederick Douglass", "History", "Memoir and treatise on abolition...", "https://www.gutenberg.org/cache/epub/202/pg202.cover.medium.jpg", 4.9f, 1845, 160, 0, false, "https://www.gutenberg.org/ebooks/202")

        // Classics
        addBook("41", "Dracula", "Bram Stoker", "Classics", "A Gothic horror novel...", "https://www.gutenberg.org/cache/epub/345/pg345.cover.medium.jpg", 4.7f, 1897, 418, 85, false, "https://www.gutenberg.org/ebooks/345")
        addBook("42", "The Picture of Dorian Gray", "Oscar Wilde", "Classics", "A philosophical novel...", "https://www.gutenberg.org/cache/epub/174/pg174.cover.medium.jpg", 4.6f, 1890, 254, 0, false, "https://www.gutenberg.org/ebooks/174")
        addBook("43", "The Odyssey", "Homer", "Classics", "Ancient Greek epic poem...", "https://www.gutenberg.org/cache/epub/1727/pg1727.cover.medium.jpg", 4.8f, -800, 400, 0, false, "https://www.gutenberg.org/ebooks/1727")
        addBook("44", "Great Expectations", "Charles Dickens", "Classics", "The coming-of-age story of Pip...", "https://www.gutenberg.org/cache/epub/1400/pg1400.cover.medium.jpg", 4.5f, 1861, 500, 0, false, "https://www.gutenberg.org/ebooks/1400")
        addBook("45", "Les Misérables", "Victor Hugo", "Classics", "A novel of law and grace...", "https://www.gutenberg.org/cache/epub/135/pg135.cover.medium.jpg", 4.8f, 1862, 1400, 0, false, "https://www.gutenberg.org/ebooks/135")

        // Academic
        addBook("46", "Relativity: The Special and General Theory", "Albert Einstein", "Academic", "A clear explanation of relativity...", "https://www.gutenberg.org/cache/epub/30155/pg30155.cover.medium.jpg", 4.9f, 1916, 160, 0, false, "https://www.gutenberg.org/ebooks/30155")
        addBook("47", "The Interpretation of Dreams", "Sigmund Freud", "Academic", "Introduction of the theory of the unconscious...", "https://www.gutenberg.org/cache/epub/5781/pg5781.cover.medium.jpg", 4.4f, 1899, 500, 0, false, "https://www.gutenberg.org/ebooks/5781")
        addBook("48", "On the Origin of Species", "Charles Darwin", "Academic", "A work of scientific literature...", "https://www.gutenberg.org/cache/epub/1228/pg1228.cover.medium.jpg", 4.7f, 1859, 500, 0, false, "https://www.gutenberg.org/ebooks/1228")
        addBook("49", "The Wealth of Nations", "Adam Smith", "Academic", "Fundamental work on economics...", "https://www.gutenberg.org/cache/epub/3300/pg3300.cover.medium.jpg", 4.6f, 1776, 900, 0, false, "https://www.gutenberg.org/ebooks/3300")
        addBook("50", "Critique of Pure Reason", "Immanuel Kant", "Academic", "Metaphysics and epistemology...", "https://www.gutenberg.org/cache/epub/4280/pg4280.cover.medium.jpg", 4.5f, 1781, 800, 0, false, "https://www.gutenberg.org/ebooks/4280")
    }

    private fun addBook(id: String, title: String, author: String, genre: String, description: String, coverImage: String, rating: Float, year: Int, pages: Int, progress: Int, isFav: Boolean, url: String) {
        books.add(Book(id, title, author, genre, description, coverImage, rating, year, pages, progress, isFav, url))
    }

    fun getAllBooks() = books
    fun getBooksByGenre(genre: String): List<Book> {
        val lower = genre.lowercase()
        return books.filter { book ->
            val bookGenre = book.genre.lowercase()
            bookGenre == lower ||
            bookGenre.contains(lower) || lower.contains(bookGenre) ||
            (lower.contains("sci") && bookGenre.contains("science")) ||
            (lower.contains("mystery") && bookGenre.contains("mystery")) ||
            (lower.contains("tech") && bookGenre.contains("tech")) ||
            (lower.contains("self") && bookGenre.contains("self"))
        }
    }
    fun getContinueReadingBooks() = books.filter { it.progress > 0 }.sortedByDescending { it.progress }
    fun searchBooks(query: String) = books.filter { 
        it.title.contains(query, true) || it.author.contains(query, true) || it.genre.contains(query, true)
    }
}
