import React, {Suspense} from "react";
import "./ListDoctors.css";
import {Accordion, Button, Card, Col, Container, Row} from "react-bootstrap";
import Rating from '@material-ui/lab/Rating';
import {makeDoctorsListRequest} from "./DoctorListRequest";
import {Fragment} from 'react';
import {withTranslation} from "react-i18next";
import {FiRefreshCw} from "react-icons/fi";

class DoctorsList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            doctorsList: []
        };
    }

    componentDidMount() {
        this.makeGetDoctorsRequest();
    }

    makeGetDoctorsRequest() {
        makeDoctorsListRequest().then((response) => {
            this.setState({doctorsList: response})
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

    renderButton() {
        let self = this;
        return <Button variant={"secondary"} onClick={() => {
            this.makeGetDoctorsRequest(self)
        }}>
            <FiRefreshCw/>
        </Button>
    }

    render() {
        const {t} = this.props;
        document.title = t("Dental Clinic") + " - " + t("List of doctors");
        return (<Fragment>
            <div className="account-refresh-button-div">
                {this.renderButton()}
            </div>
            <div className="ListDoctors">
                {!this.state.doctorsList.length ? this.renderNull() : this.renderDoctors()}
            </div>
        </Fragment>);
    }
}

const DoctorsListTr = withTranslation()(DoctorsList);

export default function ListDoctors() {
    return (
        <Suspense fallback="loading">
            <DoctorsListTr/>
        </Suspense>
    );
}

// function ListDoctors() {
//     return (
//         <div className="ListDoctors">
//             console.log(doctors.list);
//             {/*<Accordion>*/}
//             <Card className="Card">
//                 <Card.Body style={{width: "100%"}}>
//                     {/*<Accordion.Toggle as={"text"} variant="link" eventKey="0">*/}
//                     <Container style={{width: "100%"}}>
//                         <Row style={{width: "100%"}}>
//                             <Col><p className="Buttons">dr.Doktor</p></Col>
//                             <Col style={{maxWidth: "100px"}}>
//                                 <Rating name="half-rating-read" defaultValue={3.7} precision={0.1} readOnly/>
//                             </Col>
//                         </Row>
//                     </Container>
//                     {/*</Accordion.Toggle>*/}
//                 </Card.Body>
//             </Card>
//             <Card className="Card">
//                 <Card.Body style={{width: "100%"}}>
//                     {/*<Accordion.Toggle as={"text"} variant="link" eventKey="0">*/}
//                     <Container style={{width: "100%"}}>
//                         <Row style={{width: "100%"}}>
//                             <Col><p className="Buttons">dr.Doktor</p></Col>
//                             <Col style={{maxWidth: "100px"}}>
//                                 <Rating name="half-rating-read" defaultValue={3.7} precision={0.1} readOnly/>
//                             </Col>
//                         </Row>
//                     </Container>
//                     {/*</Accordion.Toggle>*/}
//                 </Card.Body>
//             </Card>
//             {/*</Accordion>*/}
//         </div>
//     );
// }
//
// export default ListDoctors;
