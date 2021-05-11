import React from "react";
import "./Account.css";
import EditAccount from "../EditAccount/EditAccount";
import {Col, Container, Row} from "react-bootstrap";

function Account() {
    return (
        <div className="EditAccount">
            <Container>
                <Row>
                    <Col>2 of 2</Col>
                    <Col><EditAccount/></Col>
                    <Col/>
                </Row>
            </Container>
        </div>
    );
}

export default Account;
