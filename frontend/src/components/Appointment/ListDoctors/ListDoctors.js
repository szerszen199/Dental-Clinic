import React, {Fragment, Suspense} from "react";
import "./ListDoctors.css";
import {Button, Card, Col, Container, Row} from "react-bootstrap";
import Rating from '@material-ui/lab/Rating';
import {makeDoctorsListRequest} from "./DoctorListRequest";
import {withTranslation} from "react-i18next";
import {FiRefreshCw} from "react-icons/fi";
import {Box} from "@material-ui/core";
import {Alert, AlertTitle} from "@material-ui/lab";

class DoctorsList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            doctorsList: [],
            requestWasMade: false
        };
    }

    componentDidMount() {
        this.makeGetDoctorsRequest();
    }

    makeGetDoctorsRequest() {
        const {t} = this.props;
        makeDoctorsListRequest(t)
            .then((response) => {
                this.setState({
                    doctorsList: response,
                    requestWasMade: true
                })
            });
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
                            <Col style={{maxWidth: "275px", textAlign: "right"}}>
                                {doctor.ratesCounter ?
                                    <Row><Rating name="half-rating-read" defaultValue={0.0} value={doctor.rate}
                                                 precision={0.1} readOnly/>
                                        <Box marginLeft={3}>{t('Rates counter')}: {doctor.ratesCounter}</Box></Row> :
                                    <Box>{t('No rates')}</Box>}
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
        return ((!this.state.doctorsList.length) && (this.state.requestWasMade === true)) ?
            <Alert severity="error">
                <AlertTitle>{t('Sorry')}</AlertTitle>
                {t('No doctors')}
            </Alert>
            : <Fragment>
                <div className="account-refresh-button-div">
                    {this.renderButton()}
                </div>
                <div className="ListDoctors">
                    {!this.state.requestWasMade ? this.renderNull() : this.renderDoctors()}
                </div>
            </Fragment>;
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
