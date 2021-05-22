import React from "react";
import Navbar from "react-bootstrap/Navbar";
import {Col, Container, Row} from "react-bootstrap";
import {DarkModeSwitch} from "react-toggle-dark-mode";
import Routes from "../../router/Routes";
import BreadCrumbs from "../../components/Breadcrumbs/Breadcrumbs";
import ReadinessComponent from "../../components/GetReadinessResource/Readiness"
import Doctor from "../Users/Doctor/Doctor";

import {useTranslation} from "react-i18next";
import i18n from "../../transaltions/i18n";
import Admin from "../Users/Admin/Admin";
import Patient from "../Users/Patient/Patient";
import Receptionist from "../Users/Receptionist/Receptionist";
import Guest from "../Guest/Guest";
import {userRolesStorageName} from "../../components/Login/LoginRequest";
import axios from "axios";
import Cookies from "js-cookie";
import {logout} from "../../components/Login/Logout";
import {MDBContainer, MDBFooter} from "mdbreact";
import './MainView.css';

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
// TODO: zastąpić użycia tego aktualnie wybraną rolą po zaimplementowaniu
const actualAccessLevel = roleDoctorName;

export default class MainView extends React.Component {
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
    //    const {t} = useTranslation();
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
            axios.get(process.env.REACT_APP_BACKEND_URL + "account/info", {
                headers: {
                    Authorization: "Bearer " + token
                }
            })
                .then(res => res.data)
                .then(result => {
                    this.setState({
                        login: result.login,
                    });
                    console.log(this.state.login)
                })
                .catch(result => {
                    // TODO:
                    console.log(result);
                })
        } else{
            this.setState({
                login: "",
            });
        }
    }


    render() {
        return (
            <div className="App container py-3 ">
                <Navbar collapseOnSelect expand="md" className=" nav-bar shadow-box-example mb-3"
                        style={{backgroundColor: accessLevelDictionary[actualAccessLevel]}}>
                    <div id="navbarDiv">
                        <Container fluid>
                            <Row>
                                <Col>
                                    <Navbar.Brand to="/" className="font-weight-bold text-muted justify-content-end">
                                        {t("Home")}
                                    </Navbar.Brand>
                                </Col>
                                <Col className="d-flex  justify-content-end">
                                    <Navbar.Toggle/>
                                    <Navbar.Collapse className="justify-content-end">
                                        <Wybierz/>
                                    </Navbar.Collapse>
                                </Col>
                            </Row>
                            <Row> <Col> <BreadCrumbs/> </Col> <Col style={{
                                textAlign: "right",
                                color: "gray"
                            }}> {this.state.login === "" ? '' : 'login: ' + this.state.login}</Col>
                                <Col className="d-flex justify-content-end"
                                     style={{maxHeight: "30px", marginRight: "10px"}}>
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
                            Klinika Stomatologiczna, &copy; {new Date().getFullYear()} Copyright by 2021SSBD01
                        </MDBContainer>
                    </div>
                </MDBFooter>
            </div>
        );
    }
}

function Wybierz() {
    function isEmpty(value) {
        return (value == null || value.length === 0);
    }

    // TODO: Ma być możliwość wyboru jaką z ról które mamy chcemy widzieć tzn mamy się móc przełączać między rolami
    //  Nie ma tego narazie więc jest tak
    let levels = Cookies.get(process.env.REACT_APP_ROLES_COOKIE_NAME);
    if (!isEmpty(levels)) {
        if (levels.includes(roleAdminName)) {
            return Admin();
        } else if (levels.includes(rolePatientName)) {
            return Patient();
        } else if (levels.includes(roleReceptionistName)) {
            return Receptionist();
        } else if (levels.includes(roleDoctorName)) {
            return Doctor();
        }
    }
    return Guest();
}
