import "./Prescription.css";
import React, {Fragment, Suspense} from "react";
import "./PrescriptionsList.css";
import {makePrescriptionsListRequest} from "./PrescriptionsListRequest";
import {withTranslation} from "react-i18next";
import BootstrapTable from 'react-bootstrap-table-next';
import Cookies from "js-cookie";

class PrescriptionsListWithoutTranslation extends React.Component {

    
    constructor(props) {
        super(props);
        this.state = {
            prescriptionsList: []
        };
    }

    componentDidMount() {
        this.makeGetPrescriptionsRequest();
    }

    makeGetPrescriptionsRequest() {
        let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
        function isPatient() {
            return token !== undefined && Cookies !== undefined && Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_PATIENT;
        }
        makePrescriptionsListRequest(isPatient()).then((response) => {
            this.prescriptionsList = response
            this.setState({prescriptionsList: this.prescriptionsList})
        })
    }

    renderPrescriptions() {
        const {t} = this.props;
        const columns = [

            {
                dataField: 'expiration',
                text: t('Expiration_Date'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'patientName',
                text: t('patient_name'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'doctorName',
                text: t("doctor_name"),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'medications',
                text: t('medications'),
                style: {verticalAlign: "middle"}
            },
        ]
        return <BootstrapTable striped keyField='login' columns={columns} data={this.state.prescriptionsList}/>;
    }

    renderNull() {
        const {t} = this.props;
        return <div>{t('Loading')}</div>
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("Users Prescriptions");
        return <Fragment>

            <div className="AccountListGroup">
                {t("Prescriptions")}
                {!this.state.prescriptionsList.length ? this.renderNull() : this.renderPrescriptions()}
            </div>
        </Fragment>
    }
}


const PrescriptionsListTr = withTranslation()(PrescriptionsListWithoutTranslation)

export default function prescriptionsList() {
    return (
        <Suspense fallback="loading">
            <PrescriptionsListTr/>
        </Suspense>
    );
}
