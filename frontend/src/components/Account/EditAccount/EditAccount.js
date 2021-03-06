import React, {Suspense} from 'react';
import {withTranslation} from 'react-i18next';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./EditAccount.css";
import axios from "axios";
import {editAccountRequest} from "./EditAccountRequest";
import Cookies from "js-cookie";
import confirmationAlerts from "../../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import {makeAccountsListRequest} from "../../AccountsList/AccountsListRequest";
import {FiRefreshCw} from "react-icons/fi";
import {Col, Container, Row} from "react-bootstrap";
import {Label} from "semantic-ui-react";
import * as moment from "moment";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";

const emailRegex = new RegExp(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);

const phoneNumberRegex = new RegExp(/^\d+$/);

const peselRegex = new RegExp(/^\d+$/);


class EditAccountWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            accId: this.props.id,
            isDisabled: true,
            email: "",
            firstName: "",
            lastName: "",
            phoneNumber: "",
            pesel: "",
            lastSuccessfulLogin: "",
            lastSuccessfulLoginIp: "",
            lastUnsuccessfulLogin: "",
            lastUnsuccessfulLoginIp: "",
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

        function findEmailErrors() {
            if (t.state.email === '') {
                newErrors.email = "Email blank error";
                return;
            }

            if (!emailRegex.test(t.state.email.toLowerCase())) {
                newErrors.email = "Email format error";
                return;
            }

            if (t.state.email.length < 4) {
                newErrors.this.state.email = "Email too short error";
                return;
            }

            if (t.state.email.length > 100) {
                newErrors.email = "Email too long error";
            }
        }


        function findFirstNameErrors() {
            if (t.state.firstName === '') {
                newErrors.firstName = "First name blank error";
                return;
            }

            if (t.state.firstName.length > 50) {
                newErrors.firstName = "First name too long error";
            }
        }

        function findLastNameErrors() {
            if (t.state.lastName === '') {
                newErrors.lastName = "Last name blank error";
                return;
            }

            if (t.state.lastName.length > 80) {
                newErrors.lastName = "Last name too long error";
            }
        }

        function findPhoneNumberErrors() {
            if (t.state.phoneNumber === null || t.state.phoneNumber === '' || t.state.phoneNumber === undefined) {
                t.setState({
                    phoneNumber: null,
                });
                return;
            }

            if (!phoneNumberRegex.test(String(t.state.phoneNumber))) {
                newErrors.phoneNumber = "Phone number format error";
                return;
            }

            if (t.state.phoneNumber.length < 9) {
                newErrors.phoneNumber = "Phone number too short error";
                return;
            }

            if (t.state.phoneNumber.length > 15) {
                newErrors.phoneNumber = "Phone number too long error";
            }
        }

        function findPeselErrors() {
            if (t.state.pesel === null || t.state.pesel === '' || t.state.pesel === undefined) {
                t.setState({
                    pesel: null,
                });
                return;
            }

            if (!peselRegex.test(String(t.state.pesel))) {
                newErrors.pesel = "Pesel format error";
                return;
            }

            if (t.state.pesel.length !== 11) {
                newErrors.pesel = "Pesel length error";
                return;
            }

            let weight = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
            let sum = 0;
            let controlNumber = parseInt(t.state.pesel.substring(10, 11));

            for (let i = 0; i < weight.length; i++) {
                sum += (parseInt(t.state.pesel.substring(i, i + 1)) * weight[i]);
            }
            sum = sum % 10;

            if ((10 - sum) % 10 !== controlNumber) {
                newErrors.pesel = "Pesel control digit error";
            }
        }


        findEmailErrors();
        findFirstNameErrors();
        findLastNameErrors();
        findPhoneNumberErrors();
        findPeselErrors();

        return newErrors;
    }


    componentDidMount() {
        this.setState({
            email: this.props.account.email,
            firstName: this.props.account.firstName,
            lastName: this.props.account.lastName,
            phoneNumber: this.props.account.phoneNumber,
            pesel: this.props.account.pesel,
            lastSuccessfulLogin: this.props.account.lastSuccessfulLogin,
            lastSuccessfulLoginIp: this.props.account.lastSuccessfulLoginIp,
            lastUnsuccessfulLogin: this.props.account.lastUnsuccessfulLogin,
            lastUnsuccessfulLoginIp: this.props.account.lastUnsuccessfulLoginIp,
            version: this.props.account.version,
            etag: this.props.account.etag,
        })
    }

    componentDidUpdate(prevProps) {
        if (prevProps.account !== this.props.account) {
            this.setState({
                email: this.props.account.email,
                firstName: this.props.account.firstName,
                lastName: this.props.account.lastName,
                phoneNumber: this.props.account.phoneNumber,
                pesel: this.props.account.pesel,
                lastSuccessfulLogin: this.props.account.lastSuccessfulLogin,
                lastSuccessfulLoginIp: this.props.account.lastSuccessfulLoginIp,
                lastUnsuccessfulLogin: this.props.account.lastUnsuccessfulLogin,
                lastUnsuccessfulLoginIp: this.props.account.lastUnsuccessfulLoginIp,
                version: this.props.account.version,
                etag: this.props.account.etag,
            })
        }
    }

    makeGetAccountRequest() {
        const {t} = this.props;
        let requestPath
        if (this.state.accId === undefined) {
            requestPath = process.env.REACT_APP_BACKEND_URL + "account/info";
        } else {
            requestPath = process.env.REACT_APP_BACKEND_URL + "account/other-account-info/" + this.state.accId;
        }
        axios
            .get(requestPath, {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            })
            .then(result => {
                this.setState({
                    email: result.data.email,
                    firstName: result.data.firstName,
                    lastName: result.data.lastName,
                    phoneNumber: result.data.phoneNumber,
                    pesel: result.data.pesel,
                    lastSuccessfulLogin: result.data.lastSuccessfulLogin,
                    lastSuccessfulLoginIp: result.data.lastSuccessfulLoginIp,
                    lastUnsuccessfulLogin: result.data.lastUnsuccessfulLogin,
                    lastUnsuccessfulLoginIp: result.data.lastUnsuccessfulLoginIp,
                    version: result.data.version,
                    etag: result.headers['etag']
                })
            })
            .catch(response => {
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
                            let phoneNumber = this.state.phoneNumber;
                            if (this.state.phoneNumber === "") {
                                phoneNumber = null;
                            }
                            let pesel = this.state.pesel
                            if (this.state.pesel === "") {
                                pesel = null;
                            }
                            editAccountRequest(this.state.email, this.state.firstName, this.state.lastName, phoneNumber, pesel, this.state.version, this.state.etag, this.state.accId, t);
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

    renderButton() {
        return <Button variant={"secondary"} size="lg" onClick={() => {
            this.makeGetAccountRequest()
        }}>
            <FiRefreshCw/>
        </Button>
    }

    render() {
        const {t} = this.props;
        let lastSuccessfulLogin = "";
        let lastUnsuccessfulLogin = "";
        if(this.state.lastSuccessfulLogin !== "" && this.state.lastSuccessfulLogin !== undefined){
            lastSuccessfulLogin = moment(this.state.lastSuccessfulLogin).format('DD.MM.YYYY HH:mm:ss');
        }
        if(this.state.lastUnsuccessfulLogin !== "" && this.state.lastUnsuccessfulLogin !== undefined){
            lastUnsuccessfulLogin = moment(this.state.lastUnsuccessfulLogin).format('DD.MM.YYYY HH:mm:ss');
        }

        return (
            <div className="EditAccount">
                <Form onSubmit={this.handleSubmit(t("Warning"), t("Question edit account"), t)}>
                    <Form.Group size="lg" controlId="email">
                        <Form.Label className="required">{t("Email")}</Form.Label>
                        <Form.Control
                            autoFocus
                            type="email"
                            value={this.state.email}
                            disabled={this.state.isDisabled}
                            onChange={
                                (e) => {
                                    this.setState({email: e.target.value});
                                    this.setValid('email');
                                }}
                            isInvalid={!!this.state.errors.email}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.email)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group size="lg" controlId="firstName">
                        <Form.Label className="required">{t("First Name")}</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.firstName}
                            disabled={this.state.isDisabled}
                            onChange={(e) => {
                                this.setState({firstName: e.target.value});
                                this.setValid('firstName')
                            }}
                            isInvalid={!!this.state.errors.firstName}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.firstName)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group size="lg" controlId="lastName">
                        <Form.Label className="required">{t("Last Name")}</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.lastName}
                            disabled={this.state.isDisabled}
                            onChange={(e) => {
                                this.setState({lastName: e.target.value});
                                this.setValid('lastName');
                            }}
                            isInvalid={!!this.state.errors.lastName}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.lastName)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group size="lg" controlId="phoneNumber">
                        <Form.Label>{t("Phone Number")}</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.phoneNumber}
                            disabled={this.state.isDisabled}
                            onChange={(e) => {
                                this.setState({phoneNumber: e.target.value});
                                this.setValid('phoneNumber');
                            }}
                            isInvalid={!!this.state.errors.phoneNumber}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.phoneNumber)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group size="lg" controlId="pesel">
                        <Form.Label>{t("Pesel")}</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.pesel}
                            disabled={this.state.isDisabled}
                            onChange={(e) => {
                                this.setState({pesel: e.target.value});
                                this.setValid('pesel');
                            }}
                            isInvalid={!!this.state.errors.pesel}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.pesel)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Row className="lastLoginDateAndIp">
                        <Col>
                            <Label>{t("lastSuccessfulLogin")}</Label>
                            <Form.Control
                                type="text"
                                disabled={true}
                                value={lastSuccessfulLogin}
                            />
                        </Col>
                        <Col>
                            <Label>{t("lastSuccessfulLoginIp")}</Label>
                            <Form.Control
                                type="text"
                                disabled={true}
                                value={this.state.lastSuccessfulLoginIp}
                            />
                        </Col>
                    </Form.Row>
                    <Form.Row className="lastLoginDateAndIp">
                        <Col>
                            <Label>{t("lastUnsuccessfulLogin")}</Label>
                            <Form.Control
                                type="text"
                                disabled={true}
                                value={lastUnsuccessfulLogin}
                            />
                        </Col>
                        <Col>
                            <Label>{t("lastUnsuccessfulLoginIp")}</Label>
                            <Form.Control
                                type="text"
                                disabled={true}
                                value={this.state.lastUnsuccessfulLoginIp}
                            />
                        </Col>
                    </Form.Row>
                    <Form.Row>
                        <Container id="containerForButtons">
                            <Row id="rowForEditButton">
                                <Col sm={11}>
                                    <Button block size="lg" type="submit">
                                        {this.state.isDisabled ? t("Edit") : t("Save")}
                                    </Button>
                                </Col>
                                <Col sm={1} id="refreshColumn">
                                    <div className="edit-account-refresh-button-div">
                                        {this.renderButton()}
                                    </div>
                                </Col>
                            </Row>
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
