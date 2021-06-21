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
    document.title = t("Dental Clinic");
    return (
        <Nav activeKey={window.location.hash}>
            <NavDropdown title={t("Appointments")} id="navbarScrollingDropdown">
                <Nav.Link as={Link} style={{color: "rgb(127, 127, 127)"}} className="navMenu" to="/add-appointment">{t("Add new appointment")}</Nav.Link>
                <Nav.Link as={Link} style={{color: "rgb(127, 127, 127)"}} className="navMenu" to="/plan-appointment">{t("Plan an appointment")}</Nav.Link>
            </NavDropdown>
            <Nav.Link as={Link} to="/list-patients">{t("Patients List")}</Nav.Link>
            <MyAccount/>
        </Nav>
    );
}


export default Receptionist;
