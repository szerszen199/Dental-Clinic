import React from "react";
import "./AccountsList.css";
import {Table} from "react-bootstrap";

export default class AccountsList extends Comment {


    constructor(props) {
        super(props);
        this.accountsList = [new Account("Jan Kowalski", "jkowalski@yahoo.com"), new Account("Adam Nowak", "adamnowak@gmail.com"), new Account("Anna Kowalska", "aka@gmail.com"), new Account("Janek Kowal", "janektoja@gmail.com")]
    }

    renderAccount(person, index) {
        return (
            <tr key={index}>
                <td>1</td>
                <td>{person.name}</td>
                <td>{person.email}</td>
            </tr>
        )
    }

    render() {
        return <div className="AccountListGroup">
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>#</th>
                    <th>
                        <div className="NameInfo">Name and surname</div>
                    </th>
                    <th>
                        <div className="EmailInfo">Email</div>
                    </th>
                </tr>
                </thead>
                <tbody>
                {this.accountsList.map(this.renderAccount)}
                </tbody>
            </Table>
        </div>
    }
}

class Account {
    constructor(name, email) {
        this.name = name;
        this.email = email;
    }

}

