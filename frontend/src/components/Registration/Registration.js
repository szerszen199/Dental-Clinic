import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./Registration.css";
import {registrationRequest} from "./RegistrationRequest";

export default function Registration() {
    const [login, setLogin] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [repeatedPassword, setRepeatedPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [pesel, setPesel] = useState("");

    function validateForm() {
        // Todo: zrobić walidację taką jaką wymaga projekt
        function loginCorrect() {
            const regex = /[a-zA-Z0-9]+([-._][a-zA-Z0-9]+)*/
            return login.length > 2 && login.length < 61 && regex.test(email);
        }

        function emailCorrect() {
            const regex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return email.length > 3 && email.length < 101 && regex.test(String(email).toLowerCase());
        }

        function passwordCorrect() {
            return password.length > 7;
        }

        function repeatedPasswordCorrect() {
            return repeatedPassword.length > 7 && password === repeatedPassword;
        }

        function firstNameCorrect() {
            return firstName.length > 0 && firstName.length < 51;
        }

        function lastNameCorrect() {
            return lastName.length > 0 && lastName.length < 81;
        }

        function phoneNumberCorrect() {
            const regex = /^\d+$/;
            return phoneNumber.length > 8 && phoneNumber.length < 16 && regex.test(phoneNumber);
        }

        function peselCorrect() {
            if (pesel.length === 0) {
                return true;
            }

            let weight = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
            let sum = 0;
            let controlNumber = parseInt(pesel.substring(10, 11));

            for (let i = 0; i < weight.length; i++) {
                sum += (parseInt(pesel.substring(i, i + 1)) * weight[i]);
            }
            sum = sum % 10;
            return (10 - sum) % 10 === controlNumber;
        }

        return emailCorrect() && passwordCorrect() && repeatedPasswordCorrect() && loginCorrect() && firstNameCorrect() && lastNameCorrect() && phoneNumberCorrect() && peselCorrect();
    }

    // Todo: prawdopodobnie wysyłać zapytanie do backendu tutaj, chciałbym zrobić tak jak w vue się da żeby jeśli odpalam w trybie debug front to łącze z localhostem, narazie nie ruszam.
    function handleSubmit(event) {
        event.preventDefault();
        registrationRequest(login, email, password, firstName, lastName, phoneNumber, pesel, 'en');
    }

    // todo: Czy dodawać tutaj też język do wyboru z en / pl? W dto go nie ma
    return (
        <div className="Registration">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="login">
                    <Form.Label>Login</Form.Label>
                    <Form.Control
                        autoFocus
                        type="login"
                        value={login}
                        onChange={(e) => setLogin(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="email">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        autoFocus
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="repeatedPassword">
                    <Form.Label>Repeat new password</Form.Label>
                    <Form.Control
                        type="password"
                        value={repeatedPassword}
                        onChange={(e) => setRepeatedPassword(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="firstName">
                    <Form.Label>First Name</Form.Label>
                    <Form.Control
                        type="text"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="lastName">
                    <Form.Label>Last Name</Form.Label>
                    <Form.Control
                        type="text"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="phoneNumber">
                    <Form.Label>Phone Number</Form.Label>
                    <Form.Control
                        type="text"
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                    />
                </Form.Group>
                {/*Todo: co z peselem dla obcokrajowca? Nic czy coś innnego? Narazie zrobiłem że może być pusty*/}
                <Form.Group size="lg" controlId="pesel">
                    <Form.Label>Pesel</Form.Label>
                    <Form.Control
                        type="text"
                        value={pesel}
                        onChange={(e) => setPesel(e.target.value)}
                    />
                </Form.Group>
                <Button block size="lg" type="submit" disabled={!validateForm()}>
                    Register
                </Button>
            </Form>
        </div>
    );
}
