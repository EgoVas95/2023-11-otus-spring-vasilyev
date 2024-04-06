package ru.otus.hw.testchangelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;

@ChangeLog(order = "001")
public class InitMongoDBClearChangeLog {
    @ChangeSet(order = "000", id = "dropDb", author = "e.vasilyev", runAlways = true)
    public void dropDb(MongoDatabase database) {
        database.drop();
    }
}
