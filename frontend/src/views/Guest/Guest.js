import React from "react";
import "./Guest.css";
import Nav from "react-bootstrap/Nav";
import {useTranslation} from "react-i18next";

const Guest = () => {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.pathname}>
            <Nav.Link to="/register">{t("Register")}</Nav.Link>
            <Nav.Link to="/login">{t("Login")}</Nav.Link>
        </Nav>
    );
};

export default Guest;
