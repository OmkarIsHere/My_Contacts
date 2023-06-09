package com.example.mycontacts;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    String id = "0";
    @Query("select * from contacts order by name asc")
    List<Contact> getAllContact();

    @Query("select * from contacts where id = :id")
    List<Contact> getContactById(int id);

    @Query("update contacts set name= :name, number=:number, numType=:numType  where id = :id")
    int updateContactById(String name, String number, String numType ,int id);

    @Insert
    void addContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Query("delete from contacts where id=:id ")
    void deleteContact(int id);



}
