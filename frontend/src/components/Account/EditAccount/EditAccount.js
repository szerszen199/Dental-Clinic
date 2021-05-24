import React, {Suspense} from 'react';
import {withTranslation} from 'react-i18next';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./EditAccount.css";
import axios from "axios";
import {editAccountRequest} from "./EditAccountRequest";
import Cookies from "js-cookie";

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
        axios
            .get(process.env.REACT_APP_BACKEND_URL + "account/info", {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            })
            .then(res => {
                this.state.etag = res.headers['etag']
                return res.data
            })
            .then(result => this.setState({
                email: result.email,
                firstName: result.firstName,
                lastName: result.lastName,
                phoneNumber: result.phoneNumber,
                pesel: result.pesel,
                version: result.version
            }))
    }

    validateForm(t) {
        // Todo: zrobić walidację taką jaką wymaga projekt

        function emailCorrect() {
            return t.state.email.length > 0;
        }

        function firstNameCorrect() {
            return t.state.firstName.length > 0;
        }

        function lastNameCorrect() {
            return t.state.lastName.length > 0;
        }

        function phoneNumberCorrect() {
            return true;
            // return t.state.phoneNumber.length > 0 && /^\d+$/.test(t.state.phoneNumber);
        }

        // TODO: przypadek obcokrajowca wymusza że peselu może nie być ale nadal warto by go zwalidowac, tylko jak?
        function peselCorrect() {
            return true;
        }

        return emailCorrect() && firstNameCorrect() && lastNameCorrect() && phoneNumberCorrect() && peselCorrect();
    }


    // Todo: prawdopodobnie wysyłać zapytanie do backendu tutaj, chciałbym zrobić tak jak w vue się da żeby jeśli odpalam w trybie debug front to łącze z localhostem, narazie nie ruszam.
    handleSubmit(event) {
        event.preventDefault();
    }

    handleOnClick(t) {
        if (this.state.isDisabled === true) {
            this.setEditable()
        } else {
            this.setNotEditable(t)
        }
    }

    setEditable() {
        this.setState({
            isDisabled: false,
        });
    }

    setNotEditable(t) {
        this.validateForm(t)
        this.setState({
            isDisabled: true,
        });
        editAccountRequest(this.state.email, this.state.firstName, this.state.lastName, this.state.phoneNumber, this.state.pesel,this.state.version, this.state.etag)
    }

    // todo: Czy dodawać tutaj też język do wyboru z en / pl? W dto go nie ma
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
                    {/*Todo: co z peselem dla obcokrajowca? Nic czy coś innnego? Narazie zrobiłem że może być pusty*/}
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
                            onClick={() => this.handleOnClick(this)}>
                        {this.state.isDisabled ? t("Edit") : t("Save")}
                    </Button>
                </Form>
            </div>
        );
    }
}


const EditAccountTr = withTranslation()(EditAccountWithoutTranslation)

export default function EditAccount() {
    return (
        <Suspense fallback="loading">
            <EditAccountTr/>
        </Suspense>
    );
}
