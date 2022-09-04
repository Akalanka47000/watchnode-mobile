package app.watchnode.ui.register;

import androidx.annotation.Nullable;

class RegisterFormState {
    @Nullable
    private final Integer nameError;
    @Nullable
    private final Integer emailError;
    @Nullable
    private final Integer passwordError;
    private final boolean isDataValid;

    RegisterFormState(@Nullable Integer nameError, @Nullable Integer emailError, @Nullable Integer passwordError) {
        this.nameError = nameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.nameError = null;
        this.emailError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getNameError() {
        return nameError;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}