import React, {Suspense} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./EditPassword.css";
import {withTranslation} from "react-i18next";
import confirmationAlerts from "../../../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import {editPasswordRequest} from "./EditPasswordRequest";

class EditPasswordWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            isDisabled: true,
            currentPassword: "",
            password: "",
            repeatedPassword: "",
            errors: {}
        }
    }

    findFormErrors(t) {

        const newErrors = {}

        function findOldPasswordErrors() {
            if (t.state.currentPassword === '') {
                newErrors['currentPassword'] = "Password blank error";
                return;
            }

            if (t.state.currentPassword.length < 8) {
                newErrors['currentPassword'] = "Password too short error";
            }
        }

        function findNewPasswordErrors() {
            if (t.state.password === '') {
                newErrors['password'] = "Password blank error";
                return;
            }

            if (t.state.password.length < 8) {
                newErrors['password'] = "Password too short error";
                return;
            }

            if (t.state.password !== t.state.repeatedPassword) {
                newErrors['password'] = "Passwords mismatch error";
            }
        }

        function findRepeatedPasswordErrors() {
            if (t.state.repeatedPassword === '') {
                newErrors['repeatedPassword'] = "Password blank error";
                return;
            }

            if (t.state.repeatedPassword.length < 8) {
                newErrors['repeatedPassword'] = "Password too short error";
                return;
            }

            if (t.state.password !== t.state.repeatedPassword) {
                newErrors['repeatedPassword'] = "Passwords mismatch error";
            }
        }


        findOldPasswordErrors();
        findNewPasswordErrors();
        findRepeatedPasswordErrors();
        console.log(newErrors)
        return newErrors;
    }


    setValid(field) {
        var array = this.state.errors;
        if (!!array[field]) {
            array[field] = null;
        }
        this.setState({errors: array})
    }

    handleSubmit(event, title, question, t) {
        return function (event) {
            event.preventDefault();
            if (this.state.isDisabled === true) {
                this.setEditable();
            } else {
                const newErrors = this.findFormErrors(this);
                if (Object.keys(newErrors).length > 0) {
                    // We got errors!
                    this.setState({errors: newErrors})
                } else {
                    this.setState({errors: newErrors})
                    confirmationAlerts(title, question).then((confirmed) => {
                        if (confirmed) {
                            this.setNotEditable(this);
                            editPasswordRequest(this.state.currentPassword, this.state.password, this.state.repeatedPassword, t)
                        }
                    })
                }
            }
        }.bind(this);
    }


    setEditable() {
        this.setState({
            isDisabled: false
        });
    }

    setNotEditable(t) {
        this.setState({
            isDisabled: true,
            text: "Edit password"
        });
    }

    render() {
        const {t} = this.props;

        return (
            <div className="EditPassword">
                <Form onSubmit={this.handleSubmit(this, t("Warning"), t("Question edit password"), t)}>
                    <Form.Group size="lg" controlId="currentPassword">
                        <Form.Label className="required">{t("Current password")}</Form.Label>
                        <Form.Control
                            autoFocus
                            type="password"
                            value={this.state.currentPassword}
                            disabled={this.state.isDisabled}
                            onChange={(e) => {
                                this.setState({currentPassword: e.target.value});
                                this.setValid('currentPassword');
                            }}
                            isInvalid={!!this.state.errors.currentPassword}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.currentPassword)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group size="lg" controlId="password">
                        <Form.Label className="required">{t("New password")}</Form.Label>
                        <Form.Control
                            type="password"
                            value={this.state.password}
                            disabled={this.state.isDisabled}
                            onChange={(e) => {
                                this.setState({password: e.target.value});
                                this.setValid('password')
                            }}
                            isInvalid={!!this.state.errors.password}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.password)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group size="lg" controlId="repeated-password">
                        <Form.Label className="required">{t("Repeat new password")}</Form.Label>
                        <Form.Control
                            type="password"
                            value={this.state.repeatedPassword}
                            disabled={this.state.isDisabled}
                            onChange={(e) => {
                                this.setState({repeatedPassword: e.target.value});
                                this.setValid('repeatedPassword')
                            }}
                            isInvalid={!!this.state.errors.repeatedPassword}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.repeatedPassword)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Button block size="lg" type="submit">
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
