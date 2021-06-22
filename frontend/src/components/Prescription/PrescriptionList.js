import "./Prescription.css";
import {Accordion, Card, Button} from "react-bootstrap";
import React, {Suspense} from "react";
import "./PrescriptionsList.css";
import {makePrescriptionsListRequest} from "./PrescriptionsListRequest";
import {withTranslation} from "react-i18next";
import BootstrapTable from 'react-bootstrap-table-next';
import {Input} from "semantic-ui-react";
import {Fragment} from "react";

class PrescriptionsListWithoutTranslation extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            prescriptionsList: []
        };
        this.unFilteredList = []
    }

    componentDidMount() {
        this.makeGetPrescriptionsRequest();
    }

    makeGetPrescriptionsRequest() {
        makePrescriptionsListRequest().then((response) => {
            this.unFilteredList = response
            this.setState({prescriptionsList: this.unFilteredList})
        })
    }

    filterList(input) {
        let tempList = []
        for (let i in this.unFilteredList) {
            if (this.unFilteredList[i].name.toUpperCase().includes(input.toUpperCase())) {
                tempList.push(this.unFilteredList[i])
            }
        }
        this.setState({prescriptionsList: tempList})
    }

    getHintList(list) {
        let tempList = [];
        let uniqueList = [...new Set(this.state.prescriptionsList.map(a => a.name))];
        for (let i in uniqueList) {
            tempList.push(<option key={i} value={uniqueList[i]}>{uniqueList[i]}</option>);
        }
        return tempList;

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
            <datalist id='options'>
                {this.state.prescriptionsList.length !== this.unFilteredList.length ? this.getHintList() : []}
            </datalist>
            <div className="AccountListGroup">
                <Input list='options' id="ListFilter" onChange={e => this.filterList(e.target.value)}
                       placeholder={t("Filter")}/>
                {!this.state.prescriptionsList.length ? this.renderNull() : this.renderPrescriptions()}
            </div>
            <div className="Prescription">
                <Accordion>
                    <Card className="Card">
                        <Card.Header>
                            <Accordion.Toggle as={Button} variant="link" eventKey="0">
                                Aspirin
                            </Accordion.Toggle>
                        </Card.Header>
                        <Accordion.Collapse eventKey="0">
                            <Card.Body>Aspirin - 1 pack, 1 pill every morning</Card.Body>
                        </Accordion.Collapse>
                    </Card>
                    <Card className="Card">
                        <Card.Header>
                            <Accordion.Toggle as={Button} variant="link" eventKey="1">
                                Ibuprom
                            </Accordion.Toggle>
                        </Card.Header>
                        <Accordion.Collapse eventKey="1">
                            <Card.Body>Ibuprom - 2 packs, 2 pills everyday</Card.Body>
                        </Accordion.Collapse>
                    </Card>
                </Accordion>
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
