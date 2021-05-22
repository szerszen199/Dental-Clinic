import React from "react";
import "./Admin.css";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import {useTranslation} from "react-i18next";
import MyAccount from "../Common/Common";


function Admin() {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.pathname}>
            <LinkContainer to="/accounts">
                <Nav.Link>{t("Users Accounts")}</Nav.Link>
            </LinkContainer>
            <MyAccount/>
        </Nav>
    );
}


export default Admin;
