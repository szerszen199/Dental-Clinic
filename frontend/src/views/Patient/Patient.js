import React from "react";
import "./Patient.css";
import Nav from "react-bootstrap/Nav";
import {NavDropdown} from "react-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import {logout} from "../../components/Login/Logout";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

const Patient = () => {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.pathname}>
            <NavDropdown title="Appointments" id="navbarScrollingDropdown">
                <Nav.Link to="/my-appointments">{t("My appointments")}</Nav.Link>
                <Nav.Link to="/plan-appointment">{t("Plan an appointment")}</Nav.Link>
                <NavDropdown.Divider/>
                <Nav.Link to="/list-doctors">{t("List of doctors")}</Nav.Link>
            </NavDropdown>
            <Nav.Link as={Link} to="/prescriptions">{t("My prescriptions")}</Nav.Link>
            <NavDropdown title="My Account" id="navbarScrollingDropdown">
                <Nav.Link as={Link} to="/account">{t("Edit My Account")}</Nav.Link>
                    <span onClick={logout}
                          style={{paddingLeft: "8px", color: "rgb(127, 127, 127)"}}>{t("Logout")}</span>
            </NavDropdown>
        </Nav>
    );
};

export default Patient;
