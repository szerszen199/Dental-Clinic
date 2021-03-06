import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "../setNewPasswordAdmin/SetNewPasswordAdmin.css";
import "../../../commonStyles/common_style.css"
import {useTranslation} from "react-i18next";
import {useParams} from "react-router-dom";
import {setNewPasswordRequest} from "../SetNewPasswordRequest";

export default function SetNewPasswordAdmin() {
    let {token} = useParams();
    const [password, setPassword] = useState("");
    const [repeatedPassword, setRepeatedPassword] = useState("");
    const {t} = useTranslation()
    document.title = t("Dental Clinic") + " - " + t("SetNewPassword");

    function handleSubmit(event) {
        event.preventDefault();

        const newErrors = findFormErrors();

        if (Object.keys(newErrors).length > 0) {
            // We got errors!
            setErrors(newErrors);
        } else {
            setNewPasswordRequest(token, password, repeatedPassword, t,"account/set-new-password-admin");
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

        function findPasswordErrors(pwd) {
            if (password === '') {
                newErrors[pwd] = "Password blank error";
                return;
            }

            if (password.length < 8) {
                newErrors[pwd] = "Password too short error";
                return;
            }

            if (password !== repeatedPassword) {
                newErrors[pwd] = "Passwords mismatch error";
            }
        }


        findPasswordErrors('password');
        findPasswordErrors('repeatedPassword');
        console.log(newErrors)
        return newErrors;
    }


    return (
        <div className="SetNewPassword">
            <Form onSubmit={handleSubmit}>
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
                <Form.Group size="lg" controlId="repeated-password">
                    <Form.Label className="required">{t("Repeat new password")}</Form.Label>
                    <Form.Control
                        type="password"
                        value={repeatedPassword}
                        onChange={(e) => {
                            setRepeatedPassword(e.target.value)
                            setValid('password')
                        }}
                        isInvalid={!!errors.password}
                    />
                    <Form.Control.Feedback type="invalid">
                        {t(errors.password)}
                    </Form.Control.Feedback>
                </Form.Group>
                <Button block size="lg" type="submit" disabled={false}>
                    {t("ResetPassword")}
                </Button>
            </Form>
        </div>
    );
}
