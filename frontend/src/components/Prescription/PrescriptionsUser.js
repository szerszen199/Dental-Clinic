import React, {Fragment, Suspense} from "react";
import {Button, Container} from "react-bootstrap";
import axios from "axios";
import Cookies from "js-cookie";
import {withTranslation} from "react-i18next";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";
import BootstrapTable from "react-bootstrap-table-next";
import {FiRefreshCw} from "react-icons/fi";
import {PrescriptionEntry} from "./PrescriptionEntry";


class PrescriptionsListWithoutTranslation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            prescriptions: []
        };
    }

    componentDidMount() {
        this.makeGetAllPrescriptionRequest();
    }

    renderButton() {
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
        axios.get(process.env.REACT_APP_BACKEND_URL + "prescription/prescriptions/patient", {
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
