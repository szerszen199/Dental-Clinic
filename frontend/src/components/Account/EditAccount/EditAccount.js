import React, {Suspense} from 'react';
import {withTranslation} from 'react-i18next';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./EditAccount.css";
import axios from "axios";
import {editAccountRequest} from "./EditAccountRequest";
import Cookies from "js-cookie";
import confirmationAlerts from "../../Alerts/ConfirmationAlerts/ConfirmationAlerts";

class EditAccountWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isDisabled: true,
            email: "",
            firstName: "",
            lastName: "",
            phoneNumber: "",
            pesel: "",
            version: 0,
            etag: "",
        }
    }

    componentDidMount() {
        let requestPath
        if (this.props.account === undefined) {
            requestPath = process.env.REACT_APP_BACKEND_URL + "account/info"
        } else {
            requestPath = process.env.REACT_APP_BACKEND_URL + "account/other-account-info/" + this.props.account
        }
        axios
            .get(requestPath, {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            })
            .then(res => {
                this.setState({
                    etag: res.headers['etag']
                })
                return res.data
            })
            .then(result => this.setState({
                email: result.email,
                firstName: result.firstName,
                lastName: result.lastName,
                phoneNumber: result.phoneNumber,
                pesel: result.pesel,
                version: result.version,
            }))
    }

    validateForm(t) {
        function emailCorrect() {
            if (t.state.email !== undefined) {
                return t.state.email.length >= 4 && t.state.email.length <= 100;
            }
            return false;
        }

        function firstNameCorrect() {
            if (t.state.firstName !== undefined) {
                return t.state.firstName.length >= 1 && t.state.firstName.length <= 50;
            }
            return false;
        }

        function lastNameCorrect() {
            if (t.state.lastName !== undefined) {
                return t.state.lastName.length >= 1 && t.state.lastName.length <= 80;
            }
            return false;
        }

        function phoneNumberCorrect() {
            if (t.state.phoneNumber !== undefined && t.state.phoneNumber !== "") {
                return t.state.phoneNumber.length >= 9 && t.state.phoneNumber.length <= 15;
            }
            t.setState({
                phoneNumber: "",
            });
            return true;
        }

        function peselCorrect() {
            if (t.state.pesel !== undefined && t.state.pesel !== "") {
                return t.state.pesel.length === 11;
            }
            t.setState({
                pesel: "",
            });
            return true;
        }

        return emailCorrect() && firstNameCorrect() && lastNameCorrect() && phoneNumberCorrect() && peselCorrect();
    }


    // Todo: prawdopodobnie wysyłać zapytanie do backendu tutaj, chciałbym zrobić tak jak w vue się da żeby jeśli odpalam w trybie debug front to łącze z localhostem, narazie nie ruszam.
    handleSubmit(event) {
        event.preventDefault();
    }

    handleOnClick(t, title, question) {
        if (this.state.isDisabled === true) {
            this.setEditable()
        } else {
            this.setNotEditable(t, title, question)
        }
    }

    setEditable() {
        this.setState({
            isDisabled: false,
        });
    }

    setNotEditable(t, title, question) {
        confirmationAlerts(title, question).then((confirmed) => {
            if (confirmed && t.validateForm(t)) {
                t.setState({
                    isDisabled: true,
                });
                let phoneNumber = t.state.phoneNumber;
                if (t.state.phoneNumber === "") {
                    phoneNumber = null;
                }
                let pesel = t.state.pesel
                if (t.state.pesel === "") {
                    pesel = null;
                }
                editAccountRequest(t.state.email, t.state.firstName, t.state.lastName, phoneNumber, pesel, t.state.version, t.state.etag, t.props.account)
            }
        });
    }

    render() {
        const {t} = this.props;

        return (
            <div className="EditAccount">
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group size="lg" controlId="email">
                        <Form.Label>{t("Email")}</Form.Label>
                        <Form.Control
                            autoFocus
                            type="email"
                            value={this.state.email}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({email: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="firstName">
                        <Form.Label>{t("First Name")}</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.firstName}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({firstName: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="lastName">
                        <Form.Label>{t("Last Name")}</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.lastName}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({lastName: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="phoneNumber">
                        <Form.Label>{t("Phone Number")}</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.phoneNumber}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({phoneNumber: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="pesel">
                        <Form.Label>{t("Pesel")}</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.pesel}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({pesel: e.target.value})}
                        />
                    </Form.Group>
                    <Button block size="lg" type="submit"
                            onClick={() => this.handleOnClick(this, t("Warning"), t("Question edit account"))}>
                        {this.state.isDisabled ? t("Edit") : t("Save")}
                    </Button>
                </Form>
            </div>
        );
    }
}


const EditAccountTr = withTranslation()(EditAccountWithoutTranslation)

export default function EditAccount(props) {
    return (
        <Suspense fallback="loading">
            <EditAccountTr account={props.account}/>
        </Suspense>
    );
}
