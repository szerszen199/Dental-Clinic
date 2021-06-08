import React from "react";
import "./Account.css";
import EditAccount from "../EditAccount/EditAccount";
import EditPassword from "./EditPassword/EditPassword";
import {Col, Container, Row} from "react-bootstrap";
import {useTranslation} from "react-i18next";

function Account() {
    const {t} = useTranslation();
    document.title = t("Dental Clinic") + " - " + t("Edit My Account");
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
