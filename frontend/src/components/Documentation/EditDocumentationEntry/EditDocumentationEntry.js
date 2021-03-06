import React, {Suspense} from 'react';
import {withTranslation} from 'react-i18next';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./EditDocumentationEntry.css";
import axios from "axios";
import Cookies from "js-cookie";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";
import confirmationAlerts from "../../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import successAlertsWithRedirect from "../../Alerts/SuccessAlerts/SuccessAlertsWithRedirect";

class EditDocumentationEntryWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            wasDone: "",
            toBeDone: "",
            version: "",
            id: this.props.entryId,
            accId: this.props.accId,
            etag: "",
            errors: {}
        }
    }

    componentDidMount() {
        axios
            .get(process.env.REACT_APP_BACKEND_URL + "documentation/get/" + this.props.entryId, {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            })
            .then(result => {
                this.setState({
                    wasDone: result.data.wasDone,
                    toBeDone: result.data.toBeDone,
                    version: result.data.version,
                    id: result.data.id,
                    etag: result.data.etag
                });
            })
    }

    setValid(field) {
        var array = this.state.errors;
        if (!!array[field]) {
            array[field] = null;
        }
        this.setState({errors: array})
    }

    makeEditEntryRequest(t) {
        console.log(this.state)
        let config = {
            method: 'post',
            url: process.env.REACT_APP_BACKEND_URL + "documentation/edit",
            headers: {
                'Authorization': 'Bearer ' + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME),
                'Content-Type': 'application/json',
                "If-Match": this.state.etag
            },
            data: {
                toBeDone: this.state.toBeDone,
                wasDone: this.state.wasDone,
                id: this.state.id,
                version: this.state.version
            }
        };
        let self = this;

        axios(config)
            .then((response) => {
                successAlertsWithRedirect(t(response.data.message), response.status.toString(), "#/account-documentation/" + self.state.accId).then(() => {
                })
            })
            .catch((response) => {
                if (response.response) {
                    console.log(response.response.data.message)
                    errorAlerts(t(response.response.data.message), response.response.status.toString(10));
                }
            });
    }

    findFormErrors(self, t) {
        const newErrors = {}

        function findToBeDoneErrors() {
            if (self.state.toBeDone === '') {
                newErrors.toBeDone = t("not_empty");
            }
        }

        function findWasDoneErrors() {
            if (self.state.wasDone === '') {
                newErrors.wasDone = t("not_empty");
            }
        }

        findWasDoneErrors();
        findToBeDoneErrors();

        return newErrors;
    }


    handleSubmit(t) {
        return function (event) {
            event.preventDefault()
            const newErrors = this.findFormErrors(this, t);
            if (Object.keys(newErrors).length > 0) {
                this.setState({errors: newErrors})
            } else {
                confirmationAlerts(t("title_edit_entry"), t("question_edit_entry")).then((confirmed) => {
                    if (confirmed) {
                        this.makeEditEntryRequest(t);
                    }
                });
            }
        }.bind(this);
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("edit_entry");

        return (
            <div className="EditEntry">
                <Form onSubmit={this.handleSubmit(t)}>
                    <Form.Group size="lg" controlId="toBeDone">
                        <Form.Label className="required">{t("to_be_done")}</Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={3}
                            value={this.state.toBeDone}
                            onChange={
                                (e) => {
                                    this.setState({toBeDone: e.target.value});
                                    this.setValid('toBeDone');
                                }}
                            isInvalid={!!this.state.errors.toBeDone}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.toBeDone)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group size="lg" controlId="toBeDone">
                        <Form.Label className="required">{t("was_done")}</Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={3}
                            value={this.state.wasDone}
                            onChange={
                                (e) => {
                                    this.setState({wasDone: e.target.value});
                                    this.setValid('wasDone');
                                }}
                            isInvalid={!!this.state.errors.wasDone}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.wasDone)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group>
                        <Button block size="lg" type="submit">
                            {t("Save")}
                        </Button>
                    </Form.Group>
                </Form>
            </div>
        );
    }
}


const EditDocumentationEntryTr = withTranslation()(EditDocumentationEntryWithoutTranslation)

export default function EditDocumentationEntry(props) {
    return (
        <Suspense fallback="loading">
            <EditDocumentationEntryTr accId={props.match.params.accId} entryId={props.match.params.entryId}/>
        </Suspense>
    );
}
