import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./Account.css";

export default class Account extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            isDisabled: true,
            text: "Edit",
            login: "",
            email:"",
            password: "",
            firstName: "",
            lastName: "",
            phoneNumber : "",
            pesel: ""
        }
    }

    validateForm() {
        // Todo: zrobić walidację taką jaką wymaga projekt
        function loginCorrect() {
            return this.state.login.length > 0;
        }

        function emailCorrect() {
            return this.state.email.length > 0;
        }

        function passwordCorrect() {
            return this.state.password.length > 0;
        }

        function firstNameCorrect() {
            return this.state.firstName.length > 0;
        }

        function lastNameCorrect() {
            return this.state.lastName.length > 0;
        }

        function phoneNumberCorrect() {
            return this.state.phoneNumber.length > 0;
        }

        // TODO: przypadek obcokrajowca wymusza że peselu może nie być ale nadal warto by go zwalidowac, tylko jak?
        function peselCorrect() {
            return true;
        }

        return emailCorrect() && passwordCorrect() && loginCorrect() && firstNameCorrect() && lastNameCorrect() && phoneNumberCorrect() && peselCorrect();
    }


    // Todo: prawdopodobnie wysyłać zapytanie do backendu tutaj, chciałbym zrobić tak jak w vue się da żeby jeśli odpalam w trybie debug front to łącze z localhostem, narazie nie ruszam.
    handleSubmit(event) {
        event.preventDefault();
    }

    handleOnClick() {
        if(this.state.isDisabled === true){
            this.setEditable()
        } else{
            this.setNotEditable()
        }
    }

    setEditable(){
        this.setState({
            isDisabled: false,
            text: "Save"
        });
    }

    setNotEditable(){
        this.validateForm()
        this.setState({
            isDisabled: true,
            text: "Edit"
        });
    }

    // todo: Czy dodawać tutaj też język do wyboru z en / pl? W dto go nie ma
    render() {
        return (
            <div className="Registration">
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group size="lg" controlId="login">
                        <Form.Label>Login</Form.Label>
                        <Form.Control
                            autoFocus
                            type="login"
                            value={this.state.login}
                            disabled={true}
                            onChange={(e) => this.setState({login: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="email">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            autoFocus
                            type="email"
                            value={this.state.email}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({email: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="password">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            value={this.state.password}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({password: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="firstName">
                        <Form.Label>First Name</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.firstName}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({pesel: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="lastName">
                        <Form.Label>Last Name</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.lastName}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({lastName: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="phoneNumber">
                        <Form.Label>Phone Number</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.phoneNumber}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({phoneNumber: e.target.value})}
                        />
                    </Form.Group>
                    {/*Todo: co z peselem dla obcokrajowca? Nic czy coś innnego? Narazie zrobiłem że może być pusty*/}
                    <Form.Group size="lg" controlId="pesel">
                        <Form.Label>Pesel</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.pesel}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({pesel: e.target.value})}
                        />
                    </Form.Group>
                    <Button block size="lg" type="submit"
                        // disabled={!validateForm()}
                            onClick={this.handleOnClick.bind(this)}>
                        {this.state.text}
                    </Button>
                </Form>
            </div>
        );
    }
}
