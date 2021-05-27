import React, {Suspense} from "react";
import "./AccountsList.css";
import {makeAccountsListRequest} from "./AccountsListRequest";
import {withTranslation} from "react-i18next";
import BootstrapTable from 'react-bootstrap-table-next';
import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import edit from "../../assets/edit.png"
import { FiRefreshCw } from "react-icons/fi";

class AccountsListWithoutTranslation extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            accountsList: []
        };
    }

    componentDidMount() {
        this.makeGetAccountsRequest();
    }

    makeGetAccountsRequest() {
        makeAccountsListRequest().then((response) => {
            this.setState({accountsList: response});
        })
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
                text: t('Actions'),
                headerStyle: {verticalAlign: "middle"},
                style: {textAlign: "center"},
                formatter: this.linkEdit
            }
        ]
        return <BootstrapTable striped keyField='login' columns={columns} data={this.state.accountsList}/>;
    }

    linkEdit = (cell, row, rowIndex, formatExtraData) => {
        return (
            <Link to={"/other-account/" + this.state.accountsList[rowIndex].login}>
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
        let self = this;
        return <Button variant={"secondary"} onClick={() => {
            this.makeGetAccountsRequest(self)
        }}>
            <FiRefreshCw />
        </Button>
    }

    render() {
        return <span>
        <div className="account-refresh-button-div">
            {this.renderButton()}
        </div>
            <div className="AccountListGroup">
            {!this.state.accountsList.length ? this.renderNull() : this.renderAccounts()}
        </div>
        </span>
    }
}


const AccountsListTr = withTranslation()(AccountsListWithoutTranslation)

export default function AccountsList() {
    return (
        <Suspense fallback="loading">
            <AccountsListTr/>
        </Suspense>
    );
}
