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
import {userRolesStorageName} from "../../components/Login/LoginRequest";
import Doctor from "../Doctor/Doctor";

const accessLevelDictionary = {
    "Guest": "rgba(1, 1, 1, 0.1)",
    "Patient": "rgba(93, 188, 242, 0.2)",
    "Receptionist": "rgba(192, 255, 0, 0.4)",
    "Doctor": "rgba(255, 216, 0, 0.2)",
    "Admin": "rgba(238, 0, 0, 0.1)",
};
let actualAccessLevel = "Doctor";

export default class MainView extends React.Component {
    urlPL = "https://img.icons8.com/color/96/000000/poland-circular.png";
    urlEN = "https://img.icons8.com/color/48/000000/great-britain-circular.png";

    constructor(props) {
        super(props);
        this.state = {
            isDarkMode: false,
            language: "PL",
            flag: this.urlEN
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
                            <Row> <Col> <BreadCrumbs/> </Col></Row>
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
    // TODO: Ma być możliwość wyboru jaką z ról które mamy chcemy widzieć tzn mamy się móc przełączać między rolami
    //  Nie ma tego narazie więc jest tak
    let levels = localStorage.getItem(userRolesStorageName) == null ? [] : localStorage.getItem(userRolesStorageName);
    if (levels.includes("level.administrator")) {
        return Admin();
    } else if (levels.includes("level.patient")) {
        return Patient();
    } else if (levels.includes("level.receptionist")) {
        return Receptionist();
    } else if (levels.includes("level.doctor")) {
        return Doctor();
    }
    return Guest();
}
