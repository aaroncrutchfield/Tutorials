package com.demos.tutorial.demologinscreen.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * This is a database holder that acts as an access point to the underlying SQLite
 * database. It uses the DAO (Database Access Object) to issue queries to the SQLite database.
 */
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    // You need an abstract method for each DAO
    public abstract UserDao userDao();

    /**
     * Returns the same instance of the database to prevent having multiple instances opened at the
     * same time
     * @param context
     * @return
     */
    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "usersDatabase")
                    // In general, don't do queries on the Main Thread. We are doing it because our
                    // small database won't effect the UI interactions on the Main Thread and it will
                    // simplify some of our query logic
                    .allowMainThreadQueries()
                    // Deletes all the data in the database whenever the version is changed
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
