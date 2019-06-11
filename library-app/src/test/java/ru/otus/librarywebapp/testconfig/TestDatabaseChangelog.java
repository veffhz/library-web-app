package ru.otus.librarywebapp.testconfig;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.*;
import org.springframework.data.util.Pair;
import ru.otus.librarywebapp.utils.Helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ChangeLog
public class TestDatabaseChangelog {

    @ChangeSet(order = "001", id = "addAuthors", author = "veffhz")
    public void insertAuthors(DB db) {
        createDbObject(db, "authors", Arrays.asList(
                Pair.of("_id", "5"),
                Pair.of("firstName", "FirstName"),
                Pair.of("lastName", "LastName")));
        createDbObject(db, "authors", Arrays.asList(
                Pair.of("_id", "7"),
                Pair.of("firstName", "FirstName7"),
                Pair.of("lastName", "LastName7")));
        createDbObject(db, "authors", Arrays.asList(
                Pair.of("_id", "9"),
                Pair.of("firstName", "FirstName9"),
                Pair.of("lastName", "LastName9")));
    }

    @ChangeSet(order = "002", id = "addGenres", author = "veffhz")
    public void insertGenres(DB db) {
        createDbObject(db, "genres", Collections.singletonList(Pair.of("genreName", "Genre")));
        createDbObject(db, "genres", Collections.singletonList(Pair.of("genreName", "Genre5")));
        createDbObject(db, "genres", Collections.singletonList(Pair.of("genreName", "Genre7")));
        createDbObject(db, "genres", Collections.singletonList(Pair.of("genreName", "Genre9")));
    }

    @ChangeSet(order = "003", id = "addBooks", author = "veffhz")
    public void insertBooks(DB db) {
        DBCollection authorsCollection = db.getCollection("authors");

        DBObject author1 = authorsCollection.findOne(new BasicDBObject("lastName", "LastName"));
        DBRef refAuthor1 = new DBRef("authors", author1.get("_id"));

        DBCollection genresCollection = db.getCollection("genres");
        DBObject genre1 = genresCollection.findOne(new BasicDBObject("genreName", "Genre"));
        DBRef refGenre1 = new DBRef("genres", genre1.get("_id"));

        createDbObject(db, "books", Arrays.asList(Pair.of("bookName", "Best"),
                Pair.of("publishDate", Helper.toDate("1991-01-01")), Pair.of("language", "Русский"),
                Pair.of("publishingHouse", "Мир"), Pair.of("city", "Москва"), Pair.of("isbn", "5-03002745-9"),
                Pair.of("author", refAuthor1), Pair.of("genre", refGenre1)));

        DBObject author2 = authorsCollection.findOne(new BasicDBObject("lastName", "LastName7"));
        DBRef refAuthor2 = new DBRef("authors", author2.get("_id"));

        DBObject genre2 = genresCollection.findOne(new BasicDBObject("genreName", "Genre5"));
        DBRef refGenre2 = new DBRef("genres", genre2.get("_id"));

        createDbObject(db, "books", Arrays.asList(Pair.of("bookName", "Best7"),
                Pair.of("publishDate", Helper.toDate("2017-01-01")), Pair.of("language", "Русский"),
                Pair.of("publishingHouse", "Эксмо-Пресс"), Pair.of("city", "Москва"), Pair.of("isbn", "978-5-699-83193-7"),
                Pair.of("author", refAuthor2), Pair.of("genre", refGenre2)));

        createDbObject(db, "books", Arrays.asList(Pair.of("bookName", "Best9"),
                Pair.of("publishDate", Helper.toDate("2017-01-01")), Pair.of("language", "Русский"),
                Pair.of("publishingHouse", "Эксмо-Пресс"), Pair.of("city", "Москва"), Pair.of("isbn", "978-5-699-83193-7"),
                Pair.of("author", refAuthor2), Pair.of("genre", refGenre2)));
    }

    private void createDbObject(DB db, String collectionName, List<Pair<String, Object>> fields) {
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject dbObject = new BasicDBObject();
        fields.forEach(field -> dbObject.append(field.getFirst(), field.getSecond()));
        collection.insert(dbObject);
    }
}
