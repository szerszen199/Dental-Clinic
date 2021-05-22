import React from "react";
import "./Guest.css";
import Nav from "react-bootstrap/Nav";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

const Guest = () => {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.pathname}>
            <Nav.Link as={Link} to="/register">{t("Register")}</Nav.Link>
            <Nav.Link as={Link} to="/login">{t("Login")}</Nav.Link>
        </Nav>
    );
};

export default Guest;
