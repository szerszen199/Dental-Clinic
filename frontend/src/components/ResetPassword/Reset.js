import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./Reset.css";
import {makeResetPasswordRequest} from "./ResetRequest";
import {useTranslation} from "react-i18next";

export default function Reset() {
    const [login, setLogin] = useState("");
    const {t} = useTranslation()

    function validateFormResetPassword() {
        return login.length > 0 && login.length <= 60;
    }

    function handleSubmit(event) {
        event.preventDefault();
        makeResetPasswordRequest(login, t);
    }

    return (
        <div className="Reset">
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
                <Button block size="lg" type="submit" disabled={!validateFormResetPassword()}>
                    {t("ResetPassword")}
                </Button>
            </Form>
        </div>
    );
}
