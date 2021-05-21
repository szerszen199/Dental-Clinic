import React, {useState} from "react";
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
import {MDBContainer, MDBFooter} from "mdbreact";
import './MainView.css';

const accessLevelDictionary = {
    "Guest": "rgba(1, 1, 1, 0.1)",
    "Patient": "rgba(93, 188, 242, 0.2)",
    "Receptionist": "rgba(192, 255, 0, 0.4)",
    "Doctor": "rgba(255, 216, 0, 0.2)",
    "Admin": "rgba(238, 0, 0, 0.1)",
};
var actualAccessLevel = "Doctor";

function MainView() {
    const [isDarkMode, setIsDarkMode] = useState(() => false);
    const urlPL = "https://img.icons8.com/color/96/000000/poland-circular.png"
    const urlEN = "https://img.icons8.com/color/48/000000/great-britain-circular.png"
    const [language, setLanguage] = useState(() => "PL");
    const [flag, setFlag] = useState(() => urlEN);

    function handleOnClick() {
        if (language === "EN") {
            setPL()
        } else {
            setEN()
        }
    }

    function setEN() {
        setLanguage("EN");
        setFlag(urlPL);
    }

    function setPL() {
        setLanguage("PL");
        setFlag(urlEN);
    }

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
                                        checked={isDarkMode}
                                        onChange={setIsDarkMode}
                                        size={30}
                                        sunColor={"#FFDF37"}
                                        moonColor={"#bfbfbb"}
                                    />
                                    <img onClick={handleOnClick} style={{marginLeft: "10px", maxWidth: "30px"}}
                                         src={flag} alt="Logo"/>

                                </Navbar.Collapse>
                            </Col>
                        </Row>
                        <Row> <Col> <BreadCrumbs/> </Col></Row>
                    </Container>
                </div>
            </Navbar>

            <MDBFooter color="blue" className="font-small pt-4 mt-4" id="footer">
                       {/*// style={{*/}
                       {/*//     position: "fixed",*/}
                       {/*//     bottom: 0,*/}
                       {/*//     minWidth: "100%",*/}
                       {/*// }}>*/}
                <div className="footer-copyright text-left py-3" style={{minWidth: "100%"}}>
                    <MDBContainer fluid>
                        Klinika Stomatologiczna, &copy; {new Date().getFullYear()} Copyright by 2021SSBD01
                    </MDBContainer>
                </div>
            </MDBFooter>


            <Routes/>
            <ReadinessComponent/>
        </div>
    );

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

export default MainView;
