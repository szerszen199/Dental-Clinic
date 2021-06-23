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
import {darkModeRequest} from "../../components/DarkMode/DarkModeRequest"
import {getBrowserLanguage, languageRequest} from "../../components/Language/LanguageRequest";

const roleAdminName = process.env.REACT_APP_ROLE_ADMINISTRATOR
const roleDoctorName = process.env.REACT_APP_ROLE_DOCTOR
const roleReceptionistName = process.env.REACT_APP_ROLE_RECEPTIONIST
const rolePatientName = process.env.REACT_APP_ROLE_PATIENT
const roleGuestName = process.env.REACT_APP_ROLE_GUEST

let accessLevelDictionary = {
    [roleGuestName]: "rgba(1, 1, 1, 0.1)",
    [rolePatientName]: "rgba(93, 188, 242, 0.2)",
    [roleReceptionistName]: "rgba(192, 255, 0, 0.4)",
    [roleDoctorName]: "rgba(255, 216, 0, 0.2)",
    [roleAdminName]: "rgba(238, 0, 0, 0.1)",
};
let loginColor = "grey"
export const jwtCookieExpirationTime = process.env.REACT_APP_JWT_EXPIRATION_MS / (24 * 60 * 60 * 100)
const actualAccessLevel = Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) !== undefined ? Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) : roleGuestName;

class MainViewWithoutTranslation extends React.Component {

    flags = {
        "PL": "https://img.icons8.com/color/96/000000/poland-circular.png",
        "EN": "https://img.icons8.com/color/48/000000/great-britain-circular.png"
    };

    constructor(props) {
        super(props);
        this.state = {
            isDarkMode: "",
            login: "",
            flag: "",
            language: "",
        }
    }

    handleOnClick() {
        if (this.state.language === "EN") {
            this.setInterfaceLanguage("PL")
        } else {
            this.setInterfaceLanguage("EN")
        }
    }

    setInterfaceLanguage(language) {
        this.setState({
            language: language,
            flag: language === "PL" ? this.flags["PL"] : this.flags["EN"]
        });

        Cookies.set(process.env.REACT_APP_LANGUAGE_COOKIE, language, {secure: true, sameSite: 'none'});
        i18n.changeLanguage(language);
        languageRequest(language);
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
                Cookies.set(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME, response.data.authJwtToken.token, {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
                Cookies.set(process.env.REACT_APP_ROLES_COOKIE_NAME, response.data.roles, {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
                Cookies.set(process.env.REACT_APP_LOGIN_COOKIE, response.data.username, {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
                if (Cookies.get(process.env.REACT_APP_LANGUAGE_COOKIE) != null) {
                    Cookies.set(process.env.REACT_APP_LANGUAGE_COOKIE, Cookies.get(process.env.REACT_APP_LANGUAGE_COOKIE), {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
                }
                if (Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) == null) {
                    Cookies.set(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME, findDefaultRole(response.data.roles), {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
                } else {
                    Cookies.set(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME, Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME), {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
                }
                if (Cookies.get(process.env.REACT_APP_DARK_MODE_COOKIE) != null) {
                    Cookies.set(process.env.REACT_APP_DARK_MODE_COOKIE, Cookies.get(process.env.REACT_APP_DARK_MODE_COOKIE), {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
                }
                localStorage.setItem(process.env.REACT_APP_JWT_REFRESH_TOKEN_STORAGE_NAME, response.data.refreshJwtToken.token);
            }).catch((response) => {
                logout();
            })
        }
    }


    componentDidMount() {
        this.makeRefreshRequest();
        setInterval(this.makeRefreshRequest, parseInt(process.env.REACT_APP_JWT_EXPIRATION_MS) / 2);
        let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
        if (typeof token !== 'undefined' && token !== null && token !== "null" && token !== undefined) {
            this.setState({
                login: Cookies.get(process.env.REACT_APP_LOGIN_COOKIE),
                isDarkMode: (Cookies.get(process.env.REACT_APP_DARK_MODE_COOKIE) === 'true'),
                language: Cookies.get(process.env.REACT_APP_LANGUAGE_COOKIE),
                flag: Cookies.get(process.env.REACT_APP_LANGUAGE_COOKIE) === "PL" ? this.flags["PL"] : this.flags["EN"]
            }, function () {
                i18n.changeLanguage(this.state.language);
                accessLevelDictionary = darkModeStyleChange(Cookies.get(process.env.REACT_APP_DARK_MODE_COOKIE));
            })
        } else {
            let tmp = getBrowserLanguage();
            this.setState({
                language: tmp,
                flag: tmp === "PL" ? this.flags["PL"] : this.flags["EN"]
            }, function () {
                i18n.changeLanguage(this.state.language)
            });
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
                                        color: loginColor,
                                        marginTop: "5px",
                                    }}>{this.state.login === "" || this.state.login === undefined ? '' : t("UserLogin") + ': ' + this.state.login}</p>
                                    <DarkModeSwitch
                                        style={{marginLeft: '1rem'}}
                                        checked={this.state.isDarkMode}
                                        onChange={(e) => {
                                            this.setState({isDarkMode: e})
                                            Cookies.set(process.env.REACT_APP_DARK_MODE_COOKIE, e, {expires: process.env.jwtCookieExpirationTime, secure: true, sameSite: 'none'})
                                            accessLevelDictionary = darkModeStyleChange(e)
                                            if (this.state.login) {
                                                darkModeRequest(e)
                                            }
                                        }}
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
                <MDBFooter color="blue" className="font-small pt-4 mt-4 bottom" id="footer">
                    <div className="footer-copyright text-right py-3">
                        <MDBContainer>
                            {t("Dental Clinic")}, &copy; {new Date().getFullYear()} Copyright by 2021SSBD01
                        </MDBContainer>
                    </div>
                </MDBFooter>
            </div>
        );
    }
}

function darkModeStyleChange(isDarkMode) {
    if (isDarkMode === true) {
        document.getElementById("root").style.backgroundColor = "#a8b4ae";
        loginColor = "black"
        return {
            [roleGuestName]: "rgba(1, 1, 1, 0.5)",
            [rolePatientName]: "rgba(34, 55, 147, 0.2)",
            [roleReceptionistName]: "rgba(46, 95, 0, 0.4)",
            [roleDoctorName]: "rgba(146, 134, 0, 0.2)",
            [roleAdminName]: "rgba(106, 0, 0, 0.1)",
        };

    } else {
        document.getElementById("root").style.backgroundColor = "#ffffff";
        loginColor = "grey"
        return {
            [roleGuestName]: "rgba(1, 1, 1, 0.1)",
            [rolePatientName]: "rgba(93, 188, 242, 0.2)",
            [roleReceptionistName]: "rgba(192, 255, 0, 0.4)",
            [roleDoctorName]: "rgba(255, 216, 0, 0.2)",
            [roleAdminName]: "rgba(238, 0, 0, 0.1)",
        };
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
