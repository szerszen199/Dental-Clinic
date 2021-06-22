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
    public static final String ACCESS_LEVEL_SELF_REVOKE = "access_level_self_revoke";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY = "password_changed_successfully";
    public static final String PASSWORD_RESET_SUCCESSFULLY = "password_reset_successfully";
    public static final String PASSWORD_RESET_MAIL_SENT_SUCCESSFULLY = "password_reset_mail_sent_successfully";
    public static final String ACCOUNT_DARK_MODE_SET_SUCCESSFULLY = "account_dark_mode_set_successfully";
    public static final String LANGUAGE_SET_SUCCESSFULLY = "language_set_successfully";

    public static final String ACCESS_LEVEL_NULL = "access_level_null";
    public static final String DOCUMENTATION_ID_NULL = "documentation_id_null";
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

    public static final String DOCTOR_ID_NULL = "doctor_id_null";
    public static final String APPOINTMENT_DATE_NULL = "appointment_date_null";

    public static final String INVALID_REFRESH_TOKEN = "invalid_refresh_token";
    public static final String PASSWORDS_NOT_DIFFER = "both_passwords_are_same";
    public static final String AUTHENTICATION_FAILURE = "invalid_login_password_combination";
    public static final String LOGIN_FAILURE = "login_failure";
    public static final String CURRENT_PASSWORD_NOT_MATCH = "current_password_does_not_match";
    public static final String ACCOUNT_SET_DARK_MODE_FAILED = "account_set_dark_mode_failed";
    public static final String ACCOUNT_SET_LANGUAGE_FAILED = "account_set_language_failed";
    public static final String ACCESS_LEVEL_NOT_FOUND = "no_such_level_access";
    public static final String ACCOUNT_LOGIN_EMAIL_PESEL_ALREADY_EXISTS = "account_login_email_pesel_already_exists";
    public static final String ACCOUNT_NOT_FOUND = "account_not_found";
    public static final String ACCOUNT_CONFIRMATION_BY_TOKEN_FAILED = "account_confirmation_by_token_failed";
    public static final String PASSWORD_RESET_FAILED = "password_reset_failed";
    public static final String DATABASE_ERROR = "database_error";
    public static final String DATABASE_OPTIMISTIC_LOCK_ERROR = "database_optimistic_lock_error";
    public static final String ACCOUNT_EDIT_FAILED = "account_edit_failed";
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
    public static final String ACCOUNT_MAIL_DELETE_BY_SCHEDULER_SUBJECT = "account_mail_deleted_by_scheduler_subject";
    public static final String ACCOUNT_MAIL_DELETE_BY_SCHEDULER_TEXT = "account_mail_deleted_by_scheduler_text";
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
    public static final String ACCOUNT_MAIL_PASSWORD_BY_ADMIN_CONFIRMATION_SUBJECT = "account_mail_pass_by_admin_confirmation_subject";
    public static final String ACCOUNT_MAIL_PASSWORD_BY_ADMIN_CONFIRMATION_TEXT = "account_mail_pass_by_admin_confirmation_text";
    public static final String ACCOUNT_MAIL_GRANT_ACCESS_LEVEL_SUBJECT = "account_mail_grant_access_level_subject";
    public static final String ACCOUNT_MAIL_GRANT_ACCESS_LEVEL_TEXT = "account_mail_grant_access_level_text";
    public static final String ACCOUNT_MAIL_REVOKE_ACCESS_LEVEL_SUBJECT = "account_mail_revoke_access_level_subject";
    public static final String ACCOUNT_MAIL_REVOKE_ACCESS_LEVEL_TEXT = "account_mail_revoke_access_level_text";

    public static final String ACCOUNT_MAIL_UNLOCK_BY_ADMIN_SUBJECT = "account_mail_unlock_by_admin_subject";
    public static final String ACCOUNT_MAIL_UNLOCK_BY_ADMIN_TEXT = "account_mail_unlock_by_admin_text";
    public static final String BAD_ETAG_VALUE = "bad_etag_value";
    public static final String TRANSACTION_FAILED_ERROR = "transaction_failed_error";
    public static final String TRANSACTION_REPEAT_FAILED_ERROR = "transaction_repeat_failed_error";
    public static final String ACCOUNT_ALREADY_CONFIRMED = "account_already_confirmed";
    public static final String EMAIL_ALREADY_CHANGED = "email_already_changed";
    public static final String PASSWORD_ALREADY_CHANGED = "password_already_changed";
    public static final String ETAG_INVALID = "etag_invalid";
    public static final String LOGIN_NOT_IN_PATTERN = "login_not_in_pattern";
    public static final String LOGIN_INVALID_SIZE = "login_invalid_size";
    public static final String BEAN_VALIDATION_ERROR = "bean_validation_error";
    public static final String FORBIDDEN = "forbidden";
    public static final String INTERNAL_SERVER_ERROR = "internal_server_error";
    public static final String NOT_FOUND = "not_found";

    public static final String DOCUMENTATION_ENTRY_DELETED_SUCCESSFULLY = "documentation_entry_deleted_successfully";
    public static final String DOCUMENTATION_ENTRY_NOT_FOUND = "documentation_entry_not_found";
    public static final String INVALID_DOCTOR_DOCUMENTATION_ENTRY = "invalid_doctor_documentation_entry";
    public static final String REMOVAL_FAILURE_ERROR = "removal_failure_error";
    public static final String DOCUMENTATION_ENTRY_DELETION_UNSUCCESSFUL = "documentation_entry_deletion_unsuccessful";
    public static final String DOCUMENTATION_ENTRY_CREATED_SUCCESSFULLY = "documentation_entry_created_successfully";
    public static final String PATIENT_ID_NULL = "patient_id_null";
    public static final String MEDICAL_DOCUMENTATION_NOT_FOUND = "medical_documentation_not_found";
    public static final String DOCUMENTATION_ENTRY_CREATION_FAILED = "documentation_entry_creation_failed";
    public static final String DOCUMENTATION_ENTRY_CREATED_UNSUCCESSFULLY = "documentation_entry_created_failed";
    public static final String ENCRYPTING_FAILED = "encrypting_failed";
    public static final String MEDICAL_DOCUMENTATION_CREATION_FAILED = "medical_documentation_creation_failed";
    public static final String DOCUMENTATION_ENTRY_ID_NULL = "documentation_entry_id_null";
    public static final String DOCUMENTATION_ENTRY_EDITION_FAILED = "documentation_entry_edition_failed";
    public static final String DOCUMENTATION_ENTRY_EDITED_SUCCESSFULLY = "documentation_entry_edited_successfully";
    public static final String DOCUMENTATION_ENTRY_EDITED_UNSUCCESSFULLY = "documentation_entry_edited_unsuccessfully";
    public static final String MEDICAL_DOCUMENTATION_FETCH_FAILED = "medical_documentation_fetch_failed";
    public static final String PATIENT_SAME_DOCTOR = "patient_same_doctor";
    
    public static final String GET_DOCTORS_AND_RATES_FAILED = "get_doctors_and_rates_failed";

    public static final String APPOINTMENT_SLOT_CREATION_FAILED = "appointment_slot_creation_failed";
    public static final String APPOINTMENT_SLOT_CREATED_SUCCESSFULLY = "appointment_slot_created_successfully";
    public static final String GET_ALL_APPOINTMENTS_FAILED = "get_all_appointments_failed";
    public static final String GET_OWN_APPOINTMENTS_FAILED = "get_own_appointments_failed";
    public static final String INVALID_MEDICATIONS = "invalid_medications";
    public static final String PRESCRIPTION_CREATION_FAILURE = "prescription_creation_failure";
    public static final String PRESCRIPTION_CREATED_SUCCESSFULLY = "prescription_created_successfully";
    public static final String EXPIRATION_DATE_NULL = "expiration_date_null";
    public static final String MEDICATIONS_NULL = "medications_null";
    public static final String APPOINTMENT_EDIT_FAILED = "appointment_edit_failed";
    public static final String APPOINTMENT_EDIT_SUCCESSFUL = "appointment_edit_successful";
    public static final String APPOINTMENT_ID_NULL = "appointment_id_null";
    public static final String APPOINTMENT_NOT_FOUND = "appointment_not_found";
    public static final String NOT_PATIENT_OR_INACTIVE = "not_patient_or_inactive";
    public static final String PRESCRIPTION_EDIT_FAILED = "prescription_edit_failed";
    public static final String PRESCRIPTION_EDITED_SUCCESSFULLY = "prescription_edited_successfully";
    public static final String PRESCRIPTION_ID_NULL = "prescription_id_null";
    public static final String PRESCRIPTION_MEDICATIONS_NULL = "prescription_medications_null";
    public static final String PRESCRIPTION_NOT_FOUND = "prescription_not_found";
    public static final String GET_ACTIVE_PATIENTS_FAILED = "get_active_patients_failed";
    public static final String GET_ALL_SCHEDULED_APPOINTMENTS_FAILED = "get_all_scheduled_appointments_failed";
    public static final String NO_ACCESS_LEVEL = "no_access_level";
}
