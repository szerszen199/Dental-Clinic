import React, {Suspense} from "react";
import {withTranslation} from "react-i18next";
import BootstrapTable from 'react-bootstrap-table-next';
import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import {FiRefreshCw} from "react-icons/fi";
import edit from "../../assets/edit.png";
import addNew from "../../assets/new.png";
import {Input} from "semantic-ui-react";
import {Fragment} from "react";
import {makeAccountsListRequest} from "./AccountsListRequest";


class DocumentationWithoutTranslation extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            accountsList: []
        };
        this.unFilteredList = []
    }

    componentDidMount() {
        this.makeGetAccountsRequest();
    }

    makeGetAccountsRequest() {
        makeAccountsListRequest().then((response) => {
            this.unFilteredList = response
            this.setState({accountsList: this.unFilteredList})
        })
    }

    filterList(input) {
        let tempList = []
        for (let i in this.unFilteredList) {
            if (this.unFilteredList[i].name.toUpperCase().includes(input.toUpperCase())) {
                tempList.push(this.unFilteredList[i])
            }
        }
        this.setState({accountsList: tempList})
    }

    getHintList(list) {
        let tempList = [];
        let uniqueList = [...new Set(this.state.accountsList.map(a => a.name))];
        for (let i in uniqueList) {
            tempList.push(<option key={i} value={uniqueList[i]}>{uniqueList[i]}</option>);
        }
        return tempList;

    }

    renderAccounts() {
        const {t} = this.props;
        const columns = [

            {
                dataField: 'login',
                text: t('UserLogin'),
                style: {verticalAlign: "middle"}
            },
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
                dataField: 'actions',
                text: t('to_documentation'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.linkDocumentation
            },
            {
                dataField: 'actions',
                text: t('new_entry'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.newEntry
            },
            {
                dataField: 'actions',
                text: t('to_create_prescription'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.createPrescription
            },
            {
                dataField: 'actions',
                text: t('to_prescriptions'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.linkPrescriptions
            }
        ]
        return <BootstrapTable striped keyField='login' columns={columns} data={this.state.accountsList}/>;
    }

    linkDocumentation = (cell, row, rowIndex, formatExtraData) => {
        return (
            <Link to={"/account-documentation/" + this.state.accountsList[rowIndex].login}>
                <Button variant="outline-secondary">
                    <img src={edit} alt="Edit" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}/>
                </Button>
            </Link>
        );
    }
    linkPrescriptions = (cell, row, rowIndex, formatExtraData) => {
        return (
            <Link to={"/account-prescriptions/" + this.state.accountsList[rowIndex].login}>
                <Button variant="outline-secondary">
                    <img src={edit} alt="Edit" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}/>
                </Button>
            </Link>
        );
    }

    newEntry = (cell, row, rowIndex, formatExtraData) => {
        return (
            <Link to={"/new-documentation-entry/" + this.state.accountsList[rowIndex].login}>
                <Button variant="outline-secondary">
                    <img src={addNew} alt="Edit" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}/>
                </Button>
            </Link>
        );
    }

    createPrescription = (cell, row, rowIndex, formatExtraData) => {
        return (
            <Link to={"/create-prescription/" + this.state.accountsList[rowIndex].login}>
                <Button variant="outline-secondary">
                    <img src={edit} alt="Edit" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}/>
                </Button>
            </Link>
        );
    }

    renderNull() {
        const {t} = this.props;
        return <div>{t('Loading')}</div>

    }

    renderButton() {
        return <Button variant={"secondary"} onClick={() => {
            this.makeGetAccountsRequest()
        }}>
            <FiRefreshCw/>
        </Button>
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("Documentation Entries");
        return <Fragment>
            <div className="account-refresh-button-div">
                {this.renderButton()}
            </div>
            <datalist id='options'>
                {this.state.accountsList.length !== this.unFilteredList.length ? this.getHintList() : []}
            </datalist>
            <div className="AccountListGroup">
                <Input list='options' id="ListFilter" onChange={e => this.filterList(e.target.value)}
                       placeholder={t("Filter")}/>
                {!this.state.accountsList.length ? this.renderNull() : this.renderAccounts()}
            </div>
        </Fragment>
    }
}


const DocumentationTr = withTranslation()(DocumentationWithoutTranslation)

export default function AccountsList() {
    return (
        <Suspense fallback="loading">
            <DocumentationTr/>
        </Suspense>
    );
}
