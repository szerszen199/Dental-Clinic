import React from "react";
import "./Admin.css";
import Nav from "react-bootstrap/Nav";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import {useTranslation} from "react-i18next";
import MyAccount from "../Common/Common";
import {Link} from "react-router-dom";

function Admin() {
    const {t} = useTranslation();
    document.title = t("Dental Clinic");
    return (
        <Nav activeKey={window.location.pathname}>
            <Nav.Link as={Link} to="/accounts">{t("Users Accounts")}</Nav.Link>
            <MyAccount/>
        </Nav>
    );
}


export default Admin;
