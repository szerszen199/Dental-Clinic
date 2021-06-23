import React, {Fragment, Suspense} from "react";
import "./MyAppointments.css";
import {Button, Container} from "react-bootstrap";
import {withTranslation} from "react-i18next";
import {makeMyAppointmentsListRequest} from "./MyAppointmentsRequest";
import {FiRefreshCw} from "react-icons/fi";
import edit from "../../../assets/edit.png";
import deleteXXL from "../../../assets/delete-xxl.png";
import BootstrapTable from "react-bootstrap-table-next";
import {Link} from "react-router-dom";
import Cookies from "js-cookie";
import axios from "axios";
import confirmationAlerts from "../../Alerts/ConfirmationAlerts/ConfirmationAlerts";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";
import successAlerts from "../../Alerts/SuccessAlerts/SuccessAlerts";

class MyAppointmentsWithoutTranslation extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            appointments: [],
        };
    }

    componentDidMount() {
        this.makeGetAppointments();
    }

    makeGetAppointments() {
        let self = this;
        makeMyAppointmentsListRequest(this.props).then((response) => {
            self.setState({appointments: response});
        })
    }

    renderButton() {
        return <Button variant={"secondary"} onClick={() => {
            this.makeGetAppointments()
        }}>
            <FiRefreshCw/>
        </Button>
    }


    linkEdit = (cell, row, rowIndex, formatExtraData) => {
        return (
            <Link to={"/edit-appointment/" + this.state.appointments[rowIndex].id}>
                <Button variant="outline-secondary">
                    <img src={edit} alt="Edit" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}/>
                </Button>
            </Link>
        );
    }



    linkCancel = (cell, row, rowIndex, formatExtraData) => {
        let url = "";
        if (Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_PATIENT) {
            url = "appointment/cancel/patient/"
        }
        if (Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_RECEPTIONIST) {
            url = "appointment/cancel/receptionist/"
        }
        url += this.state.appointments[rowIndex].id.toString();
        return (
            <Button variant="outline-secondary" onClick={() => { this.makeCancelRequest(url)}}
                    disabled={this.state.appointments[rowIndex].canceled === "+" || this.state.appointments[rowIndex].patientLogin == "" }>
                <img src={deleteXXL} alt="Delete" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}/>
            </Button>
        );

    }

    linkConfirm = (cell, row, rowIndex, formatExtraData) => {
        let url = "";
        if (Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_PATIENT) {
            url = "appointment/confirm-own/"
        }
        if (Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_RECEPTIONIST) {
            url = "appointment/confirm/"
        }
        url += this.state.appointments[rowIndex].id.toString();
        return (
            <Button variant="outline-secondary" onClick={() => { this.makeConfirmRequest(url)}}
                    disabled={this.state.appointments[rowIndex].canceled === "+" || this.state.appointments[rowIndex].patientLogin == "" ||
                    this.state.appointments[rowIndex].confirmed ==="+" }>
                <img src={deleteXXL} alt="Delete" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}/>
            </Button>
        );

    }

    makeCancelRequest = (url) => {
        let self = this;
        const {t} = this.props;
        confirmationAlerts(t('title_cancel_appointment'), t('text_cancel_appointment')).then((confirmed) => {
            if (confirmed) {
                axios.put(process.env.REACT_APP_BACKEND_URL + url, {}, {
                    headers: {
                        Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                    }
                }).then((res) => {
                    successAlerts(t(res.data.message), res.status.toString(10)).then(() => {
                        self.makeGetAppointments();
                    })
                }).catch((res) => {
                    if (res.response) {
                        errorAlerts(t(res.response.data.message), res.response.status.toString(10));
                    }
                })
            }
        });
    }

    makeConfirmRequest = (url) => {
        let self = this;
        const {t} = this.props;
        confirmationAlerts(t('title_confirm_appointment'), t('text_confirm_appointment')).then((confirmed) => {
            if (confirmed) {
                axios.get(process.env.REACT_APP_BACKEND_URL + url, {headers: {
                        Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
                    }}, {

                }).then((res) => {
                    successAlerts(t(res.data.message), res.status.toString(10)).then(() => {
                        self.makeGetAppointments();
                    })
                }).catch((res) => {
                    if (res.response) {
                        errorAlerts(t(res.response.data.message), res.response.status.toString(10));
                    }
                })
            }
        });
    }

    renderTableOfAppointments() {

        const {t} = this.props;
        const columns = [
            {
                dataField: 'doctorName',
                text: t('doctor_name'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'patientName',
                text: t('patient_name'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'date',
                text: t('date'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'canceled',
                text: t('canceled'),
                style: {verticalAlign: "middle", textAlign: "center"}
            },
            {
                dataField: 'confirmed',
                text: t('confirmed'),
                style: {verticalAlign: "middle", textAlign: "center"}
            },
            {
                dataField: 'actions',
                text: t('delete_appointment'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.linkCancel
            },
            {
                dataField: 'actions',
                text: t('confirm_appointment'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.linkConfirm
            }
        ]

        if (Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_RECEPTIONIST) {
            columns.push(
                {
                    dataField: 'actions',
                    text: t('edit'),
                    headerStyle: {verticalAlign: "middle"},
                    style: {textAlign: "center"},
                    formatter: this.linkEdit
                }
            )
        }

        return <BootstrapTable striped keyField='id' columns={columns} data={this.state.appointments}/>;
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("scheduled_appointments");
        return (
            <Fragment>
                <div className="account-refresh-button-div">
                    {this.renderButton()}
                </div>
                <div className="documentation">
                    <Container>
                        {this.renderTableOfAppointments()}
                    </Container>
                </div>
            </Fragment>
        );
    }
}


const MyAppointmentsTr = withTranslation()(MyAppointmentsWithoutTranslation)

export default function MyAppointments() {
    return (
        <Suspense fallback="loading">
            <MyAppointmentsTr/>
        </Suspense>
    );
}
