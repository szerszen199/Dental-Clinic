import {NavDropdown} from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import {Link} from "react-router-dom";
import {logout} from "../../../components/Login/Logout";
import React from "react";
import {useTranslation} from "react-i18next";

export default function MyAccount() {
    const {t} = useTranslation();

    const navStyle = {paddingLeft: "8px", color: "rgb(127, 127, 127)", marginLeft: "10px"}

    return (
        <NavDropdown title={t("My Account")} id="navbarScrollingDropdown">
            <Nav.Link style={navStyle} as={Link} to="/account">{t("Edit My Account")}</Nav.Link>
            <span onClick={logout} style={navStyle}>{t("Logout")}</span>
        </NavDropdown>
    )
}
