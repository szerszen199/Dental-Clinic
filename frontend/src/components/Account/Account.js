import React from "react";
import "./Account.css";
import EditAccount from "../EditAccount/EditAccount";
import {Col, Container, ListGroup, Row} from "react-bootstrap";

function Account() {
    return (
        <div className="EditAccount">
            {/*<ListGroup horizontal>*/}
            {/*    <ListGroup.Item><EditAccount/></ListGroup.Item>*/}
            {/*    <ListGroup.Item>ListGroup</ListGroup.Item>*/}
            {/*    <ListGroup.Item>renders</ListGroup.Item>*/}
            {/*    <ListGroup.Item>horizontally!</ListGroup.Item>*/}
            {/*</ListGroup>*/}
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
