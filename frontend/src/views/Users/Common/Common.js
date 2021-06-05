import {Dropdown, DropdownButton, NavDropdown} from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import {Link} from "react-router-dom";
import {logout} from "../../../components/Login/Logout";
import React, {Fragment} from "react";
import {useTranslation} from "react-i18next";
import "./Common.css"
import Cookies from "js-cookie";
import {jwtCookieExpirationTime} from "../../MainView/MainView";
import {changeRoleRequest} from "../../../components/Account/Role/ChangeRoleRequest";

export default function MyAccount() {
    const {t} = useTranslation();

    let accessLevels = JSON.parse(Cookies.get(process.env.REACT_APP_ROLES_COOKIE_NAME));
    let accessLevelLinks = [];
    for (let i in accessLevels) {
        accessLevelLinks.push(<Dropdown.Item
            style={{color: "rgb(127, 127, 127)"}}
            key={i}
            onClick={() => {
                if (changeRoleRequest(accessLevels[i])) {
                    updateAccessLevel(accessLevels[i])
                }
            }}> {t(accessLevels[i])}</Dropdown.Item>);
    }
    return (
        <Fragment>
            <NavDropdown title={t("My Account")} id="navbarScrollingDropdown">
                <Nav.Link className="navStyle" style={{color: "rgb(127, 127, 127)"}} as={Link}
                          to="/account">{t("Edit My Account")}</Nav.Link>
                <NavDropdown.Divider/>
                <Nav.Link onClick={logout} style={{color: "rgb(127, 127, 127)"}}
                          className="navStyle">{t("Logout")}</Nav.Link>
            </NavDropdown>
            <DropdownButton variant="secondary btn-sm" style={{marginTop: "5px"}}
                            title={t(Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME))}>
                {accessLevelLinks}
            </DropdownButton>
        </Fragment>
    )
}

function updateAccessLevel(access_level) {
    Cookies.set(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME, access_level, {expires: jwtCookieExpirationTime});
    window.location.hash = "#/home";
    window.location.reload();
}


