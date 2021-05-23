import React from "react";
import "./ListDoctors.css";
import {Accordion, Card, Col, Container, Row} from "react-bootstrap";
import Rating from '@material-ui/lab/Rating';

function ListDoctors() {
    return (
        <div className="ListDoctors">
            <Accordion>
                <Card className="Card">
                    <Card.Header style={{width: "100%"}}>
                        <Accordion.Toggle as={"text"} variant="link" eventKey="0">
                            <Container style={{width: "100%"}}>
                                <Row style={{width: "100%"}}>
                                    <Col><p className="Buttons">dr.Doktor</p></Col>
                                    <Col style={{maxWidth: "100px"}}>
                                        <Rating name="half-rating-read" defaultValue={4.7} precision={0.1} readOnly/>
                                    </Col>
                                </Row>
                            </Container>
                        </Accordion.Toggle>
                    </Card.Header>
                </Card>
                <Card className="Card">
                    <Card.Header style={{width: "100%"}}>
                        <Accordion.Toggle as={"text"} variant="link" eventKey="1">
                            <Container style={{width: "100%"}}>
                                <Row style={{width: "100%"}}>
                                    <Col><p className="Buttons">dr.Lekarz</p></Col>
                                    <Col style={{maxWidth: "100px"}}>
                                        <Rating name="half-rating-read" defaultValue={3.4} precision={0.1} readOnly/>
                                    </Col>
                                </Row>
                            </Container>
                        </Accordion.Toggle>
                    </Card.Header>
                </Card>
            </Accordion>
        </div>
    );
}

export default ListDoctors;
