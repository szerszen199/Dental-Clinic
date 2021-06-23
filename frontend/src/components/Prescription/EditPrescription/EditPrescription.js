import axios from "axios";
import Cookies from "js-cookie";

import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";
import confirmationAlerts from "../../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import "./EditPrescription.css"
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {FiRefreshCw} from "react-icons/fi";
import React, {Suspense} from "react";
import {editPrescriptionRequest} from "./EditPrescriptionRequest";
import {withTranslation} from "react-i18next";

class EditPrescriptionWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            prescId: this.props.prescId,
            isDisabled: true,
            medications: "",
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

        function findMedicationErrors() {
            if (t.state.medications == null || t.state.medications === '') {
                newErrors.medications = "Medication blank error";
            }
        }
        findMedicationErrors();


        return newErrors;
    }


    componentDidMount() {
        this.makeGetPrescriptionRequest();
    }


    makeGetPrescriptionRequest() {
        const {t} = this.props;
        let requestPath = process.env.REACT_APP_BACKEND_URL + "prescription/info/" + this.state.prescId;
        axios
            .get(requestPath, {
                headers: {
                    Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                }
            })
            .then(result => {
                this.setState({
                    medications: result.data.medications,
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
                            editPrescriptionRequest(this.state.prescId, this.state.medications, this.state.version, this.state.etag, t);
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
        this.makeGetPrescriptionRequest();
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
        document.title = t("Dental Clinic") + " - " + t("Edit prescription");
        return (
            <div className="EditPrescription">
                <Form onSubmit={this.handleSubmit(t("Warning"), t("Question edit prescription"), t)}>
                    <div class="EditPrescriptionRefreshButton">
                        {this.renderButton()}
                    </div>
                    <Form.Group size="lg" controlId="medications">
                        <Form.Label className="required">{t("Medications")}</Form.Label>
                        <Form.Control
                            autoFocus
                            as="textarea"
                            value={this.state.medications}
                            disabled={this.state.isDisabled}
                            onChange={(e) => {
                                this.setState({medications: e.target.value});
                                this.setValid('medications')
                            }}
                            isInvalid={!!this.state.errors.medications}
                        />
                        <Form.Control.Feedback type="invalid">
                            {t(this.state.errors.medications)}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Button block size="lg" type="submit" disabled={false}>
                        {this.state.isDisabled ? t("Edit") : t("Save")}
                    </Button>
                </Form>
            </div>
        );
    }
}

const EditPrescriptionTr = withTranslation()(EditPrescriptionWithoutTranslation)

export default function EditPrescription(props) {
    return (
        <Suspense fallback="loading">
            <EditPrescriptionTr prescId={props.match.params.prescId}/>
        </Suspense>
    );
}