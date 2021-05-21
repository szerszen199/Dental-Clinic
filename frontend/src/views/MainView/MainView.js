import React from "react";
import Navbar from "react-bootstrap/Navbar";
import {LinkContainer} from "react-router-bootstrap";
import {Col, Container, Row} from "react-bootstrap";
import {DarkModeSwitch} from "react-toggle-dark-mode";
import Routes from "../../router/Routes";
import BreadCrumbs from "../../components/Breadcrumbs/Breadcrumbs";
import Guest from "../Guest/Guest";
import Receptionist from "../Receptionist/Receptionist";
import Admin from "../Admin/Admin";
import ReadinessComponent from "../../components/GetReadinessResource/Readiness"
import Patient from "../Patient/Patient";
import Doctor from "../Doctor/Doctor";
import axios from "axios";import {JWTRefreshTokenStorageName, JWTTokenCookieName, RolesCookieName} from "../../components/Login/LoginRequest";
import Cookies from "js-cookie";
import {logout} from "../../components/Login/Logout";

const roleAdminName = process.env.REACT_APP_ROLE_ADMINISTRATOR
const roleDoctorName = process.env.REACT_APP_ROLE_DOCTOR
const roleReceptionistName = process.env.REACT_APP_ROLE_RECEPTIONIST
const rolePatientName = process.env.REACT_APP_ROLE_PATIENT
const roleGuestName = process.env.REACT_APP_ROLE_GUEST

const accessLevelDictionary = {
    roleGuestName: "rgba(1, 1, 1, 0.1)",
    rolePatientName: "rgba(93, 188, 242, 0.2)",
    roleReceptionistName: "rgba(192, 255, 0, 0.4)",
    roleDoctorName: "rgba(255, 216, 0, 0.2)",
    roleAdminName: "rgba(238, 0, 0, 0.1)",
};
export const jwtCookieExpirationTime = process.env.REACT_APP_JWT_EXPIRATION_MS / (24 * 60 * 60 * 100)
let actualAccessLevel = "Doctor";

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
    }

    makeRefreshRequest() {
        let variable = localStorage.getItem(JWTRefreshTokenStorageName);
        if (variable != null && variable !== "null") {
            axios.post(process.env.REACT_APP_BACKEND_URL + "auth/refresh", {
                refreshToken: localStorage.getItem(JWTRefreshTokenStorageName)
            }).then((response) => {
                // TODO: Czas expieracji.
                Cookies.set(JWTTokenCookieName, response.data.authJwtToken.token, { expires: jwtCookieExpirationTime});
                Cookies.set(RolesCookieName, response.data.roles, {expires: jwtCookieExpirationTime});
                localStorage.setItem(JWTRefreshTokenStorageName, response.data.refreshJwtToken.token);
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
        let token = Cookies.get(JWTTokenCookieName);
        if (typeof token !== 'undefined' && token !== null && token !== "null") {
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
        }
    }

    handleOnClick() {
        if (this.state.language === "EN") {
            this.setPL()
        } else {
            this.setEN()
        }
    }

    setEN() {
        this.setState({language: "EN", flag: this.urlPL})
    }

    setPL() {
        this.setState({language: "PL", flag: this.urlEN})
    }

    render() {
        return (
            <div className="App container py-3 ">
                <Navbar collapseOnSelect expand="md" className=" nav-bar shadow-box-example mb-3"
                        style={{backgroundColor: accessLevelDictionary[actualAccessLevel]}}>
                    <div style={{width: "100%"}}>
                        <Container fluid>
                            <Row>
                                <Col>
                                    <LinkContainer to="/">
                                        <Navbar.Brand className="font-weight-bold text-muted">
                                            Home
                                        </Navbar.Brand>
                                    </LinkContainer>
                                </Col>
                                <Col>
                                    <Navbar.Toggle/>
                                    <Navbar.Collapse className="justify-content-end">
                                        <Wybierz/>
                                        <DarkModeSwitch
                                            style={{marginLeft: '1rem'}}
                                            checked={this.state.isDarkMode}
                                            onChange={(e) => this.setState({isDarkMode: e})}
                                            size={30}
                                            sunColor={"#FFDF37"}
                                            moonColor={"#bfbfbb"}
                                        />
                                        <img onClick={(e) => this.handleOnClick()}
                                             style={{marginLeft: "10px", maxWidth: "30px"}}
                                             src={this.state.flag} alt="Logo"/>

                                    </Navbar.Collapse>
                                </Col>
                            </Row>
                            <Row> <Col> <BreadCrumbs/> </Col> <Col style={{
                                textAlign: "right",
                                color: "gray"
                            }}> login: {this.state.login}</Col> </Row>
                        </Container>
                    </div>
                </Navbar>
                <Routes/>
                <ReadinessComponent/>
            </div>
        );
    }
}

function Wybierz() {
    function isEmpty(value){
        return (value == null || value.length === 0);
    }
    // TODO: Ma być możliwość wyboru jaką z ról które mamy chcemy widzieć tzn mamy się móc przełączać między rolami
    //  Nie ma tego narazie więc jest tak
    let levels = Cookies.get(RolesCookieName);
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
