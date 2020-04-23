package com.students.preparation.matric.students.roomDB.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.students.preparation.matric.students.roomDB.entity.Notes;

import java.util.List;

@Dao
public interface MyNotesDAO {

    @Insert
    public void Store(Notes notes);

    @Query("select * from mynotes")
   public List<Notes> index();

}
