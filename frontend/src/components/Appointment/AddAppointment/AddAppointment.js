import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./AddAppointment.css";
//import {registrationRequest} from "./RegistrationRequest";
import {Col} from "react-bootstrap";
import Dropdown from 'react-bootstrap/Dropdown';
//import FlagIcon from './FlagIcon.js';


import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

export default function AddAppointment() {
    const [dateTime, setDateTime] = useState("");
    const [doctor, setDoctor] = useState("");
    const {t} = useTranslation();
    document.title = t("Dental Clinic") + " - " + t("Add new appointment");

    const [languages] = useState([
        {language: "pl", code: 'pl', title: "Polish"},
        {language: "en", code: 'gb', title: "English"}
    ]);
    const [toggleContents, setToggleContents] = useState(null);

    const [errors, setErrors] = useState({})

    const setValid = (field) => {
        if (!!errors[field]) setErrors({
            ...errors,
            [field]: null
        })
    }

    const findFormErrors = () => {

        const newErrors = {}

        function findDateTimeErrors() {
            if (dateTime === '') {
                newErrors.dateTime = "DateTime blank error";
                return;
            }
            if (dateTime < new Date().toISOString()) {
                newErrors.dateTime = "DateTime past error";
            }
        }

        function findDoctorErrors() {
            if (doctor == null || doctor === '') {
                newErrors.doctor = "Doctor not selected error";
            }
        }

        findDateTimeErrors();
        findDoctorErrors();

        return newErrors;
    }

    function handleSubmit(event) {
        event.preventDefault();
        console.log(dateTime);
        const newErrors = findFormErrors();

        if (Object.keys(newErrors).length > 0) {
            // We got errors!
            setErrors(newErrors);
        } else {
            //registrationRequest(login, email, password, firstName, lastName, phoneNumber, pesel, selectedLanguage, t);
        }
    }

    return (
        <div className="AddAppointment">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="dateTime">
                    <Form.Label className="required">{t("Date and Time")}</Form.Label>
                    <Form.Control
                        autoFocus
                        type="datetime-local"
                        value={dateTime}
                        onChange={(e) => {
                            setDateTime(e.target.value);
                            setValid('dateTime')
                        }}
                        isInvalid={!!errors.dateTime}
                    />
                    <Form.Control.Feedback type="invalid">
                        {t(errors.dateTime)}
                    </Form.Control.Feedback>
                </Form.Group>
                <Form.Label className="required">{t("Select doctor")}</Form.Label>
                <Form.Group size="lg" controlId="doctor">
                    <Dropdown
                        onSelect={eventKey => {
                            const {language, code, title} = languages.find(({language}) => eventKey === language);
                            setToggleContents(<>{t(title)}</>);
                            setValid('doctor');
                        }}>
                        <Dropdown.Toggle variant="outline-primary" id="dropdown-flags" className="text-left"
                                         style={{width: 320}}>
                            {toggleContents != null ? toggleContents : t("Select doctor")}
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            {languages.map(({language, code, title}) => (
                                <Dropdown.Item key={language} eventKey={language}>
                                    {t(title)}
                                </Dropdown.Item>
                            ))}
                        </Dropdown.Menu>
                    </Dropdown>
                    <Form.Control.Feedback type="invalid">
                        {t(errors.language)}
                    </Form.Control.Feedback>
                </Form.Group>
                <Button block size="lg" type="submit" disabled={false}>
                    {t("Add new appointment")}
                </Button>
            </Form>
        </div>
    );
}

