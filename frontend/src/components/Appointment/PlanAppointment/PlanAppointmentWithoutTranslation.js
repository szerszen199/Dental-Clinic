import React, {Suspense} from "react";
import "./PlanAppointment.css";
import {Accordion, Button, Card, Col, Container, Row} from "react-bootstrap";
import {withTranslation} from "react-i18next";
import Admin from "../../../views/Users/Admin/Admin";
import Patient from "../../../views/Users/Patient/Patient";
import Receptionist from "../../../views/Users/Receptionist/Receptionist";
import Doctor from "../../../views/Users/Doctor/Doctor";
import {makeDoctorsListRequest} from "../ListDoctors/DoctorListRequest";
import Rating from "@material-ui/lab/Rating";
import {makePatientsListRequest} from "../ListPatients/ListPatientsRequest";

class PlanAppointmentWithoutTranslation extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            doctorsList: [],
            patientsList: []
        };
    }

    componentDidMount() {
        this.makeGetDoctorsRequest();
        this.makeGetPatientRequest();
    }

    makeGetDoctorsRequest() {
        makeDoctorsListRequest().then((response) => {
            this.setState({doctorsList: response})
        })
    }
    makeGetPatientRequest() {
        makePatientsListRequest().then((response) => {
            this.setState({patientsList: response})
        })
    }
    renderNull() {
        const {t} = this.props;
        return <div>{t('Loading')}</div>
    }

    renderDoctors() {
        const {t} = this.props;
        return this.state.doctorsList.map((doctor, index) => (
            <Card className="Card">
                <Card.Body style={{width: "100%"}}>
                    <Container style={{width: "100%"}}>
                        <Row style={{width: "100%"}}>
                            <Col><p className="Buttons">{doctor.name}</p></Col>
                            <Col style={{maxWidth: "100px"}}>
                                <Rating name="half-rating-read" defaultValue={0.0} value={doctor.rate}
                                        precision={0.1} readOnly/>
                            </Col>
                        </Row>
                    </Container>
                </Card.Body>
            </Card>
        ));
    }

    render() {
        const {t} = this.props;

        return (
            <div className="MyAppointment">
                <div className="ListDoctors">
                    {!this.state.doctorsList.length ? this.renderNull() : this.renderDoctors()}
                </div>
                <Accordion>
                    <Card className="Card">
                            <Card.Header style={{width: "100%"}}>
                                <Accordion.Toggle as={Button} variant="link" eventKey="0">
                                    <p className="Buttons">17.08.2021 13:45</p>
                                </Accordion.Toggle>
                            </Card.Header>
                            <Accordion.Collapse eventKey="0">
                                <Card.Body>
                                    <Container style={{width: "155%"}}>
                                        <Row style={{width: "100%"}}>
                                            <Col>
                                                <p>Address: Politechniki 1 Lodz</p>
                                            </Col>
                                            <Col style={{maxWidth: "100px"}}>
                                                <Button
                                                    type="submit">
                                                    Schedule
                                                </Button>
                                            </Col>
                                        </Row>
                                    </Container>
                                </Card.Body>
                            </Accordion.Collapse>
                    </Card>
                    <Card className="Card">
                        <Card.Header>
                            <Accordion.Toggle as={Button} variant="link" eventKey="1">
                                22.10.2021 18:00 dr.Lekarz
                            </Accordion.Toggle>
                        </Card.Header>
                        <Accordion.Collapse eventKey="1">
                            <Card.Body>
                                <Container style={{width: "100%"}}>
                                    <Row style={{width: "100%"}}>
                                        <Col>
                                            <p>Address: Politechniki 2 Lodz</p>
                                        </Col>
                                        <Col style={{maxWidth: "100px"}}>
                                            <Button
                                                block size="sm"
                                                type="submit">
                                                Schedule
                                            </Button>
                                        </Col>
                                    </Row>
                                </Container>
                            </Card.Body>
                        </Accordion.Collapse>
                    </Card>
                </Accordion>
            </div>
        );
    }
}

const PlanAppointmentTr = withTranslation()(PlanAppointmentWithoutTranslation)

export default function PlanAppointment(props) {
    return (
        <Suspense fallback="loading">
            <PlanAppointmentTr account={props.account} id={props.id}/>
        </Suspense>
    );
}

function CurrentUserViewComponent(value) {
    const myMap = new Map();
    myMap.set(Admin())
    myMap.set(Patient())
    myMap.set(Receptionist())
    myMap.set(Doctor())
    return myMap
}
