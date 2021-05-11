import React from "react";
import "./Account.css";
import EditAccount from "./EditAccount/EditAccount";
import EditPassword from "./EditPassword/EditPassword";
import {Col, Container, Row} from "react-bootstrap";

function Account() {
    return (
        <div className="Account">
            <Container>
                <Row>
                    <Col><EditAccount className="EditAccount"/></Col>
                    <Col style={{maxWidth: "60px"}}/>
                    <Col><EditPassword className="EditPassword"/></Col>
                </Row>
            </Container>
        </div>
    );
}

export default Account;
