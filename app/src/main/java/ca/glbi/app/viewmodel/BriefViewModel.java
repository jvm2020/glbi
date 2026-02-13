package ca.glbi.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ca.glbi.app.model.BriefSubmission;

public class BriefViewModel extends AndroidViewModel {
    private final MutableLiveData<BriefSubmission> briefSubmission = new MutableLiveData<>();
    private final MutableLiveData<String> validationError = new MutableLiveData<>();

    public BriefViewModel(@NonNull Application application) {
        super(application);
        briefSubmission.setValue(new BriefSubmission());
    }

    public LiveData<BriefSubmission> getBriefSubmission() {
        return briefSubmission;
    }

    public LiveData<String> getValidationError() {
        return validationError;
    }

    public void updateBrief(BriefSubmission brief) {
        briefSubmission.setValue(brief);
    }

    public boolean validateBrief() {
        BriefSubmission brief = briefSubmission.getValue();
        if (brief == null) {
            validationError.setValue("Brief submission is empty");
            return false;
        }

        if (!brief.isValid()) {
            validationError.setValue("Please fill in all required fields");
            return false;
        }

        if (!brief.isEmailValid()) {
            validationError.setValue("Please enter a valid email address");
            return false;
        }

        validationError.setValue(null);
        return true;
    }
}
