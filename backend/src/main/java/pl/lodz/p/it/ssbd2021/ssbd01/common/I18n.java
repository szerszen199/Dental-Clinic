package pl.lodz.p.it.ssbd2021.ssbd01.common;

/**
 * Klasa odpowiadająca za przechowywanie ciągów znakowych oraz internacjonalizację komunikatów.
 */
public class I18n {
    public static final String PATIENT = "level.patient";
    public static final String ADMIN = "level.administrator";
    public static final String RECEPTIONIST = "level.receptionist";
    public static final String DOCTOR = "level.doctor";

    public static final String INVALID_REFRESH_TOKEN = "invalid_refresh_token";
    public static final String PASSWORDS_NOT_DIFFER = "both_passwords_are_same";
    public static final String AUTHENTICATION_FAILURE = "invalid_login_password_combination";
    public static final String PASSWORD_TOO_SHORT = "password_too_short";
    public static final String NEW_PASSWORDS_NOT_MATCH = "new_passwords_do_not_match";
    public static final String CURRENT_PASSWORD_NOT_MATCH = "current_password_does_not_match";
    public static final String ACCESS_LEVEL_NOT_FOUND = "no_such_level_access";
    public static final String ACCESS_LEVEL_ALREADY_ASSIGNED = "access_level_already_assigned";
    public static final String ACCOUNT_LOGIN_ALREADY_EXISTS = "account_login_already_exists";
    public static final String ACCOUNT_EMAIL_ALREADY_EXISTS = "account_email_already_exists";
    public static final String ACCOUNT_IS_BLOCKED = "account_is_blocked";
    public static final String ACCOUNT_NOT_FOUND = "account_not_found";
    public static final String MAIL_CONFIRMATION_PARSING_ERROR = "mail_confirmation_parsing_error";
    public static final String DATABASE_ERROR = "database_error";
    public static final String DATABASE_OPTIMISTIC_LOCK_ERROR = "database_optimistic_lock_error";
    public static final String ACCOUNT_EDIT_VALIDATION_ERROR = "account_edit_validation_error";
    public static final String LOAD_PROPERTIES_ERROR = "load_properties_error";
    public static final String INVALID_CONFIRMATION_TOKEN = "invalid_registration_confirmation_token";
    public static final String MAIL_ACTIVATION_LINK_SEND_ERROR = "mail_activation_link_send_error";
    public static final String MAIL_ACCOUNT_LOCK_SEND_ERROR = "mail_account_lock_send_error";
    public static final String MAIL_ACTIVATION_CONFIRMATION_SEND_ERROR = "mail_activation_confirmation_send_error";


    public static final String ACCOUNT_MAIL_ACTIVATE_SUBJECT = "account_mail_active_subject";
    public static final String ACCOUNT_MAIL_ACTIVATE_TEXT = "account_mail_active_text";
    public static final String ACCOUNT_MAIL_ACTIVATE_BUTTON = "account_mail_active_button";
    public static final String ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_SUBJECT = "account_mail_activation_confirmation_subject";
    public static final String ACCOUNT_MAIL_ACTIVATION_CONFIRMATION_TEXT = "account_mail_activation_confirmation_text";
    public static final String ACCOUNT_MAIL_CHANGE_CONFIRM_SUBJECT = "account_mail_change_confirm_subject";
    public static final String ACCOUNT_MAIL_CHANGE_CONFIRM_TEXT = "account_mail_change_confirm_text";
    public static final String ACCOUNT_MAIL_CHANGE_CONFIRM_BUTTON = "account_mail_change_confirm_button";
    public static final String ACCOUNT_MAIL_LOCK_BY_ADMIN_SUBJECT = "account_mail_lock_by_admin_subject";
    public static final String ACCOUNT_MAIL_LOCK_BY_ADMIN_TEXT = "account_mail_lock_by_admin_text";
    public static final String ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_SUBJECT = "account_mail_lock_by_unsuccessful_login_subject";
    public static final String ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_TEXT = "account_mail_lock_by_unsuccessful_login_text";
    public static final String ACCOUNT_MAIL_LOGIN_SUBJECT = "account_mail_login_subject";
    public static final String ACCOUNT_MAIL_LOGIN_TEXT = "account_mail_login_text";

    public static final String ACCOUNT_MAIL_UNLOCK_BY_ADMIN_SUBJECT = "account_mail_unlock_by_admin_subject";
    public static final String ACCOUNT_MAIL_UNLOCK_BY_ADMIN_TEXT = "account_mail_unlock_by_admin_text";
}
