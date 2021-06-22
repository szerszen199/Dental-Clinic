import React, {Suspense} from 'react';
import {withTranslation} from 'react-i18next';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./EditAppointmentSlot.css";
import axios from "axios";
import {editAppointmentSlotRequest} from "./EditAppointmentSlotRequest";
import {makeDoctorsListRequest} from "../ListDoctors/DoctorListRequest";
import Cookies from "js-cookie";
import confirmationAlerts from "../../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import {makeAccountsListRequest} from "../../AccountsList/AccountsListRequest";
import {FiRefreshCw} from "react-icons/fi";
import {Col, Container, Row} from "react-bootstrap";
import {Label} from "semantic-ui-react";
import * as moment from "moment";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";

class EditAppointmentSlotWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            appId: this.props.appId,
            isDisabled: true,
            dateTime: "",
            doctor: "",
            doctorsList: [],
            version: 0,
            etag: "",
            errors: {}
        }
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
        this.makeGetAppointmentRequest();
    }


    makeGetAppointmentRequest() {
        const {t} = this.props;
        let requestPath = process.env.REACT_APP_BACKEND_URL + "appointment/info/" + this.state.appId;
        axios
            .get(requestPath, {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            })
            .then(result => {
                this.setState({
                    doctor: result.data.doctorLogin,
                    dateTime: moment(result.data.appointmentDate).format("YYYY-MM-DDTHH:mm"),
                    version: result.data.version,
                    etag: result.headers['etag']
                })
            }).catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
    }

    handleSubmit(title, question, t) {
        return function (event) {
            event.preventDefault()
            console.log(this.state.errors)
            if (this.state.isDisabled === true) {
                this.setEditable()
            } else {
                const newErrors = this.findFormErrors(this);

                if (Object.keys(newErrors).length > 0) {
                    // We got errors!
                    this.setState({errors: newErrors})
                } else {
                    confirmationAlerts(title, question).then((confirmed) => {
                        if (confirmed) {
                            editAppointmentSlotRequest(this.state.appId, this.state.doctor, this.state.dateTime, this.state.version, this.state.etag, t);
                            this.setNotEditable(this)
                        }

                    });
                }
            }
        }.bind(this);
    }


    setEditable() {
        this.setState({
            isDisabled: false,
        });
    }

    setNotEditable(t) {
        t.setState({
            isDisabled: true,
        });
    }

    refreshList(self) {
        makeAccountsListRequest().then((response) => {
            self.setState({accountsList: response});
        })
    }

    makeGetDoctorsRequest() {
        makeDoctorsListRequest().then((response) => {
            this.setState({doctorsList: response})
        })
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
                    disabled={this.state.isDisabled}
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

    renderButton() {
        return <Button variant={"secondary"} size="lg" onClick={() => {
            this.makeGetAccountRequest()
        }}>
            <FiRefreshCw/>
        </Button>
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("Edit appointment slot");
        return (
            <div className="EditAppointmentSlot">
                <Form onSubmit={this.handleSubmit(t("Warning"), t("Question edit appointment slot"), t)}>
                    <Form.Group size="lg" controlId="dateTime">
                        <Form.Label className="required">{t("Date and Time")}</Form.Label>
                        <Form.Control
                            autoFocus
                            type="datetime-local"
                            value={this.state.dateTime}
                            disabled={this.state.isDisabled}
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
                    {this.renderDoctors()}
                    <Button block size="lg" type="submit" disabled={false}>
                        {this.state.isDisabled ? t("Edit") : t("Save")}
                    </Button>
                </Form>
            </div>
        );
    }
}


const EditAppointmentSlotTr = withTranslation()(EditAppointmentSlotWithoutTranslation)

export default function EditAppointmentSlot(props) {
    return (
        <Suspense fallback="loading">
            <EditAppointmentSlotTr appId={props.match.params.appId}/>
        </Suspense>
    );
}
