import React, {Fragment, Suspense} from "react";
import "./PlanAppointment.css";
import {Accordion, Button, Card, Col, Container, Row} from "react-bootstrap";
import {withTranslation} from "react-i18next";
import {makePatientsListRequest} from "../ListPatients/ListPatientsRequest";
import {Input} from "semantic-ui-react";
import BootstrapTable from "react-bootstrap-table-next";
import {planAppointmentRequest} from "./PlanAppointmentRequest";

class PlanAppointmentWithoutTranslation extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            patientsList: [],
            appointmentList: [],
            chosenAccount: ""
        };
        this.unFilteredList = []
    }

    componentDidMount() {
        this.makeGetPatientRequest();
    }

    makeGetPatientRequest() {
        makePatientsListRequest().then((response) => {
            this.unFilteredList = response
            this.setState({patientsList: this.unFilteredList})
        })
    }

    filterList(input) {
        let tempList = []
        for (let i in this.unFilteredList) {
            if (this.unFilteredList[i].name.toUpperCase().includes(input.toUpperCase())) {
                tempList.push(this.unFilteredList[i])
            }
        }
        this.setState({patientsList: tempList})
    }

    getHintList(list) {
        let tempList = [];
        let uniqueList = [...new Set(this.state.patientsList.map(a => a.name))];
        for (let i in uniqueList) {
            tempList.push(<option key={i} value={uniqueList[i]}>{uniqueList[i]}</option>);
        }
        return tempList;

    }

    renderNull() {
        const {t} = this.props;
        return <div>{t('Loading')}</div>
    }

    renderAccounts() {
        const {t} = this.props;
        const selectRow = {
            mode: 'radio',
            clickToSelect: true,
            hideSelectColumn: true,
            onSelect:(var1,var2,rowIndex)=>{
                this.setState({chosenAccount: this.state.patientsList[rowIndex]});
                console.log(this.state.patientsList[rowIndex])
            },
            bgColor: "rgba(192, 255, 0, 0.4)",
        };
        const columns = [

            {
                dataField: 'login',
                text: t('UserLogin'),
                style: {verticalAlign: "middle"}
            },
            {
                dataField: 'name',
                text: t('Name and Surname'),
                style: {verticalAlign: "middle"}
            }
        ]
        return <BootstrapTable striped keyField='login' columns={columns} data={this.state.patientsList} selectRow={ selectRow }/>;
    }

    setChosenAccount(rowIndex) {
        this.setState({chosenAccount: this.state.patientsList[rowIndex]})
    }

    handleSubmit(t) {
        planAppointmentRequest(7,t);
    }

    render() {
        const {t} = this.props;
        return (
            <div className="MyAppointment">
                <form  onSubmit={()=>this.handleSubmit(t)}>
                    <Button size={"lg"} type="submit">
                        book
                    </Button>
                    <Row>
                        <Col>
                            <Accordion>
                                <Card className="Card">
                                    <Card.Header style={{width: "100%"}}>
                                        <Accordion.Toggle as={Button} variant="link" eventKey="0">
                                            <p className="Buttons">17.08.2021 13:45</p>
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
                        </Col>
                        <Col>
                            <Fragment>
                                <datalist id='options'>
                                    {this.state.patientsList.length !== this.unFilteredList.length ? this.getHintList() : []}
                                </datalist>
                                <div className="AccountListGroup">
                                    <Input list='options' id="ListFilter"
                                           onChange={e => this.filterList(e.target.value)}
                                           placeholder={t("Filter")}/>
                                    {!this.state.patientsList.length ? this.renderNull() : this.renderAccounts()}
                                </div>
                            </Fragment>
                        </Col>
                    </Row>
                </form>
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
