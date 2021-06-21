import React, {Fragment, Suspense, useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./AddAppointment.css";
import {withTranslation} from "react-i18next";
import {makeDoctorsListRequest} from "../ListDoctors/DoctorListRequest";
import confirmationAlerts from "../../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import {addAppointmentRequest} from "./AddAppointmentRequest";

class AddAppointmentWithoutTranslation extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            dateTime: "",
            doctor: "",
            toggleContents: "",
            doctorsList: [],
            errors: {}
        };
    }

    setValid(field) {
        var array = this.state.errors;
        if (!!array[field]) {
            array[field] = null;
        }
        this.setState({errors: array})
    }

    findFormErrors(t) {
        const newErrors = {}

        function findDateTimeErrors() {
            if (t.state.dateTime === '') {
                newErrors.dateTime = "DateTime blank error";
                return;
            }
            if (t.state.dateTime < new Date().toISOString()) {
                newErrors.dateTime = "DateTime past error";
            }
        }

        function findDoctorErrors() {
            console.log(t.state.doctor);
            if (t.state.doctor == null || t.state.doctor === '') {
                newErrors.doctor = "Doctor blank error";
            }
        }

        findDateTimeErrors();
        findDoctorErrors();

        return newErrors;
    }

    componentDidMount() {
        this.makeGetDoctorsRequest();
    }

    makeGetDoctorsRequest() {
        makeDoctorsListRequest().then((response) => {
            this.setState({doctorsList: response})
        })
    }

    handleSubmit(title, question, t) {
        return function (event) {
            event.preventDefault();
            console.log(this.state.dateTime);
            const newErrors = this.findFormErrors(this);

            if (Object.keys(newErrors).length > 0) {
                // We got errors!
                this.setState({errors: newErrors})
            } else {
                confirmationAlerts(title, question).then((confirmed) => {
                    if (confirmed) {
                        addAppointmentRequest(this.state.doctor, this.state.dateTime, t);
                    }
                });

            }
        }.bind(this);
    }

    renderDoctors() {
        const {t} = this.props;
        console.log(this.state.doctorsList);
        return (
                <Form.Group size="lg" controlId="doctor">
                    <Form.Label className="required">{t("Select doctor")}</Form.Label>
                    <Form.Control
                        as="select"
                        value={this.state.doctor}
                        onChange={e => {
                            this.setState({doctor: e.target.value});
                            this.setValid('doctor')
                        }}
                        isInvalid={!!this.state.errors.doctor}
                    >
                        <option value={""}/>
                        {this.state.doctorsList.map(({login, name}) => (
                            <option value={login}>
                                {name}
                            </option>
                        ))}
                    </Form.Control>
                    <Form.Control.Feedback type="invalid">
                        {t(this.state.errors.doctor)}
                    </Form.Control.Feedback>
                </Form.Group>

        );
    }

    renderNull() {
        const {t} = this.props;
        return <div>{t('Loading')}</div>
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("Add new appointment");
        return (
            <div className="AddAppointment">
                <Form onSubmit={this.handleSubmit(t("Warning"), t("Question add appointment slot"), t)}>
                    <Form.Group size="lg" controlId="dateTime">
                        <Form.Label className="required">{t("Date and Time")}</Form.Label>
                        <Form.Control
                            autoFocus
                            type="datetime-local"
                            value={this.state.dateTime}
                            onChange={(e) => {
                                this.setState({dateTime: e.target.value});
                                this.setValid('dateTime')
                            }}
                            isInvalid={!!this.state.errors.dateTime}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.dateTime)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    {!this.state.doctorsList.length ? this.renderNull() : this.renderDoctors()}
                    <Button block size="lg" type="submit" disabled={false}>
                        {t("Add new appointment")}
                    </Button>
                </Form>
            </div>
        );
    }
}

const AddAppointmentTr = withTranslation()(AddAppointmentWithoutTranslation);

export default function AddAppointment() {
    return (
        <Suspense fallback="loading">
            <AddAppointmentTr/>
        </Suspense>
    );
}
