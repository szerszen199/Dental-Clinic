import React, {Suspense} from "react";
import Navbar from "react-bootstrap/Navbar";
import {Col, Container, Row} from "react-bootstrap";
import {DarkModeSwitch} from "react-toggle-dark-mode";
import Routes from "../../router/Routes";
import BreadCrumbs from "../../components/Breadcrumbs/Breadcrumbs";
import ReadinessComponent from "../../components/GetReadinessResource/Readiness"
import Doctor from "../Users/Doctor/Doctor";

import {withTranslation} from "react-i18next";
import i18n from "../../transaltions/i18n";
import Admin from "../Users/Admin/Admin";
import Patient from "../Users/Patient/Patient";
import Receptionist from "../Users/Receptionist/Receptionist";
import Guest from "../Guest/Guest";
import axios from "axios";
import Cookies from "js-cookie";
import {logout} from "../../components/Login/Logout";
import {MDBContainer, MDBFooter} from "mdbreact";
import './MainView.css';
import {Link} from "react-router-dom";
import findDefaultRole from "../../roles/findDefaultRole";

const roleAdminName = process.env.REACT_APP_ROLE_ADMINISTRATOR
const roleDoctorName = process.env.REACT_APP_ROLE_DOCTOR
const roleReceptionistName = process.env.REACT_APP_ROLE_RECEPTIONIST
const rolePatientName = process.env.REACT_APP_ROLE_PATIENT
const roleGuestName = process.env.REACT_APP_ROLE_GUEST

const accessLevelDictionary = {
    [roleGuestName]: "rgba(1, 1, 1, 0.1)",
    [rolePatientName]: "rgba(93, 188, 242, 0.2)",
    [roleReceptionistName]: "rgba(192, 255, 0, 0.4)",
    [roleDoctorName]: "rgba(255, 216, 0, 0.2)",
    [roleAdminName]: "rgba(238, 0, 0, 0.1)",
};
export const jwtCookieExpirationTime = process.env.REACT_APP_JWT_EXPIRATION_MS / (24 * 60 * 60 * 100)
const actualAccessLevel = Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) !== undefined ? Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) : roleGuestName;

class MainViewWithoutTranslation extends React.Component {
    urlPL = "https://img.icons8.com/color/96/000000/poland-circular.png";
    urlEN = "https://img.icons8.com/color/48/000000/great-britain-circular.png";

    constructor(props) {
        super(props);
        this.state = {
            isDarkMode: false,
            language: "PL",
            flag: this.urlEN,
            login: "",

        }
    }

    handleOnClick() {
        if (this.state.language === "EN") {
            this.setPL()
            i18n.changeLanguage("PL");
        } else {
            this.setEN()
            i18n.changeLanguage("EN");
        }
    }

    setEN() {
        this.setState({language: "EN", flag: this.urlPL})
    }

    setPL() {
        this.setState({language: "PL", flag: this.urlEN})
    }

    makeRefreshRequest() {
        let JWTRefreshToken = localStorage.getItem(process.env.REACT_APP_JWT_REFRESH_TOKEN_STORAGE_NAME);
        let JWTAuthToken = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
        if (JWTRefreshToken != null && JWTRefreshToken !== "null") {
            axios.post(process.env.REACT_APP_BACKEND_URL + "auth/refresh", {
                refreshToken: localStorage.getItem(process.env.REACT_APP_JWT_REFRESH_TOKEN_STORAGE_NAME)
            }, {
                headers: {Authorization: "Bearer " + JWTAuthToken}
            }).then((response) => {
                // TODO: Czas expieracji.
                Cookies.set(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME, response.data.authJwtToken.token, {expires: jwtCookieExpirationTime});
                Cookies.set(process.env.REACT_APP_ROLES_COOKIE_NAME, response.data.roles, {expires: jwtCookieExpirationTime});
                if (Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) == null) {
                    Cookies.set(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME, findDefaultRole(response.data.roles), {expires: process.env.jwtCookieExpirationTime});
                }
                localStorage.setItem(process.env.REACT_APP_JWT_REFRESH_TOKEN_STORAGE_NAME, response.data.refreshJwtToken.token);
            }).catch((response) => {
                // todo cos z tym response?
                console.log(response);
                logout();
            })
        }
    }


    componentDidMount() {
        this.makeRefreshRequest();
        setInterval(this.makeRefreshRequest, parseInt(process.env.REACT_APP_JWT_EXPIRATION_MS) / 10);
        let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
        console.log(token)
        if (typeof token !== 'undefined' && token !== null && token !== "null" && token !== undefined) {
            this.setState({
                login: Cookies.get(process.env.REACT_APP_LOGIN_COOKIE)
            })
        }
    }


    render() {
        const {t} = this.props;

        return (
            <div className="App container py-3 ">
                <Navbar collapseOnSelect expand="md" className=" nav-bar shadow-box-example mb-3"
                        style={{backgroundColor: accessLevelDictionary[actualAccessLevel]}}>
                    <div id="navbarDiv">
                        <Container fluid>
                            <Row>
                                <Col>
                                    <Navbar.Brand as={Link} to="/"
                                                  className="font-weight-bold text-muted justify-content-end">
                                        {t("Home")}
                                    </Navbar.Brand>
                                </Col>
                                <Col className="d-flex  justify-content-end">
                                    <Navbar.Toggle/>
                                    <Navbar.Collapse className="justify-content-end">
                                        <CurrentUserViewComponent/>
                                    </Navbar.Collapse>
                                </Col>
                            </Row>
                            <Row> <Col> <BreadCrumbs/> </Col>
                                <Col className="d-flex justify-content-end"
                                     style={{maxHeight: "30px", marginRight: "10px"}}>
                                    <p style={{
                                        color: "gray",
                                        marginTop: "5px",
                                    }}>{this.state.login === "" ? '' : 'login: ' + this.state.login}</p>
                                    <DarkModeSwitch
                                        style={{marginLeft: '1rem'}}
                                        checked={this.state.isDarkMode}
                                        onChange={(e) => this.setState({isDarkMode: e})}
                                        size={30}
                                        sunColor={"#FFDF37"}
                                        moonColor={"#bfbfbb"}
                                    />
                                    <img id="flag" onClick={(e) => this.handleOnClick()}
                                         src={this.state.flag} alt="Logo"/>
                                </Col>
                            </Row>
                        </Container>
                    </div>
                </Navbar>
                <Routes/>
                <ReadinessComponent/>
                <MDBFooter color="blue" className="font-small pt-4 mt-4" id="footer">
                    <div className="footer-copyright text-right py-3">
                        <MDBContainer fluid>
                            {t("Dental Clinic")}, &copy; {new Date().getFullYear()} Copyright by 2021SSBD01
                        </MDBContainer>
                    </div>
                </MDBFooter>
            </div>
        );
    }
}

function CurrentUserViewComponent() {
    const myMap = new Map();
    myMap.set(roleAdminName, Admin())
    myMap.set(rolePatientName, Patient())
    myMap.set(roleReceptionistName, Receptionist())
    myMap.set(roleDoctorName, Doctor())
    let currentRole = Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME)
    return myMap.has(currentRole) ? myMap.get(currentRole) : Guest()
}

const MainViewTr = withTranslation()(MainViewWithoutTranslation)

export default function MainView() {
    return (
        <Suspense fallback="loading">
            <MainViewTr/>
        </Suspense>
    );
}
