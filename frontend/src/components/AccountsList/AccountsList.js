import React, {Suspense} from "react";
import "./AccountsList.css";
import {makeAccountsListRequest} from "./AccountsListRequest";
import {withTranslation} from "react-i18next";
import BootstrapTable from 'react-bootstrap-table-next';
import {Dropdown, Table} from "react-bootstrap";
import filterFactory, {textFilter} from 'react-bootstrap-table2-filter';

class AccountsListWithoutTranslation extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            accountsList: []
        };
    }

    componentDidMount() {
        makeAccountsListRequest().then((response) => {
            this.setState({accountsList: response})
        })
    }

    renderAccount(person, index) {
        return (
            <tr key={index}>
                <td>{index + 1}</td>
                <td>{person.login}</td>
                <td>{person.name}</td>
                <td>{person.email}</td>
            </tr>
        )
    }

    renderAccounts() {
        const {t} = this.props;

        const columns = [

            {
                dataField: 'login',
                text: t('Login-Noun'),
                filter: textFilter({
                    placeholder: t("Filter"),
                    style: {marginLeft: "20px"}
                })
            },
            {
                dataField: 'name',
                text: t('Name and Surname'),
                filter: textFilter({
                    placeholder: t("Filter"),
                    style: {marginLeft: "20px"}
                })
            },
            {
                dataField: 'email',
                text: t('Email'),
                filter: textFilter({
                    placeholder: t("Filter"),
                    style: {marginLeft: "20px"}
                })
            }
        ]
        return <BootstrapTable striped keyField='id' columns={columns} data={this.state.accountsList} filter={filterFactory()}/>;
        return <Table striped bordered hover>
            <thead>
            <tr>
                <th>#</th>
                <th>
                    <div className="LoginInfo">{t("Login-Noun")}</div>

                </th>
                <th>

                    <div className="NameInfo">{t("Name and Surname")}</div>

                </th>
                <th>
                    <div className="EmailInfo">{t("Email")}</div>
                </th>
            </tr>
            </thead>
            <tbody>
            {this.state.accountsList.map(this.renderAccount)}
            </tbody>
        </Table>
    }

    renderNull() {
        return <div>Trwa wczytywanie...</div>

    }

    render() {
        return <div className="AccountListGroup">
            {!this.state.accountsList.length ? this.renderNull() : this.renderAccounts()}
        </div>
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
