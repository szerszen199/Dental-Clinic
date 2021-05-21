import React from "react";
import "./Guest.css";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import {useTranslation} from "react-i18next";

const Guest = () => {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.pathname}>
            <LinkContainer to="/register">
                <Nav.Link>{t("Register")}</Nav.Link>
            </LinkContainer>
            <LinkContainer to="/login">
                <Nav.Link>{t("Login")}</Nav.Link>
            </LinkContainer>
        </Nav>
    );
};

export default Guest;
