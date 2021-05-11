import React from "react";
import "./Prescription.css";
import {Accordion, Card, Button} from "react-bootstrap";

function Prescription() {
    return (
        <div className="Prescription">
            <Accordion>
                <Card className="Card">
                    <Card.Header>
                        <Accordion.Toggle as={Button} variant="link" eventKey="0">
                            Aspirin
                        </Accordion.Toggle>
                    </Card.Header>
                    <Accordion.Collapse eventKey="0">
                        <Card.Body>Aspirin - 1 pack, 1 pill every morning</Card.Body>
                    </Accordion.Collapse>
                </Card>
                <Card className="Card">
                    <Card.Header>
                        <Accordion.Toggle as={Button} variant="link" eventKey="1">
                            Ibuprom
                        </Accordion.Toggle>
                    </Card.Header>
                    <Accordion.Collapse eventKey="1">
                        <Card.Body>Ibuprom - 2 packs, 2 pills everyday</Card.Body>
                    </Accordion.Collapse>
                </Card>
            </Accordion>
        </div>
    );
}

export default Prescription;
