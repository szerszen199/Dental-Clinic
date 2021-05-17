import React from "react";
import {Card, CardDeck} from "react-bootstrap";
import tooth from "../../assets/tooth.png"
import "./Dashboard.css";


export default function Dashboard() {
    return (
        <div className="Dashboard">
            <CardDeck>
                <Card>
                    <Card.Img variant="top" src={tooth} style={{width: "30px"}}/>
                    <Card.Body>
                        <Card.Title>Nearest appointments</Card.Title>
                        <Card.Text>
                            This is a wider card with supporting text below as a natural lead-in to
                            additional content. This content is a little bit longer.
                        </Card.Text>
                    </Card.Body>
                    <Card.Footer>
                        {/*<small className="text-muted">Last updated 3 mins ago</small>*/}
                    </Card.Footer>
                </Card>
                <Card>
                    <Card.Img variant="top" src={tooth} style={{width: "30px"}}/>
                    <Card.Body>
                        <Card.Title>Prescriptions</Card.Title>
                        <Card.Text>
                            This card has supporting text below as a natural lead-in to additional
                            content.{' '}
                        </Card.Text>
                    </Card.Body>
                    <Card.Footer>
                        {/*<small className="text-muted">Last updated 3 mins ago</small>*/}
                    </Card.Footer>
                </Card>
                <Card>
                    <Card.Img variant="top" src={tooth} style={{width: "30px"}}/>
                    <Card.Body>
                        <Card.Title>Account</Card.Title>
                        <Card.Text>
                            This is a wider card with supporting text below as a natural lead-in to
                            additional content. This card has even longer content than the first to
                            show that equal height action.
                        </Card.Text>
                    </Card.Body>
                    <Card.Footer>
                        {/*<small className="text-muted">Last updated 3 mins ago</small>*/}
                    </Card.Footer>
                </Card>
            </CardDeck>
        </div>
    );
}
