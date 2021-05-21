import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./Registration.css";
import {registrationRequest} from "./RegistrationRequest";
import {Col} from "react-bootstrap";

export default function Registration() {
    const [login, setLogin] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [repeatedPassword, setRepeatedPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState(null);
    const [pesel, setPesel] = useState(null);

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
                newErrors.login = "Login cannot be blank!";
                return;
            }

            if (!loginRegex.test(login)) {
                newErrors.login = "Login can contain letters and numbers, and must not contain spaces, special characters or emoji."
                return;
            }

            if (login.length < 3) {
                newErrors.login = "Login too short! Must be at least 3 characters long.";
                return;
            }

            if (login.length > 60) {
                newErrors.login = "Login too long! Cannot be more than 60 characters long.";
            }
        }

        function findEmailErrors() {
            if (email === '') {
                newErrors.email = "Email cannot be blank!";
                return;
            }

            if (!emailRegex.test(email.toLowerCase())) {
                newErrors.email = "Email format is invalid!"
                return;
            }

            if (email.length < 4) {
                newErrors.email = "Email too short! Must be at least 4 characters long.";
                return;
            }

            if (email.length > 100) {
                newErrors.email = "Email too long! Cannot be more than 100 characters long.";
            }
        }

        function findPasswordErrors() {
            if (password === '') {
                newErrors.password = "Password cannot be blank!";
                return;
            }

            if (password.length < 8) {
                newErrors.password = "Password too short! Must be at least 8 characters long.";
                return;
            }

            if (password !== repeatedPassword) {
                newErrors.password = "Passwords mismatch!"
            }
        }

        function findRepeatedPasswordErrors() {
            if (repeatedPassword === '') {
                newErrors.repeatedPassword = "Password cannot be blank!";
                return;
            }

            if (repeatedPassword.length < 8) {
                newErrors.repeatedPassword = "Password too short! Must be at least 8 characters long.";
                return;
            }

            if (password !== repeatedPassword) {
                newErrors.repeatedPassword = "Passwords mismatch!"
            }
        }

        function findFirstNameErrors() {
            if (firstName === '') {
                newErrors.firstName = "First name cannot be blank!";
                return;
            }

            if (firstName.length > 50) {
                newErrors.firstName = "First name too long! Cannot be more than 50 characters long.";
            }
        }

        function findLastNameErrors() {
            if (lastName === '') {
                newErrors.lastName = "Last name cannot be blank!";
                return;
            }

            if (lastName.length > 50) {
                newErrors.lastName = "Last name too long! Cannot be more than 80 characters long.";
            }
        }

        function findPhoneNumberErrors() {
            if (phoneNumber === null || phoneNumber === '') {
                return;
            }

            if (!phoneNumberRegex.test(email)) {
                newErrors.phoneNumber = "Phone number can contain only numbers!";
                return;
            }

            if (phoneNumber.length < 9) {
                newErrors.phoneNumber = "Phone number too short! Must be at least 9 characters long.";
                return;
            }

            if (phoneNumber.length > 15) {
                newErrors.phoneNumber = "Phone number too long! Cannot be more than 15 characters long.";
            }
        }

        function findPeselErrors() {
            if (pesel === null || pesel === '') {
                setPesel(null)
                return;
            }

            if (!peselRegex.test(String(pesel))) {
                newErrors.pesel = "Pesel can contain only numbers!";
                return;
            }

            if (pesel.length !== 11) {
                newErrors.pesel = "Pesel must be 11 characters long!";
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
                newErrors.pesel = "Pesel is invalid!";
            }
        }

        findLoginErrors();
        findEmailErrors();
        findPasswordErrors();
        findRepeatedPasswordErrors();
        findFirstNameErrors();
        findLastNameErrors();
        findPhoneNumberErrors();
        findPeselErrors();

        return newErrors;
    }

    function handleSubmit(event) {
        event.preventDefault();

        const newErrors = findFormErrors()

        if (Object.keys(newErrors).length > 0) {
            // We got errors!
            setErrors(newErrors)
        } else {
            registrationRequest(login, email, password, firstName, lastName, phoneNumber, pesel, 'en');
        }
    }

    // todo: Czy dodawać tutaj też język do wyboru z en / pl? W dto go nie ma
    return (
        <div className="Registration">
            <Form noValidate onSubmit={handleSubmit}>
                <Form.Row>
                    <Form.Group as={Col} size="lg" controlId="login">
                        <Form.Label>Login*</Form.Label>
                        <Form.Control
                            required
                            placeholder="Login"
                            autoFocus
                            type="login"
                            value={login}
                            onChange={e => {
                                setLogin(e.target.value);
                                setValid('login');
                            }}
                            isInvalid={!!errors.login}
                        />
                        <Form.Control.Feedback type="valid">

                        </Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">
                            {errors.login}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group as={Col} size="lg" controlId="email">
                        <Form.Label>Email*</Form.Label>
                        <Form.Control
                            required
                            placeholder="Email"
                            type="email"
                            value={email}
                            onChange={e => {
                                setEmail(e.target.value);
                                setValid('email');
                            }}
                            isInvalid={!!errors.email}
                        />
                        <Form.Control.Feedback type="valid">

                        </Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">
                            {errors.email}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Form.Row>
                    <Form.Group as={Col} size="lg" controlId="password">
                        <Form.Label>Password*</Form.Label>
                        <Form.Control
                            required
                            placeholder="Password"
                            type="password"
                            value={password}
                            onChange={e => {
                                setPassword(e.target.value);
                                setValid('password');
                            }}
                            isInvalid={!!errors.password}
                        />
                        <Form.Control.Feedback type="valid">

                        </Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">
                            {errors.password}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group as={Col} size="lg" controlId="repeatedPassword">
                        <Form.Label>Repeat password*</Form.Label>
                        <Form.Control
                            required
                            placeholder="Repeat password"
                            type="password"
                            value={repeatedPassword}
                            onChange={e => {
                                setRepeatedPassword(e.target.value);
                                setValid('repeatedPassword');
                            }}
                            isInvalid={!!errors.repeatedPassword}
                        />
                        <Form.Control.Feedback type="valid">

                        </Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">
                            {errors.repeatedPassword}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Form.Row>
                    <Form.Group as={Col} size="lg" controlId="firstName">
                        <Form.Label>First Name*</Form.Label>
                        <Form.Control
                            required
                            placeholder="First name"
                            type="text"
                            value={firstName}
                            onChange={e => {
                                setFirstName(e.target.value);
                                setValid('firstName');
                            }}
                            isInvalid={!!errors.firstName}
                        />
                        <Form.Control.Feedback type="valid">

                        </Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">
                            {errors.firstName}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group as={Col} size="lg" controlId="lastName">
                        <Form.Label>Last Name*</Form.Label>
                        <Form.Control
                            required
                            placeholder="Last name"
                            type="text"
                            value={lastName}
                            onChange={e => {
                                setLastName(e.target.value);
                                setValid('lastName');
                            }}
                            isInvalid={!!errors.lastName}
                        />
                        <Form.Control.Feedback type="valid">

                        </Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">
                            {errors.lastName}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Form.Row>
                    <Form.Group as={Col} size="lg" controlId="phoneNumber">
                        <Form.Label>Phone Number</Form.Label>
                        <Form.Control
                            placeholder="Phone number"
                            type="text"
                            value={phoneNumber}
                            onChange={e => {
                                setPhoneNumber(e.target.value);
                                setValid('phoneNumber');
                            }}
                            isInvalid={!!errors.phoneNumber}
                        />
                        <Form.Control.Feedback type="valid">

                        </Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">
                            {errors.phoneNumber}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group as={Col} size="lg" controlId="pesel">
                        <Form.Label>Pesel</Form.Label>
                        <Form.Control
                            placeholder="Pesel"
                            type="text"
                            value={pesel}
                            onChange={e => {
                                setPesel(e.target.value);
                                setValid('pesel');
                            }}
                            isInvalid={!!errors.pesel}
                        />
                        <Form.Control.Feedback type="valid">

                        </Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">
                            {errors.pesel}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Button block size="lg" type="submit">
                    Register
                </Button>
            </Form>
        </div>
    );
}
