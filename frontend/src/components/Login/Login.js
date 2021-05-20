import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./Login.css";
import {makeLoginRequest} from "./LoginRequest";

export default function Login() {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");

    function validateForm() {
        return login.length > 0 && login.length <= 60 && password.length > 0;
    }

    function handleSubmit(event) {
        event.preventDefault();
        makeLoginRequest(login, password);
    }

    return (
        <div className="Login">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="login">
                    <Form.Label>Login</Form.Label>
                    <Form.Control
                        autoFocus
                        type="text"
                        value={login}
                        onChange={(e) => setLogin(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <Button block size="lg" type="submit" disabled={!validateForm()}>
                    Login
                </Button>
            </Form>
        </div>
    );
}
