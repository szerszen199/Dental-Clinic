import React from "react";
import "./AccountsList.css";
import {Table} from "react-bootstrap";
import {makeAccountsListRequest} from "./AccountsListRequest";

class AccountsList extends React.Component {


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
                <td>{index+1}</td>
                <td>{person.login}</td>
                <td>{person.name}</td>
                <td>{person.email}</td>
            </tr>
        )
    }

    renderAccounts() {
        return <Table striped bordered hover>
            <thead>
            <tr>
                <th>#</th>
                <th>
                    <div className="LoginInfo">Login</div>
                </th>
                <th>
                    <div className="NameInfo">Name and surname</div>
                </th>
                <th>
                    <div className="EmailInfo">Email</div>
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

export default AccountsList

