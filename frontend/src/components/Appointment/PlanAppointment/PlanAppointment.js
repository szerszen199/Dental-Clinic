import React from "react";
import "./PlanAppointment.css";
import {Accordion, Button, Card, Col, Container, Fade, Modal, Row} from "react-bootstrap";
import {Alert, AlertTitle} from "@material-ui/lab";
import swal from 'sweetalert';
import {green} from "@material-ui/core/colors";

function PlanAppointment() {
    const [modalShow, setModalShow] = React.useState(false);

    function MyVerticallyCenteredModal(props) {
        return (
            <Modal
                {...props}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                background-color="red"
            >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Modal heading
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <h4>Centered Modal</h4>
                    <p>
                        Cras mattis consectetur purus sit amet fermentum. Cras justo odio,
                        dapibus ac facilisis in, egestas eget quam. Morbi leo risus, porta ac
                        consectetur ac, vestibulum at eros.
                    </p>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={props.onHide}>Close</Button>
                </Modal.Footer>
            </Modal>
        );
    }

    function ok(){
        swal({
            closeOnClickOutside: false,
            text: "Hello world!",
            icon: "success",
            button: {
                text: "OK",
                value: true,
                visible: true,
                closeModal: true,
            },
            closeOnEsc: false,
        });
    }

    return (
        <div className="MyAppointment">

            <Button variant="primary" onClick={() => ok()}>
                Launch vertically centered modal
            </Button>

            {/*<MyVerticallyCenteredModal*/}
            {/*    show={modalShow}*/}
            {/*    onHide={() => setModalShow(false)}*/}
            {/*/>*/}

            <Alert severity="info">
                <AlertTitle>Info</AlertTitle>
                This is an info alert — <strong>check it out!</strong>
            </Alert>


            <Accordion>
                <Card className="Card">
                    <Card.Header style={{width: "100%"}}>
                        <Accordion.Toggle as={Button} variant="link" eventKey="0">
                            <p className="Buttons">17.08.2021 13:45 dr.Doktor</p>
                        </Accordion.Toggle>
                    </Card.Header>
                    <Accordion.Collapse eventKey="0">
                        <Card.Body>
                            <Container style={{width: "100%"}}>
                                <Row style={{width: "100%"}}>
                                    <Col>
                                        <p>Address: Politechniki 1 Lodz</p>
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

export default PlanAppointment;
