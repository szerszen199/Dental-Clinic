import {NavDropdown} from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import {Link} from "react-router-dom";
import {logout} from "../../../components/Login/Logout";
import React from "react";
import {useTranslation} from "react-i18next";
import "./Common.css"

export default function MyAccount() {
    const {t} = useTranslation();

    return (
        <NavDropdown title={t("My Account")} id="navbarScrollingDropdown">
            <Nav.Link className="navStyle" style={{color: "rgb(127, 127, 127)"}} as={Link} to="/account">{t("Edit My Account")}</Nav.Link>
            <NavDropdown.Divider/>
            <Nav.Link onClick={logout} style={{color: "rgb(127, 127, 127)"}} className="navStyle">{t("Logout")}</Nav.Link>
        </NavDropdown>
    )
}
