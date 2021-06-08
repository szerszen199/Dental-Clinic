import React from "react";
import {Card, CardDeck} from "react-bootstrap";
import tooth from "../../assets/tooth.png"
import "./Dashboard.css";
import {useTranslation} from "react-i18next";


export default function Dashboard() {
    const {t} = useTranslation();
    document.title = t("Dental Clinic");
    return (
        <div className="Dashboard">
            <CardDeck>
                <Card>
                    <Card.Img variant="top" src={tooth} style={{width: "30px"}}/>
                    <Card.Body>
                        <Card.Title>{t("Nearest appointments")}</Card.Title>
                        <Card.Text>
                            {t("Sample Text")}
                        </Card.Text>
                    </Card.Body>
                    <Card.Footer>
                        {/*<small className="text-muted">Last updated 3 mins ago</small>*/}
                    </Card.Footer>
                </Card>
                <Card>
                    <Card.Img variant="top" src={tooth} style={{width: "30px"}}/>
                    <Card.Body>
                        <Card.Title>{t("Prescriptions")}</Card.Title>
                        <Card.Text>
                            {t("Sample Text")}
                        </Card.Text>
                    </Card.Body>
                    <Card.Footer>
                        {/*<small className="text-muted">Last updated 3 mins ago</small>*/}
                    </Card.Footer>
                </Card>
                <Card>
                    <Card.Img variant="top" src={tooth} style={{width: "30px"}}/>
                    <Card.Body>
                        <Card.Title>{t("Account")}</Card.Title>
                        <Card.Text>
                            {t("Sample Text")}
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
