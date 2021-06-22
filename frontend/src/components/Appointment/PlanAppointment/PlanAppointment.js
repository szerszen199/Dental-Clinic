import React from "react";
import "./PlanAppointment.css";
import {Accordion, Button, Card, Col, Container, Row} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import {AppointmentSlot} from "../AppointmentSlot";

class PlanAppointment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            accountsList: []
        };
        this.unFilteredList = []
        this.testList = [
            new AppointmentSlot(1,"teraz","Jan Sobieski"),
            new AppointmentSlot(2,"jutro","Janusz Tracz"),
            new AppointmentSlot(3,"nigdy","Świnka Peppa")
        ]

    }

    renderAppointments(){
        const columns = [
            {
                dataField: 'id',
                text: 'ID',
                style: {verticalAlign: "middle"},
                sort: true
            },
            {
                dataField: 'date',
                text: 'Data',
                style: {verticalAlign: "middle"},
                sort: true
            },
            {
                dataField: 'doctor',
                text: 'Lekarz',
                style: {verticalAlign: "middle"},
                sort: true
            }
        ]
        const selectRow = {
            mode: 'radio',
            clickToSelect: true,
            clickToExpand: true,
            hideSelectColumn: true,
            bgColor: '#00BFFF'
        };

        const expandRow = {
            showExpandColumn: false,
            onlyOneExpanding: true,
            renderer: row => (
                <div>
                    <p>{ `Tu moze isc przycisk edycji/wyboru/idk, kolumna  ${row.doctor}` }</p>
                    <p>You can render anything here, also you can add additional data on every row object</p>
                    <p>expandRow.renderer callback will pass the origin row object to you</p>
                </div>
            )
        };

        return <BootstrapTable striped keyField='id' columns={columns} data={this.testList} selectRow={ selectRow }   expandRow={ expandRow } />;
    }

    render() {
        return (
            <div className="MyAppointment">
                {/*<Accordion>*/}
                {/*    <Card className="Card">*/}
                {/*        <Card.Header style={{width: "100%"}}>*/}
                {/*            <Accordion.Toggle as={Button} variant="link" eventKey="0">*/}
                {/*                <p className="Buttons">17.08.2021 13:45 dr.Doktor</p>*/}
                {/*            </Accordion.Toggle>*/}
                {/*        </Card.Header>*/}
                {/*        <Accordion.Collapse eventKey="0">*/}
                {/*            <Card.Body>*/}
                {/*                <Container style={{width: "100%"}}>*/}
                {/*                    <Row style={{width: "100%"}}>*/}
                {/*                        <Col>*/}
                {/*                            <p>Address: Politechniki 1 Lodz</p>*/}
                {/*                        </Col>*/}
                {/*                        <Col style={{maxWidth: "100px"}}>*/}
                {/*                            <Button*/}
                {/*                                block size="sm"*/}
                {/*                                type="submit">*/}
                {/*                                Schedule*/}
                {/*                            </Button>*/}
                {/*                        </Col>*/}
                {/*                    </Row>*/}
                {/*                </Container>*/}
                {/*            </Card.Body>*/}
                {/*        </Accordion.Collapse>*/}
                {/*    </Card>*/}
                {/*    <Card className="Card">*/}
                {/*        <Card.Header>*/}
                {/*            <Accordion.Toggle as={Button} variant="link" eventKey="1">*/}
                {/*                22.10.2021 18:00 dr Lekarz*/}
                {/*            </Accordion.Toggle>*/}
                {/*        </Card.Header>*/}
                {/*        <Accordion.Collapse eventKey="1">*/}
                {/*            <Card.Body>*/}
                {/*                <Container style={{width: "100%"}}>*/}
                {/*                    <Row style={{width: "100%"}}>*/}
                {/*                        <Col>*/}
                {/*                            <p>Address: Politechniki 2 Lodz</p>*/}
                {/*                        </Col>*/}
                {/*                        <Col style={{maxWidth: "100px"}}>*/}
                {/*                            <Button*/}
                {/*                                block size="sm"*/}
                {/*                                type="submit">*/}
                {/*                                Schedule*/}
                {/*                            </Button>*/}
                {/*                        </Col>*/}
                {/*                    </Row>*/}
                {/*                </Container>*/}
                {/*            </Card.Body>*/}
                {/*        </Accordion.Collapse>*/}
                {/*    </Card>*/}
                {/*</Accordion>*/}

                {this.renderAppointments()}
            </div>
        );
    }
}

export default PlanAppointment;
