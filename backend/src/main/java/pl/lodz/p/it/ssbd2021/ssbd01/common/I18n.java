package pl.lodz.p.it.ssbd2021.ssbd01.common;

/**
 * Klasa odpowiadająca za przechowywanie ciągów znakowych oraz internacjonalizację komunikatów.
 */
public class I18n {
    public static final String PATIENT = "level.patient";
    public static final String ADMIN = "level.administrator";
    public static final String RECEPTIONIST = "level.receptionist";
    public static final String DOCTOR = "level.doctor";

    public static final String ACCOUNT_CREATION_FAILED = "account_creation_failed";
    public static final String ACCOUNT_CREATED_SUCCESSFULLY = "account_created_successfully";
    public static final String ACCOUNT_GET_LOGGED_IN_FAILED = "account_get_logged_in_failed";
    public static final String ACCOUNT_GET_WITH_LOGIN_FAILED = "account_get_with_login_failed";
    public static final String ACCOUNT_GET_ALL_ACCOUNTS_FAILED = "account_get_all_accounts_failed";
    public static final String PASSWORD_CHANGE_FAILED = "password_change_failed";
    public static final String ACCOUNT_CONFIRMED_SUCCESSFULLY = "account_confirmed_successfully";
    public static final String ACCOUNT_EDITED_SUCCESSFULLY = "account_edited_successfully";
    public static final String EMAIL_CONFIRMED_SUCCESSFULLY = "email_confirmed_successfully";
    public static final String EMAIL_CONFIRMATION_FAILED = "email_confirmation_failed";
    public static final String CONFIRM_BY_MAIL_SUCCESSFULLY = "confirm_by_mail_successfully";
    public static final String CONFIRM_BY_MAIL_FAILED = "confirm_by_mail_failed";
    public static final String ACCOUNT_LOCKED_SUCCESSFULLY = "account_locked_successfully";
    public static final String ACCOUNT_LOCKED_FAILED = "account_locked_failed";
    public static final String ACCOUNT_UNLOCKED_FAILED = "account_unlocked_failed";
    public static final String ACCOUNT_UNLOCKED_SUCCESSFULLY = "account_unlocked_successfully";
    public static final String ACCESS_LEVEL_ADDED_SUCCESSFULLY = "access_level_added_successfully";
    public static final String ACCESS_LEVEL_ADD_FAILED = "access_level_add_failed";
    public static final String ACCESS_LEVEL_REVOKED_SUCCESSFULLY = "access_level_revoked_successfully";
    public static final String ACCESS_LEVEL_REVOKE_FAILED = "access_level_revoke_failed";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY = "password_changed_successfully";
    public static final String PASSWORD_RESET_SUCCESSFULLY = "password_reset_successfully";
    public static final String PASSWORD_RESET_MAIL_SENT_SUCCESSFULLY = "password_reset_mail_sent_successfully";
    public static final String ACCOUNT_DARK_MODE_SET_SUCCESSFULLY = "account_dark_mode_set_successfully";
    public static final String LANGUAGE_SET_SUCCESSFULLY = "language_set_successfully";

    public static final String ACCESS_LEVEL_NULL = "access_level_null";
    public static final String ACCESS_LEVEL_INVALID_SIZE = "access_level_invalid_size";
    public static final String ACCESS_LEVEL_INVALID_LEVEL = "access_level_invalid_level";

    public static final String LOGIN_NULL = "login_null";
    public static final String UPDATE_ACCOUNT_AFTER_UNSUCCESSFUL_LOGIN = "update_after_unsuccessful_login";
    public static final String UPDATE_ACCOUNT_AFTER_SUCCESSFUL_LOGIN = "update_after_successful_login";

    public static final String PASSWORD_NULL = "password_null";
    public static final String PASSWORD_INVALID_SIZE = "password_invalid_size";

    public static final String TOKEN_NULL = "TOKEN_NULL";

    public static final String EMAIL_NULL = "email_null";
    public static final String EMAIL_INVALID_SIZE = "email_invalid_size";
    public static final String NOT_AN_EMAIL = "not_an_email";

    public static final String FIRST_NAME_NULL = "first_name_null";
    public static final String FIRST_NAME_INVALID_SIZE = "first_name_invalid_size";

    public static final String LAST_NAME_NULL = "last_name_null";
    public static final String LAST_NAME_INVALID_SIZE = "last_name_invalid_size";

    public static final String PHONE_NUMBER_INVALID_SIZE = "phone_number_invalid_size";

    public static final String PESEL_INVALID_SIZE = "pesel_invalid_size";

    public static final String LANGUAGE_NULL = "language_null";
    public static final String LANGUAGE_NOT_IN_PATTERN = "language_not_in_pattern";

    public static final String VERSION_NULL = "version_null";

    public static final String VERSION_MISMATCH = "version_mismatch";

    public static final String DARK_MODE_NULL = "dark_mode_null";

