import React, {Suspense} from "react";
import "./AccountsList.css";
import {makeAccountsListRequest} from "./AccountsListRequest";
import {withTranslation} from "react-i18next";
import BootstrapTable from 'react-bootstrap-table-next';
import { Button } from "react-bootstrap";
import {Link} from "react-router-dom";
import edit from "../../assets/edit.png";
import {Input} from "semantic-ui-react";
import {Fragment} from "react";

class AccountsListWithoutTranslation extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            accountsList: []
        };
    }

    componentDidMount() {
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
        return <BootstrapTable striped keyField='login' columns={columns} data={this.state.accountsList} />;
    }

    linkEdit = (cell, row, rowIndex, formatExtraData) => {
        const {t} = this.props;
        return (
            <Link to={"/other-account/" + this.state.accountsList[rowIndex].login}>
                <Button variant="outline-secondary" >
                    <img src={edit} alt="Edit" width={20} style={{paddingBottom: "5px", paddingLeft: "3px"}}/>
                </Button>
            </Link>
        );
    }

    renderNull() {
        const {t} = this.props;
        return <div>{t('Loading')}</div>

    }

    render() {
        const {t} = this.props;
        return <Fragment>
            <Input onChange={e => this.filterList(e.target.value)} placeholder={t("Filter")}/>
            <div className="AccountListGroup">
                {!this.state.accountsList.length ? this.renderNull() : this.renderAccounts()}
            </div>
        </Fragment>
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
