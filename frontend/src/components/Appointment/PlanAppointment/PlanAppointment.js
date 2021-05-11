import React from "react";
import "./PlanAppointment.css";
import {Accordion, Card, Button, Container, Row, Col} from "react-bootstrap";

function PlanAppointment() {
    return (
        <div className="MyAppointment">
            <Accordion>
                <Card className="Card">
                    <Card.Header style={{width: "100%"}}>
                        <Accordion.Toggle as={Button} variant="link" eventKey="0">
                            <a className="Buttons">17.08.2021 13:45  dr.Doktor</a>
                        </Accordion.Toggle>
                    </Card.Header>
                    <Accordion.Collapse eventKey="0">
                        <Card.Body>
                            <Container style={{width: "100%"}}>
                                <Row style={{width: "100%"}}>
                                    <Col>
                                        <a>Address: Politechniki 1 Lodz</a>
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
                                        <a>Address: Politechniki 2 Lodz</a>
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

export default PlanAppointment;
