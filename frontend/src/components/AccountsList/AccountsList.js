import React from "react";
import "./AccountsList.css";

export default class AccountsList extends Comment {


    constructor(props) {
        super(props);
        //let accounts = props.accounts;
        let accounts = [new Account("Jan Kowalski", "jkowalski@yahoo.com"), new Account("Adam Nowak", "adamnowak@gmail.com"), new Account("Anna Kowalska", "aka@gmail.com"), new Account("Janek Kowal", "janektoja@gmail.com")]
        this.accountsList = accounts.map((account) => <div className="Account">
            <div className="Name">{account.name}</div>
            <div className="Email">{account.email}</div>
        </div>)
        this.nameList = accounts.map((account) =>
            <div className="Name">{account.name}</div>)
        this.emailList = accounts.map((account) =>
            <div className="Email">{account.email}</div>)
    }

    render() {
        return <div className="Home">
            <div className="lander">
                <div className="AccountInfo"><div className="NameInfo"> Imie i Nazwisko</div> <div className="EmailInfo"> Email</div> </div>
                <div className="AccountsList"> {this.accountsList} </div>
            </div>
        </div>
    }
}

class Account {
    constructor(name, email) {
        this.name = name;
        this.email = email;
    }

}

