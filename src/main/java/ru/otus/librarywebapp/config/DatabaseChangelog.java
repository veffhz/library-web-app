package ru.otus.librarywebapp.config;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.*;
import org.springframework.data.util.Pair;

import ru.otus.librarywebapp.utils.Helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "addAuthors", author = "veffhz")
    public void insertAuthors(DB db) {
        createDbObject(db, "authors", Arrays.asList(Pair.of("firstName", "Роберт"),
                Pair.of("birthDate", Helper.toDate("01-01-1985")),
                Pair.of("lastName", "Шекли")));
        createDbObject(db, "authors", Arrays.asList(Pair.of("firstName", "Агата"),
                Pair.of("birthDate", Helper.toDate("02-02-1985")),
                Pair.of("lastName", "Кристи")));
    }

    @ChangeSet(order = "002", id = "addGenres", author = "veffhz")
    public void insertGenres(DB db) {
        createDbObject(db, "genres", Collections.singletonList(Pair.of("genreName", "Фантастика")));
        createDbObject(db, "genres", Collections.singletonList(Pair.of("genreName", "Детектив")));
    }

    @ChangeSet(order = "003", id = "addBooks", author = "veffhz")
    public void insertBooks(DB db) {
        DBCollection authorsCollection = db.getCollection("authors");

        DBObject author1 = authorsCollection.findOne(new BasicDBObject("lastName", "Шекли"));
        DBRef refAuthor1 = new DBRef("authors", author1.get("_id"));

        DBCollection genresCollection = db.getCollection("genres");
        DBObject genre1 = genresCollection.findOne(new BasicDBObject("genreName", "Фантастика"));
        DBRef refGenre1 = new DBRef("genres", genre1.get("_id"));

        createDbObject(db, "books", Arrays.asList(Pair.of("bookName", "Избранное"),
                Pair.of("publishDate", Helper.toDate("01-01-1991")), Pair.of("language", "Русский"),
                Pair.of("publishingHouse", "Мир"), Pair.of("city", "Москва"), Pair.of("isbn", "5-03002745-9"),
                Pair.of("author", refAuthor1), Pair.of("genre", refGenre1)));

        DBObject author2 = authorsCollection.findOne(new BasicDBObject("lastName", "Кристи"));
        DBRef refAuthor2 = new DBRef("authors", author2.get("_id"));

        DBObject genre2 = genresCollection.findOne(new BasicDBObject("genreName", "Детектив"));
        DBRef refGenre2 = new DBRef("genres", genre2.get("_id"));

        createDbObject(db, "books", Arrays.asList(Pair.of("bookName", "Десять негритят"),
                Pair.of("publishDate", Helper.toDate("01-01-2017")), Pair.of("language", "Русский"),
                Pair.of("publishingHouse", "Эксмо-Пресс"), Pair.of("city", "Москва"), Pair.of("isbn", "978-5-699-83193-7"),
                Pair.of("author", refAuthor2), Pair.of("genre", refGenre2)));
    }

    @ChangeSet(order = "004", id = "addComments", author = "veffhz")
    public void insertComments(DB db) {
        DBCollection booksCollection = db.getCollection("books");

        DBObject book1 = booksCollection.findOne(new BasicDBObject("bookName", "Избранное"));
        DBRef refBook1 = new DBRef("books", book1.get("_id"));

        createDbObject(db, "comments", Arrays.asList(Pair.of("author", "Me"),
                Pair.of("date", Helper.toDate("01-01-2018")), Pair.of("content", "Очень"),
                Pair.of("book", refBook1)));

        createDbObject(db, "comments", Arrays.asList(Pair.of("author", "Anonymous"),
                Pair.of("date", Helper.toDate("01-01-2019")), Pair.of("content", "Cool!"),
                Pair.of("book", refBook1)));

        DBObject book2 = booksCollection.findOne(new BasicDBObject("bookName", "Десять негритят"));
        DBRef refBook2 = new DBRef("books", book2.get("_id"));

        createDbObject(db, "comments", Arrays.asList(Pair.of("author", "Me"),
                Pair.of("date", Helper.toDate("01-01-2018")), Pair.of("content", "Хорошо"),
                Pair.of("book", refBook2)));

        createDbObject(db, "comments", Arrays.asList(Pair.of("author", "Anonymous"),
                Pair.of("date", Helper.toDate("01-01-2019")), Pair.of("content", "Nice!"),
                Pair.of("book", refBook2)));
    }

    private void createDbObject(DB db, String collectionName, List<Pair<String, Object>> fields) {
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject dbObject = new BasicDBObject();
        fields.forEach(field -> dbObject.append(field.getFirst(), field.getSecond()));
        collection.insert(dbObject);
    }
}