    public static final String INVALID_REFRESH_TOKEN = "invalid_refresh_token";
    public static final String PASSWORDS_NOT_DIFFER = "both_passwords_are_same";
    public static final String AUTHENTICATION_FAILURE = "invalid_login_password_combination";
    public static final String LOGIN_FAILURE = "login_failure";
    public static final String PASSWORD_TOO_SHORT = "password_too_short";
    public static final String NEW_PASSWORDS_NOT_MATCH = "new_passwords_do_not_match";
    public static final String CURRENT_PASSWORD_NOT_MATCH = "current_password_does_not_match";
    public static final String ACCOUNT_SET_DARK_MODE_FAILED = "account_set_dark_mode_failed";
    public static final String ACCOUNT_SET_LANGUAGE_FAILED = "account_set_language_failed";
    public static final String ACCESS_LEVEL_NOT_FOUND = "no_such_level_access";
    public static final String ACCESS_LEVEL_ALREADY_ASSIGNED = "access_level_already_assigned";
    public static final String ACCOUNT_LOGIN_ALREADY_EXISTS = "account_login_already_exists";
    public static final String ACCOUNT_EMAIL_ALREADY_EXISTS = "account_email_already_exists";
    public static final String ACCOUNT_LOGIN_EMAIL_PESEL_ALREADY_EXISTS = "account_login_email_pesel_already_exists";
    public static final String ACCOUNT_IS_BLOCKED = "account_is_blocked";
    public static final String ACCOUNT_NOT_FOUND = "account_not_found";
    public static final String ACCOUNT_CONFIRMATION_BY_TOKEN_FAILED = "account_confirmation_by_token_failed";
    public static final String PASSWORD_RESET_FAILED = "password_reset_failed";
    public static final String MAIL_CONFIRMATION_PARSING_ERROR = "mail_confirmation_parsing_error";
    public static final String DATABASE_ERROR = "database_error";
    public static final String DATABASE_OPTIMISTIC_LOCK_ERROR = "database_optimistic_lock_error";
    public static final String ACCOUNT_EDIT_VALIDATION_ERROR = "account_edit_validation_error";
    public static final String ACCOUNT_EDIT_FAILED = "account_edit_failed";
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
    public static final String EDIT_ACCOUNT_MAIL = "edit_account_mail";
    public static final String ACCOUNT_MAIL_CHANGE_CONFIRM_TEXT = "account_mail_change_confirm_text";
    public static final String ACCOUNT_MAIL_CHANGE_CONFIRM_BUTTON = "account_mail_change_confirm_button";
    public static final String ACCOUNT_MAIL_LOCK_BY_ADMIN_SUBJECT = "account_mail_lock_by_admin_subject";
    public static final String ACCOUNT_MAIL_LOCK_BY_ADMIN_TEXT = "account_mail_lock_by_admin_text";
    public static final String ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_SUBJECT = "account_mail_lock_by_unsuccessful_login_subject";
    public static final String ACCOUNT_MAIL_LOCK_BY_UNSUCCESSFUL_LOGIN_TEXT = "account_mail_lock_by_unsuccessful_login_text";
    public static final String ACCOUNT_MAIL_LOGIN_SUBJECT = "account_mail_login_subject";
    public static final String ACCOUNT_MAIL_LOGIN_TEXT = "account_mail_login_text";
    public static final String ACCOUNT_MAIL_GENERATED_PASSWORD_SUBJECT = "account_mail_generated_password_subject";
    public static final String ACCOUNT_MAIL_GENERATED_PASSWORD_TEXT = "account_mail_generated_password_text";
    public static final String ACCOUNT_MAIL_SCHEDULER_LOCK_SUBJECT = "account_mail_scheduler_lock_subject";
    public static final String ACCOUNT_MAIL_SCHEDULER_LOCK_TEXT = "account_mail_scheduler_lock_text";
    public static final String ACCOUNT_MAIL_SCHEDULER_LOCK_BUTTON = "account_mail_scheduler_lock_button";
    public static final String PASSWORD_RESET_MAIL_SENT_ERROR = "password_reset_mail_sent_error";
    public static final String ACCOUNT_MAIL_PASSWORD_CONFIRMATION_SUBJECT = "account_mail_pass_confirmation_subject";
    public static final String ACCOUNT_MAIL_PASSWORD_CONFIRMATION_TEXT = "account_mail_pass_confirmation_text";

    public static final String ACCOUNT_MAIL_UNLOCK_BY_ADMIN_SUBJECT = "account_mail_unlock_by_admin_subject";
    public static final String ACCOUNT_MAIL_UNLOCK_BY_ADMIN_TEXT = "account_mail_unlock_by_admin_text";
    public static final String BAD_ETAG_VALUE = "bad_etag_value";
    public static final String TRANSACTION_FAILED_ERROR = "transaction_failed_error";
}
