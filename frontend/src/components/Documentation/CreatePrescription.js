import React, {Suspense} from 'react';
import {withTranslation} from 'react-i18next';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import axios from "axios";
import Cookies from "js-cookie";
import {FiRefreshCw} from "react-icons/fi";
import {Col, Container, Row} from "react-bootstrap";
import {Label} from "semantic-ui-react";
import * as moment from "moment";
import confirmationAlerts from "../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";
import successAlertsWithRedirect from "../Alerts/SuccessAlerts/SuccessAlertsWithRedirect";

class EditAccountWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            accId: this.props.accId,
            dueDate: moment(Date.now()).format('YYYY-MM-DD'),
            dueTime: moment(Date.now()).format('HH:mm'),
            medications: "",
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


        function findMedicationsErrors() {
            if (t.state.medications === '') {
                newErrors.medications = "medications blank error";
            }
        }

        function findDueDateErrors() {
            if (t.state.dueDate == "") {
                newErrors.dueDate = "due date blank error";
            }
            if (t.state.dueDate < moment(Date.now()).format('YYYY-MM-DD')) {
                newErrors.dueDate = "due date past date";
            }
        }

        function findDueTimeErrors() {
            if (t.state.dueTime == "") {
                newErrors.dueTime = "due time blank error";
            }
        }


        findMedicationsErrors();
        findDueDateErrors();
        findDueTimeErrors()

        return newErrors;
    }


    componentDidMount() {
        this.setState({})
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
                            let dueDate = this.state.dueDate + "T" + this.state.dueTime;
                            axios.put(process.env.REACT_APP_BACKEND_URL + "prescription/create", {
                                patient: this.state.accId,
                                expiration: dueDate,
                                medications: this.state.medications
                            }, {
                                headers: {
                                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                                }
                            }).then((res) => {
                                successAlertsWithRedirect(t(res.data.message), res.status.toString(10), "#/patients_account_list")
                            }).catch((response) => {
                                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
                            })
                        }

                    });
                }
            }
        }.bind(this);
    }


    render() {
        const {t} = this.props;

        return (
            <div className="EditAccount">
                <Form onSubmit={this.handleSubmit(t("Warning"), t("Question create prescription"), t)}>
                    <Form.Group size="lg" controlId="username">
                        <Form.Label>{t("Login")}</Form.Label>
                        <Form.Control
                            autoFocus
                            type="text"
                            value={this.state.accId}
                            disabled={true}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="email">
                        <Form.Label className="required">{t("medications")}</Form.Label>
                        <Form.Control
                            autoFocus
                            as="textarea"
                            rows={5}
                            value={this.state.medications}
                            onChange={
                                (e) => {
                                    this.setState({medications: e.target.value}, () => {
                                    });
                                    this.setValid('medications');
                                }}
                            isInvalid={!!this.state.errors.medications}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.medications)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group size="lg" controlId="dueDate">
                        <Form.Label className="required">{t("dueDate")}</Form.Label>
                        <Form.Control
                            type="date"
                            value={this.state.dueDate}
                            onChange={(e) => {
                                this.setState({dueDate: e.target.value}, () => {
                                });
                                this.setValid('dueDate');
                            }}
                            isInvalid={!!this.state.errors.dueDate}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.dueDate)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group size="lg" controlId="dueTime">
                        <Form.Label className="required">{t("dueTime")}</Form.Label>
                        <Form.Control
                            type="time"
                            value={this.state.dueTime}
                            onChange={(e) => {
                                this.setState({dueTime: e.target.value}, () => {
                                });
                                this.setValid('dueTime');
                            }}
                            isInvalid={!!this.state.errors.dueTime}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.dueTime)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Row>
                        <Container id="containerForButtons">
                            <Button block size="lg" type="submit">
                                {t("create_prescription")}
                            </Button>
                        </Container>
                    </Form.Row>
                </Form>
            </div>
        );
    }
}


const EditAccountTr = withTranslation()(EditAccountWithoutTranslation)

export default function EditAccount(props) {
    return (
        <Suspense fallback="loading">
            <EditAccountTr accId={props.match.params.accId}/>
        </Suspense>
    );
}
