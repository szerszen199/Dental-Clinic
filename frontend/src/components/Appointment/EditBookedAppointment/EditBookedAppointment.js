import React, {Suspense} from "react";
import axios from "axios";
import Cookies from "js-cookie";
import * as moment from "moment";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";
import confirmationAlerts from "../../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {FiRefreshCw} from "react-icons/fi";
import {editBookedAppointmentRequest} from "./EditBookedAppointmentRequest";
import {withTranslation} from "react-i18next";
import "./EditBookedAppointment.css";
import {makePatientsListRequest} from "../ListPatients/ListPatientsRequest";


class EditBookedAppointmentWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            appId: this.props.appId,
            isDisabled: true,
            dateTime: "",
            patient: "",
            patientsList: [],
            version: 0,
            etag: "",
            errors: {}
        }
    }

    setValid(field) {
        let array = this.state.errors;
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

        function findPatientsErrors() {
            if (t.state.patient == null || t.state.patient === '') {
                newErrors.patient = "Patient blank error";
            }
        }

        findDateTimeErrors();
        findPatientsErrors();


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
                    patient: result.data.patientLogin,
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
                            editBookedAppointmentRequest(this.state.appId, this.state.patient, this.state.dateTime, this.state.version, this.state.etag, t);
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

    refreshList() {
        this.makeGetDoctorsRequest();
        this.makeGetAppointmentRequest();
    }

    makeGetDoctorsRequest() {
        makePatientsListRequest().then((response) => {
            this.setState({patientsList: response})
        })
    }

    renderPatients() {
        const {t} = this.props;
        return (
            <Form.Group size="lg" controlId="doctor">
                <Form.Label className="required">{t("Select patient")}</Form.Label>
                <Form.Control
                    as="select"
                    value={this.state.patient}
                    disabled={this.state.isDisabled}
                    onChange={e => {
                        this.setState({patient: e.target.value});
                        this.setValid('patient')
                    }}
                    isInvalid={!!this.state.errors.patient}
                >
                    <option value={""}/>
                    {this.state.patientsList.map(({login, name}) => (
                        <option value={login}>
                            {name}
                        </option>
                    ))}
                </Form.Control>
                <Form.Control.Feedback type="invalid">
                    {t(this.state.errors.patient)}
                </Form.Control.Feedback>
            </Form.Group>

        );
    }

    renderButton() {
        return <Button variant={"secondary"} size="sm" onClick={() => {
            this.refreshList();
        }}>
            <FiRefreshCw/>
        </Button>
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("Edit appointment slot");
        return (
            <div className="EditBookedAppointment">
                <Form onSubmit={this.handleSubmit(t("Warning"), t("Question edit booked appointment"), t)}>
                    <div class="EditBookedAppointmentRefreshButton">
                        {this.renderButton()}
                    </div>
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
                    {this.renderPatients()}
                    <Button block size="lg" type="submit" disabled={false}>
                        {this.state.isDisabled ? t("Edit") : t("Save")}
                    </Button>
                </Form>
            </div>
        );
    }
}

const EditBookedAppointmentTr = withTranslation()(EditBookedAppointmentWithoutTranslation)

export default function EditBookedAppointment(props) {
    return (
        <Suspense fallback="loading">
            <EditBookedAppointmentTr appId={props.match.params.appId}/>
        </Suspense>
    );
}