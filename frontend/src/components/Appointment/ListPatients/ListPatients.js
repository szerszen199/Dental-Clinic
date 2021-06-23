import React, {Suspense} from "react";
import "./ListPatients.css";
import {makePatientsListRequest} from "./ListPatientsRequest";
import {withTranslation} from "react-i18next";
import BootstrapTable from 'react-bootstrap-table-next';
import {Button} from "react-bootstrap";
import {FiRefreshCw} from "react-icons/fi";
import {Input} from "semantic-ui-react";
import {Fragment} from "react";

class ListPatientsWithoutTranslation extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            patientsList: []
        };
        this.unFilteredList = []
    }

    componentDidMount() {
        this.makeGetPatientsRequest();
    }

    makeGetPatientsRequest() {
        const {t} = this.props;
        makePatientsListRequest(t).then((response) => {
            this.unFilteredList = response
            this.setState({patientsList: this.unFilteredList})
        })
    }

    filterList(input) {
        let tempList = []
        for (let i in this.unFilteredList) {
            if (this.unFilteredList[i].name.toUpperCase().includes(input.toUpperCase())) {
                tempList.push(this.unFilteredList[i])
            }
        }
        this.setState({patientsList: tempList})
    }

    getHintList(list){
        let tempList = [];
        let uniqueList = [...new Set(this.state.patientsList.map(a =>a.name))];
        for(let i in uniqueList){
            tempList.push(<option key={i} value={uniqueList[i]}>{uniqueList[i]}</option>);
        }
        return tempList;

    }

    renderPatients() {
        const {t} = this.props;
        const columns = [

            {
                dataField: 'name',
                text: t('Name and Surname'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'email',
                text: t('Email'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'phone',
                text: t('Phone Number'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'pesel',
                text: t('Pesel'),
                style: {verticalAlign: "middle"}
            }
        ]
        return <BootstrapTable striped keyField='login' columns={columns} data={this.state.patientsList} />;
    }

    renderNull() {
        const {t} = this.props;
        return <div>{t('Loading')}</div>

    }

    renderButton() {
        let self = this;
        return <Button variant={"secondary"} onClick={() => {
            this.makeGetPatientsRequest(self)
        }}>
            <FiRefreshCw />
        </Button>
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("Patients List");
        return <Fragment>
            <div className="account-refresh-button-div">
                {this.renderButton()}
            </div>
            <datalist id='options'>
                {this.state.patientsList.length !== this.unFilteredList.length?this.getHintList():[]}
            </datalist>
            <div className="AccountListGroup">
                <Input list='options' id="ListFilter" onChange={e => this.filterList(e.target.value)} placeholder={t("Filter")}/>
                {!this.state.patientsList.length ? this.renderNull() : this.renderPatients()}
            </div>
        </Fragment>
    }
}


const PatientsListTr = withTranslation()(ListPatientsWithoutTranslation)

export default function ListPatients() {
    return (
        <Suspense fallback="loading">
            <PatientsListTr/>
        </Suspense>
    );
}
