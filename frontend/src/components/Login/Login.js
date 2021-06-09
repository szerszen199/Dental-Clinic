import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./Login.css";
import {makeLoginRequest} from "./LoginRequest";
import "../../commonStyles/common_style.css"
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

export default function Login() {

    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const {t} = useTranslation()
    document.title = t("Dental Clinic") + " - " + t("Login");
    const loginRegex = new RegExp(/^[a-z0-9]+$/i);

    function handleSubmit(event) {
        event.preventDefault();

        const newErrors = findFormErrors();

        if (Object.keys(newErrors).length > 0) {
            // We got errors!
            setErrors(newErrors);
        } else {
            makeLoginRequest(login, password, t);
        }
    }

    const [errors, setErrors] = useState({})

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

        function findPasswordErrors() {

            if (password.length < 8) {
                newErrors.password = "Password too short error";

            }
        }

        findLoginErrors();
        findPasswordErrors();
        return newErrors;
    }

    return (
        <div className="Login">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="login">
                    <Form.Label className="required">{t("UserLogin")}</Form.Label>
                    <Form.Control
                        autoFocus
                        type="text"
                        value={login}
                        onChange={(e) => {
                            setLogin(e.target.value);
                            setValid('login')
                        }}
                        isInvalid={!!errors.login}
                    />
                    <Form.Control.Feedback type="invalid">
                        {t(errors.login)}
                    </Form.Control.Feedback>
                </Form.Group>
                <Form.Group size="lg" controlId="password">
                    <Form.Label className="required">{t("Password")}</Form.Label>
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => {
                            setPassword(e.target.value)
                            setValid('password')
                        }}
                        isInvalid={!!errors.password}
                    />
                    <Form.Control.Feedback type="invalid">
                        {t(errors.password)}
                    </Form.Control.Feedback>
                </Form.Group>
                <Link block size="lg" as={Link} to="/reset-password">
                    {t("ForgotPassword")}
                </Link>
                <Button block size="lg" type="submit" disabled={false}>
                    {t("Login")}
                </Button>
            </Form>
        </div>
    );
}
