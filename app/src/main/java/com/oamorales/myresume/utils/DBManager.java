package com.oamorales.myresume.utils;

import android.content.Context;
import android.widget.Toast;

import com.oamorales.myresume.models.Degree;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public abstract class DBManager {

    public static final int SORT_ASCENDING = 0;
    public static final int SORT_DESCENDING = 1;

    public static void insert(Degree degree, Context context){
        Realm realm = Realm.getDefaultInstance();
        try{
            realm.beginTransaction();
            realm.copyToRealm(degree);
            realm.commitTransaction();
            realm.close();
        }catch (Error error){
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            realm.close();
        }
    }

    public static void update(Degree oldDegree, int id, Context context){
        Realm realm = Realm.getDefaultInstance();
        String logo = oldDegree.getImageLogo();
        String tittle = oldDegree.getDegreeTittle();
        String university = oldDegree.getUniversity();
        String discipline = oldDegree.getDiscipline();
        int yearBegin = oldDegree.getYearBegin();
        int yearEnd = oldDegree.getYearEnd();
        float average = oldDegree.getGradeAverage();
        Degree degree = realm.where(Degree.class).equalTo("id", id).findFirst();
        try{
            realm.beginTransaction();
            assert degree != null;
            degree.setImageLogo(logo);
            degree.setDegreeTittle(tittle);
            degree.setUniversity(university);
            degree.setDiscipline(discipline);
            degree.setYearBegin(yearBegin);
            degree.setYearEnd(yearEnd);
            degree.setGradeAverage(average);
            realm.copyToRealmOrUpdate(degree);
            realm.commitTransaction();
            realm.close();
        }catch (Error error){
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            realm.close();
        }
    }

    public static void delete(Degree degree, Context context){
        Realm realm = Realm.getDefaultInstance();
        try{
            realm.beginTransaction();
            degree.deleteFromRealm();
            realm.commitTransaction();
            realm.close();
        }catch (Error error){
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            realm.close();
        }
    }

    public static Degree getDegreeById(int id){
        Realm realm = Realm.getDefaultInstance();
        Degree degree = realm.where(Degree.class).equalTo("id", id).findFirst();
        realm.close();
        return  degree;
    }

    public static RealmResults<Degree> getAllDegrees(String sortBy, int sortType){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Degree> degrees;
        if (sortType == SORT_DESCENDING){
            degrees = realm.where(Degree.class).findAll().sort(sortBy, Sort.DESCENDING);
            realm.close();
            return degrees;
        }
        degrees = realm.where(Degree.class).findAll().sort(sortBy, Sort.ASCENDING);
        realm.close();
        return degrees;
    }

}