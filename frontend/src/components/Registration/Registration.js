import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./Registration.css";
import {registrationRequest} from "./RegistrationRequest";
import {Col} from "react-bootstrap";
import Dropdown from 'react-bootstrap/Dropdown';
import FlagIcon from './FlagIcon.js';

import {useTranslation} from "react-i18next";

export default function Registration() {
    const [login, setLogin] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [repeatedPassword, setRepeatedPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const {t} = useTranslation();
    const [phoneNumber, setPhoneNumber] = useState(null);
    const [pesel, setPesel] = useState(null);
    const [selectedLanguage, setSelectedLanguage] = useState("");

    const [languages] = useState([
        {language: "pl", code: 'pl', title: "Polish"},
        {language: "en", code: 'gb', title: "English"}
    ]);
    const [toggleContents, setToggleContents] = useState(null);

    const [errors, setErrors] = useState({})

    const loginRegex = new RegExp(/^[a-z0-9]+$/i);
    const emailRegex = new RegExp(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
    const phoneNumberRegex = new RegExp(/^\d+$/);
    const peselRegex = new RegExp(/^\d+$/);

    const setValid = (field) => {
        if (!!errors[field]) setErrors({
            ...errors,
            [field]: null
        })
    }

    const findFormErrors = () => {

        const newErrors = {}

        function findLoginErrors() {
            if (login === '') {
                newErrors.login = "Login blank error";
                return;
            }

            if (!loginRegex.test(login)) {
                newErrors.login = "Login format error";
                return;
            }

            if (login.length < 3) {
                newErrors.login = "Login too short error";
                return;
            }

            if (login.length > 60) {
                newErrors.login = "Login too long error";
            }
        }

        function findEmailErrors() {
            if (email === '') {
                newErrors.email = "Email blank error";
                return;
            }

            if (!emailRegex.test(email.toLowerCase())) {
                newErrors.email = "Email format error";
                return;
            }

            if (email.length < 4) {
                newErrors.email = "Email too short error";
                return;
            }

            if (email.length > 100) {
                newErrors.email = "Email too long error";
            }
        }

        function findPasswordErrors(pwd) {
            if (password === '') {
                newErrors.pwd = "Password blank error";
                return;
            }

            if (password.length < 8) {
                newErrors.pwd = "Password too short error";
                return;
            }

            if (password !== repeatedPassword) {
                newErrors.pwd = "Passwords mismatch error";
            }
        }

        function findFirstNameErrors() {
            if (firstName === '') {
                newErrors.firstName = "First name blank error";
                return;
            }

            if (firstName.length > 50) {
                newErrors.firstName = "First name too long error";
            }
        }

        function findLastNameErrors() {
            if (lastName === '') {
                newErrors.lastName = "Last name blank error";
                return;
            }

            if (lastName.length > 80) {
                newErrors.lastName = "Last name too long error";
            }
        }

        function findPhoneNumberErrors() {
            if (phoneNumber === null || phoneNumber === '') {
                setPhoneNumber(null);
                return;
            }

            if (!phoneNumberRegex.test(String(phoneNumber))) {
                newErrors.phoneNumber = "Phone number format error";
                return;
            }

            if (phoneNumber.length < 9) {
                newErrors.phoneNumber = "Phone number too short error";
                return;
            }

            if (phoneNumber.length > 15) {
                newErrors.phoneNumber = "Phone number too long error";
            }
        }

        function findPeselErrors() {
            if (pesel === null || pesel === '') {
                setPesel(null)
                return;
            }

            if (!peselRegex.test(String(pesel))) {
                newErrors.pesel = "Pesel format error";
                return;
            }

            if (pesel.length !== 11) {
                newErrors.pesel = "Pesel length error";
                return;
            }

            let weight = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
            let sum = 0;
            let controlNumber = parseInt(pesel.substring(10, 11));

            for (let i = 0; i < weight.length; i++) {
                sum += (parseInt(pesel.substring(i, i + 1)) * weight[i]);
            }
            sum = sum % 10;

            if ((10 - sum) % 10 !== controlNumber) {
                newErrors.pesel = "Pesel control digit error";
            }
        }

        function findLanguageErrors() {
            if (selectedLanguage == null || selectedLanguage === '') {
                newErrors.language = "Language not selected error";
            }
        }

        findLoginErrors();
        findEmailErrors();
        findPasswordErrors(password);
        findPasswordErrors(repeatedPassword);
        findFirstNameErrors();
        findLastNameErrors();
        findPhoneNumberErrors();
        findPeselErrors();
        findLanguageErrors();

        return newErrors;
    }

    function handleSubmit(event) {
        event.preventDefault();

        const newErrors = findFormErrors();

        if (Object.keys(newErrors).length > 0) {
            // We got errors!
            setErrors(newErrors);
        } else {
            registrationRequest(login, email, password, firstName, lastName, phoneNumber, pesel, selectedLanguage);
        }
    }

    return (
        <div className="Registration">
            <Form noValidate onSubmit={handleSubmit}>
                <Form.Row>
                    <Form.Group as={Col} size="lg" controlId="login">
                        <Form.Label className="required">{t("UserLogin")}</Form.Label>
                        <Form.Control
                            required
                            placeholder={t("UserLogin")}
                            autoFocus
                            type="login"
                            value={login}
                            onChange={e => {
                                setLogin(e.target.value);
                                setValid('login');
                            }}
                            isInvalid={!!errors.login}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(errors.login)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group as={Col} size="lg" controlId="email">
                        <Form.Label className="required">{t("Email")}</Form.Label>
                        <Form.Control
                            required
                            placeholder={t("Email")}
                            type="email"
                            value={email}
                            onChange={e => {
                                setEmail(e.target.value);
                                setValid('email');
                            }}
                            isInvalid={!!errors.email}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(errors.email)}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Form.Row>
                    <Form.Group as={Col} size="lg" controlId="password">
                        <Form.Label className="required">{t("Password")}</Form.Label>
                        <Form.Control
                            required
                            placeholder={t("Password")}
                            type="password"
                            value={password}
                            onChange={e => {
                                setPassword(e.target.value);
                                setValid('password');
                            }}
                            isInvalid={!!errors.password}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(errors.password)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group as={Col} size="lg" controlId="repeatedPassword">
                        <Form.Label className="required">{t("Repeat new password")}</Form.Label>
                        <Form.Control
                            required
                            placeholder={t("Repeat new password")}
                            type="password"
                            value={repeatedPassword}
                            onChange={e => {
                                setRepeatedPassword(e.target.value);
                                setValid('repeatedPassword');
                            }}
                            isInvalid={!!errors.repeatedPassword}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(errors.repeatedPassword)}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Form.Row>
                    <Form.Group as={Col} size="lg" controlId="firstName">
                        <Form.Label className="required">{t("First Name")}</Form.Label>
                        <Form.Control
                            required
                            placeholder={t("First Name")}
                            type="text"
                            value={firstName}
                            onChange={e => {
                                setFirstName(e.target.value);
                                setValid('firstName');
                            }}
                            isInvalid={!!errors.firstName}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(errors.firstName)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group as={Col} size="lg" controlId="lastName">
                        <Form.Label className="required">{t("Last Name")}</Form.Label>
                        <Form.Control
                            required
                            placeholder={t("Last Name")}
                            type="text"
                            value={lastName}
                            onChange={e => {
                                setLastName(e.target.value);
                                setValid('lastName');
                            }}
                            isInvalid={!!errors.lastName}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(errors.lastName)}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Form.Row>
                    <Form.Group as={Col} size="lg" controlId="phoneNumber">
                        <Form.Label>{t("Phone Number")}</Form.Label>
                        <Form.Control
                            placeholder={t("Phone Number")}
                            type="text"
                            value={phoneNumber}
                            onChange={e => {
                                setPhoneNumber(e.target.value);
                                setValid('phoneNumber');
                            }}
                            isInvalid={!!errors.phoneNumber}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(errors.phoneNumber)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group as={Col} size="lg" controlId="pesel">
                        <Form.Label>{t("Pesel")}</Form.Label>
                        <Form.Control
                            placeholder={t("Pesel")}
                            type="text"
                            value={pesel}
                            onChange={e => {
                                setPesel(e.target.value);
                                setValid('pesel');
                            }}
                            isInvalid={!!errors.pesel}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(errors.pesel)}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Form.Label className="required">{t("Language")}</Form.Label>
                <Form.Row>
                    <Form.Group as={Col} size="lg" controlId="language">
                        <Dropdown
                            onSelect={eventKey => {
                                const {language, code, title} = languages.find(({language}) => eventKey === language);
                                setSelectedLanguage(language);
                                setToggleContents(<><FlagIcon code={code}/>{t(title)}</>);
                                setValid('language');
                            }}>
                            <Dropdown.Toggle variant="outline-primary" id="dropdown-flags" className="text-left"
                                             style={{width: 200}}>
                                {toggleContents != null ? toggleContents : t("Select language")}
                            </Dropdown.Toggle>

                            <Dropdown.Menu>
                                {languages.map(({language, code, title}) => (
                                    <Dropdown.Item key={language} eventKey={language}>
                                        <FlagIcon code={code}/>{t(title)}
                                    </Dropdown.Item>
                                ))}
                            </Dropdown.Menu>
                        </Dropdown>
                        <Form.Control.Feedback type="invalid">
                            {t(errors.language)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Button size={"lg"} type="submit">
                        {t("Register")}
                    </Button>
                </Form.Row>
            </Form>
        </div>
    );
}