import React from "react";
import "./Receptionist.css";
import Nav from "react-bootstrap/Nav";
import {NavDropdown} from "react-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import {useTranslation} from "react-i18next";
import MyAccount from "../Common/Common";
import {Link} from "react-router-dom";


function Receptionist() {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.pathname}>
            <NavDropdown title={t("Appointments")} id="navbarScrollingDropdown">
                <Nav.Link as={Link} style={{color: "rgb(127, 127, 127)"}} className="navMenu" to="/plan-appointment">{t("Plan an appointment")}</Nav.Link>
            </NavDropdown>
            <Nav.Link as={Link} to="/accounts">{t("Users Accounts")}</Nav.Link>
            <MyAccount/>
        </Nav>
    );
}


export default Receptionist;
