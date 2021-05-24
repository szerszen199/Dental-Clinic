import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./Login.css";
import {makeLoginRequest} from "./LoginRequest";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

export default function Login() {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const {t} = useTranslation()

    function validateForm() {
        return login.length > 0 && login.length <= 60 && password.length > 0;
    }

    function handleSubmit(event) {
        event.preventDefault();
        makeLoginRequest(login, password);
    }

    return (
        <div className="Login">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="login">
                    <Form.Label>{t("UserLogin")}</Form.Label>
                    <Form.Control
                        autoFocus
                        type="text"
                        value={login}
                        onChange={(e) => setLogin(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="password">
                    <Form.Label>{t("Password")}</Form.Label>
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <Link block size="lg" as={Link} to="/reset-password">
                    {t("ForgotPassword")}
                </Link>
                <Button block size="lg" type="submit" disabled={!validateForm()}>
                    {t("Login")}
                </Button>
            </Form>
        </div>
    );
}
