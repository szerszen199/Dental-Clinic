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

const emailRegex = new RegExp(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);

const phoneNumberRegex = new RegExp(/^\d+$/);

const peselRegex = new RegExp(/^\d+$/);


class EditAccountWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            accId: this.props.id,
            dueDate: moment(Date.now()).format('YYYY-MM-DD'),
            dueTime: moment(Date.now()).format('hh:mm'),
            medications: "",
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


        function findMedicationsErrors() {
            if (t.state.medications === '') {
                newErrors.lastName = "medications blank error";

            }

        }


        findMedicationsErrors()

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
                            // editAccountRequest(this.state.email, this.state.firstName, this.state.lastName, phoneNumber, pesel, this.state.version, this.state.etag, this.state.accId, t);
                        }

                    });
                }
            }
        }.bind(this);
    }


    render() {
        const {t} = this.props;
        let lastSuccessfulLogin = "";
        let lastUnsuccessfulLogin = "";
        if (this.state.lastSuccessfulLogin !== "" && this.state.lastSuccessfulLogin !== undefined) {
            lastSuccessfulLogin = moment(this.state.lastSuccessfulLogin).format('DD.MM.YYYY HH:mm:ss');
        }
        if (this.state.lastUnsuccessfulLogin !== "" && this.state.lastUnsuccessfulLogin !== undefined) {
            lastUnsuccessfulLogin = moment(this.state.lastUnsuccessfulLogin).format('DD.MM.YYYY HH:mm:ss');
        }

        return (
            <div className="EditAccount">
                <Form onSubmit={this.handleSubmit(t("Warning"), t("Question edit account"), t)}>
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
                            type="text"
                            value={this.state.medications}
                            onChange={
                                (e) => {
                                    this.setState({medications: e.target.value}, () => {
                                        console.log(this.state)
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
                                    console.log(this.state)
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
                                    console.log(this.state)
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
            <EditAccountTr account={props.account} id={props.id}/>
        </Suspense>
    );
}
