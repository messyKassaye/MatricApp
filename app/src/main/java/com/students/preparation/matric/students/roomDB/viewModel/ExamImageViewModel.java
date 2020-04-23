package com.students.preparation.matric.students.roomDB.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.students.preparation.matric.students.roomDB.entity.ExamQuestionImage;
import com.students.preparation.matric.students.roomDB.repository.ExamImageRepository;

import java.util.List;

public class ExamImageViewModel extends AndroidViewModel {


    private ExamImageRepository repository;

    public ExamImageViewModel(@NonNull Application application) {
        super(application);

        repository = new ExamImageRepository(application);
    }

    public LiveData<List<ExamQuestionImage>> show(String questionName){
        return repository.show(questionName);
    }

}
