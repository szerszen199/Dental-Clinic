import React, {Suspense} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./EditPassword.css";
import {withTranslation} from "react-i18next";
import confirmationAlerts from "../../../Alerts/ConfirmationAlerts/ConfirmationAlerts";

class EditPasswordWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            isDisabled: true,
            currentPassword: "",
            password: "",
            repeatedPassword: "",
        }
    }

    validateForm(t) {
        // Todo: zrobić walidację taką jaką wymaga projekt
        function currentPasswordCorrect() {
            return t.state.currentPassword.length > 7;
        }

        function newPasswordCorrect() {
            return t.state.password.length > 7;
        }

        function newRepeatedPasswordCorrect() {
            return t.state.repeatedPassword.length > 7 && t.state.password === t.state.repeatedPassword;
        }

        return currentPasswordCorrect() && newPasswordCorrect() && newRepeatedPasswordCorrect();
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
            text: "Save password"
        });
    }

    setNotEditable(t, title, question) {
        confirmationAlerts(title, question).then((confirmed) => {
            if (t.validateForm(t) && confirmed) {
                //todo zrobić tutaj zapytanie o zmiane hasła
                this.setState({
                    isDisabled: true,
                    text: "Edit password"
                });
            }
        });
    }

    render() {
        const {t} = this.props;

        return (
            <div className="EditPassword">
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group size="lg" controlId="currentPassword">
                        <Form.Label>{t("Current password")}</Form.Label>
                        <Form.Control
                            autoFocus
                            type="password"
                            value={this.state.currentPassword}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({currentPassword: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="password">
                        <Form.Label>{t("New password")}</Form.Label>
                        <Form.Control
                            type="password"
                            value={this.state.password}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({password: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="repeated-password">
                        <Form.Label>{t("Repeat new password")}</Form.Label>
                        <Form.Control
                            type="password"
                            value={this.state.repeatedPassword}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({repeatedPassword: e.target.value})}
                        />
                    </Form.Group>
                    <Button block size="lg" type="submit"
                            onClick={() => this.handleOnClick(this, t("Warning"), t("Question edit password"))}>
                        {this.state.isDisabled ? t("Edit") : t("Save")}
                    </Button>
                </Form>
            </div>
        );
    }
}

const EditPasswordTr = withTranslation()(EditPasswordWithoutTranslation)

export default function EditPassword() {
    return (
        <Suspense fallback="loading">
            <EditPasswordTr/>
        </Suspense>
    );
}
