import React, {Fragment, Suspense} from "react";

import {Button, Container} from "react-bootstrap";

import axios from "axios";
import Cookies from "js-cookie";
import {withTranslation} from "react-i18next";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";
import BootstrapTable from "react-bootstrap-table-next";
import {FiRefreshCw} from "react-icons/fi";
import {PrescriptionEntry} from "./PrescriptionEntry";
import {Link} from "react-router-dom";
import edit from "../../assets/edit.png";
import deleteIcon from "../../assets/delete-xxl.png";
import confirmationAlerts from "../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import {deleteAppointmentSlotRequest} from "../Appointment/PlanAppointment/DeleteAppointmentSlotRequest";
import {makeDeletePrescriptionRequest} from "../Prescription/DeletePrescriptionRequest";


class PrescriptionsListWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            accId: this.props.accId,
            prescriptions: []
        };
    }

    componentDidMount() {
        this.makeGetAllPrescriptionRequest();
    }

    renderButton() {
        let self = this;
        return <Button variant={"secondary"} onClick={() => {
            this.makeGetAllPrescriptionRequest()
        }}>
            <FiRefreshCw/>
        </Button>
    }

    makeGetAllPrescriptionRequest() {
        const {t} = this.props;
        console.log(this.state)
        let self = this;
        axios.get(process.env.REACT_APP_BACKEND_URL + "prescription/prescriptions/doctor/" + self.state.accId, {
            headers: {
                Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
            }
        }).then(function (result) {
            const allEntries = [];
            for (const prescriptionEntry of result.data) {
                allEntries.push(new PrescriptionEntry(
                    prescriptionEntry.prescriptionId,
                    prescriptionEntry.expiration,
                    prescriptionEntry.patientFirstname,
                    prescriptionEntry.patientLastname,
                    prescriptionEntry.doctorLogin,
                    prescriptionEntry.doctorFirstname,
                    prescriptionEntry.doctorLastname,
                    prescriptionEntry.creationDateTime,
                    prescriptionEntry.medications));
            }

            let compare = function (a, b) {
                if (a.creationDateTime < b.creationDateTime) {
                    return -1;
                }
                if (a.creationDateTime > b.creationDateTime) {
                    return 1;
                }
                return 0;
            }
            allEntries.sort(compare);
            self.setState({
                prescriptions: allEntries
            })
        }).catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
    }

    linkEdit = (cell, row, rowIndex, formatExtraData) => {

        return (

            <Link to={"/edit-prescription/" + this.state.prescriptions[rowIndex].prescriptionId}>
                <Button variant="outline-secondary"
                        disabled={Date.parse(this.state.prescriptions[rowIndex].expiration) < new Date()}>
                    <img src={edit} alt="Edit" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}
                    />
                </Button>
            </Link>
        );
    }

    linkDelete = (cell, row, rowIndex, formatExtraData) => {
        const {t} = this.props;
        return (
            <Button
                disabled={this.state.prescriptions[rowIndex].doctorLogin !== Cookies.get(process.env.REACT_APP_LOGIN_COOKIE)}
                variant="outline-secondary"
                onClick={() => {
                    this.handleDeleteButtonClick(this.state.prescriptions[rowIndex].prescriptionId, t('Warning'), t('Question delete prescription'), t)
                }}>
                <img src={deleteIcon} alt="Delete" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}/>
            </Button>
        );
    }

    handleDeleteButtonClick(id, title, question, t) {
        confirmationAlerts(title, question).then((confirmed) => {
            if (confirmed) {
                makeDeletePrescriptionRequest(id, t);
            }
        });
    }

    renderDocumentation() {
        const {t} = this.props;
        const columns = [
            {
                dataField: 'expiration',
                text: t('expiration'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'patientFirstname',
                text: t('patient_firstname'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'patientLastname',
                text: t('patient_lastname'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'doctorFirstname',
                text: t('doctor_firstname'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'doctorLastname',
                text: t('doctor_lastname'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'medications',
                text: t('medications'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'actions',
                text: t('edit'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.linkEdit
            },
            {
                dataField: 'actions',
                text: t('delete'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.linkDelete
            }
        ]

        return <BootstrapTable striped keyField='id' columns={columns} data={this.state.prescriptions}/>;
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("Prescriptions");
        return (
            <Fragment>
                <div className="account-refresh-button-div">
                    {this.renderButton()}
                </div>
                <div className="documentation">
                    <Container>
                        {this.renderDocumentation()}
                    </Container>
                </div>
            </Fragment>
        );
    }

}

const DocumentationListTr = withTranslation()(PrescriptionsListWithoutTranslation)

export default function DocumentationList(props) {
    return (
        <Suspense fallback="loading">
            <DocumentationListTr accId={props.match.params.accId}/>
        </Suspense>
    );
}
